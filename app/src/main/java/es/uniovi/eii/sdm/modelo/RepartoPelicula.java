package es.uniovi.eii.sdm.modelo;

import android.os.Parcel;
import android.os.Parcelable;
@SuppressWarnings("SpellCheckingInspection")
public class RepartoPelicula implements Parcelable {
    private int id_pelicula;
    private int id_actor;
    private String nombre_personaje;

    public RepartoPelicula(int id_pelicula, int id_actor, String nombre_personaje) {
        this.id_pelicula = id_pelicula;
        this.id_actor = id_actor;
        this.nombre_personaje = nombre_personaje;
    }


    public static final Creator<RepartoPelicula> CREATOR = new Creator<RepartoPelicula>() {
        @Override
        public RepartoPelicula createFromParcel(Parcel in) {
            return new RepartoPelicula(in);
        }

        @Override
        public RepartoPelicula[] newArray(int size) {
            return new RepartoPelicula[size];
        }
    };

    public RepartoPelicula() {

    }

    public int getId_pelicula() {
        return id_pelicula;
    }

    public void setId_pelicula(int id_pelicula) {
        this.id_pelicula = id_pelicula;
    }

    public int getId_actor() {
        return id_actor;
    }

    public void setId_actor(int id_actor) {
        this.id_actor = id_actor;
    }

    public String getNombre_personaje() {
        return nombre_personaje;
    }

    public void setNombre_personaje(String nombre_personaje) {
        this.nombre_personaje = nombre_personaje;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_pelicula);
        dest.writeInt(id_actor);
        dest.writeString(nombre_personaje);
    }

    protected RepartoPelicula(Parcel in) {
        id_actor = in.readInt();
        id_actor = in.readInt();
        nombre_personaje = in.readString();
    }

}
