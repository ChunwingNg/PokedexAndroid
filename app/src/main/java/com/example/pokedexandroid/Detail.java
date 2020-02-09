package com.example.pokedexandroid;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pokedexandroid.Adapter.EvolutionAdapter;
import com.example.pokedexandroid.Adapter.TypeAdapter;
import com.example.pokedexandroid.Common.Common;
import com.example.pokedexandroid.Model.Pokemon;


/**
 * A simple {@link Fragment} subclass.
 */
public class Detail extends Fragment {

    ImageView pokemon_img;
    TextView pokemon_name,pokemon_height,pokemon_weight;
    RecyclerView rV_type,rV_weakness,rV_evol,rV_prevol;

    static Detail instance;

    public static Detail getInstance(){
        if(instance == null){
            instance = new Detail();
        }
        return instance;
    }

    public Detail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView =inflater.inflate(R.layout.fragment_detail, container, false);

        Pokemon pokemon = Common.findPokemonByNum(getArguments().getString("num"));


        pokemon_img = itemView.findViewById(R.id.pokemon_image);
        pokemon_name = itemView.findViewById(R.id.name);
        pokemon_height = itemView.findViewById(R.id.height);
        pokemon_weight = itemView.findViewById(R.id.weight);

        rV_type = itemView.findViewById(R.id.recycler_type);
        rV_type.setHasFixedSize(true);
        rV_type.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rV_weakness = itemView.findViewById(R.id.recycler_weakness);
        rV_weakness.setHasFixedSize(true);
        rV_weakness.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rV_evol = itemView.findViewById(R.id.recycler_evolution);
        rV_evol.setHasFixedSize(true);
        rV_evol.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rV_prevol = itemView.findViewById(R.id.recycler_prevolution);
        rV_prevol.setHasFixedSize(true);
        rV_prevol.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        setDetailPokemon(pokemon);

        return itemView;
    }

    private void setDetailPokemon(Pokemon pokemon) {
        //Load the image
        Glide.with(getActivity()).load(pokemon.getImg()).into(pokemon_img);

        pokemon_name.setText(pokemon.getName());
        pokemon_height.setText("Height: " + pokemon.getHeight());
        pokemon_weight.setText("Weight: " + pokemon.getWeight());

        //Set Type
        TypeAdapter typeAdapter = new TypeAdapter(getActivity(), pokemon.getType());
        rV_type.setAdapter(typeAdapter);

        //Set Weakness
        TypeAdapter weaknessAdapter = new TypeAdapter(getActivity(), pokemon.getWeaknesses());
        rV_weakness.setAdapter(weaknessAdapter);

        //Set prevEvolution
        EvolutionAdapter pEvolAD = new EvolutionAdapter(getActivity(), pokemon.getPrev_evolution());
        rV_prevol.setAdapter(pEvolAD);

        //Set Evolution
        EvolutionAdapter evolAD = new EvolutionAdapter(getActivity(), pokemon.getNext_evolution());
        rV_evol.setAdapter(evolAD);
    }

}
