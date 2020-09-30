package es.uniovi.eii.sdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import es.uniovi.eii.sdm.modelo.Categoria;

public class CategoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        //Recepcion de datos
        Intent intent= getIntent();
        int posCategoria = intent.getIntExtra(MainActivity.POS_CATEGORIA_SELECCIONADA,0);
        Categoria categEntrada=null;
         if(posCategoria>0){
             categEntrada=intent.getParcelableExtra(MainActivity.POS_CATEGORIA_SELECCIONADA);
         }
    }
}