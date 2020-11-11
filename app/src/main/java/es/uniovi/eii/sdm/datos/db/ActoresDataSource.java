package es.uniovi.eii.sdm.datos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.sdm.modelo.Actor;

public class ActoresDataSource {

    /**
     * Referencia para manejar la base de datos. Este objeto lo obtenemos a partir de MyDBHelper
     * y nos proporciona metodos para hacer operaciones
     * CRUD (create, read, update and delete)
     */
    private SQLiteDatabase database;
    /**
     * Referencia al helper que se encarga de crear y actualizar la base de datos.
     */
    private MyDBHelper dbHelper;
    /**
     * Columnas de la tabla
     */
    private final String[] allColumns = {MyDBHelper.COLUMNA_ID_REPARTO, MyDBHelper.COLUMNA_NOMBRE_ACTOR,
            MyDBHelper.COLUMNA_IMAGEN_ACTOR, MyDBHelper.COLUMNA_URL_imdb};

    /**
     * Constructor.
     *
     * @param context
     */
    public ActoresDataSource(Context context) {
        //el último parámetro es la versión
        dbHelper = new MyDBHelper(context, null, null, 1);
    }

    /**
     * Abre una conexion para escritura con la base de datos.
     * Esto lo hace a traves del helper con la llamada a getWritableDatabase. Si la base de
     * datos no esta creada, el helper se encargara de llamar a onCreate
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();

    }

    /**
     * Cierra la conexion con la base de datos
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Recibe la película y crea el registro en la base de datos.
     *
     * @param actor
     * @return
     */
    public long createactor(Actor actor) {
        // Establecemos los valores que se insertaran
        ContentValues values = new ContentValues();

        values.put(MyDBHelper.COLUMNA_ID_REPARTO, actor.getId());
        values.put(MyDBHelper.COLUMNA_NOMBRE_ACTOR, actor.getNombre_actor());
        values.put(MyDBHelper.COLUMNA_IMAGEN_ACTOR, actor.getImage());
        values.put(MyDBHelper.COLUMNA_URL_imdb, actor.getImdb());


        // Insertamos la valoracion
        long insertId =
                database.insert(MyDBHelper.TABLA_REPARTO, null, values);

        return insertId;
    }

    /**
     * Obtiene todas las valoraciones andadidas por los usuarios.
     *
     * @return Lista de objetos de tipo Pelicula
     */
    public List<Actor> getAllActors() {
        // Lista que almacenara el resultado
        List<Actor> actorsList = new ArrayList<Actor>();
        //hacemos una query porque queremos devolver un cursor

        Cursor cursor = database.query(MyDBHelper.TABLA_PELICULAS, allColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final Actor actor = new Actor();
            actor.setId(cursor.getInt(0));
            actor.setNombre_actor(cursor.getString(1));
            actor.setImage("https://image.tmdb.org/t/p/original/" +cursor.getString(2));
            actor.setImdb("https://image.tmdb.org/t/p/original/" + cursor.getString(3));
            actorsList.add(actor);
            cursor.moveToNext();
        }

        cursor.close();
        // Una vez obtenidos todos los datos y cerrado el cursor, devolvemos la
        // lista.

        return actorsList;
    }


    /**
     * Obtiene todas las valoraciones andadidas por los usuarios con el filtro introducido en el SQL.
     *
     * @return Lista de objetos de tipo Pelicula
     */
    public List<Actor> getFilteredActorss(int id_pelicula) {
        // Lista que almacenara el resultado
        List<Actor> actorsList = new ArrayList<Actor>();
        //hacemos una query porque queremos devolver un cursor

        Cursor cursor = database.rawQuery("SELECT " +
                MyDBHelper.TABLA_REPARTO + "." + MyDBHelper.COLUMNA_NOMBRE_ACTOR + ", " +
                MyDBHelper.TABLA_PELICULAS_REPARTO + "." + MyDBHelper.COLUMNA_PERSONAJE + ", " +
                MyDBHelper.TABLA_REPARTO + "." + MyDBHelper.COLUMNA_IMAGEN_ACTOR + ", " +
                MyDBHelper.TABLA_REPARTO + "." + MyDBHelper.COLUMNA_URL_imdb +
                " FROM " + MyDBHelper.TABLA_PELICULAS_REPARTO +
                " JOIN " + MyDBHelper.TABLA_REPARTO + " ON " +
                MyDBHelper.TABLA_PELICULAS_REPARTO + "." + MyDBHelper.COLUMNA_ID_REPARTO +
                " = " + MyDBHelper.TABLA_REPARTO + "." + MyDBHelper.COLUMNA_ID_REPARTO +
                " WHERE " + MyDBHelper.TABLA_PELICULAS_REPARTO + "." + MyDBHelper.COLUMNA_ID_PELICULAS + " = " + id_pelicula, null);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final Actor actor = new Actor();
            actor.setId(cursor.getInt(0));
            actor.setNombre_actor(cursor.getString(1));
            actor.setImage(cursor.getString(2));
            actor.setImdb(cursor.getString(3));


            actorsList.add(actor);
            cursor.moveToNext();
        }

        cursor.close();
        // Una vez obtenidos todos los datos y cerrado el cursor, devolvemos la
        // lista.

        return actorsList;
    }


}
