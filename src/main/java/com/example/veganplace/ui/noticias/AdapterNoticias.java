package com.example.veganplace.ui.noticias;



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
import com.example.veganplace.data.modelnoticias.Article;

import java.util.List;


public class AdapterNoticias extends RecyclerView.Adapter<AdapterNoticias.MyViewHolder> {
    private static final String LOG_TAG = VeganPlaceRepository.class.getSimpleName();
    private List<Article> mDataset;

    Context context;

    public interface OnListInteractionListener {
        public void onListInteraction(Article article);
    }

    public AdapterNoticias.OnListInteractionListener mListener;


    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public static   class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public View mView;


        public MyViewHolder(View v) {
            super(v);
            mView=v;
            mImageView = v.findViewById(R.id.imgnoticia);
            mTextView1 = v.findViewById(R.id.nombreuserlist);
            mTextView2=v.findViewById(R.id.email);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterNoticias(List<Article> myDataset,AdapterNoticias.OnListInteractionListener listener) {
        mDataset = myDataset;
        mListener = listener;

    }

    @Override
    public AdapterNoticias.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listadonoticias, parent, false);

        return new AdapterNoticias.MyViewHolder(v);
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final AdapterNoticias.MyViewHolder holder,int position) {

        Glide.with(holder.itemView.getContext()).load(mDataset.get(position).getUrlToImage().toString()).into(holder.mImageView);

        if(mDataset.get(position).getTitle().length()>80) {
            holder.mTextView1.setText(mDataset.get(position).getTitle().toString().substring(0, 80) + "...");
        }
        else{
            holder.mTextView1.setText(mDataset.get(position).getTitle().toString());
        }
        holder.mTextView2.setText( mDataset.get(position).getDescription().toString());

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

    public void swap(List<Article> dataset){
        mDataset = dataset;
        notifyDataSetChanged();
    }




    public void clear(){
        mDataset.clear();
        notifyDataSetChanged();
    }
}