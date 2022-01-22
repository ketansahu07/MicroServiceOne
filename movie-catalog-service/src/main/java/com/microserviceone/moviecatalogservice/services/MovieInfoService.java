package com.microserviceone.moviecatalogservice.services;

import com.microserviceone.moviecatalogservice.models.CatalogItem;
import com.microserviceone.moviecatalogservice.models.Movie;
import com.microserviceone.moviecatalogservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoService {

  @Autowired
  private RestTemplate restTemplate;

  @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
  public CatalogItem getCatalogItem(Rating rating) {
    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
    return new CatalogItem(movie.getTitle(), movie.getOverview(), rating.getRating());
  }

  public CatalogItem getFallbackCatalogItem(Rating rating) {
    return new CatalogItem("Movie not found", "", rating.getRating());
  }
}
