package com.example.preston.movecard;

/**
 * Created by preston on 7/5/15.
 */
public class Card {

    private int value;
    private String suit;

    public Card(String suit, int value) {

        this.suit = suit;
        this.value = value;
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
}
