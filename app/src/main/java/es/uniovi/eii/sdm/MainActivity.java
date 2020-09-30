package es.uniovi.eii.sdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private Snackbar msgCreaCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toast.makeText(getApplicationContext(),getString(R.string.OnCreate), Toast.LENGTH_SHORT).show();

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

    }

    private void modificarCategoria() {
        Intent categoriaIntent = new Intent(MainActivity.this, CategoriaActivity.class);
        startActivity(categoriaIntent);
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
}