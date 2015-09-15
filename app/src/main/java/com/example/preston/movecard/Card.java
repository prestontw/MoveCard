package com.example.preston.movecard;

/**
 * Created by preston on 7/5/15.
 * Represents a default card using enum's
 */
public class Card {

    enum values  {two, three, four, five, six, seven, eight, nine, ten, jack, queen, king, ace}
    enum suits   {club, diamond, heart, spade}
    private int value;
    private String suit;
    private boolean faceDown;

    public Card(String suit, int value) {

        this.suit = suit;
        this.value = value;
        faceDown = false;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public boolean equals(Card other){
        return other.getValue() == this.getValue() &&
                other.getSuit().equals(this.getSuit());
    }

    public String toString(){
        return suit + "_" + value;
    }

    public void flipOver() {
        faceDown = !faceDown;
    }

    public boolean getFaceDown() {
        return faceDown;
    }
}
