package es.uniovi.eii.sdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import es.uniovi.eii.sdm.modelo.Categoria;

public class CategoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        //Recepcion de datos
        Intent intent = getIntent();
        int posCategoria = intent.getIntExtra(MainActivity.POS_CATEGORIA_SELECCIONADA, 0);
        Categoria categEntrada = null;
        if (posCategoria > 0) {
            categEntrada = intent.getParcelableExtra(MainActivity.POS_CATEGORIA_SELECCIONADA);
        }

        TextView textViewCrea = (TextView) findViewById(R.id.TituloCrearCategoria);
        final EditText editNomCategoria = (EditText) findViewById(R.id.txtNombreCategoria);
        final EditText editDescripcion = (EditText) findViewById(R.id.txtDescripcionCategoria);
        // Recuperamos referencia al botón
        Button btnOk = (Button) findViewById(R.id.btnOK);
        Button btnCancel = (Button) findViewById(R.id.btnCancelar);
//cambiamos la etiqueta del titulo en funcion de si crea o modifica
        if (posCategoria == 0) {
            textViewCrea.setText(R.string.TituloCrearCategoria);
        } else {
            textViewCrea.setText(R.string.TituloModificarCategoria);
            editNomCategoria.setText(categEntrada.getNombre());
            editDescripcion.setText(categEntrada.getDescripcion());
            editNomCategoria.setEnabled(false);
        }

    }
}