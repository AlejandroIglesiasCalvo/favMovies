package es.uniovi.eii.sdm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import es.uniovi.eii.sdm.modelo.Pelicula;

public class ShowMovie extends AppCompatActivity {
    CollapsingToolbarLayout toolBarLayout;
    ImageView imagenFondo;
    TextView categoria;
    TextView estreno;
    TextView duracion;
    TextView argumento;
    ImageView caratula;
    private Pelicula pelicula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);
        Intent intentPeli = getIntent();
        pelicula = intentPeli.getParcelableExtra(MainRecycler.PELICULA_SELECCIONADA);

// Gestión barra de la app
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        imagenFondo = (ImageView) findViewById(R.id.imagenFondo);

// Gestión de los controles que contienen los datos de la película
        categoria = (TextView) findViewById(R.id.categoria);
        estreno = (TextView) findViewById(R.id.estreno);
        duracion = (TextView) findViewById(R.id.duracion);
        argumento = (TextView) findViewById(R.id.argumento);
        caratula = (ImageView) findViewById(R.id.caratula);

        if (pelicula != null) //apertura en modo consulta
            mostrarDatos(pelicula);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void mostrarDatos(Pelicula pelicula) {
        if (!pelicula.getTitulo().isEmpty()) { //apertura en modo consulta
            //Actualizar componentes con valores de la pelicula específica
            String fecha = pelicula.getFecha();
            toolBarLayout.setTitle(pelicula.getTitulo() + " (" + fecha.substring(fecha.lastIndexOf('/') + 1) + ")");
            // Imagen de fondo
            Picasso.get().load(pelicula.getUrlFondo()).into(imagenFondo);

            categoria.setText(pelicula.getCategoria().getNombre());
            estreno.setText(pelicula.getFecha());
            duracion.setText(pelicula.getDuracion());
            argumento.setText(pelicula.getArgumento());

            // Imagen de la carátula
            Picasso.get().load(pelicula.getUrlCaratula()).into(caratula);
        }
    }
}