package com.example.pokedexandroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedexandroid.Common.Common;
import com.example.pokedexandroid.Interface.IItemClickListener;
import com.example.pokedexandroid.R;
import com.example.pokedexandroid.Type;
import com.robertlevonyan.views.chip.Chip;

import java.net.Proxy;
import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {

    Context context;
    List<String> typeList;

    public TypeAdapter(Context context, List<String> typeList) {
        this.context = context;
        this.typeList = typeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.chip_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.chip.setText(typeList.get(position));
        holder.chip.setChipBackgroundColor(Common.getColorByType(typeList.get(position)));

    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        Chip chip;

        public ViewHolder(View itemView){
            super(itemView);
            chip = itemView.findViewById(R.id.chip);
            chip.setOnClickListener(view -> LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(new Intent(Common.KEY_POKEMON_TYPE)
                            .putExtra("type", typeList.get(getAdapterPosition())))
            );
        }
    }
}
