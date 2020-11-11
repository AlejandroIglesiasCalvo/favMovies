package es.uniovi.eii.sdm.datos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.sdm.modelo.RepartoPelicula;

public class RepartoPeliculaDataSource {

    private SQLiteDatabase database;

    private MyDBHelper dbHelper;

    private final String[] allColumns = {MyDBHelper.COLUMNA_ID_REPARTO, MyDBHelper.COLUMNA_ID_PELICULAS,
            MyDBHelper.COLUMNA_PERSONAJE};

    public RepartoPeliculaDataSource(Context context) {
        //el último parámetro es la versión
        dbHelper = new MyDBHelper(context, null, null, 1);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();

    }

    public void close() {
        dbHelper.close();
    }

    public long creactoReparto(RepartoPelicula repartoToInsert) {
        ContentValues values = new ContentValues();

        values.put(MyDBHelper.COLUMNA_ID_REPARTO, repartoToInsert.getId_actor());
        values.put(MyDBHelper.COLUMNA_ID_PELICULAS, repartoToInsert.getId_pelicula());
        values.put(MyDBHelper.COLUMNA_PERSONAJE, repartoToInsert.getNombre_personaje());

        long insertId =
                database.insert(MyDBHelper.TABLA_PELICULAS_REPARTO, null, values);

        return insertId;
    }

    public List<RepartoPelicula> getAllValirations() {
        List<RepartoPelicula> repartoList = new ArrayList<RepartoPelicula>();

        Cursor cursor = database.query(MyDBHelper.TABLA_PELICULAS_REPARTO, allColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final RepartoPelicula reparto = new RepartoPelicula();
            reparto.setId_actor(cursor.getInt(0));
            reparto.setId_pelicula(cursor.getInt(1));
            reparto.setNombre_personaje(cursor.getString(3));

            repartoList.add(reparto);
            cursor.moveToNext();
        }
        cursor.close();


        return repartoList;
    }
}