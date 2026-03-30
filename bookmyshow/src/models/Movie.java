package models;

import enums.Genre;
import enums.MovieStatus;
import enums.MovieType;

import java.time.LocalDate;

public class Movie {

    private static int idCounter = 1;

    private final int movieId;
    private String title;
    private String description;
    private int durationMinutes;
    private Genre genre;
    private MovieType movieType;
    private MovieStatus movieStatus;
    private String director;
    private LocalDate releaseDate;
    private double rating;

    public Movie(String title, String description, int durationMinutes, Genre genre,
                 MovieType movieType, String director, LocalDate releaseDate) {
        this.movieId = idCounter++;
        this.title = title;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.genre = genre;
        this.movieType = movieType;
        this.movieStatus = MovieStatus.AVAILABLE;
        this.director = director;
        this.releaseDate = releaseDate;
        this.rating = 0.0;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public Genre getGenre() {
        return genre;
    }

    public MovieType getMovieType() {
        return movieType;
    }

    public MovieStatus getMovieStatus() {
        return movieStatus;
    }

    public void setMovieStatus(MovieStatus movieStatus) {
        this.movieStatus = movieStatus;
    }

    public String getDirector() {
        return director;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{id=" + movieId + ", title='" + title + "', type=" + movieType
                + ", genre=" + genre + ", duration=" + durationMinutes + "min, status=" + movieStatus + "}";
    }
}
