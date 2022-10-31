package com.example.veganplace.ui.detallesreceta;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.veganplace.AppContainer;
import com.example.veganplace.InjectorUtils;
import com.example.veganplace.MyApplication;
import com.example.veganplace.R;
import com.example.veganplace.VeganPlaceRepository;
import com.example.veganplace.data.modelrecetas.Recipe;

public class detallesreceta extends AppCompatActivity {
    private static final String LOG_TAG = VeganPlaceRepository.class.getSimpleName();
    ImageView image;


    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private AdapterIngredientes mAdapter;
    AppContainer appContainer;
    DetallesRecetasViewModel mViewModel;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
        setContentView(R.layout.detallesreceta);
        Toolbar toolbar = findViewById(R.id.toolbaringre);
        setSupportActionBar(toolbar);
        this.setTitle(R.string.title_detalle_receta);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        Recipe receta = (Recipe) getIntent().getSerializableExtra("recetasdetalles");

        TextView text = findViewById(R.id.nombre_receta);
        text.setText( receta.getLabel().toString());
        TextView calorias = findViewById(R.id.calorias_);
        String caloriastext=getString(R.string.calories);
        calorias.setText(receta.getCalories().substring(0, 6).toString()+"  " +getResources().getString(R.string.calories2));

        image = findViewById(R.id.imagendetalle);
        Glide.with(this).load(receta.getImage()).into(image);

        recyclerView = (RecyclerView) findViewById(R.id.recicleingre);
        assert (recyclerView) != null;
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new AdapterIngredientes( );
        recyclerView.setAdapter(mAdapter);
        DetallesrecetasViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactorydetalles(getApplicationContext());
        appContainer = ((MyApplication) getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, appContainer.factorydetalles).get(DetallesRecetasViewModel.class);

        mViewModel.setid(receta.getLabel());
        //Se obtienen los ingredientes de cada receta y se pasan al adaptador
        mViewModel.getingredientes().observe(this, ingredientes -> {
            Log.d(LOG_TAG, "Getting datos"+ingredientes.size());
            mAdapter.swap(ingredientes);
           if (ingredientes != null)
                recyclerView.setVisibility(View.VISIBLE);
            else {
                recyclerView.setVisibility(View.INVISIBLE);
            }

        });
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

