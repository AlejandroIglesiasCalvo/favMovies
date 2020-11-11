package es.uniovi.eii.sdm.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import es.uniovi.eii.sdm.ListaActoresAdapter;
import es.uniovi.eii.sdm.R;
import es.uniovi.eii.sdm.datos.db.ActoresDataSource;
import es.uniovi.eii.sdm.modelo.Actor;


public class actoresFragment extends Fragment {

    public static final String ACTORES = "actores";

    List<Actor> listaActores;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_actores, container, false);
        //Y tomamos el RecyclerView que viene en dicho fragment definidio por xml
        final RecyclerView recycleActors = (RecyclerView) root.findViewById(R.id.reciclerViewActores);


        //Instanciamos el LayoutManager correspondiente
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recycleActors.setLayoutManager(layoutManager);

        recycleActors.setHasFixedSize(true);

        Bundle args = getArguments();
        int id_pelicula = args.getInt("id_pelicula");

        //Creamos un películas data source para llamar al método que por SQL nos localizará el reparto
        ActoresDataSource actoresDataSource = new ActoresDataSource(root.getContext());
        actoresDataSource.open();
        listaActores = actoresDataSource.getFilteredActorss(id_pelicula);
        actoresDataSource.close();

        // Definición de onclik del actor
        ListaActoresAdapter laAdapter = new ListaActoresAdapter(listaActores,
                new ListaActoresAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Actor actor) {
                        //Si pulsamos sobre un actor nos llevará a su ficha en Imdb
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(actor.getImdb())));
                    }
                });

        recycleActors.setAdapter(laAdapter);
        return root;
    }
}