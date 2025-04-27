package com.order.order.Service;

import com.order.order.Dto.PaymentDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StripeServiceImpl implements StripeService {

    @Override
    public String createPaymentIntent(PaymentDTO paymentDTO) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long) (paymentDTO.getAmount() * 100)) // Convert to cents
                .setCurrency("usd")
                .setConfirm(true)
                .setPaymentMethodData(
                        PaymentIntentCreateParams.PaymentMethodData.builder()
                                .putExtraParam("type", "card")
                                .putExtraParam("card[token]", paymentDTO.getPaymentToken())
                                .build()
                )
                .setReturnUrl("http://localhost:8081/api/v1/payments/confirm")
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        log.info("Created payment intent: {}", paymentIntent.getId());
        return paymentIntent.getId();
    }
    

    @Override
    public void refundPayment(String paymentIntentId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        paymentIntent.cancel();
        log.info("Refunded payment: {}", paymentIntentId);
    }

    @Override
    public boolean confirmPayment(String paymentIntentId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        log.info("Payment status: {}", paymentIntent.getStatus());
        return "succeeded".equals(paymentIntent.getStatus());
    }
} 