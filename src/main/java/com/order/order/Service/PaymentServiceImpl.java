package com.order.order.Service;

import com.order.order.Dto.PaymentDTO;
import com.order.order.Model.OrderStatus;
import com.order.order.Model.Payment;
import com.order.order.Model.PaymentStatus;
import com.order.order.Repository.PaymentRepository;
import com.order.order.Service.Impl.OrderService;
import com.order.order.Service.Impl.PaymentService;
import com.order.order.Service.Impl.StripeService;
import com.order.order.exception.PaymentNotFoundException;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final StripeService stripeService;

    @Override
    public Payment processPayment(PaymentDTO paymentDTO) {
        // Create payment record
        Payment payment = Payment.builder()
                .customerId(paymentDTO.getCustomerId())
                .orderId(paymentDTO.getOrderId())
                .amount(paymentDTO.getAmount())
                .paymentMethod(paymentDTO.getPaymentMethod())
                .status(Payment.PaymentStatus.PENDING)
                .paymentTime(LocalDateTime.now())
                .build();

        try {
           
            String paymentIntentId = stripeService.createPaymentIntent(paymentDTO);
            payment.setTransactionId(paymentIntentId);

            
            boolean paymentSuccessful = stripeService.confirmPayment(paymentIntentId);

            if (paymentSuccessful) {
                payment.setStatus(Payment.PaymentStatus.COMPLETED);
              
                orderService.updatePaymentStatus(paymentDTO.getOrderId(), PaymentStatus.COMPLETED, paymentIntentId);
            } else {
                payment.setStatus(Payment.PaymentStatus.FAILED);
                payment.setFailureReason("Payment processing failed");
            }
        } catch (StripeException e) {
            payment.setStatus(Payment.PaymentStatus.FAILED);
            payment.setFailureReason(e.getMessage());
            log.error("Stripe payment processing failed", e);
        }

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + paymentId));
    }

    @Override
    public List<Payment> getPaymentsByOrder(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    @Override
    public List<Payment> getPaymentsByCustomer(Long customerId) {
        return paymentRepository.findByCustomerId(customerId);
    }

    @Override
    public Payment refundPayment(Long paymentId) {
        Payment payment = getPayment(paymentId);
        
        if (payment.getStatus() != Payment.PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Only completed payments can be refunded");
        }

        try {
            // Process refund through Stripe
            stripeService.refundPayment(payment.getTransactionId());
            payment.setStatus(Payment.PaymentStatus.REFUNDED);
            // Update order status to CANCELLED
            orderService.updateOrderStatus(payment.getOrderId(), OrderStatus.CANCELLED);
        } catch (StripeException e) {
            log.error("Stripe refund processing failed", e);
            throw new RuntimeException("Failed to process refund", e);
        }

        return paymentRepository.save(payment);
    }

    @Override
    public boolean isPaymentSuccessful(Long paymentId) {
        Payment payment = getPayment(paymentId);
        return payment.getStatus() == Payment.PaymentStatus.COMPLETED;
    }
} 