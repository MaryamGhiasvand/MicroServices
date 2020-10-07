package io.javabrains.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//for every callback we should have separate class => Hystrix can create proxy for both of them
//a separate class which is making the call as the fallback
@Service
public class MovieInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getCallBackCatalogItem",
    //an array of Hystrix prop annotations
    commandProperties = {
            //set time out=> wait for 2scc, if you didn't get the result return callback methlod
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            //number of request that you need to see => look at the last 10 request
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
            //percentage of fail request to do the circuitBreak
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            //how long the circuit breaker will sleep before it picks up again =5 sec
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "5000")
    })
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getMovieName(), "desc", rating.getRating());
    }
    //call back method with the same signature
    public CatalogItem getCallBackCatalogItem(Rating rating) {
        //should return some hard-coded data
        return new CatalogItem("movie not found", "", rating.getRating());
    }
}
