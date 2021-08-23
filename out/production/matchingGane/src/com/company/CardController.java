package com.company;
// Controller class

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

public class CardController implements ActionListener {
    private Vector turnedCards;
    private Timer turnDownTimer;
    private final int turnDownDelay = 2000; // milliseconds
    private int pairs;
    public int numTries;
    public int size;
    public int numTimed;
    String str = "0Joker.png";
    ImageIcon joker = new ImageIcon(str);
    URL img = ClassLoader.getSystemResource(str);
    Card testCard = new Card(this, new ImageIcon("0Joker.png"),
            new ImageIcon("0Joker.png"), 0);


    public CardController() {

        this.turnedCards = new Vector(2);
        this.turnDownTimer = new Timer(this.turnDownDelay, this);
        this.turnDownTimer.setRepeats(false);
        this.pairs = 0;
        this.numTries = 0;
        this.size = 0;
        this.numTimed = 0;
    }

    public boolean turnUp(Card card) {
        if(this.turnedCards.size() < 2)
            return doAddCard(card);
        return false;
    }

    private boolean doAddCard(Card card) {
        this.turnedCards.add(card);
        if(this.turnedCards.size() == 2) {
            Card otherCard = (Card) this.turnedCards.get(0);
            if(otherCard.getNum() == card.getNum()) {
                pairs++;
                numTimed--;
                this.turnedCards.clear();
            } else {
                numTimed++;
                this.turnDownTimer.start();
            }
        }
        return true;
    }

    public int getPairs() {
        return pairs;
    }

    public int getNumTries() {
        return numTries;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < this.turnedCards.size(); i++) {
            Card card = (Card) this.turnedCards.get(i);
            card.turnDown();
        }
        numTries++;
        System.out.println("Pairs: " + getPairs());
        System.out.println("Tries: " + getNumTries());
        System.out.println("Card.size : " + size);
        System.out.println("Timer runs: " + numTimed);
        this.turnedCards.clear();


    }
    // add code snippet about controller to the Card and MatchingGame classes

}
