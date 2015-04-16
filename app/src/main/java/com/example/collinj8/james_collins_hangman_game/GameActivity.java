package com.example.collinj8.james_collins_hangman_game;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class GameActivity extends ActionBarActivity {

    //Variables
    public static int current_word_itt = 0;
    public static int wrong_answer_count = 0;
    private String[] picture_array = {"hangman0.png", "hangman1.png", "hangman2.png", "hangman3.png" , "hangman4.png", "hangman5.png", "hangman6.png"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Grab fields and views from user view (activity_main.xml)
        final EditText user_guess = (EditText) findViewById(R.id.user_guess);
        final TextView current_guessed_letters = (TextView) findViewById(R.id.guessed_letters);
        final TextView revealed_word = (TextView) findViewById(R.id.guess_word);
        final Button guess_button = (Button) findViewById(R.id.button_guess);
        final TextView num_remaining_guesses = (TextView) findViewById(R.id.num_guesses_left);

        //variables
        final String game_word = setupgame(current_word_itt);
        int game_word_length = game_word.length();

        //Add Watcher for button "GUESS"
        guess_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String active_guess = user_guess.getText().toString();
                    String current_guessed_string = current_guessed_letters.getText().toString();
                    String revealed_word_string = revealed_word.getText().toString();
                    ImageView img = (ImageView) findViewById(R.id.current_picture_status);

                    //Has the user guess this already?
                    boolean already_guessed = current_guessed_string.contains(active_guess);
                    if (already_guessed) {
                        //display in long period of time
                        Toast toast = Toast.makeText(getApplicationContext(), "You have already guessed this letter",
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
                        toast.show();
                    }
                    else
                    {
                        //Has not guessed this letter yet
                        int char_found_loc = 0;
                        boolean found = false;
                        StringBuilder temp = new StringBuilder(revealed_word_string);
                        while (char_found_loc != -1) {
                            char_found_loc = game_word.indexOf(active_guess, char_found_loc + 1);
                            if (char_found_loc != -1) { //Did we find his guess in our game word?
                                temp.setCharAt(char_found_loc, active_guess.charAt(0));
                                found = true;
                            }
                        }
                        //So did we ever find a match?
                        if (found) {
                            //Update Current guessed letters
                            StringBuilder new_string_current_guesses = new StringBuilder(current_guessed_string);
                            new_string_current_guesses.append(active_guess);
                            current_guessed_letters.setText(new_string_current_guesses);

                            //Update revealed word
                            revealed_word.setText(temp);

                        } else { //wrong guess
                            wrong_answer_count++;
                            //updated picture and remaining guesses
                            if (wrong_answer_count == 1) {
                                img.setImageResource(R.mipmap.hangman1);
                                num_remaining_guesses.setText("5");
                            } else if (wrong_answer_count == 2) {
                                img.setImageResource(R.mipmap.hangman2);
                                num_remaining_guesses.setText("4");
                            } else if (wrong_answer_count == 3) {
                                img.setImageResource(R.mipmap.hangman3);
                                num_remaining_guesses.setText("3");
                            } else if (wrong_answer_count == 4) {
                                img.setImageResource(R.mipmap.hangman4);
                                num_remaining_guesses.setText("2");
                            } else if (wrong_answer_count == 5) {
                                img.setImageResource(R.mipmap.hangman5);
                                num_remaining_guesses.setText("1");
                            } else if (wrong_answer_count == 6) { //GAME OVER
                                //Hide the Keyboard
                                InputMethodManager inputManager = (InputMethodManager)
                                        getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);

                                //Update Picture and other aspects on page
                                img.setImageResource(R.mipmap.hangman6);
                                guess_button.setVisibility(View.GONE); //Hide Guess Button
                                num_remaining_guesses.setText("0");
                                Toast.makeText(getApplicationContext(), "Sorry! You Lost! Try Again...",
                                        Toast.LENGTH_LONG).show();
                            }

                        }

                    }
                }//END TRY BLOCK
                catch (Exception e) { //User Didn't enter anything
                    Toast.makeText(getApplicationContext(), "Please enter a guess!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


        //Add Watcher for button "NEW WORD" --> User intends on getting a new word and starting from scratch
        Button new_word_button = (Button) findViewById(R.id.button_new_word);
        new_word_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
               current_word_itt ++;
               setupgame(current_word_itt);
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
        // Grab fields and views from user view (activity_main.xml)
        EditText user_guess= (EditText) findViewById(R.id.user_guess);
        TextView current_guessed_letters = (TextView) findViewById(R.id.guessed_letters);
        TextView num_remaining_guesses = (TextView) findViewById(R.id.num_guesses_left);
        TextView revealed_word= (TextView) findViewById(R.id.guess_word);
        Button guess_button = (Button) findViewById(R.id.button_guess);
        ImageView img = (ImageView) findViewById(R.id.current_picture_status);

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

        //Show Guess Button
        guess_button.setVisibility(View.VISIBLE);

        //Set Default Picture for hangman
        img.setImageResource(R.mipmap.hangman0);

        //Reset Wrong Answer Count
        wrong_answer_count = 0;

        return new_word;
    }
}

