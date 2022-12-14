package com.example.veganplace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.veganplace.ui.login.LoginActivity;
import com.example.veganplace.ui.login.Perfilusuario;
import com.example.veganplace.ui.login.Registro;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity  {
    private Context mContext;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_recetas, R.id.navigation_notificaciones, R.id.navigation_restaurantes, R.id.navigation_chat)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.toolbar) {
            SettingsActivity.start(this);
        }
        else if (item.getItemId() == R.id.usertoolbar) {
            if(MyApplication.usuario!=null) {
                Intent intentusuario = new Intent(MainActivity.this, Perfilusuario.class);
                startActivity(intentusuario);
            }
            else{
                Intent intentusuario = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentusuario);
            }
        }
        else if(item.getItemId()==R.id.usertoolbar2){
            Intent intentusuario = new Intent(MainActivity.this, Registro.class);
            startActivity(intentusuario);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(MyApplication.usuario!=null) {
            inflater.inflate(R.menu.toolbar_menu2, menu);
        }
        else{
            inflater.inflate(R.menu.toolbar_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

}