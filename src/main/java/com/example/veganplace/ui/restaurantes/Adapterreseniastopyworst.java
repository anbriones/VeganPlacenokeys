package com.example.veganplace.ui.restaurantes;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.veganplace.MyApplication;
import com.example.veganplace.R;
import com.example.veganplace.VeganPlaceRepository;
import com.example.veganplace.data.modelusuario.Resenia;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Adapterreseniastopyworst extends RecyclerView.Adapter<Adapterreseniastopyworst.MyViewHolder> {
    private static final String LOG_TAG = VeganPlaceRepository.class.getSimpleName();
    private  List<Resenia> mDataset;

    Context context;

    public interface OnListInteractionListener {
        public void onListInteraction(String nombre);
    }

    public Adapterreseniastopyworst.OnListInteractionListener mListener;

    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public static   class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case


    public RatingBar mrating;
    public TextView mTextView1;
    public TextView mTextView2;
    public TextView mTextView3;
    public TextView mTextView4;

        public View mView;


        public MyViewHolder(View v) {
            super(v);
            mView=v;
            mrating = v.findViewById(R.id.ratingBar2);
            mTextView1 = v.findViewById(R.id.nombre_resenia);
            mTextView2 = v.findViewById(R.id.descripcionresenia);
            mTextView3 = v.findViewById(R.id.dir_resenia);
            mTextView4 = v.findViewById(R.id.nombre);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Adapterreseniastopyworst(List<Resenia> myDataset, Adapterreseniastopyworst.OnListInteractionListener listener) {
        mDataset = myDataset;
mListener=listener;

    }

    public Adapterreseniastopyworst(List<Resenia> myDataset) {
        mDataset = myDataset;


    }

    @Override
    public Adapterreseniastopyworst.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listadoresenias, parent, false);

        return new Adapterreseniastopyworst.MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final Adapterreseniastopyworst.MyViewHolder holder, int position) {

        holder.mTextView1.setText( mDataset.get(position).getName_res().toString());
        holder.mTextView2.setText(mDataset.get(position).getDescripcion().toString());
        holder.mrating.setRating((float) mDataset.get(position).getValor());
        holder.mTextView3.setText(mDataset.get(position).getDir_res());
        holder.mTextView4.setText(mDataset.get(position).getName_user().substring(0,1)+"****");

        holder.mTextView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if(MyApplication.usuario!=null){
    if(mListener!=null) {
        mListener.onListInteraction(mDataset.get(position).getName_user().toString());
    }
}            }
        });
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public void swap(List<Resenia> dataset){
        mDataset = dataset;
       // notifyDataSetChanged();
    }

public void cargar(Task<QuerySnapshot> task){
        List<Resenia> resenias = new ArrayList<Resenia>();
    if (task.isSuccessful()) {
        for (QueryDocumentSnapshot document : task.getResult()) {
            String desc = document.getString("descripcion");
            String dir_res = document.getString("dir_res");
            String id_resenia = document.getString("id_resenia");
            String name_res = document.getString("name_res");
            String name_user = document.getString("name_user");
            Double valor = document.getDouble("valor");
            double val = (double) valor;
            Resenia res = new Resenia(id_resenia, name_user, name_res, dir_res, desc, val);
            resenias.add(res);
            Log.d(LOG_TAG, document.getId() + " => " + document.getData());
        }
       mDataset = resenias;
        Log.d(LOG_TAG, "tam resenias"+ resenias.size());
    }
}


    public void clear(){
        mDataset.clear();
        notifyDataSetChanged();
    }
}