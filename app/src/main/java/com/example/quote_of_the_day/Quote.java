package com.example.quote_of_the_day;
public class Quote {

    private int id;
    private String text;
    private String author;
    private int favorite;

    public Quote(String text, String author) {
        this.text = text;
        this.author = author;
    }

    public Quote(int id, String text, String author, int favorite) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int isFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
