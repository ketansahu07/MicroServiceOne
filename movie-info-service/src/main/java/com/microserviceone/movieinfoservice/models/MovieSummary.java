package com.microserviceone.movieinfoservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieSummary {
  private String id;
  private String title;
  private String overview;
}
