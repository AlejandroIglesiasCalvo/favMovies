package es.uniovi.eii.sdm.ui.home;

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
import es.uniovi.eii.sdm.R;
import es.uniovi.eii.sdm.ShowMovie;
import es.uniovi.eii.sdm.datos.db.PeliculasDataSource;
import es.uniovi.eii.sdm.modelo.Pelicula;
@SuppressWarnings("SpellCheckingInspection")
public class FavFragment extends Fragment {

    private HomeViewModel homeViewModel;
    View root;
    RecyclerView listaPeliView;
    public static final String PELICULA_SELECCIONADA = null;
    public static final String PELICULA_CREADA = null;
    Pelicula peli;
    List<Pelicula> ListaPeli;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_fav, container, false);
        listaPeliView = root.findViewById(R.id.recycler);
        listaPeliView.setHasFixedSize(true);
        cargarBD();
        return root;
    }

    protected void cargarBD() {
        PeliculasDataSource peliculasDataSource = new PeliculasDataSource(root.getContext());
        peliculasDataSource.open();
        ListaPeli = peliculasDataSource.getAllValorations();
        peliculasDataSource.close();
        mostarVistaPeliculas();
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