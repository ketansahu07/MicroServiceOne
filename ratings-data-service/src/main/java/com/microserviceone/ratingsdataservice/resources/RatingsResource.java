package com.microserviceone.ratingsdataservice.resources;

import com.microserviceone.ratingsdataservice.models.Rating;
import com.microserviceone.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingsResource {

  @RequestMapping("/{movieId}")
  public Rating getRating(@PathVariable("movieId") String movieId) {
    return new Rating(movieId, 5);
  }

  @RequestMapping("/users/{userId}")
  public UserRating getUserRating(@PathVariable("userId") String userId) {
    List<Rating> ratings =  List.of(
          new Rating("1234", 4),
          new Rating("5678", 5)
    );

    UserRating userRating = new UserRating();
    userRating.setUserRatings(ratings);

    return userRating;
  }
}
