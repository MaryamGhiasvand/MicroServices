package io.javabrains.moviecatalogservice.models;

import java.util.List;

public class UserRating {
    public UserRating(){}
    private String userId;
    private List<Rating> userRatings;

    public UserRating(List<Rating> userRatings) {
        this.userRatings = userRatings;
    }

    public List<Rating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(List<Rating> userRatings) {
        this.userRatings = userRatings;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
