package com.order.order.Service.Impl;

import com.order.order.Dto.PaymentDTO;
import com.order.order.Model.Payment;

import java.util.List;

public interface PaymentService {
    Payment processPayment(PaymentDTO paymentDTO);
    Payment getPayment(Long paymentId);
    List<Payment> getPaymentsByOrder(Long orderId);
    List<Payment> getPaymentsByCustomer(Long customerId);
    Payment refundPayment(Long paymentId);
    boolean isPaymentSuccessful(Long paymentId);
} 