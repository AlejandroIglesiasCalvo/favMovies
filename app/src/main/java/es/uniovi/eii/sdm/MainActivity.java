package es.uniovi.eii.sdm;

import org.jetbrains.annotations.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.sdm.modelo.Categoria;
import es.uniovi.eii.sdm.modelo.Pelicula;
@SuppressWarnings("SpellCheckingInspection")
public class MainActivity extends AppCompatActivity {
    private List<Categoria> listaCategorias;

    // componentes
    private Spinner spinner;
    private EditText editTitulo;
    private EditText editContenido;
    private EditText editfecha;
    private EditText editDuracion;
    private Snackbar msgCreaCategoria;

    public static final String CATEGORIA_SELECCIONADA = "categoria_seleccionada";
    public static final String CATEGORIA_MODIFICADA = "categoria_modificada";
    public static final String POS_CATEGORIA_SELECCIONADA = "pos_categoria_seleccionada";

    // identificador de activity
    private static final int GESTION_CATEGORIA = 1;
    Boolean creandoCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toast.makeText(getApplicationContext(),getString(R.string.OnCreate), Toast.LENGTH_SHORT).show();

        listaCategorias = new ArrayList<Categoria>();
        listaCategorias.add(new Categoria("Accion", "pelicula de Accion"));
        listaCategorias.add(new Categoria("Drama", "pelicula de Drama"));

        // Inicializa el spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        introListaSpinner(spinner, listaCategorias);


        Button btnGuardar = (Button) findViewById(R.id.button);
        btnGuardar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidarCampos()) {
                    Snackbar.make(findViewById(R.id.Layaut), R.string.msg_guardado,
                            Snackbar.LENGTH_SHORT)
                            .show();
                } else {
                    Snackbar.make(findViewById(R.id.Layaut), R.string.msg_no_guardado,
                            Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        }));


        ImageButton IB = (ImageButton) findViewById(R.id.imgBoton);
        IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinerCategorias = (Spinner) findViewById(R.id.spinner);
                if (spinerCategorias.getSelectedItemPosition() == 0) {
                    msgCreaCategoria = Snackbar.make(findViewById(R.id.Layaut), R.string.msg_crear_nueva_categoria,
                            Snackbar.LENGTH_LONG);
                } else {
                    msgCreaCategoria = Snackbar.make(findViewById(R.id.Layaut), R.string.msg_modif_categoria,
                            Snackbar.LENGTH_LONG);
                }
                //Accion de crear una nueva categoria
                msgCreaCategoria.setAction(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(findViewById(R.id.Layaut), R.string.msg_accion_ok,
                                Snackbar.LENGTH_LONG)
                                .show();

                        modificarCategoria();
                    }

                });
                msgCreaCategoria.show();
            }
        });

        Intent intentPeli = getIntent();
        Pelicula pelicula = intentPeli.getParcelableExtra(MainRecycler.PELICULA_SELECCIONADA);
        if (pelicula != null) {
            abrirModoConsulta(pelicula);
        }

    }

    private void abrirModoConsulta(@org.jetbrains.annotations.NotNull Pelicula pelicula) {
        TextView tvTitulo = (TextView)findViewById(R.id.idTitulo);
        TextView txSinapsis = (TextView)findViewById(R.id.idDescripcion);
        Spinner spCategoria = (Spinner)findViewById(R.id.spinner);
        EditText edDuracion = (EditText)findViewById(R.id.idDuracion);
        EditText edFecha = (EditText)findViewById(R.id.idFecha);
        tvTitulo.setText(pelicula.getTitulo());
        txSinapsis.setText(pelicula.getArgumento());
        edDuracion.setText(pelicula.getDuracion());
        edFecha.setText(pelicula.getFecha());

        tvTitulo.setEnabled(false);
        txSinapsis.setEnabled(false);
        edDuracion.setEnabled(false);
        edFecha.setEnabled(false);
    }

    private void modificarCategoria() {
        Intent categoriaIntent = new Intent(MainActivity.this, CategoriaActivity.class);
        //startActivity(categoriaIntent);
        categoriaIntent.putExtra(POS_CATEGORIA_SELECCIONADA, spinner.getSelectedItemPosition());
        creandoCategoria = true;
        if (spinner.getSelectedItemPosition() > 0) {
            creandoCategoria = false;
            categoriaIntent.putExtra(CATEGORIA_SELECCIONADA, listaCategorias.get(spinner.getSelectedItemPosition() - 1));
        }
        startActivityForResult(categoriaIntent, GESTION_CATEGORIA);
    }

    boolean ValidarCampos() {
        //Los objetos
        EditText titulo, descripcion, duracion, fecha;
        titulo = (EditText) findViewById(R.id.idTitulo);
        descripcion = (EditText) findViewById(R.id.idDescripcion);
        duracion = (EditText) findViewById(R.id.idDuracion);
        fecha = (EditText) findViewById(R.id.idFecha);
        //Los strings
        String stitulo, sdescripcion, sduracion, sfecha;
        stitulo = titulo.getText().toString();
        sdescripcion = descripcion.getText().toString();
        sduracion = duracion.getText().toString();
        sfecha = fecha.getText().toString();
        //Esto no es mas facil, pero vale
        return !stitulo.isEmpty() && !sdescripcion.isEmpty() && !sduracion.isEmpty() && !sfecha.isEmpty();
    }

    private void introListaSpinner(Spinner spinner, List<Categoria> listaCategorias) {
        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("Sin definir");
        for (Categoria elemento : listaCategorias) {
            nombres.add(elemento.getNombre());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Comprobamos a qué petición se está respondiendo
        if (requestCode == GESTION_CATEGORIA) {
            // Nos aseguramos que el resultado fue OK
            if (resultCode == RESULT_OK) {
                Categoria cateAux = data.getParcelableExtra(CATEGORIA_MODIFICADA);
                //String cad= data.getStringExtra(CATEGORIA_MODIFICADA);
                Log.d("FavMovies.MainActivity", cateAux.toString());

                if (creandoCategoria) {
                    // añadimos categoría a la lista
                    listaCategorias.add(cateAux);
                    introListaSpinner(spinner, listaCategorias);
                } else {
                    // busca la categoría del mismo nombre en la lista y cambia la descripción
                    for (Categoria cat : listaCategorias) {
                        if (cat.getNombre().equals(cateAux.getNombre())) {
                            cat.setDescripcion(cateAux.getDescripcion());
                            Log.d("FavMovies.MainActivity", "Modificada la descripción de: " + cat.getNombre());
                            break;
                        }

                    }
                }

            } else if (resultCode == RESULT_CANCELED) {
                Log.d("FavMovie.MainActivity", "CategoriaActivity cancelada");
            }

        }


    }
}










    /*  @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(),getString(R.string.onStart), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(),getString(R.string.onPause), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(),getString(R.string.onRestart), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(),getString(R.string.onStop), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(),getString(R.string.onDestroy), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(),getString(R.string.onResume), Toast.LENGTH_SHORT).show();
    }*/
