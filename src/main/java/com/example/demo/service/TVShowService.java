package com.example.demo.service;

import com.example.demo.model.TvShow;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class TVShowService {

    //https://api.tvmaze.com/search/shows?q=girls

    private static final String SEARCH_SHOWS_URL = "https://api.tvmaze.com/search/shows";

    public Optional<List<TvShow>> searchShows(String searchTerm) {

        String url = UriComponentsBuilder
                .fromUriString(SEARCH_SHOWS_URL)
                .queryParam("q",searchTerm)
                .toUriString();

        RequestEntity req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req,String.class);

        System.out.println(">>>>TvShowService searchShows: "+resp.getBody());

        try {
            List<TvShow> tvShowList = TvShow.create(resp.getBody());
            return Optional.of(tvShowList);
        } catch (Exception e) {
            System.out.println(">>>> TVService - searchShows: Error creating List<TvShow>");
            e.printStackTrace();
        }


        return Optional.empty();
    }
}
