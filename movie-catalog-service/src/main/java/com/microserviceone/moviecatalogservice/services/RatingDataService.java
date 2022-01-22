package com.microserviceone.moviecatalogservice.services;

import com.microserviceone.moviecatalogservice.models.Rating;
import com.microserviceone.moviecatalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RatingDataService {

  @Autowired
  private RestTemplate restTemplate;

  @HystrixCommand(fallbackMethod = "getFallbackUserRating")
  public UserRating getUserRating(String userId) {
    return restTemplate.getForObject("http://ratings-data-service/ratings/users/" + userId, UserRating.class);
  }

  public UserRating getFallbackUserRating(String userId) {
    UserRating userRating = new UserRating();
    userRating.setUserId(userId);
    userRating.setUserRatings(List.of(new Rating("0", 0)));
    return userRating;
  }
}
