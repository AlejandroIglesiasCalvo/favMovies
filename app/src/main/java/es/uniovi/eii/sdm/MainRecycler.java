package es.uniovi.eii.sdm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.sdm.datos.ActoresDataSource;
import es.uniovi.eii.sdm.datos.PeliculasDataSource;
import es.uniovi.eii.sdm.datos.RepartoPeliculaDataSource;
import es.uniovi.eii.sdm.modelo.Actor;
import es.uniovi.eii.sdm.modelo.Categoria;
import es.uniovi.eii.sdm.modelo.Pelicula;
import es.uniovi.eii.sdm.modelo.RepartoPelicula;


public class MainRecycler extends AppCompatActivity {
    public static String filtrocategoria = null;
    RecyclerView listaPeliView;
    Pelicula peli;
    List<Pelicula> ListaPeli;
    List<RepartoPelicula> listaReparto;
    List<Actor> listaActor;
    public static final String PELICULA_SELECCIONADA = null;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);
        ConstruirNotificacion(getString(R.string.app_name), "Acceso a la BD de peliculas");
        //Lanzamos la tarea asíncrona en segundo término
        DonwloadFilesTask task = new DonwloadFilesTask();
        task.execute();
    }

    protected void cargarView() {
        PeliculasDataSource peliculasDataSource = new PeliculasDataSource(getApplicationContext());
        peliculasDataSource.open();
        if (filtrocategoria == null) {
            ListaPeli = peliculasDataSource.getAllValorations();
        } else {
            ListaPeli = peliculasDataSource.getFilteredValorations(filtrocategoria);
        }

        peliculasDataSource.close();
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
            Intent intentSettingsActivity = new Intent(MainRecycler.this, SettingsActivity.class);
            startActivity(intentSettingsActivity);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DonwloadFilesTask extends AsyncTask<Void, Integer, String> {
        private ProgressDialog progressDialog;
        private float lineasALeer = 0.0f;
        float numeroLineasLeidas = 0.0f;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(MainRecycler.this);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();

            //Inicializamos el lineasALeer, con un repaso a la cantidad de líneas que tienen los ficheros.
            lineasALeer = (float) (lineasFichero("peliculas.csv"));
            lineasALeer = (float) (lineasALeer + lineasFichero("peliculas-reparto.csv"));
            lineasALeer = (float) (lineasALeer + lineasFichero("reparto.csv"));


        }

        private float lineasFichero(String nombreFichero) {
            InputStream file = null;
            InputStreamReader reader = null;
            BufferedReader bufferedReader = null;
            int lineas = 0;

            try {
                file = getAssets().open(nombreFichero);
                reader = new InputStreamReader(file);
                bufferedReader = new BufferedReader(reader);

                //Pase rápido mirando el total de líneas,
                // sin perder tiempo de procesamiento en nada más
                //Necesario para el progressbar
                while (bufferedReader.readLine() != null)
                    lineas++;

                bufferedReader.close();
            } catch (Exception e) {
            }
            ;
            return lineas;
        }

        @Override
        protected String doInBackground(Void... voids) {
            //El mensaje que vamos a mostrar como notificación
            String mensaje = "";

            try {
                //Cargamos la base de datos.
                cargarPeliculas();
                //cargarReparto();
                //cargarRepartoPelicula();

                //Si la carga no da ningún error inesperado...
                mensaje = "Lista de películas actualizada";

            } catch (Exception e) {
                //Si la carga da algún error
                mensaje = "Error en la actualización de la lista de películas";
            }
            //Lanzamos notificación.
            mNotificationManager.notify(001, mBuilder.build());
            return mensaje;
        }

        protected void onProgressUpdate(Integer... progress) {
            progressDialog.setProgress(progress[0]);
        }

        protected void onPostExecute(String message) {
            //descartar el mensaje después de que la base de datos haya sido actualizada
            this.progressDialog.dismiss();
            //Avisamos que la base de datos se cargó satisfactoriamente (o hubo error, según lo que haya ocurrido)
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            //Y cargamos el recyclerview por primera vez.
            //Este método ya no tiene sentido llamarlo desde el onCreate u onResume, pues necesitamos asegurarnos
            //de haber cargado la base de datos antes de lanzarlo.
            cargarView();
        }

        protected void cargarPeliculas() {
        /*si una película le falta la caratual, el fondo o el trailer, le pongo unos por defecto. De esta manera me aseguro
             estos campos en las películas*/
            String Caratula_por_defecto = "https://image.tmdb.org/t/p/original/jnFCk7qGGWop2DgfnJXeKLZFuBq.jpg\n";
            String fondo_por_defecto = "https://image.tmdb.org/t/p/original/xJWPZIYOEFIjZpBL7SVBGnzRYXp.jpg\n";
            String trailer_por_defecto = "https://www.youtube.com/watch?v=lpEJVgysiWs\n";
            Pelicula peli;

            InputStream file = null;
            InputStreamReader reader = null;
            BufferedReader bufferedReader = null;
            //ACTUALIZAMOS EL PROGRESSBAR

            try {
                file = getAssets().open("peliculas.csv");
                reader = new InputStreamReader(file);
                bufferedReader = new BufferedReader(reader);
                String line = null;
                bufferedReader.readLine();
                numeroLineasLeidas++;
                publishProgress((int) ((numeroLineasLeidas / lineasALeer) * 100));
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data != null && data.length >= 5) {
                        if (data.length == 9) {
                            peli = new Pelicula(Integer.parseInt(data[0]), data[1], data[2], new Categoria(data[3], ""), data[4], data[5], data[6], data[7], data[8]);
                        } else {
                            peli = new Pelicula(Integer.parseInt(data[0]), data[1], data[2], new Categoria(data[3], ""), data[4], data[5], "", "", "");
                        }
                        Log.d("cargarPeliculas", peli.toString());
                        //ListaPeli.add(peli);

                        //Metemos la película en la base de datos:
                        PeliculasDataSource peliculasDataSource = new PeliculasDataSource(getApplicationContext());
                        peliculasDataSource.open();
                        peliculasDataSource.createpelicula(peli);
                        peliculasDataSource.close();
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

        private void cargarRepartoPelicula() {
            RepartoPelicula reparto;
            listaReparto = new ArrayList<RepartoPelicula>();
            InputStream file = null;
            InputStreamReader reader = null;
            BufferedReader bufferedReader = null;

            try {
                file = getAssets().open("peliculas-reparto.csv");
                reader = new InputStreamReader(file);
                bufferedReader = new BufferedReader(reader);

                String line = null;

                bufferedReader.readLine();

                numeroLineasLeidas++;
                publishProgress((int) ((numeroLineasLeidas / lineasALeer) * 100));

                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data.length == 3) {
                        reparto = new RepartoPelicula(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2]);
                        Log.d("cargarReparto", reparto.toString());
                        RepartoPeliculaDataSource repartoDataSource = new RepartoPeliculaDataSource(getApplicationContext());
                        repartoDataSource.open();
                        repartoDataSource.creactoReparto(reparto);
                        repartoDataSource.close();

                        numeroLineasLeidas++;
                        publishProgress((int) ((numeroLineasLeidas / lineasALeer) * 100));
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

        private void cargarReparto() {
            Actor actor;
            listaActor = new ArrayList<Actor>();
            InputStream file = null;
            InputStreamReader reader = null;
            BufferedReader bufferedReader = null;

            try {
                file = getAssets().open("reparto.csv");
                reader = new InputStreamReader(file);
                bufferedReader = new BufferedReader(reader);

                String line = null;

                bufferedReader.readLine();
                numeroLineasLeidas++;
                publishProgress((int) ((numeroLineasLeidas / lineasALeer) * 100));
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data.length == 4) {
                        actor = new Actor(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4]);
                        Log.d("cargarReparto", actor.toString());
                        ActoresDataSource actorDataSource = new ActoresDataSource(getApplicationContext());
                        actorDataSource.open();
                        actorDataSource.createactor(actor);
                        actorDataSource.close();
                        numeroLineasLeidas++;
                        publishProgress((int) ((numeroLineasLeidas / lineasALeer) * 100));
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
    }

    private void crearNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "CANAL";
            String description = "DESCRIPCION CANAL";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("M_CH_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void ConstruirNotificacion(String titulo, String contenido) {

        crearNotificationChannel(); //Para la versión Oreo es necesario primero crear el canal
        //Instancia del servicio de notificaciones
        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //construcción de la notificación
        mBuilder = new NotificationCompat.Builder(getApplicationContext(), "M_CH_ID");
        mBuilder.setSmallIcon(R.drawable.ic_dashboard_black_24dp)
                .setContentTitle(titulo)
                .setContentText(contenido);
    }
}

