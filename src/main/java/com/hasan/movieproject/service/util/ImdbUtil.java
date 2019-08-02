package com.hasan.movieproject.service.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hasan.movieproject.model.movie.dao.DirectorEntity;
import com.hasan.movieproject.model.movie.dao.GenreEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ImdbUtil {

    private static ResponseEntity<String> response;
    private final static String apiKey = "72a51ec2";
    private final static ObjectMapper mapper = new ObjectMapper();
    private static JsonNode imdbMovie;

    public static void setProperties(String imdbId){
        RestTemplate restTemplate = new RestTemplate();
        String movieIMDBUrl = "http://www.omdbapi.com/?apikey="+ apiKey +"&i=" + imdbId;
        response = restTemplate.getForEntity(movieIMDBUrl, String.class);

        try{
            imdbMovie = mapper.readTree(response.getBody());
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    public static String getTitle(){
        if(imdbMovie == null){ return null; }

        return imdbMovie.path("Title").asText();
    }

    public static Date getReleaseDate(){
        if(imdbMovie == null){ return null; }

        String released = imdbMovie.path("Released").asText();
        DateFormat format = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH);
        Date date = null;
        try{
            date = format.parse(released);
        } catch (ParseException exception){
            exception.printStackTrace();
        }

        return date;
    }

    public static String getImdbId(){
        if(imdbMovie == null){ return null; }

        return imdbMovie.path("imdbID").asText();
    }

    public static Double getImdbRating(){
        if(imdbMovie == null){ return null; }

        return imdbMovie.path("imdbRating").asDouble();
    }

    public static Integer getDuration(){
        String duration = imdbMovie.path("Runtime").asText();

        if(duration.contains(" ")){
            duration = duration.substring(0, duration.indexOf(" "));
            return Integer.parseInt(duration);
        }

        return 0;
    }

    public static List<GenreEntity> getGenres(){
        if(imdbMovie == null){ return null; }

        String genreNames = imdbMovie.path("Genre").asText();
        List<GenreEntity> genres = new ArrayList<>();

        while (genreNames.contains(",")){
            String genreName = genreNames.substring(0, genreNames.indexOf(","));
            genreNames = genreNames.substring(genreNames.indexOf(",") + 2);

            GenreEntity genre = new GenreEntity(genreName);
            genres.add(genre);
        }

        GenreEntity genre = new GenreEntity(genreNames);
        genres.add(genre);

        return genres;
    }

    public static List<DirectorEntity> getDirectors(){
        if(imdbMovie == null){ return null; }

        String directorNames = imdbMovie.path("Director").asText();
        List<DirectorEntity> directors = new ArrayList<>();

        while(directorNames.contains(",")) {
            String directorName = directorNames.substring(0, directorNames.indexOf(","));
            directorNames = directorNames.substring(directorNames.indexOf(",") + 2);

            if (directorName.contains(" ")) {
                String name = directorName.substring(0, directorName.indexOf(" "));
                String surname = directorName.substring(directorName.indexOf(" "));

                DirectorEntity director = new DirectorEntity(name, surname);
                directors.add(director);
            }
        }

        if (directorNames.contains(" ")) {
            String name = directorNames.substring(0, directorNames.indexOf(" "));
            String surname = directorNames.substring(directorNames.indexOf(" "));

            DirectorEntity director = new DirectorEntity(name, surname);
            directors.add(director);
        }

        return directors;
    }
}
