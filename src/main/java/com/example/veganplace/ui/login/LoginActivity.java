package com.example.veganplace.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.veganplace.AppContainer;
import com.example.veganplace.InjectorUtils;
import com.example.veganplace.MyApplication;
import com.example.veganplace.R;
import com.example.veganplace.data.modelusuario.User;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {
    EditText nombre;
    EditText password;
    EditText nombre2;
    EditText password2;
    Button IniciarSesion;
    Button registrar;
    LoginViewModel loginViewModel;
    AppContainer appContainer;
    LoginViewModelFactory factory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nombre = findViewById(R.id.username);
        password = findViewById(R.id.password);


        IniciarSesion = findViewById(R.id.login);
        registrar = findViewById(R.id.resgitro);
        factory = InjectorUtils.provideMainActivityViewModelFactorylogin(getApplicationContext());
        appContainer = ((MyApplication) getApplication()).appContainer;
        loginViewModel = new ViewModelProvider(this, appContainer.factoryusers).get(LoginViewModel.class);

        IniciarSesion.setOnClickListener(v -> {
                    String nombre_g = nombre.getText().toString();
                    String password_g = password.getText().toString();
                    if (nombre_g.isEmpty() || password_g.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Debes de rellenar los dos campos", Toast.LENGTH_SHORT).show();
                    } else {
                        loginViewModel.setnombreypassword(nombre_g, password_g);
                        loginViewModel.getusuarios().observe(LoginActivity.this, user -> {
                            if (user != null) {
                                MyApplication.usuario = user;
                                MyApplication.activo = true;
                                perfil(user);
                            } else {
                                Toast.makeText(getApplicationContext(), "Datos incorrectos, asegurate de escribirlos correctamente", Toast.LENGTH_SHORT).show();
                            }

                        });

                    }
                }
        );


    }

    public void perfil(User user) {
        Intent intentperfil = new Intent(this, Perfilusuario.class);
        intentperfil.putExtra("usuario", (Serializable) user);
        startActivity(intentperfil);
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