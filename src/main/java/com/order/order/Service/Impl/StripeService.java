package com.order.order.Service.Impl;

import com.order.order.Dto.PaymentDTO;
import com.stripe.exception.StripeException;

public interface StripeService {
    String createPaymentIntent(PaymentDTO paymentDTO) throws StripeException;
    void refundPayment(String paymentIntentId) throws StripeException;
    boolean confirmPayment(String paymentIntentId) throws StripeException;
} 