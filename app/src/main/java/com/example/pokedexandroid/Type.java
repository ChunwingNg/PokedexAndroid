package com.example.pokedexandroid;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokedexandroid.Adapter.PokeListAdapter;
import com.example.pokedexandroid.Common.Common;
import com.example.pokedexandroid.Common.ItemOffsetDecoration;
import com.example.pokedexandroid.Model.Pokemon;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Type extends Fragment {

    RecyclerView poke_list_rv;

    PokeListAdapter adapter,searchAdapter;

    List<String> last_suggest = new ArrayList<>();

    MaterialSearchBar searchBar;

    List<Pokemon> typeList;

    static Type instance;

    public static Type getInstance() {
        if(instance == null){
            instance = new Type();
        }
        return instance;
    }

    public Type() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_type, container, false);

        poke_list_rv = view.findViewById(R.id.pokemon_list_recyclerview);
        poke_list_rv.setHasFixedSize(true);
        poke_list_rv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        ItemOffsetDecoration iOD = new ItemOffsetDecoration(getActivity(),R.dimen.spacing);
        poke_list_rv.addItemDecoration(iOD);

        //Search bar setup
        searchBar = view.findViewById(R.id.search_bar);
        searchBar.setHint("Search by name");
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();
                for(String search:last_suggest){
                    if(search.toLowerCase().contains(searchBar.getText().toLowerCase())){
                        suggest.add(search);
                    }
                    searchBar.setLastSuggestions(suggest);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled){
                    poke_list_rv.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        if(getArguments() != null){
            String type = getArguments().getString("type");
            if(type != null){

                typeList = Common.findPokemonsByType(type);
                adapter = new PokeListAdapter(getActivity(),typeList);
                poke_list_rv.setAdapter(adapter);
                loadSuggest();
            }
        }

        return view;
    }

    private void loadSuggest() {
        last_suggest.clear();
        if(typeList.size() > 0){
            for(Pokemon pokemon:typeList){
                last_suggest.add(pokemon.getName());
            }
            searchBar.setLastSuggestions(last_suggest);
        }
    }

    private void startSearch(CharSequence text){
        if(Common.commonPokemonList.size() > 0) {
            List<Pokemon> result = new ArrayList<>();
            for (Pokemon pokemon : typeList){
                if (pokemon.getName().toLowerCase().contains(text)){
                    result.add(pokemon);
                }
            }
            searchAdapter = new PokeListAdapter(getActivity(),result);
            poke_list_rv.setAdapter(searchAdapter);

        }
    }

}
