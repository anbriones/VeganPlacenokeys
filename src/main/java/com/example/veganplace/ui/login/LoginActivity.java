package com.example.veganplace.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.veganplace.AppContainer;
import com.example.veganplace.InjectorUtils;
import com.example.veganplace.MyApplication;
import com.example.veganplace.R;
import com.example.veganplace.data.modelusuario.User;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {
    EditText nombre;
    EditText password;
    Button IniciarSesion;
    Button registrar;
    LoginViewModel loginViewModel;
    AppContainer appContainer;
    LoginViewModelFactory factory;
    String nombre_g;
    String password_g;
boolean encontrado;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nombre = findViewById(R.id.username);
        password = findViewById(R.id.password);

        IniciarSesion = findViewById(R.id.login);

        factory = InjectorUtils.provideMainActivityViewModelFactorylogin(getApplicationContext());
        appContainer = ((MyApplication) getApplication()).appContainer;
        loginViewModel = new ViewModelProvider(this, appContainer.factoryusers).get(LoginViewModel.class);
        encontrado=false;
        loginViewModel.getusuarios().removeObservers(this);
        IniciarSesion.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 nombre_g = nombre.getText().toString();
                                                 password_g = password.getText().toString();
                                                 if (nombre_g.isEmpty() || password_g.isEmpty()) {
                                                     Toast.makeText(LoginActivity.this.getApplicationContext(), "Debes de rellenar los dos campos", Toast.LENGTH_SHORT).show();
                                                 } else {
                                                     loginViewModel.setnombreypassword(nombre_g, password_g);
                                                     loginViewModel.getusuarios().observe(LoginActivity.this, new Observer<User>() {
                                                         @Override
                                                         public void onChanged(@Nullable User user) {
                                                             if (user != null) {

                                                                 if(encontrado) {
                                                                     Toast.makeText(LoginActivity.this, "Iniciando sesión", Toast.LENGTH_SHORT).show();
                                                                 }
                                                                 MyApplication.usuario = user;
                                                                 SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


                                                                 SharedPreferences.Editor editor = preferences.edit();
                                                                 editor.putString("Username", MyApplication.usuario.getDisplayName()); //This is just an example, you could also put boolean, long, int or floats
                                                                 editor.commit();

                                                                 encontrado=true;
                                                                 Intent intentperfil = new Intent(LoginActivity.this, Perfilusuario.class);
                                                                 intentperfil.putExtra("usuario", (Serializable) user);
                                                                 startActivity(intentperfil);


                                                             }
                                                             if(!encontrado ) {
                                                                 nombre.setText("Escribe bien el usuario");
                                                                 password.setText("");
                                                             }
                                                         }
                                                     }
                                                     );

                                                 }
                                             }
                                         }
        );
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