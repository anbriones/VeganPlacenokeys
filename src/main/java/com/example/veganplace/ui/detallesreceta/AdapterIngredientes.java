package com.example.veganplace.ui.detallesreceta;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.veganplace.R;
import com.example.veganplace.VeganPlaceRepository;
import com.example.veganplace.data.modelrecetas.Ingredient;

import java.util.ArrayList;
import java.util.List;


public class AdapterIngredientes extends RecyclerView.Adapter<AdapterIngredientes.MyViewHolder> {
    private static final String LOG_TAG = VeganPlaceRepository.class.getSimpleName();
    private List<Ingredient> mDataset = new ArrayList<Ingredient>();
    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public static   class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView mTextView2;
        public View mView;


        public MyViewHolder(View v) {
            super(v);
            mView=v;

            mTextView2=v.findViewById(R.id.nombre_ing);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterIngredientes(){
          }

    @Override
    public AdapterIngredientes.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listadoingredientes, parent, false);

        return new AdapterIngredientes.MyViewHolder(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final AdapterIngredientes.MyViewHolder holder,int position) {
        Log.d(LOG_TAG, "Getting the leyendo data source"+position);

        holder.mTextView2.setText( mDataset.get(position).getText()+"\n");
            }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void swap(List<Ingredient> dataset){
        mDataset = dataset;
        notifyDataSetChanged();
    }




    public void clear(){
        mDataset.clear();

        notifyDataSetChanged();
    }
}