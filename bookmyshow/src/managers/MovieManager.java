package managers;

import models.Movie;
import enums.MovieStatus;
import enums.MovieType;
import enums.Genre;

import java.util.ArrayList;
import java.util.List;

public class MovieManager {

    private static MovieManager instance;
    private final List<Movie> movies;

    private MovieManager() {
        this.movies = new ArrayList<>();
    }

    public static MovieManager getInstance() {
        if (instance == null) {
            synchronized (MovieManager.class) {
                if (instance == null) {
                    instance = new MovieManager();
                }
            }
        }
        return instance;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
        System.out.println("[MovieManager] Added: " + movie.getTitle());
    }

    public Movie getMovieById(int movieId) {
        for (Movie movie : movies) {
            if (movie.getMovieId() == movieId) {
                return movie;
            }
        }
        return null;
    }

    public List<Movie> searchByTitle(String title) {
        List<Movie> results = new ArrayList<>();
        String query = title.toLowerCase();
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(query)
                    && movie.getMovieStatus() == MovieStatus.AVAILABLE) {
                results.add(movie);
            }
        }
        return results;
    }

    public List<Movie> searchByGenre(Genre genre) {
        List<Movie> results = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getGenre() == genre && movie.getMovieStatus() == MovieStatus.AVAILABLE) {
                results.add(movie);
            }
        }
        return results;
    }

    public List<Movie> searchByLanguage(MovieType type) {
        List<Movie> results = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getMovieType() == type && movie.getMovieStatus() == MovieStatus.AVAILABLE) {
                results.add(movie);
            }
        }
        return results;
    }

    public List<Movie> getAllAvailableMovies() {
        List<Movie> results = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getMovieStatus() == MovieStatus.AVAILABLE) {
                results.add(movie);
            }
        }
        return results;
    }

    public void removeMovie(int movieId) {
        Movie movie = getMovieById(movieId);
        if (movie != null) {
            movie.setMovieStatus(MovieStatus.NOT_AVAILABLE);
            System.out.println("[MovieManager] Movie '" + movie.getTitle() + "' marked as unavailable.");
        }
    }

    public List<Movie> getAllMovies() {
        return movies;
    }
}
