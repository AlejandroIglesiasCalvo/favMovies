package es.uniovi.eii.sdm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import es.uniovi.eii.sdm.modelo.Pelicula;
import es.uniovi.eii.sdm.util.Conexion;

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
                verTrailer(pelicula.getUrlTrailer());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.compartir) {
            Conexion conexion = new Conexion(getApplicationContext());
            if (conexion.CompruebaConexion()) {
                compartirPeli();
            } else
                Toast.makeText(getApplicationContext(), R.string.error_conexion, Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }


    public void compartirPeli() {
        /* es necesario hacer un intent con la constate ACTION_SEND */
        /*Llama a cualquier app que haga un envío*/
        Intent itSend = new Intent(Intent.ACTION_SEND);
        /* vamos a enviar texto plano */
        itSend.setType("text/plain");
        // itSend.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{​​para}​​);
        itSend.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.subject_compartir) + ": " + pelicula.getTitulo());
        itSend.putExtra(Intent.EXTRA_TEXT, getString(R.string.titulo)
                + ": " + pelicula.getTitulo() + "\n" +
                getString(R.string.contenido)
                + ": " + pelicula.getArgumento());

        /* iniciamos la actividad */
            /* puede haber más de una aplicacion a la que hacer un ACTION_SEND,
               nos sale un ventana que nos permite elegir una.
               Si no lo pongo y no hay activity disponible, pueda dar un error */
        Intent shareIntent = Intent.createChooser(itSend, null);

        startActivity(shareIntent);

    }


    private void verTrailer(String urlTrailer) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlTrailer)));
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