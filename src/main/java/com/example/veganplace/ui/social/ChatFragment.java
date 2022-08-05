package com.example.veganplace.ui.social;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veganplace.AppContainer;
import com.example.veganplace.MyApplication;
import com.example.veganplace.R;
import com.example.veganplace.data.modelusuario.ChatMessage;
import com.example.veganplace.data.modelusuario.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment implements Adaptermensajes.OnListInteractionListener,Adapterusers.OnListInteractionListener{
    private static final String LOG_TAG = ChatFragment.class.getSimpleName();
    private ChatViewModel chatViewModel;
    AppContainer appContainer;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private Adaptermensajes mAdapterenviados;
    private Adaptermensajes mAdapterrecibidos;
    private Adapterusers mAdapteruser;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager2;
    private RecyclerView.LayoutManager layoutManager3;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        appContainer = ((MyApplication) this.getActivity().getApplication()).appContainer;
       chatViewModel = new ViewModelProvider(this, appContainer.factorychat).get(ChatViewModel.class);

        TextView text = root.findViewById(R.id.noiniciado);
        Button send = root.findViewById(R.id.sendind);
        Button receive = root.findViewById(R.id.receiving);
        Button users = root.findViewById(R.id.usersbuttom);

if(MyApplication.usuario!=null) {
    text.setVisibility(View.INVISIBLE);
    recyclerView = (RecyclerView) root.findViewById(R.id.listadochats);
    recyclerView2= (RecyclerView) root.findViewById(R.id.listadousers);
    recyclerView3= (RecyclerView) root.findViewById(R.id.listadoenviados);
    assert (recyclerView) != null;
    assert (recyclerView2) != null;
    assert (recyclerView3) != null;
    recyclerView.setHasFixedSize(true);
    recyclerView2.setHasFixedSize(true);
    recyclerView3.setHasFixedSize(true);
    layoutManager = new LinearLayoutManager(getActivity());
    layoutManager2 = new LinearLayoutManager(getActivity());
    layoutManager3 = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView2.setLayoutManager(layoutManager2);
    recyclerView3.setLayoutManager(layoutManager3);
    send.setTextColor(Color.BLACK);
    adaptarenviado();
    send.setOnClickListener(new View.OnClickListener() {
        @SuppressLint("ResourceAsColor")
        public void onClick(View v) {
            users.setTextColor(Color.WHITE);
            receive.setTextColor(Color.WHITE);
            send.setTextColor(Color.BLACK);
            adaptarenviado();
            recyclerView3.setAdapter(mAdapterenviados);
            recyclerView2.setVisibility(View.INVISIBLE);//usuarios
            recyclerView.setVisibility(View.VISIBLE);//recibidos
        }
   });

    receive.setOnClickListener(new View.OnClickListener() {
        @SuppressLint("ResourceAsColor")
        public void onClick(View v) {
            users.setTextColor(Color.WHITE);
            receive.setTextColor(Color.BLACK);
            send.setTextColor(Color.WHITE);
           adaptarrecibido();
            recyclerView.setAdapter(mAdapterrecibidos);
            recyclerView2.setVisibility(View.INVISIBLE);
            recyclerView3.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    });

    users.setOnClickListener(new View.OnClickListener() {
        @SuppressLint("ResourceAsColor")
        public void onClick(View v) {
            users.setTextColor(Color.BLACK);
            receive.setTextColor(Color.WHITE);
            send.setTextColor(Color.WHITE);
            db.collection("User").whereNotEqualTo("nombre",MyApplication.usuario.getNombre())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                adaptar2(task);
                            } else {
                                Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    });

}
else{
    text.setVisibility(View.VISIBLE);
    send.setVisibility(View.INVISIBLE);
    receive.setVisibility(View.INVISIBLE);
    users.setVisibility(View.INVISIBLE);
}
        return root;
    }


    public void adaptarrecibido(){
        mAdapterrecibidos = new Adaptermensajes(new ArrayList<ChatMessage>(), "R",this);
        recyclerView.setAdapter(mAdapterrecibidos);
        chatViewModel.setreceptor(MyApplication.usuario.getNombre());
        chatViewModel.getreceives().observe(this, chatss -> {
            mAdapterrecibidos.cargar2(chatss);
        });
   }

    public void adaptarenviado(){
        recyclerView.setVisibility(View.VISIBLE);//recibidos
        recyclerView3.setVisibility(View.INVISIBLE);//enviados
        recyclerView2.setVisibility(View.INVISIBLE);//usuarios
        mAdapterenviados = new Adaptermensajes(new ArrayList<ChatMessage>(), "S",this);
        recyclerView.setAdapter(mAdapterenviados);

        chatViewModel.setreceptor(MyApplication.usuario.getNombre());
        chatViewModel.getsends().observe(this, chatss -> {
            List<ChatMessage> lista = new ArrayList<ChatMessage>();
            Boolean encontrado=false;
            for(int i=0;i<chatss.size();i++) {
                ChatMessage chat = chatss.get(i);
                for (int j = 0; j < lista.size(); j++) {
                    if (lista.get(j).getReceptor().equals(chat.getReceptor())) {
                        encontrado = true;
                    }
                }
                if (!encontrado) {
                    lista.add(chat);
                }
                encontrado = false;
            }
            mAdapterenviados.cargar3(lista);
        });
    }

    public void adaptar2(Task<QuerySnapshot> task){
        recyclerView.setVisibility(View.INVISIBLE);//recibidos
        recyclerView3.setVisibility(View.INVISIBLE);//enviados
        recyclerView2.setVisibility(View.VISIBLE);//usuarios
        mAdapteruser = new Adapterusers(new ArrayList<User>(),this);
        recyclerView2.setAdapter(mAdapteruser);
        mAdapteruser.cargar(task);
    }
    @Override
    public void onListInteraction(ChatMessage chat) {
        Intent intentmandarmendaje = new Intent(this.getActivity(), Conversacion.class);
        intentmandarmendaje.putExtra("chatmensaje", (Serializable) chat);
        startActivity(intentmandarmendaje) ;
    }

    @Override
    public void onListInteraction(String  user) {
        Intent intentmandarmendaje2 = new Intent(this.getActivity(), Conversacion.class);
        intentmandarmendaje2.putExtra("user", (Serializable) user);
        startActivity(intentmandarmendaje2) ;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
