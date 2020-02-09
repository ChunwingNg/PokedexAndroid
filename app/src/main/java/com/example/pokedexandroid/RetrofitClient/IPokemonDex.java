package com.example.pokedexandroid.RetrofitClient;

import com.example.pokedexandroid.Model.Pokedex;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IPokemonDex {
    @GET("pokedex.json")
    Observable<Pokedex> getListPokemon();
}
