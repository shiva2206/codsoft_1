package com.example.quote_of_the_day;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView quoteTextView;
    private Button favoriteButton;
    private Button allFavoritesButton;
    private Button shareButton;
    private Quote today;
    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onResume() {
        today = databaseHelper.getQuoteByText(today.getText());
        favoriteButton.setText((today.isFavorite()==1)?"Unmark as Favorite":"Mark as Favorite");
        super.onResume();
    }

    private QuotesDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new QuotesDatabaseHelper(this);
        today = getQuoteOfTheDay();

        quoteTextView = findViewById(R.id.quote_text);
        favoriteButton = findViewById(R.id.favorite_button);
        allFavoritesButton = findViewById(R.id.all_favorites_button);
        shareButton = findViewById(R.id.share_button);


        quoteTextView.setText(today.getText()+"");

        // Set daily quote
//        Quote today = setDailyQuote();
        favoriteButton.setText((today.isFavorite()==1)?"Unmark as Favorite":"Mark as Favorite");
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mark quote as favorite

                databaseHelper.markFavorite(quoteTextView.getText().toString(),(today.isFavorite()==0)?1:0);
                today.setFavorite((today.isFavorite()==0)?1:0);
                favoriteButton.setText((today.isFavorite()==1)?"Unmark as Favorite":"Mark as Favorite");
                Toast.makeText(MainActivity.this, "Quote marked as favorite", Toast.LENGTH_SHORT).show();
            }
        });

        allFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show all favorites
                Intent intent = new Intent(MainActivity.this, AllFavoritesActivity.class);
                startActivity(intent);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // Share quote on social media
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, quoteTextView.getText().toString());
                startActivity(intent);
            }
        });

//        List<Quote> quotes = new ArrayList<>(25);
//
//// Inspirational quotes
//        quotes.add(new Quote("The best and most beautiful things in the world cannot be seen or even touched - they must be felt with the heart.","- Helen Keller"));
//        quotes.add(new Quote("The only way to do great work is to love what you do.","Steve Jobs"));
//        quotes.add(new Quote("It is during our darkest moments that we must focus to see the light.","Aristotle"));
//        quotes.add(new Quote("The difference between ordinary and extraordinary is that little extra.","Jimmy Johnson"));
//        quotes.add(new Quote("Twenty years from now you will be more disappointed by the things that you didn't do than by the ones you did do. So throw off the bowlines, sail away from safe harbor, catch the trade winds in your sails. Explore, Dream, Discover.","Mark Twain"));
//
//// Motivational quotes
//        quotes.add(new Quote("Don't let what you cannot do interfere with what you can do.","John Wooden"));
//        quotes.add(new Quote("The only person you are destined to become is the person you decide to be.","Ralph Waldo Emerson"));
//        quotes.add(new Quote("Everything you've ever wanted is on the other side of fear.","George Addair"));
//        quotes.add(new Quote("We are all in the gutter, but some of us are looking at the stars.","Oscar Wilde"));
//        quotes.add(new Quote("The mind is everything. What you think you become.","Buddha"));
//
//// Humorous quotes
//        quotes.add(new Quote("I'm not lazy, I'm just highly motivated to do nothing.","Unknown"));
//        quotes.add(new Quote("I'm on a seafood diet. I see food, and I eat it.","Mitch Hedberg"));
//        quotes.add(new Quote("I walk around like everything is fine, but deep down, inside my shoe, my sock is falling off.","Unknown"));
//        quotes.add(new Quote("I'm not sure why I did that, but it seemed like a good idea at the time.","Unknown"));
//        quotes.add(new Quote("I'm not superstitious, but I am a little stitious.","Michael Scott"));
//
//// Wisdom and life lessons
//        quotes.add(new Quote("The only true wisdom is in knowing you know nothing.","Socrates"));
//        quotes.add(new Quote("The unexamined life is not worth living.","Socrates"));
//        quotes.add(new Quote("Life is what happens when you're busy making other plans.","John Lennon"));
//        quotes.add(new Quote("The only limit to our realization of tomorrow will be our doubts of today.","Franklin D. Roosevelt"));
//        quotes.add(new Quote("The purpose of life is not to be happy. It is to be useful, to be honorable, to be compassionate, to have it make some difference that you have lived and lived well.","Ralph Waldo Emerson"));
//
//// Love and relationships
//        quotes.add(new Quote("Love is the only force capable of transforming an enemy into a friend.","Martin Luther King Jr."));
//        quotes.add(new Quote("A successful marriage requires falling in love many times, always with the same person.","Mignon McLaughlin"));
//        quotes.add(new Quote("The best thing to hold onto in life is each other.","Audrey Hepburn"));
//        quotes.add(new Quote("To love and be loved is to feel the sun from both sides.","David Viscott"));
//        quotes.add(new Quote("Grow old with me, the best is yet to be.","Robert Browning"));
//
//
//        for(Quote q : quotes){
//            databaseHelper.addQuote(q);
//        }
    }

    private void setDailyQuote() {
        Quote quote = getQuoteOfTheDay();
        quoteTextView.setText(quote.getText());

    }

    private Quote getQuoteOfTheDay() {
        // Implement logic to fetch a random quote from the database
        // or other source and return it
        List<Quote> l = databaseHelper.getAllQuotes();
        Random r = new Random();
        return l.get(r.nextInt(l.size()));
    }
}