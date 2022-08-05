package com.example.veganplace.ui.social;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veganplace.AppContainer;
import com.example.veganplace.MyApplication;
import com.example.veganplace.R;
import com.example.veganplace.data.modelusuario.ChatMessage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Conversacion extends AppCompatActivity implements Adaptermensajes.OnListInteractionListener {
    private static final String LOG_TAG = Conversacion.class.getSimpleName();
    private ChatViewModel chatViewModel;
    AppContainer appContainer;
    private RecyclerView recyclerView;
    private Adaptermensajes mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String receptor;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.convesacion);

        /* Esta actividad pùede recibir dos objetos serializaos, uno de ellos puede ser el nombre del usuario que recibe el mensaje y otro
         * el mensaje recibido y de ahí obtener los datos del emisor y el receptor del mismo.
         */
        if((String) getIntent().getSerializableExtra("user")!=null) {
            Log.d(LOG_TAG, "No es nuloooooooo"+getIntent().getSerializableExtra("user".toString()));
            receptor = (String) getIntent().getSerializableExtra("user");
        }

        if((ChatMessage) getIntent().getSerializableExtra("chatmensaje")!=null){
            ChatMessage chat= (ChatMessage) getIntent().getSerializableExtra("chatmensaje");
            //esta comprobación se hace porque puede que se trate de mensajes enviados en vez de recibidos
            if(chat.getReceptor().equals(MyApplication.usuario.getNombre())) {
                receptor = chat.getEmisor();
            }
            else{
                receptor=chat.getReceptor();
            }
        }


        appContainer = ((MyApplication) getApplicationContext()).appContainer;
        chatViewModel = new ViewModelProvider(this, appContainer.factorychat).get(ChatViewModel.class);

        setContentView(R.layout.convesacion);
        EditText message = this.findViewById(R.id.textoconver);

        if (MyApplication.usuario != null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarconver);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(receptor.toString());

            recyclerView = (RecyclerView) findViewById(R.id.recyclerchar);
            assert (recyclerView) != null;
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            mAdapter = new Adaptermensajes(new ArrayList<ChatMessage>(), "X", this);
            recyclerView.setAdapter(mAdapter);
            chatViewModel.setemirec(receptor,MyApplication.usuario.getNombre());
            chatViewModel.getconver().observe(this, chatss -> {
                mAdapter.cargar3(chatss);
            });


            Button enviar = findViewById(R.id.send_conversation);
            enviar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
                    String now = ISO_8601_FORMAT.format(new Date());
                    ChatMessage mensaje = new ChatMessage(MyApplication.usuario.getNombre().toString(), receptor, message.getText().toString(), now);
                    db.collection("ChatMessage")
                            .add(mensaje)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(LOG_TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Toast.makeText(getApplicationContext(), "Mensaje enviado", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(LOG_TAG, "Error adding document", e);
                                }
                            });
            /* Se actualizan los datos de los mensajes guardados en room para que se almacene el nuevo mensaje también,
             así nos aseguramos de que aparezcan siempre los más actualizados. */
                    chatViewModel.actualizar();

                }
            });
        }
}


    @Override
    public void onListInteraction(ChatMessage chat) {
    }
}


