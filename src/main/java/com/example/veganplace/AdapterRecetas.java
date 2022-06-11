package com.example.veganplace;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.veganplace.data.modelrecetas.Recipe;

import java.util.List;



public class AdapterRecetas extends RecyclerView.Adapter<AdapterRecetas.MyViewHolder> {
    private List<Recipe> mDataset;
    Context context;




    public interface OnListInteractionListener{
        public void onListInteraction(Recipe receta);

    }



    public AdapterRecetas.OnListInteractionListener mListener;



    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageView;
        public TextView mTextView1;
        public View mView;
        public Recipe mItem;

        public MyViewHolder(View v) {
            super(v);
            mView=v;
            mImageView = v.findViewById(R.id.imgreceta);
            mTextView1 = v.findViewById(R.id.nombrereceta);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterRecetas(List<Recipe> myDataset, AdapterRecetas.OnListInteractionListener listener) {
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
    @Override
    public void onBindViewHolder(final AdapterRecetas.MyViewHolder holder, int position) {
        holder.mItem = mDataset.get(position);
        holder.mImageView.setImageURI(Uri.parse(mDataset.get(position).getImage().toString()));
        holder.mTextView1.setText(mDataset.get(position).getLabel().toString());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListInteraction( mDataset.get(position));
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