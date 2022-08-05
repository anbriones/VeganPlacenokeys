package com.example.veganplace.ui.noticias;

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
import com.example.veganplace.data.modelnoticias.Article;

import java.io.Serializable;
import java.util.ArrayList;

public class NoticiasFragment extends Fragment implements AdapterNoticias.OnListInteractionListener{
    private RecyclerView recyclerView;
    private AdapterNoticias mAdapter;
    AppContainer appContainer;
    NoticiasViewModel mViewModel;
    private RecyclerView.LayoutManager layoutManager;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_noticias, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.listadonoticias);
        assert (recyclerView) != null;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new AdapterNoticias(new ArrayList<Article>(),this);
        recyclerView.setAdapter(mAdapter);
        NoticiasViewModelFactory factorynoticias = InjectorUtils.provideMainActivityViewModelFactorynoticias(this.getActivity().getApplicationContext());
        appContainer = ((MyApplication) this.getActivity().getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, appContainer.factorynoticias).get(NoticiasViewModel.class);

        mViewModel.getnoticias().observe(this.getActivity(), noticias -> {
            mAdapter.swap(noticias);
        });




        return root;
    }


    @Override
    public void onListInteraction(Article articulo) {
        Intent intentnoticias = new Intent(this.getActivity(), NoticiaenlaWeb.class);
        intentnoticias.putExtra("noticiasweb", (Serializable) articulo);
        startActivity(intentnoticias) ;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}