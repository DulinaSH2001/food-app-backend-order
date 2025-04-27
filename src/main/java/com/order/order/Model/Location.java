package com.order.order.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Column(name = "loc_latitude")
    private double latitude;
    
    @Column(name = "loc_longitude")
    private double longitude;

    public static Location fromString(String coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates cannot be null");
        }
        
        // Remove any whitespace and parentheses
        String cleanCoords = coordinates.trim().replaceAll("[()\\s]", "");
        
        // Split by comma
        String[] parts = cleanCoords.split(",");
        
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid coordinates format. Expected: latitude,longitude or (latitude,longitude)");
        }
        
        try {
            double lat = Double.parseDouble(parts[0]);
            double lon = Double.parseDouble(parts[1]);
            
            // Basic validation
            if (lat < -90 || lat > 90) {
                throw new IllegalArgumentException("Latitude must be between -90 and 90");
            }
            if (lon < -180 || lon > 180) {
                throw new IllegalArgumentException("Longitude must be between -180 and 180");
            }
            
            return new Location(lat, lon);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in coordinates");
        }
    }

    @Override
    public String toString() {
        return String.format("(%f,%f)", latitude, longitude);
    }
} 