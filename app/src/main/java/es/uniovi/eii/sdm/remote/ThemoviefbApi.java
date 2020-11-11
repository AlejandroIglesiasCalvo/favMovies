package es.uniovi.eii.sdm.remote;

import es.uniovi.eii.sdm.datos.server.MovieListResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ThemoviefbApi {
    public static final String BASE_URL= "https://api.themoviedb.org/3/";
    @GET("movie/{lista}")
    Call<MovieListResult> getListMovies(
            @Path("lista") String lista,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

}
