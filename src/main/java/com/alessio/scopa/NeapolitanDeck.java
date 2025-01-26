package com.alessio.scopa;

public class NeapolitanDeck extends Deck {
    public static final String[] SUITS = {"Cups", "Coins", "Swords", "Clubs"};

    // Initializes a deck of Neapolitan cards
    @Override
    protected void initializeDeck() {
        for (String suit : SUITS) {
            for (int value = 1; value <= 10; value++) {
                cards.add(new Card(value, suit));
            }
        }
    }
}
