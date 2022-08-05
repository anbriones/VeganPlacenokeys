package com.example.veganplace.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.veganplace.AppContainer;
import com.example.veganplace.MainActivity;
import com.example.veganplace.MyApplication;
import com.example.veganplace.R;
import com.example.veganplace.data.modelusuario.Resenia;
import com.example.veganplace.data.modelusuario.User;
import com.example.veganplace.ui.home.Adapterresenias;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Perfilusuario extends AppCompatActivity {
    ImageView imageView;
     User user = new User();
    private RecyclerView recyclerView;
    private Adapterresenias mAdapter;
    AppContainer appContainer;
    private RecyclerView.LayoutManager layoutManager;
    List<Resenia> resenias = new ArrayList<Resenia>();
    private static final String LOG_TAG = Perfilusuario.class.getSimpleName();
    FirebaseStorage storage = FirebaseStorage.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfilusuario);
        Toolbar toolbar = findViewById(R.id.toolbarperfil);
        setSupportActionBar(toolbar);
        MyApplication appState = ((MyApplication) getApplicationContext());
//Esta actividad puede ser incializada mediante dos puntos, por lo que dependiendo desde que
        if (MyApplication.usuario != null) {
            user = MyApplication.usuario;
        } else {
            user = (User) getIntent().getSerializableExtra("usuario");
        }


        recyclerView = (RecyclerView) this.findViewById(R.id.reseñas);
        assert (recyclerView) != null;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        appContainer = ((MyApplication) this.getApplication()).appContainer;

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Resenia").whereEqualTo("name_user", MyApplication.usuario.getNombre())
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
                            }
                            mAdapter = new Adapterresenias(new ArrayList<Resenia>());
                            recyclerView.setAdapter(mAdapter);

                            mAdapter.swap(resenias);
                            Log.d(LOG_TAG, "tam resenias" + resenias.size());
                        } else {
                            Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });


        TextView nombre = this.findViewById(R.id.nombre_user);
        nombre.setText(user.getNombre().toString().toUpperCase().substring(0,1)+user.getNombre().toString().substring(1,user.getNombre().length()));


        ImageButton logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                MyApplication.usuario = null;
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Perfilusuario.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Username", "Sin definir"); //This is just an example, you could also put boolean, long, int or floats
                editor.commit();
                Intent intentinicio = new Intent(getApplication(), MainActivity.class);
                startActivity(intentinicio);
            }
        });


        imageView = (ImageView) findViewById(R.id.img_user);
        String url = user.getDirimagen().toString();


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReference();

        ref.child(url).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext())
                        .load(uri)
                        .fitCenter()
                        .override(400, 400)// look here
                        .into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(LOG_TAG, "LA URI es: " + ref.toString(), exception);
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



