package com.order.order.Dto;

import com.order.order.Model.Location;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRequestDTO {
    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;

    @NotNull(message = "Delivery location is required")
    @Valid
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitude", column = @Column(name = "delivery_latitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "delivery_longitude"))
    })
    private Location deliveryLocation;

    @NotBlank(message = "Restaurant address is required")
    private String restaurantAddress;

    @NotNull(message = "Restaurant location is required")
    @Valid
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitude", column = @Column(name = "restaurant_latitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "restaurant_longitude"))
    })
    private Location restaurantLocation;

    @NotBlank(message = "Delivery instructions are required")
    private String deliveryInstructions;
} 