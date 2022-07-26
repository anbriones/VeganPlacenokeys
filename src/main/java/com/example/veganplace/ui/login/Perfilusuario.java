package com.example.veganplace.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veganplace.AppContainer;
import com.example.veganplace.InjectorUtils;
import com.example.veganplace.MainActivity;
import com.example.veganplace.MyApplication;
import com.example.veganplace.R;
import com.example.veganplace.data.modelusuario.Resenia;
import com.example.veganplace.data.modelusuario.User;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Perfilusuario extends AppCompatActivity {
    ImageView imageView;

    public static final int PICK_FILE = 99;
    public static int RESULT_LOAD_IMAGE = 1;
    Bitmap bitmap;
    File imagencorrecta;
    User user = new User();
LoginViewModel loginViewModel;
    private RecyclerView recyclerView;
    private Adapterresenias mAdapter;
    AppContainer appContainer;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfilusuario);
        Toolbar toolbar = findViewById(R.id.toolbarperfil);
        setSupportActionBar(toolbar);
        MyApplication appState = ((MyApplication) getApplicationContext());

        if (MyApplication.usuario != null ) {
            user = MyApplication.usuario;
        } else {
            user = (User) getIntent().getSerializableExtra("usuario");
        }


        recyclerView = (RecyclerView) this.findViewById(R.id.reseñas);
        assert (recyclerView) != null;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new Adapterresenias(new ArrayList<Resenia>());
        recyclerView.setAdapter(mAdapter);
        LoginViewModelFactory factorylogin = InjectorUtils.provideMainActivityViewModelFactorylogin(this.getApplicationContext());
        appContainer = ((MyApplication) this.getApplication()).appContainer;
        loginViewModel  = new ViewModelProvider(this, appContainer.factoryusers).get(LoginViewModel.class);

        loginViewModel.getresenia().observe(this, resenias -> {
            mAdapter.swap(resenias);
        });


        TextView nombre = this.findViewById(R.id.nombre_user);
        nombre.setText(user.getDisplayName().toString());


        ImageButton logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
               MyApplication.usuario=null;
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Perfilusuario.this);


                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Username", "Sin definir"); //This is just an example, you could also put boolean, long, int or floats
                editor.commit();
                Intent intentinicio = new Intent(getApplication(), MainActivity.class);
                startActivity(intentinicio);
            }
        });



        imageView = (ImageView) findViewById(R.id.img_user);
        File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File directory = new File(path + "/imagenes fithealth/"+MyApplication.usuario.getDisplayName());
        if (directory.exists()) {
            File files[] = directory.listFiles();
            if (files.length >= 1) {
                String numCadena = String.valueOf(files.length);

                if (files != null && files.length >= 1) {
                    for (int i = 0; i < files.length; i++) {
                        if (i == (files.length) - 1) {
                            imagencorrecta = files[i];
                        }
                    }
                }
                String filePath = imagencorrecta.getAbsolutePath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                imageView.setImageBitmap(bitmap);
            }
        }
        ImageButton buttonLoadImage = findViewById(R.id.botonimagen);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
            bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            saveImage(bitmap);

        }
    }




    private void saveImage(Bitmap finalBitmap) {
        File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File myDir = new File(path + "/imagenes fithealth");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        String imageName = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

        String fname = "Image-" + imageName + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
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



