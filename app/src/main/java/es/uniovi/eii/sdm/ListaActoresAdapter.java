package es.uniovi.eii.sdm;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.uniovi.eii.sdm.modelo.Actor;
import es.uniovi.eii.sdm.modelo.Pelicula;
@SuppressWarnings("SpellCheckingInspection")
public class ListaActoresAdapter extends RecyclerView.Adapter<ListaActoresAdapter.ActoresViewHolder> {

    // Interfaz para manejar el evento click sobre un elemento
    public interface OnItemClickListener {
        void onItemClick(Actor item);
    }

    private List<Actor> listaActores;
    private final ListaActoresAdapter.OnItemClickListener listener;

    public ListaActoresAdapter(List<Actor> listaPeli, ListaActoresAdapter.OnItemClickListener listener) {
        this.listaActores = listaPeli;
        this.listener = listener;
    }

    /* Indicamos el layout a "inflar" para usar en la vista
     */
    @NonNull
    @Override
    public ActoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creamos la vista con el layout para un elemento
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linea_recycler_view_actor, parent, false);
        return new ActoresViewHolder(itemView);
    }


    /**
     * Asocia el contenido a los componentes de la vista,
     * concretamente con nuestro PeliculaViewHolder que recibimos como parámetro
     */
    @Override
    public void onBindViewHolder(@NonNull ListaActoresAdapter.ActoresViewHolder holder, int position) {
        // Extrae de la lista el elemento indicado por posición
        Actor pelicula = listaActores.get(position);
        Log.i("Lista", "Visualiza elemento: " + pelicula);
        // llama al método de nuestro holder para asignar valores a los componentes
        // además, pasamos el listener del evento onClick
        holder.bindUser(pelicula, listener);
    }

    @Override
    public int getItemCount() {
        return listaActores.size();
    }


    /*Clase interna que define los compoonentes de la vista*/
    public static class ActoresViewHolder extends RecyclerView.ViewHolder {

        private TextView titulo;
        private TextView fecha;
        private ImageView imagen;

        public ActoresViewHolder(View itemView) {
            super(itemView);

            titulo = (TextView) itemView.findViewById(R.id.titulopeli);
            fecha = (TextView) itemView.findViewById(R.id.fechaestreno);
            imagen = (ImageView) itemView.findViewById(R.id.imagen);
        }

        // asignar valores a los componentes
        public void bindUser(final Actor pelicula, final ListaActoresAdapter.OnItemClickListener listener) {
            titulo.setText(pelicula.getNombre_actor());
            //fecha.setText(pelicula.getFecha());
            fecha.setText(pelicula.getNombre_personaje());
            Picasso.get().load(pelicula.getImage()).into(imagen);
            // cargar imagen
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Hola", "Hola");
                    listener.onItemClick(pelicula);
                }
            });
        }
    }
}
