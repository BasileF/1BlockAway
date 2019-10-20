package com.learning.a1blockaway;

public class Haversine {
    private final static double RADIUS_OF_EARTH = 6371;
    public static boolean isWithinDistance(double uLatitude, double uLongitude, double rLatitude, double rLongitude){
        double latDistance = Math.toRadians(uLatitude - rLatitude);
        double longDistance = Math.toRadians(uLongitude - rLongitude);

        double formulaResult = Math.sin(latDistance/2) * Math.sin(latDistance/2) + Math.cos(Math.toRadians(uLatitude))
                * Math.cos(Math.toRadians(rLatitude)) * Math.sin(longDistance/2) * Math.sin(longDistance/2);

        double lastStep = 2 * Math.atan2(Math.sqrt(formulaResult), Math.sqrt(1-formulaResult));

        return lastStep <= 0.3;
    }
}
