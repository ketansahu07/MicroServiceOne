package com.microserviceone.moviecatalogservice.resources;

import com.microserviceone.moviecatalogservice.models.CatalogItem;
import com.microserviceone.moviecatalogservice.models.UserRating;
import com.microserviceone.moviecatalogservice.services.MovieInfoService;
import com.microserviceone.moviecatalogservice.services.RatingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

  @Autowired
  private RestTemplate restTemplate;

//  @Autowired
//  private WebClient.Builder webClientBuilder;

  @Autowired
  private MovieInfoService movieInfoService;

  @Autowired
  private RatingDataService ratingDataService;

  @RequestMapping("/{userId}")
  public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
    UserRating ratings = ratingDataService.getUserRating(userId);

    return ratings.getUserRatings().stream().map(rating -> movieInfoService.getCatalogItem(rating))
          .collect(Collectors.toList());
  }
}

  /*
  Movie movie = webClientBuilder.build()
        .get()
        .uri("http://localhost:8082/movies/" + rating.getMovieId())
        .retrieve()
        .bodyToMono(Movie.class)
        .block();
   */
