package es.uniovi.eii.sdm.remote;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit= null;

    private RetrofitClient() {}

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            HttpLoggingInterceptor loggin= new HttpLoggingInterceptor();

            retrofit= new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
