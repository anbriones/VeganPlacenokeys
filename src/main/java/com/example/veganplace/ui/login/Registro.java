package com.example.veganplace.ui.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.veganplace.AppContainer;
import com.example.veganplace.R;
import com.example.veganplace.data.modelusuario.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.HashMap;

public class Registro extends AppCompatActivity {
    EditText nombre;
    EditText password;
    EditText email;
    String nombre_g;
    String password_g;
    String email_g;
    Button registrar;
    AppContainer appContainer;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String LOG_TAG = Registro.class.getSimpleName();
    Bitmap bitmap;
    ImageView imageView;
    public static final int PICK_FILE = 99;
    public static int RESULT_LOAD_IMAGE = 1;
    public Uri imagenseleccionada;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nombre = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.password2);


        registrar = findViewById(R.id.resgitro);


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



        imageView = (ImageView) findViewById(R.id.img_user);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre_g = nombre.getText().toString();
                password_g = password.getText().toString();
                email_g = email.getText().toString();

                if (nombre_g.isEmpty() || password_g.isEmpty()) {
                    if (nombre_g.isEmpty() || password_g.isEmpty())
                        Toast.makeText(getApplicationContext(), "Name or password can't be empty", Toast.LENGTH_SHORT).show();
                } else {
                    /*
                    Compobar si existe este usuario
                     */
                    db.collection("User").whereEqualTo("nombre", nombre_g).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult().getDocuments().size() > 0) {
                                            if (nombre_g.equals(task.getResult().getDocuments().get(0).getString("nombre"))) {
                                                Toast.makeText(getApplicationContext(), "El usuario" + nombre_g + " ya existe", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else {
                                                Toast.makeText(getApplicationContext(), "Insertando" + nombre_g, Toast.LENGTH_SHORT).show();
                                            // [START upload_file]
                                            Uri file = Uri.fromFile(new File(imagenseleccionada.toString()));
                                            StorageReference Folder = FirebaseStorage.getInstance().getReference().child(nombre_g);

                                            StorageReference file_name = Folder.child("file" + imagenseleccionada.getLastPathSegment());

                                            StorageReference finalFile_name = file_name;
                                            file_name.putFile(imagenseleccionada).addOnSuccessListener(taskSnapshot -> finalFile_name.getDownloadUrl().addOnSuccessListener(uri -> {
                                                        HashMap<String, String> hashMap = new HashMap<>();
                                                        final String link = hashMap.put("link", String.valueOf(uri));

                                                storageRef.child("imagen").getFile(new File(hashMap.toString()));

                                                    }
                                            ));

                                            User user = new User(nombre_g, password_g, email_g,"/"+nombre_g+"/file"+imagenseleccionada.getLastPathSegment());

                                                db.collection("User")
                                                        .add(user)
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
                                                                Log.d(LOG_TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                                Toast.makeText(getApplicationContext(), "Usuario resgitrado", Toast.LENGTH_SHORT).show();

                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w(LOG_TAG, "Error adding document", e);
                                                            }
                                                        });


                                            }


                                            Intent intentlogin = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(intentlogin);


                                        }

                                     else {
                                        Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });


                }
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
            imagenseleccionada=selectedImage;
        }
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