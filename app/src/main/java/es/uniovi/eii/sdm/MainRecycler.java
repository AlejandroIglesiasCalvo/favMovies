package es.uniovi.eii.sdm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.sdm.modelo.Categoria;
import es.uniovi.eii.sdm.modelo.Pelicula;


public class MainRecycler extends AppCompatActivity {
    public static String filtrocategoria=null;
    RecyclerView listaPeliView;
    Pelicula peli;
    List<Pelicula> ListaPeli;
    public static final String PELICULA_SELECCIONADA = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);

        cargarPeliculas();

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

        Intent intent = new Intent(MainRecycler.this, ShowMovie.class);
        intent.putExtra(PELICULA_SELECCIONADA, peli);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }


    public void crearPeliNUevaFab(View v) {
        Log.d("CrearPeli", "CrearPeli");
        Intent intent = new Intent(MainRecycler.this, MainActivity.class);
        //startActivityForResult(intent, GESTION_ACTIVITY, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    protected void cargarPeliculas() {
        /*si una película le falta la caratual, el fondo o el trailer, le pongo unos por defecto. De esta manera me aseguro
    estos campos en las películas*/
        String Caratula_por_defecto = "https://image.tmdb.org/t/p/original/jnFCk7qGGWop2DgfnJXeKLZFuBq.jpg\n";
        String fondo_por_defecto = "https://image.tmdb.org/t/p/original/xJWPZIYOEFIjZpBL7SVBGnzRYXp.jpg\n";
        String trailer_por_defecto = "https://www.youtube.com/watch?v=lpEJVgysiWs\n";
        Pelicula peli;
        ListaPeli = new ArrayList<Pelicula>();
        InputStream file = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;

        try {
            file = getAssets().open("lista_peliculas_url_utf8.csv");
            reader = new InputStreamReader(file);
            bufferedReader = new BufferedReader(reader);

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";");
                if (data != null && data.length >= 5) {
                    if (data.length == 8) {
                        peli = new Pelicula(data[0], data[1], new Categoria(data[2], ""), data[3], data[4], data[5], data[6], data[7]);
                    } else {
                        peli = new Pelicula(data[0], data[1], new Categoria(data[2], ""), data[3], data[4], Caratula_por_defecto, fondo_por_defecto, trailer_por_defecto);
                    }
                    Log.d("cargarPeliculas", peli.toString());
                    ListaPeli.add(peli);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //Gestión del menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menusettings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            //Llamamos a la activity settings de ajustes
            Intent intentSettingsActivity=new Intent(MainRecycler.this, SettingsActivity.class);
            startActivity(intentSettingsActivity);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}