package com.example.veganplace.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.veganplace.MainActivity;
import com.example.veganplace.MyApplication;
import com.example.veganplace.R;
import com.example.veganplace.data.modelmapas.Result;

public class Restaurant_detail extends AppCompatActivity {
       ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detallerestaurante);
        Toolbar toolbar = findViewById(R.id.toolbarres);
        setSupportActionBar(toolbar);
        MyApplication appState = ((MyApplication) getApplicationContext());

       Result  res = (Result) getIntent().getSerializableExtra("restaurantedetalle");

        image = findViewById(R.id.imagenrestaurante);
        Glide.with(this).load(res.getIcon()).into(image);

        TextView dir = this.findViewById(R.id.nombre_res);
        dir.setText(res.getFormattedAddress().toString());

        TextView nombre = this.findViewById(R.id.nameres);
        nombre.setText(res.getName().toString());

        TextView rating = this.findViewById(R.id.ratingnumber);
        rating.setText(res.getRating().toString());

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



