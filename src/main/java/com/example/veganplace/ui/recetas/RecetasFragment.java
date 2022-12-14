package com.example.veganplace.ui.recetas;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veganplace.AppContainer;
import com.example.veganplace.InjectorUtils;
import com.example.veganplace.MyApplication;
import com.example.veganplace.R;
import com.example.veganplace.data.modelrecetas.Recipe;
import com.example.veganplace.ui.detallesreceta.detallesreceta;

import java.io.Serializable;
import java.util.ArrayList;

public class RecetasFragment extends Fragment implements AdapterRecetas.OnListInteractionListener{
    private RecyclerView recyclerView;
    private AdapterRecetas mAdapter;
    AppContainer appContainer;
    RecetasViewModel mViewModel;
    private RecyclerView.LayoutManager layoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recetas, container, false);


        recyclerView = (RecyclerView) root.findViewById(R.id.listadorecetasrecicle);
        assert (recyclerView) != null;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new AdapterRecetas(new ArrayList<Recipe>(),this);
        recyclerView.setAdapter(mAdapter);
        RecetasViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactoryhome(this.getActivity().getApplicationContext());
        appContainer = ((MyApplication) this.getActivity().getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, appContainer.factoryrecetas).get(RecetasViewModel.class);

        mViewModel.getRecetas().observe(this.getActivity(), recetas -> {
         mAdapter.swap(recetas);
        });
        return root;
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onListInteraction(Recipe receta) {
       Intent intentdetalles = new Intent(this.getActivity(), detallesreceta.class);
        intentdetalles.putExtra("recetasdetalles", (Serializable) receta);
       startActivity(intentdetalles) ;
    }


}