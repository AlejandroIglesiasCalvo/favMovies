package es.uniovi.eii.sdm.modelo;

import android.os.Parcel;
import android.os.Parcelable;
@SuppressWarnings("SpellCheckingInspection")
public class Actor implements Parcelable {
    private int id;
    private String nombre_actor;
    private String nombre_personaje;
    private String image;
    private String imdb;

    public Actor(int id, String nombre_actor, String nombre_personaje, String image, String imdb) {
        this.id = id;
        this.nombre_actor = nombre_actor;
        this.nombre_personaje = nombre_personaje;
        this.image = image;
        this.imdb = imdb;
    }

    protected Actor(Parcel in) {
        id = in.readInt();
        nombre_actor = in.readString();
        nombre_personaje = in.readString();
        image = in.readParcelable(Categoria.class.getClassLoader());
        imdb = in.readString();
    }

    public static final Creator<Actor> CREATOR = new Creator<Actor>() {
        @Override
        public Actor createFromParcel(Parcel in) {
            return new Actor(in);
        }

        @Override
        public Actor[] newArray(int size) {
            return new Actor[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre_actor);
        dest.writeString(nombre_personaje);
        dest.writeString(image);
        dest.writeString(imdb);
    }

    public Actor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_actor() {
        return nombre_actor;
    }

    public void setNombre_actor(String nombre_actor) {
        this.nombre_actor = nombre_actor;
    }

    public String getNombre_personaje() {
        return nombre_personaje;
    }

    public void setNombre_personaje(String nombre_personaje) {
        this.nombre_personaje = nombre_personaje;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
