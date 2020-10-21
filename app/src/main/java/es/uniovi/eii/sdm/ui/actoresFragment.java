package es.uniovi.eii.sdm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.uniovi.eii.sdm.R;


public class actoresFragment extends Fragment {

    public static final String ACTORES = "actores";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_actores, container, false);

        //Referencias componentes
        final TextView textaactores = root.findViewById(R.id.actoresID);

        Bundle args = getArguments();
        if (args != null) {
            textaactores.setText(args.getString(ACTORES));
        }
        return root;
    }
}