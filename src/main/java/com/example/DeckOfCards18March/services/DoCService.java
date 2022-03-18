package com.example.DeckOfCards18March.services;

import java.io.IOException;

import com.example.DeckOfCards18March.models.Deck;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DoCService {
    
    public Deck createDeck() throws IOException{
        final String CREATE_SHUFFLED_DECK_URL = "https://deckofcardsapi.com/api/deck/new/shuffle/";


        String url = UriComponentsBuilder
        .fromUriString(CREATE_SHUFFLED_DECK_URL)
        .queryParam("deck_count", 1)
        .toUriString();

        RequestEntity req = RequestEntity.get(url).build();
        
        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req,String.class);

        return Deck.create(resp.getBody());
    }

    public Deck drawCards(String deckId, Integer count) throws IOException{
        System.out.println(">>>>>>%s >>>>>>%s".formatted(deckId,count));
        String drawCardUrl ="https://deckofcardsapi.com/api/deck/" + deckId +"/draw/";

        String url = UriComponentsBuilder
        .fromUriString(drawCardUrl)
        .queryParam("count", count)
        .toUriString();

        RequestEntity req = RequestEntity.get(url).build();
        
        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req,String.class);


        return Deck.draw(resp.getBody());
        
    }

}
