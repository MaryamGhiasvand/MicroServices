package io.javabrains.moviecatalogservice.resources;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;
import io.javabrains.moviecatalogservice.services.MovieInfo;
import io.javabrains.moviecatalogservice.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//implement more granularity in fallback mechanism
//so depending on which service has failed=> that service cause the circuit to break=> you have only for that service
//=> the rest of services work fine
@RestController
@RequestMapping("/catalog")
public class MovieCatalogResourceMethodCallBack {

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;

    @Autowired
    private Environment env;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        //there is 2 api calling here => better to make them 2 separated method and have 2 call back calling for every of them
        //option+cmd+ m to create a method of piece of code.
        UserRating ratings = userRatingInfo.getUserRating(userId);
        return ratings
                .getUserRatings()
                .stream()
                .map(rating -> {
                    //separate this line to another method also
                    return movieInfo.getCatalogItem(rating);
                })
                .collect(Collectors.toList());
    }

    //use environment object to get info about configuration
    //give us option to look up profile information
    @GetMapping("/environment")
    public String envDetail(){
        //we can get the name of active profile/default profile
        //env.getDefaultProfiles()
        //env.getProperty()
        return env.getActiveProfiles().toString();

    }


}
