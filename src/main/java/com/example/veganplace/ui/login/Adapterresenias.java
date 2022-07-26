package com.example.veganplace.ui.login;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.veganplace.R;
import com.example.veganplace.VeganPlaceRepository;
import com.example.veganplace.data.modelusuario.Resenia;

import java.util.List;


public class Adapterresenias extends RecyclerView.Adapter<Adapterresenias.MyViewHolder> {
    private static final String LOG_TAG = VeganPlaceRepository.class.getSimpleName();
    private List<Resenia> mDataset;

    Context context;

    public interface OnListInteractionListener {
        public void onListInteraction(Resenia resenia);
    }




    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public static   class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case


    public RatingBar mrating;
    public TextView mTextView1;
    public TextView mTextView2;
    public TextView mTextView3;


        public View mView;


        public MyViewHolder(View v) {
            super(v);
            mView=v;
            mrating = v.findViewById(R.id.ratingBar2);
            mTextView1 = v.findViewById(R.id.nombre_resenia);
            mTextView2 = v.findViewById(R.id.descripcionresenia);
            mTextView3 = v.findViewById(R.id.dir_resenia);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Adapterresenias(List<Resenia> myDataset) {
        mDataset = myDataset;


    }

    @Override
    public Adapterresenias.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listadoresenias, parent, false);

        return new Adapterresenias.MyViewHolder(v);
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final Adapterresenias.MyViewHolder holder, int position) {
        holder.mTextView1.setText( mDataset.get(position).getName_res().toString());
        holder.mTextView2.setText(mDataset.get(position).getDescripcion().toString());
        holder.mrating.setRating(((float) mDataset.get(position).getValor()));
        holder.mTextView3.setText(mDataset.get(position).getDir_res());
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void swap(List<Resenia> dataset){
        mDataset = dataset;
        notifyDataSetChanged();
    }




    public void clear(){
        mDataset.clear();
        notifyDataSetChanged();
    }
}