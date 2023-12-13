package com.example.quote_of_the_day;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class FavoritesAdapter extends ArrayAdapter<Quote> {

    private Context context;
    private List<Quote> quotes;

    public FavoritesAdapter(Context context, int resource, List<Quote> objects) {
        super(context, resource, objects);
        this.context = context;
        this.quotes = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite_quote, parent, false);

        TextView quoteText = view.findViewById(R.id.quote_text);
        TextView authorText = view.findViewById(R.id.author_text);
        Button fav = view.findViewById(R.id.favorite_button);


        Quote quote = quotes.get(position);
        quoteText.setText(quote.getText());
        authorText.setText(quote.getAuthor());

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuotesDatabaseHelper databaseHelper = new QuotesDatabaseHelper(context);
                databaseHelper.markFavorite(quoteText.getText()+"",0);
                Intent t = new Intent(context,AllFavoritesActivity.class);
                ((Activity)context).startActivity(t);
                ((Activity)context).finish();

            }
        });
        return view;
    }
}
