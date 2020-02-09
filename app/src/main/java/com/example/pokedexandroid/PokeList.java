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

import com.example.pokedexandroid.Common.Common;
import com.example.pokedexandroid.Common.ItemOffsetDecoration;
import com.example.pokedexandroid.Model.Pokedex;
import com.example.pokedexandroid.Adapter.PokeListAdapter;
import com.example.pokedexandroid.Model.Pokemon;
import com.example.pokedexandroid.RetrofitClient.IPokemonDex;
import com.example.pokedexandroid.RetrofitClient.RetrofitClient;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class PokeList extends Fragment {

    IPokemonDex iPokemonDex;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    RecyclerView poke_list_rv;

    PokeListAdapter adapter,searchAdapter;

    List<String> last_suggest = new ArrayList<>();

    MaterialSearchBar searchBar;

    static PokeList instance;

    public static PokeList getInstance(){

        if(instance == null){
            instance = new PokeList();
        }

        return instance;
    }

    public PokeList() {
        Retrofit retrofit = RetrofitClient.getInstance();
        iPokemonDex = retrofit.create(IPokemonDex.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_poke_list, container, false);

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



        fetchData();

        return view;
    }

    private void startSearch(CharSequence text){
        if(Common.commonPokemonList.size() > 0) {
            List<Pokemon> result = new ArrayList<>();
            for (Pokemon pokemon : Common.commonPokemonList){
                if (pokemon.getName().toLowerCase().contains(text)){
                    result.add(pokemon);
                }
            }
            searchAdapter = new PokeListAdapter(getActivity(),result);
            poke_list_rv.setAdapter(searchAdapter);

        }
    }

    private void fetchData(){
        compositeDisposable.add(iPokemonDex.getListPokemon()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pokedex -> {
                    Common.commonPokemonList = pokedex.getPokemon();
                    adapter = new PokeListAdapter(getActivity(), Common.commonPokemonList);

                    poke_list_rv.setAdapter(adapter);
                    last_suggest.clear();
                    for(Pokemon pokemon : Common.commonPokemonList){
                        last_suggest.add(pokemon.getName());
                    }
                    searchBar.setVisibility(View.VISIBLE);
                    searchBar.setLastSuggestions(last_suggest);
                })
        );
    }

}
