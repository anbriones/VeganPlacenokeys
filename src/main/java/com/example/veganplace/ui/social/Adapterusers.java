package com.example.veganplace.ui.social;


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
import com.example.veganplace.R;
import com.example.veganplace.VeganPlaceRepository;
import com.example.veganplace.data.modelusuario.User;
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


class Adapterusers extends RecyclerView.Adapter<Adapterusers.MyViewHolder> {
    private static final String LOG_TAG = VeganPlaceRepository.class.getSimpleName();
    private  List<User> mDataset;
    FirebaseFirestore db= FirebaseFirestore.getInstance();

    public interface OnListInteractionListener {
        public void onListInteraction(String user);
    }


    public Adapterusers.OnListInteractionListener mListener;




    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public static   class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case



    public TextView mTextView1;
    public TextView mTextView2;
    public ImageView image;
    public Button mbutton;


        public View mView;


        public MyViewHolder(View v) {
            super(v);
            mView=v;

            mTextView1 = v.findViewById(R.id.nombreuserlist);
            mTextView2 = v.findViewById(R.id.email);
            image = v.findViewById(R.id.imguserlist);
            mbutton = v.findViewById(R.id.sendlist);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Adapterusers(List<User> myDataset, Adapterusers.OnListInteractionListener listener) {
        mDataset = myDataset;
        mListener = listener;
    }



    @Override
    public Adapterusers.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listadousuarios, parent, false);

        return new Adapterusers.MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final Adapterusers.MyViewHolder holder, int position) {
        if(mDataset.get(position)!=null) {
            holder.mTextView1.setText(mDataset.get(position).getNombre().toString());
            holder.mTextView2.setText(mDataset.get(position).getEmail().toString());
            insertarimagen(holder, position);
            holder.mbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onListInteraction(mDataset.get(position).getNombre());
                }
            });
        }
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }



    public void insertarimagen(final Adapterusers.MyViewHolder holder, int position) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReference();
        ref.child(mDataset.get(position).getDirimagen().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(holder.mView)
                        .load(uri)
                        .fitCenter()
                        .override(350, 350)// look here
                        .into(holder.image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(LOG_TAG, "LA URI es: " + ref.toString(), exception);
            }
        });
    }

        public void cargar(Task<QuerySnapshot> task) {
            List<User> users = new ArrayList<User>();
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String nombre = document.getString("nombre");
                    String password = document.getString("password");
                    String email = document.getString("email");
                    String dirimagen = document.getString("dirimagen");
                    User user = new User(nombre, password, email, dirimagen);
                    users.add(user);
                    Log.d(LOG_TAG, document.getId() + " => " + document.getData());
                }
                mDataset = users;
                Log.d(LOG_TAG, "tam usuarios leidos" + mDataset.size());
            }
        }


    }