package com.order.order.Model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDetails {
    private String deliveryAddress;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitude", column = @Column(name = "delivery_latitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "delivery_longitude"))
    })
    private Location deliveryLocation;
    
    private String restaurantAddress;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitude", column = @Column(name = "restaurant_latitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "restaurant_longitude"))
    })
    private Location restaurantLocation;
    
    private String deliveryInstructions;
} 