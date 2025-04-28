package com.order.order.Util;

import com.order.order.Model.Location;

public class DeliveryCalculator {
    private static final double EARTH_RADIUS = 6371; // Earth's radius in kilometers

    public static double calculateDistance(Location location1, Location location2) {
        double lat1 = Math.toRadians(location1.getLatitude());
        double lon1 = Math.toRadians(location1.getLongitude());
        double lat2 = Math.toRadians(location2.getLatitude());
        double lon2 = Math.toRadians(location2.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    public static double calculateDeliveryCharges(double distance) {
        // Base delivery charge for first 3 km
        double baseCharge = 10.0;
        
        // Additional charge for each extra kilometer
        if (distance <= 3.0) {
            return baseCharge;
        } else {
            double extraKm = Math.ceil(distance - 3.0);
            return baseCharge + (extraKm * 2.0); // 2 currency units per extra km
        }
    }
} 