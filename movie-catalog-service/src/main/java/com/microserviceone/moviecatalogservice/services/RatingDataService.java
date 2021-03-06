package com.microserviceone.moviecatalogservice.services;

import com.microserviceone.moviecatalogservice.models.Rating;
import com.microserviceone.moviecatalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RatingDataService {

  @Autowired
  private RestTemplate restTemplate;

  /*
  // Bulkhead Pattern: defines separate thread pools for separate apis
  @HystrixCommand(
        fallbackMethod = "getFallbackUserRating",
        threadPoolKey = "ratingDataPool",
        threadPoolProperties = {
              @HystrixProperty(name = "coreSize", value = "20"),  // number of threads in this pool
              @HystrixProperty(name = "axQueueSize", value = "10")  // queue beyond max threads
        }
  )
   */
  @HystrixCommand(fallbackMethod = "getFallbackUserRating",
        commandProperties = {
              @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
              @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
              @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
              @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
        })
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
