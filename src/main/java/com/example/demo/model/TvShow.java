package com.example.demo.model;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TvShow {

    private String showName;
    private String language;
    private List<String> genre;
    private String image;
    private String summary;
    private double averageRating;

    public String getShowName() {
        return showName;
    }
    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getGenre() {
        return genre;
    }
    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public double getAverageRating() {
        return averageRating;
    }
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public static List<TvShow> create(String json) {

        List<TvShow> tvShowList = new ArrayList<>();
        try{
            InputStream is = new ByteArrayInputStream(json.getBytes());
            JsonReader reader = Json.createReader(is);
            JsonArray jsonArray = reader.readArray();

            jsonArray.stream()
                    .map(v-> (JsonObject) v)
                    .forEach(v->{
                        TvShow tvShow = new TvShow();
                        JsonObject show = v.getJsonObject("show");
                        tvShow.setShowName(show.getString("name"));
                        tvShow.setLanguage(show.getString("language"));
                        JsonArray genreArray = show.getJsonArray("genres");
                        List<String> genres = new ArrayList<>();
                        genreArray.stream()
                                        .forEach(jsonValue -> genres.add(jsonValue.toString()));
                        tvShow.setGenre(genres);
                        if(show.isNull("image") || show.getJsonObject("image").isNull("medium") ) {
                            tvShow.setImage("https://i.stack.imgur.com/6M513.png");
                        } else {
                            tvShow.setImage(show.getJsonObject("image").getString("medium"));
                        }

                        if(show.isNull("summary")) {
                            tvShow.setSummary("nil");
                        } else {
                            tvShow.setSummary(show.getString("summary"));
                        }

                        if(show.getJsonObject("rating").isNull("average")) {
                            tvShow.setAverageRating(0.00);
                        } else {
                            tvShow.setAverageRating(Double.parseDouble(String.valueOf(show.getJsonObject("rating").getJsonNumber("average"))));
                        }

                        tvShowList.add(tvShow);
                    });
        } catch (Exception e) {
            System.out.println(">>>>>> TvShow class: unable to convert to JsonObject");
            e.printStackTrace();
        }
        return tvShowList;
    }
}
