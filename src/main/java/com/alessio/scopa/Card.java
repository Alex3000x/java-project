package com.alessio.scopa;

public class Card {
    private final int value; // Value of the card (1-10)
    private final String suit; // Suit of the card (e.g., Cups, Coins, Swords, Clubs)
    private final int prime; // Value for the prime calculation
    private boolean scopa; // Indicates if this card is a "scopa"

    // Constructor
    public Card(int value, String suit) {
        this.value = value;
        this.suit = suit;
        this.prime = calculatePrime(value);
        this.scopa = false;
    }

    // Getters
    public int getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    public int getPrime() {
        return prime;
    }

    public boolean isScopa() {
        return scopa;
    }

    // Setter
    public void setScopa(boolean scopa) {
        this.scopa = scopa;
    }

    // Private method to calculate the prime value
    private int calculatePrime(int value) {
        switch (value) {
            case 7: return 21;
            case 6: return 18;
            case 1: return 16;
            case 5: return 15;
            case 4: return 14;
            case 3: return 13;
            case 2: return 12;
            default: return 10;
        }
    }

    // Debug method to print the card
    @Override
    public String toString() {
        return value + " of " + suit
        /*+ " (prime: " + prime + ")" + (scopa ? " (scopa)" : "")*/;
    }
}
