package com.example.fruitslegumes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    private List<Fruit> values;
    private ImageView imageView;
    private Context context;


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nom;
        View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            nom = v.findViewById(R.id.nom_fruit);
            imageView = v.findViewById(R.id.image);

        }
    }




    public ListAdapter(List<Fruit> Test, Context context) {
        values = Test;
        this.context = context;
    }



    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Fruit name = values.get(position);
        holder.nom.setText(name.getNom());
        Glide.with(context).load(values.get(position).getUrl()).into(imageView);




    }


    @Override
    public int getItemCount() {
        return values.size();
    }


}
