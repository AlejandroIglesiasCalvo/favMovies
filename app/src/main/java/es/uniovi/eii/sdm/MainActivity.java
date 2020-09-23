package es.uniovi.eii.sdm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toast.makeText(getApplicationContext(),getString(R.string.OnCreate), Toast.LENGTH_SHORT).show();
        Button btnGuardar = (Button) findViewById(R.id.button);
        btnGuardar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(R.id.Layaut), R.string.msg_guardado,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }));

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