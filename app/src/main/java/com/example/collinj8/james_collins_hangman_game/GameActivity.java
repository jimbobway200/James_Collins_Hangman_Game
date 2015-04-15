package com.example.collinj8.james_collins_hangman_game;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class GameActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Grab fields from user view (activity_main.xml)
        EditText user_guess= (EditText) findViewById(R.id.user_guess);
        TextView current_guessed_letters = (TextView) findViewById(R.id.current_guesses);
        TextView remaining_guesses = (TextView) findViewById(R.id.remaining_guesses);
        TextView revealed_word= (TextView) findViewById(R.id.guess_word);

        String game_word = setupgame(0);

        //Add Watcher for button "GUESS"
        Button guess_button = (Button) findViewById(R.id.button_guess);
        guess_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        //Add Watcher for button "NEW WORD"
        Button new_word_button = (Button) findViewById(R.id.button_new_word);
        new_word_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String setupgame (int i)
    {
        // Grab fields from user view (activity_main.xml)
        EditText user_guess= (EditText) findViewById(R.id.user_guess);
        TextView current_guessed_letters = (TextView) findViewById(R.id.guessed_letters);
        TextView num_remaining_guesses = (TextView) findViewById(R.id.num_guesses_left);
        TextView revealed_word= (TextView) findViewById(R.id.guess_word);

        //Get array I created from strings.xml
        String[] word_array = getResources().getStringArray(R.array.words);
        String new_word = word_array[i];

        //Get size of word and fill question marks on page
        int size_of_new_word = new_word.length();
        int itt = 0;
        String updated_string = "";
        for (itt = 0; itt < size_of_new_word; itt ++)
        {
            updated_string = updated_string + "?";
        }
        revealed_word.setText(updated_string);

        //Set Guessed Letters back to default
        current_guessed_letters.setText("");

        //Set Number of guess left to default
        num_remaining_guesses.setText("6");

        return new_word;
    }
}

