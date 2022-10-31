package com.example.veganplace.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.veganplace.AppContainer;
import com.example.veganplace.InjectorUtils;
import com.example.veganplace.MainActivity;
import com.example.veganplace.MyApplication;
import com.example.veganplace.R;
import com.example.veganplace.data.modelmapas.Result;
import com.example.veganplace.data.modelusuario.Resenia;
import com.example.veganplace.ui.restaurantes.Adapterreseniastopyworst;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Restaurant_detail extends AppCompatActivity {
    ImageView image;
    private HomeViewModel homeViewModel;
    private AppContainer appContainer;
    private HomeViewModelFactory factory;
    private static final String LOG_TAG = Restaurant_detail.class.getSimpleName();


    private RecyclerView recyclerView;
    private Adapterreseniastopyworst mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detallerestaurante);
        Toolbar toolbar = findViewById(R.id.toolbarres);
        setSupportActionBar(toolbar);


        factory = InjectorUtils.provideMainActivityViewModelFactoryrestaurantes(this.getApplicationContext());
        appContainer = ((MyApplication) this.getApplication()).appContainer;
        homeViewModel = new ViewModelProvider(this, appContainer.factoryrestaurantes).get(HomeViewModel.class);

        Result res = (Result) getIntent().getSerializableExtra("restaurantedetalle");
        if (res != null) {
            image = findViewById(R.id.imageres);
            Glide.with(this).load(res.getIcon()).into(image);
        }

        recyclerView = (RecyclerView) findViewById(R.id.listadoreseniasresta);
        assert (recyclerView) != null;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mostrarresenias();

        TextView dir = this.findViewById(R.id.dir_res);
        dir.setText(res.getFormattedAddress().toString());


        TextView nombre = this.findViewById(R.id.nameres);
        nombre.setText(res.getName().toString());

        TextView rating = this.findViewById(R.id.ratingnumber);
        rating.setGravity(Gravity.CENTER);
        rating.setText("Rating \n"+res.getRating().toString());

        EditText descripcion = this.findViewById(R.id.descripcionresenia);
        descripcion.setVisibility(View.INVISIBLE);

        TextView message = this.findViewById(R.id.messagesendreview);
         message.setVisibility(View.INVISIBLE);

        RatingBar ratingbar = this.findViewById(R.id.ratingBar);
        ratingbar.setVisibility(View.INVISIBLE);


        Button button = (Button) findViewById(R.id.sendresenia);
        if(MyApplication.usuario==null){
            button.setEnabled(false);
        }
        button.setVisibility(View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Add the resenia into room database
                Resenia resenia = new Resenia();
                resenia.setName_user(  MyApplication.usuario.getNombre());
                resenia.setName_res(res.getName());
                resenia.setDir_res(res.getFormattedAddress());
                resenia.setDescripcion(descripcion.getText().toString());
                resenia.setId_resenia(res.getName()+MyApplication.usuario.getNombre());
                resenia.setValor(((double) ratingbar.getRating()));
               Toast.makeText(Restaurant_detail.this, "Review registered", Toast.LENGTH_SHORT).show();
                button.setEnabled(false);

                //Insertar en firebase
                homeViewModel.insertarreseniafirebase(resenia);
            }
        });
        Button bvalorar = (Button) findViewById(R.id.valorar);
        bvalorar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(bvalorar.getText().equals("Review")){
                    bvalorar.setText("See reviews");
                }
                else if(bvalorar.getText().equals("See reviews")){
                    bvalorar.setText("Review");
                }

                if (recyclerView.getVisibility() == View.INVISIBLE) {
                    mostrarresenias();
                    recyclerView.setVisibility(View.VISIBLE);
                    descripcion.setVisibility(View.INVISIBLE);
                    ratingbar.setVisibility(View.INVISIBLE);
                    button.setVisibility(View.INVISIBLE);
                    message.setVisibility(View.INVISIBLE);

                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    descripcion.setVisibility(View.VISIBLE);
                    ratingbar.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    if(MyApplication.usuario==null)
                    message.setVisibility(View.VISIBLE);
                }
            }
        });
    }



    public void mostrarresenias() {
        Result res = (Result) getIntent().getSerializableExtra("restaurantedetalle");
//Añado la resña en firebase
        List<Resenia> resenias = new ArrayList<Resenia>();

        db.collection("Resenia").
                whereEqualTo("name_res", res.getName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String desc = document.getString("descripcion");
                                String dir_res = document.getString("dir_res");
                                String id_resenia = document.getString("id_resenia");
                                String name_res = document.getString("name_res");
                                String name_user = document.getString("name_user");
                                Double valor = document.getDouble("valor");
                                double val = (double) valor;
                                Resenia res = new Resenia(id_resenia, name_user, name_res, dir_res, desc, val);
                                resenias.add(res);
                                Log.d(LOG_TAG, document.getId() + " => " + document.getData());

                            }
                            mAdapter = new Adapterreseniastopyworst(new ArrayList<Resenia>());
                            recyclerView.setAdapter(mAdapter);

                            mAdapter.swap(resenias);
                            Log.d(LOG_TAG, "tam resenias" + resenias.size());
                        } else {
                            Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }




}



