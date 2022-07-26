package com.example.veganplace.ui.recetas;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.veganplace.R;
import com.example.veganplace.VeganPlaceRepository;
import com.example.veganplace.data.modelrecetas.Ingredient;
import com.example.veganplace.data.modelrecetas.Recipe;

import java.util.List;


public class AdapterRecetas extends RecyclerView.Adapter<AdapterRecetas.MyViewHolder> {
    private static final String LOG_TAG = VeganPlaceRepository.class.getSimpleName();
    private List<Recipe> mDataset;



    Context context;

    public interface OnListInteractionListener {
        public void onListInteraction(Recipe receta);
    }

        public AdapterRecetas.OnListInteractionListener mListener;


        // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public static   class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public View mView;
        public Recipe mItem;
        public Ingredient mItemi;

            public MyViewHolder(View v) {
            super(v);
            mView=v;
            mImageView = v.findViewById(R.id.imgreceta);
            mTextView1 = v.findViewById(R.id.nombrereceta);
            mTextView2=v.findViewById(R.id.descripcionresenia);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterRecetas(List<Recipe> myDataset,AdapterRecetas.OnListInteractionListener listener) {
        mDataset = myDataset;
        mListener = listener;

    }

    @Override
    public AdapterRecetas.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
 View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listadorecetas, parent, false);

        return new AdapterRecetas.MyViewHolder(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final AdapterRecetas.MyViewHolder holder,int position) {

        Glide.with(holder.itemView.getContext()).load(mDataset.get(position).getImage().toString()).into(holder.mImageView);
        holder.mTextView1.setText(mDataset.get(position).getLabel().toString());
        holder.mTextView2.setText("Calories: " +mDataset.get(position).getCalories().substring(0,6));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListInteraction(mDataset.get(position));
                    }
                }
            });


        }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void swap(List<Recipe> dataset){
        mDataset = dataset;
        notifyDataSetChanged();
    }




    public void clear(){
        mDataset.clear();
        notifyDataSetChanged();
    }
}