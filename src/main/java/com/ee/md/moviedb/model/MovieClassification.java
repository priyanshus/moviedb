package com.ee.md.moviedb.model;

import lombok.Getter;

@Getter
public enum MovieClassification {
    SUPER_HIT("Super Hit", 4.5, 100),
    HIT("Hit", 4.0, 50),
    AVERAGE("Average", 3.0, 25),
    BELOW_AVERAGE("Below Average", 2.0, 10),
    FLOP("Flop", 0.0, 10),
    INSUFFICIENT_DATA("Insufficient Data", 0.0, 0);

    private final String displayName;
    private final double minRating;
    private final int minReviews;

    MovieClassification(String displayName, double minRating, int minReviews) {
        this.displayName = displayName;
        this.minRating = minRating;
        this.minReviews = minReviews;
    }

    public static MovieClassification getClassification(double avgRating, int reviewCount) {
        if (reviewCount < 10) {
            return INSUFFICIENT_DATA;
        }
        
        if (avgRating >= 4.5 && reviewCount >= 100) {
            return SUPER_HIT;
        } else if (avgRating >= 4.0 && reviewCount >= 50) {
            return HIT;
        } else if (avgRating >= 3.0 && reviewCount >= 25) {
            return AVERAGE;
        } else if (avgRating >= 2.0 && reviewCount >= 10) {
            return BELOW_AVERAGE;
        } else {
            return FLOP;
        }
    }
}
