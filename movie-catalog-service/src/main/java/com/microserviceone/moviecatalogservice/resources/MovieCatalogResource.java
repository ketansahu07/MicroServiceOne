package com.microserviceone.moviecatalogservice.resources;

import com.microserviceone.moviecatalogservice.models.CatalogItem;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

  @RequestMapping("/{userId}")
  public List<CatalogItem> getcatalog(@PathVariable("userId") String userId) {
    return Collections.singletonList(
          new CatalogItem("The Fantastic Beast", "A memorable movie for me", 5)
    );
  }
}
