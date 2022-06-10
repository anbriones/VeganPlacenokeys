package com.example.veganplace.ui.recetas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veganplace.R;

public class RecetasFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecetasViewModel recetasdViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
          View root = inflater.inflate(R.layout.fragment_recetas, container, false);


        recyclerView = (RecyclerView) root.findViewById(R.id.listadorecetas);
        assert (recyclerView) != null;
        recyclerView.setHasFixedSize(true);
        return root;
    }
}