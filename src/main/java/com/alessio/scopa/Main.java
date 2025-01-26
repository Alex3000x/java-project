package com.alessio.scopa;

public class Main {
    // Main method to test Deck's methods
    public static void main(String[] args) {
        System.out.println("Generation of a deck of Neapolitan cards.");
        Deck deck = new NeapolitanDeck();
        System.out.println("Shuffle of the deck.");
        deck.shuffleDeck();
        System.out.println("Cards of the deck after the shuffle:");
        System.out.println(deck);
        System.out.println("Draw a card from the deck:");
        Card drawnCard = deck.drawCard();
        System.out.println("Card drawn: " + drawnCard);
        System.out.println("Cards left in the deck: " + deck.remainingCards());
    }
}
