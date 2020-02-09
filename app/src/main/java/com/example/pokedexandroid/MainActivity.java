package com.example.pokedexandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.pokedexandroid.Common.Common;
import com.example.pokedexandroid.Model.Pokemon;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    BroadcastReceiver showType = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().toString().equals(Common.KEY_POKEMON_TYPE)){
                //Replace Fragment
                Fragment pokemonType = Type.getInstance();
                String type = intent.getStringExtra("type");
                Bundle bundle = new Bundle();
                bundle.putString("type", type);
                pokemonType.setArguments(bundle);

                FragmentTransaction fragTran = getSupportFragmentManager().beginTransaction();
                fragTran.replace(R.id.list_pokemon_fragment,pokemonType);
                fragTran.addToBackStack("type");
                fragTran.commit();

                toolbar.setTitle("POKEMON TYPE: "  + type.toUpperCase());
            }
        }
    };

    BroadcastReceiver showDetail = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().toString().equals(Common.KEY_ENABLE_HOME)){
                //Enable back button
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);

                //Replace Fragment
                Fragment detailFragment = Detail.getInstance();
                String num = intent.getStringExtra("num");
                Bundle bundle = new Bundle();
                bundle.putString("num", num);
                detailFragment.setArguments(bundle);

                getSupportFragmentManager().popBackStack(0,FragmentManager.POP_BACK_STACK_INCLUSIVE);

                FragmentTransaction fragTran = getSupportFragmentManager().beginTransaction();
                fragTran.replace(R.id.list_pokemon_fragment,detailFragment);
                fragTran.addToBackStack("detail");
                fragTran.commit();

                Pokemon pokemon = Common.findPokemonByNum(num);
                toolbar.setTitle(pokemon.getName());
            }
        }
    };

    BroadcastReceiver showEvolution = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().toString().equals(Common.KEY_NUM_EVOLUTION)){

                //Replace Fragment
                Fragment detailFragment = Detail.getInstance();
                Bundle bundle = new Bundle();
                String num = intent.getStringExtra("num");
                bundle.putString("num",num);
                detailFragment.setArguments(bundle);

                FragmentTransaction fragTran = getSupportFragmentManager().beginTransaction();
                fragTran.remove(detailFragment);//Removes the current frag
                fragTran.replace(R.id.list_pokemon_fragment,detailFragment);
                fragTran.addToBackStack("detail");
                fragTran.commit();

                Pokemon pokemon = Common.findPokemonByNum(num);
                toolbar.setTitle(pokemon.getName());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("POKEMON LIST");
        setSupportActionBar(toolbar);

        //Register the broadcast for going home
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showDetail,new IntentFilter(Common.KEY_ENABLE_HOME));

        //Register the broadcast for evolution click
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showEvolution,new IntentFilter(Common.KEY_NUM_EVOLUTION));

        //Register the broadcast for type click
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showType,new IntentFilter(Common.KEY_POKEMON_TYPE));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                toolbar.setTitle("POKEMON LIST");

                //Clears all of the fragments to go back to the list fragment
                getSupportFragmentManager().popBackStack("detail", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().popBackStack("type", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                Fragment pokeList = PokeList.getInstance();

                FragmentTransaction fragTran = getSupportFragmentManager().beginTransaction();
                fragTran.remove(pokeList);
                fragTran.replace(R.id.list_pokemon_fragment,pokeList);
                fragTran.commit();

                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(false);

                break;
            default:
                break;
        }
        return true;
    }
}
