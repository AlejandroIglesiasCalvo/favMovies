package es.uniovi.eii.sdm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import es.uniovi.eii.sdm.R;

@SuppressWarnings("SpellCheckingInspection")
public class infoFragment extends Fragment {

    public static final String ESTRENO = "estreno";
    public static final String DURACION = "duracion";
    public static final String CARATULA = "caratula";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_info, container, false);

        //Referencias componentes
        final TextView testreno = root.findViewById(R.id.fechaId);
        final TextView tduracion = root.findViewById(R.id.duracionId);
        ImageView caratula = root.findViewById((R.id.imagenID));

        Bundle args = getArguments();
        if (args != null) {
            testreno.setText(args.getString(ESTRENO));
            tduracion.setText(args.getString(DURACION));

            Picasso.get()
                    .load(args.getString(CARATULA)).into(caratula);
        }
        return root;
    }
}