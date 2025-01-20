package com.alessio.scopa;

public class Carta {
    private final int valore; // Valore numerico della carta (1-10)
    private final String seme; // Seme della carta ("Coppe", "Spade", "Denari", "Bastoni")
    private final int primiera; // Valore per il calcolo della primiera/settanta
    private boolean scopa; // Indica se questa carta è una "scopa"

    public Carta(int valore, String seme) {
        this.valore = valore;
        this.seme = seme;
        this.primiera = valorePrimiera(valore); // Determina il valore della primiera
        this.scopa = false; // Di default non è scopa
    }

    public static void main(String[] args) {
        Carta carta = new Carta(7, "Denari");
        System.out.println(carta); // Output: 7 di Denari (Primiera: 21)
    }

    // Getter
    public int getValore() {
        return valore;
    }

    public String getSeme() {
        return seme;
    }

    public int getSettanta() {
        return primiera;
    }

    public boolean isScopa() {
        return scopa;
    }

    // Setter
    public void setScopa(boolean scopa) {
        this.scopa = scopa;
    }

    // Metodo privato per calcolare il valore della primiera
    private int valorePrimiera(int valore) {
        switch (valore) {
            case 7:
                return 21;
            case 6:
                return 18;
            case 1:
                return 16;
            case 5:
                return 15;
            case 4:
                return 14;
            case 3:
                return 13;
            case 2:
                return 12;
            default:
                return 10; // Valori 8, 9 e 10 hanno valore della primiera=10
        }
    }

    // Metodo di debug per stampare la carta
    @Override
    public String toString() {
        return valore + " di " + seme + " (primiera: " + primiera + ")" + (scopa ? " (è scopa)" : "");
    }
}
