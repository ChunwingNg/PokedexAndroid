package com.example.pokedexandroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedexandroid.Common.Common;
import com.example.pokedexandroid.Interface.IItemClickListener;
import com.example.pokedexandroid.Model.Evolution;
import com.example.pokedexandroid.R;
import com.robertlevonyan.views.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class EvolutionAdapter extends RecyclerView.Adapter<EvolutionAdapter.ViewHolder> {

    Context context;
    List<Evolution> evolutions;


    public EvolutionAdapter(Context context, List<Evolution> evolutions) {
        this.context = context;
        if(evolutions != null) {
            this.evolutions = evolutions;
        }
        else{
            this.evolutions = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.chip_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.chip.setText(evolutions.get(position).getName());
        holder.chip.setChipBackgroundColor(
                Common.getColorByType(
                        Common.findPokemonByNum(
                                evolutions.get(position).getNum()
                        ).getType().get(0)
                )
        );
    }

    @Override
    public int getItemCount() {
        return evolutions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        Chip chip;


        public ViewHolder(View itemView){
            super(itemView);
            chip = (Chip) itemView.findViewById(R.id.chip);
            chip.setOnClickListener(view -> LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(new Intent(Common.KEY_NUM_EVOLUTION)
                            .putExtra("num",evolutions.get(getAdapterPosition())
                                    .getNum()
                            )
                    )
            );
        }
    }
}
