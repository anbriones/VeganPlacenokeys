package com.example.veganplace.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.veganplace.AppContainer;
import com.example.veganplace.InjectorUtils;
import com.example.veganplace.MyApplication;
import com.example.veganplace.R;
import com.example.veganplace.data.modelusuario.User;

public class Registro extends AppCompatActivity {
    EditText nombre;
    EditText password;
    String nombre_g;
    String password_g;
    Button registrar;
    LoginViewModel loginViewModel;
    AppContainer appContainer;
    LoginViewModelFactory factory;
    boolean registrado;
    boolean buscado;

    private static final String LOG_TAG = Registro.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nombre = findViewById(R.id.username);
        password = findViewById(R.id.password);


        registrar = findViewById(R.id.resgitro);
        factory = InjectorUtils.provideMainActivityViewModelFactorylogin(getApplicationContext());
        appContainer = ((MyApplication) getApplication()).appContainer;
        loginViewModel = new ViewModelProvider(this, appContainer.factoryusers).get(LoginViewModel.class);


        registrar.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             nombre_g = nombre.getText().toString();
                                             password_g = password.getText().toString();
                                             if (nombre_g.isEmpty() || password_g.isEmpty()) {
                                                 Toast.makeText(getApplicationContext(), "Debes de rellenar los dos campos", Toast.LENGTH_SHORT).show();
                                             } else {
                                                 loginViewModel.setnombre(nombre_g);
                                                 final LiveData<User> userDetailObservable = loginViewModel.getusuario();
                                                 userDetailObservable.observe(Registro.this, user -> {
                                                             if (user != null && user.getDisplayName().equals(nombre_g.toString()))
                                                                 Toast.makeText(Registro.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                                                             if (user == null && nombre_g!="") {
                                                                 User usuario = new User();
                                                                 usuario.setDisplayName(nombre_g.toString());
                                                                 usuario.setPassword(password_g.toString());
                                                                 if(usuario.getDisplayName()!="") {
                                                                     loginViewModel.insertarusuario(usuario);
                                                                 }
                                                                 nombre_g = "";
                                                                 Toast.makeText(getApplicationContext(), "Usuario resgitrado", Toast.LENGTH_SHORT).show();
                                                                 Intent inicio = new Intent(getApplicationContext(), LoginActivity.class);
                                                                 startActivity(inicio);
                                                             }
                                                         }
                                                 );
                                             }
                                         }
                                     }
        );
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