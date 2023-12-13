package com.example.quote_of_the_day;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AllFavoritesActivity extends AppCompatActivity {

    private ListView favoritesListView;
    private QuotesDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_favorites);

        favoritesListView = findViewById(R.id.favorites_list_view);
        databaseHelper = new QuotesDatabaseHelper(this);

        // Display all favorite quotes
        List<Quote> favoriteQuotes = databaseHelper.getFavoriteQuotes();
        FavoritesAdapter adapter = new FavoritesAdapter(this, R.layout.item_favorite_quote, favoriteQuotes);
        favoritesListView.setAdapter(adapter);
    }
}
