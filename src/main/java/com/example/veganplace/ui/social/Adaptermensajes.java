package com.example.veganplace.ui.social;


import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.veganplace.MyApplication;
import com.example.veganplace.R;
import com.example.veganplace.VeganPlaceRepository;
import com.example.veganplace.data.modelusuario.ChatMessage;
import com.example.veganplace.data.modelusuario.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


class Adaptermensajes extends RecyclerView.Adapter<Adaptermensajes.MyViewHolder> {
    private static final String LOG_TAG = VeganPlaceRepository.class.getSimpleName();
    private  List<ChatMessage> mDataset;
    private String tipo;
    Context context;
    FirebaseFirestore db= FirebaseFirestore.getInstance();


    public interface OnListInteractionListener {
        public void onListInteraction(ChatMessage chat);
    }


    public Adaptermensajes.OnListInteractionListener mListener;




    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public static   class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case



    public TextView mTextView1;
    public TextView mTextView2;
    public TextView mTextView3;
    public ImageView image;
    public Button mbutton;


        public View mView;


        public MyViewHolder(View v) {
            super(v);
            mView=v;

            mTextView1 = v.findViewById(R.id.nombre_recemi);
            mTextView2 = v.findViewById(R.id.mensaje_texto);
            mTextView3 = v.findViewById(R.id.fecha);
            image = v.findViewById(R.id.avatar);
            mbutton = v.findViewById(R.id.answer);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Adaptermensajes(List<ChatMessage> myDataset, User user1, Adaptermensajes.OnListInteractionListener listener, String tipo) {
        mDataset = myDataset;
        mListener = listener;

    }


    public Adaptermensajes(List<ChatMessage> myDataset, String tipo, Adaptermensajes.OnListInteractionListener listener) {
        mDataset = myDataset;
       this.tipo = tipo;
        mListener = listener;


    }

    @Override
    public Adaptermensajes.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listadochatrecycle, parent, false);

        return new Adaptermensajes.MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final Adaptermensajes.MyViewHolder holder, int position) {
        String fecha =  mDataset.get(position).getMessageTime();
       fecha=  fecha.replace("T", "  ");
        fecha= fecha.replace("Z"," ");

    if(tipo.equals("R")) {
                if (MyApplication.usuario.getNombre().toString().equals(mDataset.get(position).getReceptor())) {
            holder.mTextView1.setText(mDataset.get(position).getEmisor().toString());
            insertarimagen(holder, position, mDataset.get(position).getEmisor().toString());
            holder.mbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onListInteraction(mDataset.get(position));
                }
            });
        }
    }
        if(tipo.equals("S")) {//Para los enviados
            if (MyApplication.usuario.getNombre().toString().equals(mDataset.get(position).getEmisor())) {
                holder.mTextView1.setText(mDataset.get(position).getReceptor().toString());
                insertarimagen(holder, position, mDataset.get(position).getReceptor().toString());
                holder.mbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onListInteraction(mDataset.get(position));
                    }
                });
            }
        }

      else   if(tipo.equals("X")) {//Para las conversaciones
        holder.mTextView3.setText(fecha);
              holder.mTextView1.setText(mDataset.get(position).getEmisor().toString());
        holder.mTextView2.setText( mDataset.get(position).getTexto().toString());
                insertarimagen( holder, position,mDataset.get(position).getEmisor().toString() );
                holder.mbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onListInteraction(mDataset.get(position));
                    }
                });
        holder.mbutton.setVisibility(View.INVISIBLE);

}
else{
    if (MyApplication.usuario.getNombre().toString().equals(mDataset.get(position).getEmisor())) {
        holder.mTextView3.setText(fecha);
        holder.mTextView2.setText( mDataset.get(position).getTexto().toString());
        holder.mTextView1.setText(mDataset.get(position).getReceptor().toString());
        insertarimagen( holder, position,mDataset.get(position).getReceptor().toString() );
        holder.mbutton.setVisibility(View.INVISIBLE);
    }
}}



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

public void insertarimagen(final Adaptermensajes.MyViewHolder holder, int position, String nombre_user){

    db.collection("User").whereEqualTo("nombre", nombre_user).get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().getDocuments().size() > 0) {

                            String url = task.getResult().getDocuments().get(0).getString("dirimagen");
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference ref = storage.getReference();
                            ref.child(url).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Glide.with(holder.mView)
                                            .load(uri)
                                            .fitCenter()
                                            .override(400, 400)// look here
                                            .into(holder.image);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Log.d(LOG_TAG, "LA URI es: " + ref.toString(), exception);
                                }
                            });

                            // insertarimagen(url, holder, position);
                        }
                        else{
                            Log.d(LOG_TAG, "No encontrado", task.getException());
                        }
                    } else {
                        Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                    }
                }
            });

}
    public void cargar2(List<ChatMessage> chats){
        List<ChatMessage> lista = new ArrayList<ChatMessage>();
        Boolean encontrado=false;
        for(int i=0;i<chats.size();i++) {
            ChatMessage chat = chats.get(i);
            for (int j = 0; j < lista.size(); j++) {

                if (lista.get(j).getEmisor().equals(chat.getEmisor())) {
                    encontrado = true;

                }
            }
               if (!encontrado) {
                    lista.add(chat);
                }
                encontrado = false;
            }

    mDataset=lista;
        notifyDataSetChanged();
    }

    public void cargar3(List<ChatMessage> chats){
        mDataset=chats;
        notifyDataSetChanged();
    }


        public void cargar(Task<QuerySnapshot> task) {
            List<ChatMessage> chats = new ArrayList<ChatMessage>();
            Boolean encontrado=false;
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String emisor = document.getString("emisor");
                    String receptor = document.getString("receptor");
                    String texto = document.getString("texto");
                    String messageTime = document.getString("messageTime");
                    ChatMessage chat = new ChatMessage(emisor, receptor, texto, messageTime);
                  for(int i=0;i<chats.size();i++){
                      if(chats.get(i).getEmisor().equals(chat.getEmisor())){
                        encontrado=true;

                      }
                  }
                  if(!encontrado) {
                      chats.add(chat);
                  }
                    Log.d(LOG_TAG, document.getId() + " => " + document.getData());
                  encontrado=false;
                }
                mDataset = chats;
                Log.d(LOG_TAG, "tam chats" + chats.size());
            }
        }



    public void cargarx(Task<QuerySnapshot> task) {
    List<ChatMessage> chats2 = new ArrayList<ChatMessage>();

            if (task.isSuccessful()) {
        for (QueryDocumentSnapshot document : task.getResult()) {
            String emisor = document.getString("emisor");
            String receptor = document.getString("receptor");
            String texto = document.getString("texto");
            String messageTime = document.getString("messageTime");
            ChatMessage chat = new ChatMessage(emisor, receptor, texto, messageTime);
            chats2.add(chat);


            Log.d(LOG_TAG, document.getId() + " => " + document.getData());
        }
        }
        mDataset = chats2;
        Log.d(LOG_TAG, "tam chats" + chats2.size());
    }


}


