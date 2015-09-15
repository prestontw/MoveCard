package com.example.preston.movecard;

/**
 * Created by preston on 7/5/15.
 * Represents a default card using enum's
 */
public class Card {

    enum values  {two, three, four, five, six, seven, eight, nine, ten, jack, queen, king, ace}
    enum suits   {club, diamond, heart, spade}
    private values value;
    private suits suit;

    public Card(suits suit, values value) {

        this.suit = suit;
        this.value = value;
    }

    public suits getSuit() {
        return suit;
    }

    public values getValue() {
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
