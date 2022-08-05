package com.example.veganplace.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.veganplace.MyApplication;
import com.example.veganplace.R;
import com.example.veganplace.data.modelusuario.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {
    EditText nombre;
    EditText password;
    Button IniciarSesion;
    String nombre_g;
    String password_g;
    private static final String LOG_TAG = Registro.class.getSimpleName();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nombre = findViewById(R.id.username);
        password = findViewById(R.id.password);

        IniciarSesion = findViewById(R.id.login);
                        IniciarSesion.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                nombre_g = nombre.getText().toString();
                                password_g = password.getText().toString();
                                if (nombre_g.isEmpty() || password_g.isEmpty()) {
                                    Toast.makeText(LoginActivity.this.getApplicationContext(), R.string.fields_not_filled, Toast.LENGTH_SHORT).show();
                                } else {
                                    db.collection("User").whereEqualTo("nombre", nombre_g).whereEqualTo("password",password_g)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        if (task.getResult().getDocuments().size()>0) {
                                                            Log.d(LOG_TAG, "Iniciando sesion", task.getException());
                                                            String nombre = task.getResult().getDocuments().get(0).getString("nombre");
                                                            String password = task.getResult().getDocuments().get(0).getString("password");
                                                            String email = task.getResult().getDocuments().get(0).getString("email");
                                                            String imagen = task.getResult().getDocuments().get(0).getString("dirimagen");
                                                            User user = new User(nombre,password,email,imagen);
                                                            MyApplication.usuario = user;
                                                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                                            SharedPreferences.Editor editor = preferences.edit();
                                                            editor.putString("Username", MyApplication.usuario.getNombre());
                                                            editor.commit();
                                                            Intent intentperfil = new Intent(LoginActivity.this, Perfilusuario.class);
                                                            intentperfil.putExtra("usuario", (Serializable) user);
                                                            startActivity(intentperfil);
                                                        }
                                                        else{
                                                            Toast.makeText(LoginActivity.this.getApplicationContext(), "User or password are incorrect", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                                                    }
                                                }
                                            });
                                }
                                }
                                 });
    }



@Override
public void onResume() {
    super.onResume();
  }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();



    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }

}