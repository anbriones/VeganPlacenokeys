package com.example.veganplace.ui.restaurantes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veganplace.AppContainer;
import com.example.veganplace.MyApplication;
import com.example.veganplace.R;
import com.example.veganplace.data.modelusuario.Resenia;
import com.example.veganplace.ui.social.Conversacion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantesFragment extends Fragment implements Adapterreseniastopyworst.OnListInteractionListener{
    private RestaurantesViewModel restaurantesViewModel;
    private RecyclerView recyclerView;
    private Adapterreseniastopyworst mAdapter;
    AppContainer appContainer;
    private RecyclerView.LayoutManager layoutManager;
    private static final String LOG_TAG = RestaurantesFragment.class.getSimpleName();
    private Parcelable recyclerViewState;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_restaurantes, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.listadoresenias);
        assert (recyclerView) != null;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(mAdapter);
        appContainer = ((MyApplication) this.getActivity().getApplication()).appContainer;
        restaurantesViewModel = new ViewModelProvider(this, appContainer.factoryrestaurantestop).get(RestaurantesViewModel.class);

        todas();
        Button top = (Button) root.findViewById(R.id.top);
        Button worst = (Button) root.findViewById(R.id.worst);
        Button all = (Button) root.findViewById(R.id.all);

        top.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                db.collection("Resenia").
                        whereGreaterThan("valor" , 4)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                   adaptar(task);
                                }
                                else {
                                    Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });


        worst.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                db.collection("Resenia").
                        whereLessThan("valor" , 3)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    adaptar(task);
                                }
                                else {
                                    Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                todas();
            }

        });


        return root;
    }


    public void todas(){
        db.collection("Resenia")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            adaptar(task);
                        }
                        else {
                            Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

public void adaptar(Task<QuerySnapshot> task){
    mAdapter = new Adapterreseniastopyworst(new ArrayList<Resenia>(),this::onListInteraction);
    recyclerView.setAdapter(mAdapter);
    mAdapter.cargar(task);
}



    @Override
    public void onResume() {
        super.onResume();

    }





    @Override
    public void onDestroy() {
        super.onDestroy();



    }


    @Override
    public void onListInteraction(String nombre) {
        Intent intent = new Intent(this.getActivity(), Conversacion.class);
        intent.putExtra("user", (Serializable) nombre);
        startActivity(intent);

    }
}