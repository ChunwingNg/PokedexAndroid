package com.example.pokedexandroid.Model;

import java.util.List;

public class Pokedex {
    //Creates a list of pokemon
    private List<Pokemon> pokemon;

    //Default method
    public Pokedex(){

    }

    public Pokedex(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }

    public List<Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }
}
