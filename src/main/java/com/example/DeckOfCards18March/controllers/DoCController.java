package com.example.DeckOfCards18March.controllers;

import java.io.IOException;
import java.util.List;

import com.example.DeckOfCards18March.models.Card;
import com.example.DeckOfCards18March.models.Deck;
import com.example.DeckOfCards18March.services.DoCService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/deck")
public class DoCController {
    
    @Autowired
    private DoCService deckSvc;

    @PostMapping(path="",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String postDeck(Model model){
        Deck deck = null;
        try {
            deck = deckSvc.createDeck();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String deckImgUrl = "https://deckofcardsapi.com/static/img/back.png";
        Integer deckRemaining = deck.getRemaining();
        Boolean deckShuffled = deck.isShuffled();
        String deckId = deck.getDeck_id();

        model.addAttribute("deckImgUrl", deckImgUrl);
        model.addAttribute("deckRemaining",deckRemaining);
        model.addAttribute("deckShuffled", deckShuffled);
        model.addAttribute("deckId",deckId);
        return "start";
    }

    @PostMapping(path="/{deck_id}",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String drawCard(@RequestBody MultiValueMap<String, String> form,
    @PathVariable String deck_id, Model model){
        String drawCard = form.getFirst("drawCard");

        try {
            Deck drawnDeck = deckSvc.drawCards(deck_id, Integer.parseInt(drawCard));
            String deckImgUrl = "https://deckofcardsapi.com/static/img/back.png";
            Integer deckRemaining = drawnDeck.getRemaining();
            Boolean deckShuffled = drawnDeck.isShuffled();
            String deckId = drawnDeck.getDeck_id();
            List<Card> cardList = drawnDeck.getListOfCards();
            model.addAttribute("deckImgUrl", deckImgUrl);
            model.addAttribute("deckRemaining",deckRemaining);
            model.addAttribute("deckShuffled", deckShuffled);
            model.addAttribute("deckId",deckId);
            model.addAttribute("cardList", cardList.toArray());
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


       

       

        return "drawn";
    }



}
