package es.uniovi.eii.sdm.ui.gallery;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.uniovi.eii.sdm.ListaPeliculasAdapter;
import es.uniovi.eii.sdm.MainRecycler;
import es.uniovi.eii.sdm.R;
import es.uniovi.eii.sdm.ShowMovie;
import es.uniovi.eii.sdm.datos.server.ServerDataMapper;
import es.uniovi.eii.sdm.datos.server.movieList.MovieData;
import es.uniovi.eii.sdm.datos.server.movieList.MovieListResult;
import es.uniovi.eii.sdm.modelo.Pelicula;
import es.uniovi.eii.sdm.remote.ThemoviedbApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static es.uniovi.eii.sdm.remote.ApiUtils.API_KEY;
import static es.uniovi.eii.sdm.remote.ApiUtils.LANGUAGE;
import static es.uniovi.eii.sdm.remote.ApiUtils.createThemoviedbApi;
@SuppressWarnings("SpellCheckingInspection")
public class PopularFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    View root;
    RecyclerView listaPeliView;
    Pelicula peli;
    List<Pelicula> ListaPeli;
    private ThemoviedbApi clienteApi;
    public static final String PELICULA_SELECCIONADA = null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_popular, container, false);
        listaPeliView = root.findViewById(R.id.recycler);
        listaPeliView.setHasFixedSize(true);

        clienteApi = createThemoviedbApi();
        realizarPeticionPeliculasPopulares(clienteApi);
        return root;
    }
    private void realizarPeticionPeliculasPopulares(ThemoviedbApi clienteThemoviedbApi) {
        Call<MovieListResult> call =
                clienteThemoviedbApi.getListMovies("popular", API_KEY, LANGUAGE, 1);

        // Petición asíncrona a la API
        call.enqueue(new Callback<MovieListResult>() {
            @Override
            public void onResponse(Call<MovieListResult> call, Response<MovieListResult> response) {
                switch (response.code()) {
                    case 200:
                        MovieListResult data = response.body();
                        List<MovieData> listaDatosPeliculas = data.getMovieData();
                        Log.d("PeticionPelPopulares", "ListaDatosPeliculas: " + listaDatosPeliculas);

                        // Convertir datos de la API a clase Pelicula del dominio
                        ListaPeli = ServerDataMapper.convertMovieListToDomain(listaDatosPeliculas);

                        // cargar de RecyclerView con los datos
                        listaPeliView = (RecyclerView) root.findViewById(R.id.recycler);
                        listaPeliView.setHasFixedSize(true);

                        mostarVistaPeliculas();


                        break;
                    default:
                        call.cancel();
                        break;
                }
            }

            @Override
            public void onFailure(Call<MovieListResult> call, Throwable t) {
                Log.e("Lista - error", t.toString());
            }
        });
    }

    private void mostarVistaPeliculas() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        listaPeliView.setLayoutManager(layoutManager);

        ListaPeliculasAdapter lpAdapter = new ListaPeliculasAdapter(ListaPeli,
                new ListaPeliculasAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Pelicula peli) {
                        clikonIntem(peli);
                    }
                });
        listaPeliView.setAdapter(lpAdapter);
    }

    public void clikonIntem(Pelicula peli) {
        Log.i("Click adapter", "Item Clicked" + peli.getCategoria().getNombre());

        Intent intent = new Intent(root.getContext(), ShowMovie.class);
        intent.putExtra(PELICULA_SELECCIONADA, peli);
        startActivity(intent);
    }

}