package com.example.veganplace;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Informacion extends AppCompatActivity {
EditText texto1;
        private Context mContext;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                setContentView(R.layout.informacion);
                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);




        }
}
