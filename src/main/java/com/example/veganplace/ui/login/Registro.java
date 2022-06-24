package com.example.veganplace.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class Registro extends AppCompatActivity {
    EditText nombre;
    EditText password;

    Button registrar;
    LoginViewModel loginViewModel;
    AppContainer appContainer;
    LoginViewModelFactory factory;


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
                                         public void onClick(View v) {
                                             String nombre_g = nombre.getText().toString();
                                             String password_g = password.getText().toString();
                                             if (nombre_g.isEmpty() || password_g.isEmpty()) {
                                                 Toast.makeText(getApplicationContext(), "Debes de rellenar los dos campos", Toast.LENGTH_SHORT).show();
                                             }
                                             else {
                                                 loginViewModel.setnombre(nombre_g);
                                                 loginViewModel.getusuario().observe(Registro.this, user -> {
                                                     if (user != null) {
                                                          Toast.makeText(Registro.this, " El usuario" + user.getDisplayName() + " ya existe ", Toast.LENGTH_SHORT).show();

                                                     }
                                                     else{
                                                         User usuario = new User();
                                                         usuario.setDisplayName(nombre_g.toString());
                                                         usuario.setPassword(password_g.toString());
                                                         loginViewModel.insertarusuario(usuario);
                                                         Toast.makeText(Registro.this, " Usuario registrado ", Toast.LENGTH_SHORT).show();
                                                            Intent inicio=new Intent(getApplicationContext(),LoginActivity.class);
                                                         startActivity(inicio);
                                                     }


                                                 });


                                             }

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