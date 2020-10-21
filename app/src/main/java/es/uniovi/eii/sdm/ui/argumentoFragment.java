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


public class argumentoFragment extends Fragment {

    public static final String ARGUMENTO = "argumento";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_argumento, container, false);

        //Referencias componentes
        final TextView textargumento = root.findViewById(R.id.argumentoID);

        Bundle args = getArguments();
        if (args != null) {
            textargumento.setText(args.getString(ARGUMENTO));
        }
        return root;
    }
}