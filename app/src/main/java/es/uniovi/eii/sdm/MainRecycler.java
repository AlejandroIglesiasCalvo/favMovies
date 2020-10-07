package es.uniovi.eii.sdm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.sdm.modelo.Categoria;
import es.uniovi.eii.sdm.modelo.Pelicula;


public class MainRecycler extends AppCompatActivity {
    RecyclerView listaPeliView;
    Pelicula peli;
    List<Pelicula> ListaPeli;
    public static final String PELICULA_SELECCIONADA = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);

        rellenarLista();

        listaPeliView = (RecyclerView) findViewById(R.id.recycler);
        listaPeliView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
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

        Intent intent = new Intent(MainRecycler.this, MainActivity.class);
        intent.putExtra(PELICULA_SELECCIONADA, peli);
        startActivity(intent);
    }

    private void rellenarLista() {
        ListaPeli = new ArrayList<Pelicula>();
        Categoria cataccion = new Categoria("Acción", "PelisAccion");
        Pelicula peli = new Pelicula("Tenet", "Una acción épica que gira en torno al espionaje internacional, los viajes en el tiempo y la evolución, en la que un agente secreto debe prevenir la Tercera Guerra Mundial.",
                cataccion, "150", "26/8/2020");
        ListaPeli.add(peli);

    }

    public void crearPeliNUevaFab(View v) {
        Log.d("CrearPeli", "CrearPeli");
        Intent intent = new Intent(MainRecycler.this, MainActivity.class);
        //startActivityForResult(intent, GESTION_ACTIVITY, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}