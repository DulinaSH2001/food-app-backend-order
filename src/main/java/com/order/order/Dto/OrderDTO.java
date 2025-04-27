package com.order.order.Dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long customerId;
    private Long restaurantId;
    private List<OrderItemDTO> orderItems;
    private String status;
}
