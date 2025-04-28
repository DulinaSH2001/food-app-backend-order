package com.order.order.Controller;

import com.order.order.Dto.PaymentDTO;
import com.order.order.Model.Payment;
import com.order.order.Service.Impl.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:8080/")

public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> processPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        log.info("Processing payment for order: {}", paymentDTO.getOrderId());
        return new ResponseEntity<>(paymentService.processPayment(paymentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long paymentId) {
        log.info("Getting payment: {}", paymentId);
        return ResponseEntity.ok(paymentService.getPayment(paymentId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Payment>> getPaymentsByOrder(@PathVariable Long orderId) {
        log.info("Getting payments for order: {}", orderId);
        return ResponseEntity.ok(paymentService.getPaymentsByOrder(orderId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Payment>> getPaymentsByCustomer(@PathVariable Long customerId) {
        log.info("Getting payments for customer: {}", customerId);
        return ResponseEntity.ok(paymentService.getPaymentsByCustomer(customerId));
    }

    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<Payment> refundPayment(@PathVariable Long paymentId) {
        log.info("Processing refund for payment: {}", paymentId);
        return ResponseEntity.ok(paymentService.refundPayment(paymentId));
    }

    @GetMapping("/{paymentId}/status")
    public ResponseEntity<Boolean> isPaymentSuccessful(@PathVariable Long paymentId) {
        log.info("Checking payment status for: {}", paymentId);
        return ResponseEntity.ok(paymentService.isPaymentSuccessful(paymentId));
    }
} 