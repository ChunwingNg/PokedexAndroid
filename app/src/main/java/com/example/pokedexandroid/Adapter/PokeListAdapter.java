package com.example.pokedexandroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokedexandroid.Common.Common;
import com.example.pokedexandroid.Interface.IItemClickListener;
import com.example.pokedexandroid.Model.Pokemon;
import com.example.pokedexandroid.R;

import java.util.List;

public class PokeListAdapter extends RecyclerView.Adapter<PokeListAdapter.MyViewHolder> {

    Context context;
    List<Pokemon> pokemonList;

    public PokeListAdapter(Context context, List<Pokemon> pokemonList) {
        this.context = context;
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Image will load here using Glide
        Glide.with(context).load(pokemonList.get(position).getImg()).into(holder.image);
        //Get the name and set it into the holder
        holder.name.setText(pokemonList.get(position).getName());

        //Event
        holder.setiItemclickListener((view,p)->
                LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(new Intent(Common.KEY_ENABLE_HOME)
                                .putExtra("num",pokemonList.get(p).
                                        getNum())));
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView name;

        IItemClickListener iItemClickListener;

        public void setiItemclickListener(IItemClickListener iItemclickListener){
            this.iItemClickListener = iItemclickListener;
        }

        public MyViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.pokemon_image);
            name = itemView.findViewById(R.id.pokemon_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iItemClickListener.onClick(view,getAdapterPosition());
        }
    }
}
