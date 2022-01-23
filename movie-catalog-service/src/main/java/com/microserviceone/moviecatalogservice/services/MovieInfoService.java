package com.microserviceone.moviecatalogservice.services;

import com.microserviceone.moviecatalogservice.models.CatalogItem;
import com.microserviceone.moviecatalogservice.models.Movie;
import com.microserviceone.moviecatalogservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoService {

  @Autowired
  private RestTemplate restTemplate;

  /*
  // Bulkhead Pattern: defines separate thread pools for separate apis
  @HystrixCommand(
        fallbackMethod = "getFallbackCatalogItem",
        threadPoolKey = "movieInfoPool",
        threadPoolProperties = {
              @HystrixProperty(name = "coreSize", value = "20"),  // number of threads in this pool
              @HystrixProperty(name = "axQueueSize", value = "10")  // queue beyond max threads
        }
  )
   */
  @HystrixCommand(
        fallbackMethod = "getFallbackCatalogItem",
        commandProperties = {
              @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
              @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
              @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
              @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
        })
  public CatalogItem getCatalogItem(Rating rating) {
    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
    return new CatalogItem(movie.getTitle(), movie.getOverview(), rating.getRating());
  }

  public CatalogItem getFallbackCatalogItem(Rating rating) {
    return new CatalogItem("Movie not found", "", rating.getRating());
  }
}
