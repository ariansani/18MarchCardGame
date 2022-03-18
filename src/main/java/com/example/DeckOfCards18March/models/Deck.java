package com.example.DeckOfCards18March.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Deck {
    
    private String deck_id;
    private boolean shuffled;
    private Integer remaining;
    private List<Card> listOfCards;

    public String getDeck_id() {
        return deck_id;
    }
    public void setDeck_id(String deck_id) {
        this.deck_id = deck_id;
    }
    public boolean isShuffled() {
        return shuffled;
    }
    public void setShuffled(boolean shuffled) {
        this.shuffled = shuffled;
    }
    public Integer getRemaining() {
        return remaining;
    }
    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }
    public List<Card> getListOfCards() {
        return listOfCards;
    }
    public void setListOfCards(List<Card> listOfCards) {
        this.listOfCards = listOfCards;
    }

    public static Deck create(String json) throws IOException{
        Deck d = new Deck();
        try(InputStream is = new ByteArrayInputStream(json.getBytes())){
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            d.setDeck_id(o.getString("deck_id"));
            d.setRemaining(o.getInt("remaining"));
            d.setShuffled(o.getBoolean("shuffled"));

            r.close();
            return d;
        }
    }

    public static Deck draw(String json) throws IOException{
  
        try(InputStream is = new ByteArrayInputStream(json.getBytes())){
            Deck d = new Deck();
            List<Card> cardList = new LinkedList<>();
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            d.setDeck_id(o.getString("deck_id"));
            d.setRemaining(o.getInt("remaining"));
            d.setShuffled(true);
            JsonArray cardJsonArray = o.getJsonArray("cards");
            for (int i=0; i < cardJsonArray.size(); i++) {
                Card card = new Card();
                JsonObject jso = cardJsonArray.getJsonObject(i);
                card.setCode( jso.getString("code"));
                card.setImage(jso.getString("image"));
                card.setSuit(jso.getString("suit"));
                card.setValue(jso.getString("value"));
                cardList.add(card);
            }

            d.setListOfCards(cardList);
            r.close();
            return d;
        }
    }

    
}
