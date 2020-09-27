package io.javabrains.moviecatalogservice.resources;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
        //create an instance of rest template ( a utility object that is supposed to make rest api call)
        RestTemplate restTemplate = new RestTemplate();
        //1.get all the rated movie id
        List<Rating> ratings = Arrays.asList(
                new Rating("movie1", 4),
                new Rating("movie2", 3)
        );

        //2."for each movie" id "call info service" and get detail
        ratings
                .stream()
                //for each movie make a separate call and return the "movie"
                //these call are sync => to be async(at the same time) => use webClient
                .map(rating -> {
                    //"option"+"cmd"+"v" => to create a variable name for it 
                    Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);

                    //3. put them all together
                    return new CatalogItem(movie.getMovieName(),"desc",rating.getRating());
                })
                .collect(Collectors.toList());



        return Collections.singletonList(
                //new CatalogItem("Spiderman","desc Spiderman",4),
                new CatalogItem("inception", "dec",5)
        );
    }
}
