package es.uniovi.eii.sdm.remote;

import retrofit2.Retrofit;
@SuppressWarnings("SpellCheckingInspection")
public class ApiUtils {
    public static final String LANGUAGE = "es-ES";
    public static final String API_KEY = "d92c9352629e5677e9ded29344f4761a";

    public static ThemoviedbApi createThemoviedbApi() {
        Retrofit retrofit = RetrofitClient.getClient(ThemoviedbApi.BASE_URL);
        return retrofit.create(ThemoviedbApi.class);
    }


}
