package com.alessio.scopa;

import java.util.ArrayList;
import java.util.Collections;

public class Mazzo {
    private final ArrayList<Carta> carte; // Lista delle carte del mazzo

    public Mazzo() {
        this.carte = new ArrayList<>();
        inizializzaMazzo();
    }

    public static void main(String[] args) {
        Mazzo mazzo = new Mazzo();
        mazzo.mescolaMazzo();
        System.out.println("Carte nel mazzo dopo la mischiata:");
        System.out.println(mazzo);

        Carta cartaPescata = mazzo.pescaCarta();
        System.out.println("Carta pescata: " + cartaPescata);

        System.out.println("Carte rimanenti nel mazzo: " + mazzo.carteRimanenti());
    }

    // Metodo per inizializzare il mazzo con tutte le 40 carte
    private void inizializzaMazzo() {
        String[] semi = {"Coppe", "Denari", "Spade", "Bastoni"};
        for (String seme : semi) {
            for (int valore = 1; valore <= 10; valore++) {
                carte.add(new Carta(valore, seme));
            }
        }
    }

    // Mischia il mazzo
    public void mescolaMazzo() {
        Collections.shuffle(carte);
        Collections.shuffle(carte);
        Collections.shuffle(carte);
    }

    // Pesca una carta dal mazzo
    public Carta pescaCarta() {
        if (carte.isEmpty()) {
            throw new IllegalStateException("Il mazzo è vuoto!");
        }
        return carte.remove(0); // Rimuove e restituisce la prima carta
    }

    // Resetta il mazzo (svuota e reinizializza)
    public void resetMazzo() {
        carte.clear();
        inizializzaMazzo();
        mescolaMazzo();
    }

    // Getter per il numero di carte rimanenti
    public int carteRimanenti() {
        return carte.size();
    }

    // Metodo di debug per stampare il mazzo
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Carta carta : carte) {
            sb.append(carta).append("\n");
        }
        return sb.toString();
    }

/*
    public Mazzo(ArrayList m, int max)
    {
        int n = 40;
        for(int i=0;i<max;i++)
        {
            int numeroCasuale = (int)(Math.random() * n) + 1;
            while(numeroCasuale<0 || numeroCasuale>=m.size())
                numeroCasuale = (int)(Math.random() * n) + 1;
            Carta carta = (Carta)m.remove(numeroCasuale);
            Mazzo.add(i,carta);
            n--;
        }
    }

    public Mazzo(int max)
    {

    }

    public ArrayList getMazzo()
    {
        return Mazzo;
    }

    public Carta getCarta(int pos)
    {
        return (Carta)Mazzo.get(pos);
    }

    public void aggiungiCarta(Carta c)
    {
        Mazzo.add(c);
    }

    public int getNumeroCarte()
    {
        return Mazzo.size();
    }

    private String figura(int n)
    {
        String stringa = "";
        switch (n)
        {
            case 1: stringa = "carte/01_basto.png";
            V = 1;
            S = 16;
            D = false;
            break;

            case 2: stringa = "carte/02_basto.png";
            V = 2;
            S = 12;
            D = false;
            break;

            case 3: stringa = "carte/03_basto.png";
            V = 3;
            S = 13;
            D = false;
            break;

            case 4: stringa = "carte/04_basto.png";
            V = 4;
            S = 14;
            D = false;
            break;

            case 5: stringa = "carte/05_basto.png";
            V = 5;
            S = 15;
            D = false;
            break;

            case 6: stringa = "carte/06_basto.png";
            V = 6;
            S = 18;
            D = false;
            break;

            case 7: stringa = "carte/07_basto.png";
            V = 7;
            S = 21;
            D = false;
            break;

            case 8: stringa = "carte/08_basto.png";
            V = 8;
            S = 10;
            D = false;
            break;

            case 9: stringa = "carte/09_basto.png";
            V = 9;
            S = 10;
            D = false;
            break;

            case 10: stringa = "carte/10_basto.png";
            V = 10;
            S = 10;
            D = false;
            break;

            case 11: stringa = "carte/01_coppe.png";
            V = 1;
            S = 16;
            D = false;
            break;

            case 12: stringa = "carte/02_coppe.png";
            V = 2;
            S = 12;
            D = false;
            break;

            case 13: stringa = "carte/03_coppe.png";
            V = 3;
            S = 13;
            D = false;
            break;
            

            case 14: stringa = "carte/04_coppe.png";
            V = 4;
            S = 14;
            D = false;
            break;

            case 15: stringa = "carte/05_coppe.png";
            V = 5;
            S = 15;
            D = false;
            break;

            case 16: stringa = "carte/06_coppe.png";
            V = 6;
            S = 18;
            D = false;
            break;

            case 17: stringa = "carte/07_coppe.png";
            V = 7;
            S = 21;
            D = false;
            break;

            case 18: stringa = "carte/08_coppe.png";
            V = 8;
            S = 10;
            D = false;
            break;

            case 19: stringa = "carte/09_coppe.png";
            V = 9;
            S = 10;
            D = false;
            break;

            case 20: stringa = "carte/10_coppe.png";
            V = 10;
            S = 10;
            D = false;
            break;

            case 21: stringa = "carte/01_denar.png";
            V = 1;
            S = 16;
            D = true;
            break;

            case 22: stringa = "carte/02_denar.png";
            V = 2;
            S = 12;
            D = true;
            break;

            case 23: stringa = "carte/03_denar.png";
            V = 3;
            S = 13;
            D = true;
            break;

            case 24: stringa = "carte/04_denar.png";
            V = 4;
            S = 14;
            D = true;
            break;

            case 25: stringa = "carte/05_denar.png";
            V = 5;
            S = 15;
            D = true;
            break;

            case 26: stringa = "carte/06_denar.png";
            V = 6;
            S = 18;
            D = true;
            break;

            case 27: stringa = "carte/07_denar.png";
            V = 7;
            S = 21;
            D = true;
            break;

            case 28: stringa = "carte/08_denar.png";
            V = 8;
            S = 10;
            D = true;
            break;

            case 29: stringa = "carte/09_denar.png";
            V = 9;
            S = 10;
            D = true;
            break;

            case 30: stringa = "carte/10_denar.png";
            V = 10;
            S = 10;
            D = true;
            break;
            
            case 31: stringa = "carte/01_spade.png";
            V = 1;
            S = 16;
            D = false;
            break;
            
            case 32: stringa = "carte/02_spade.png";
            V = 2;
            S = 12;
            D = false;
            break;

            case 33: stringa = "carte/03_spade.png";
            V = 3;
            S = 13;
            D = false;
            break;

            case 34: stringa = "carte/04_spade.png";
            V = 4;
            S = 14;
            D = false;
            break;

            case 35: stringa = "carte/05_spade.png";
            V = 5;
            S = 15;
            D = false;
            break;

            case 36: stringa = "carte/06_spade.png";
            V = 6;
            S = 18;
            D = false;
            break;

            case 37: stringa = "carte/07_spade.png";
            V = 7;
            S = 21;
            D = false;
            break;

            case 38: stringa = "carte/08_spade.png";
            V = 8;
            S = 10;
            D = false;
            break;

            case 39: stringa = "carte/09_spade.png";
            V = 9;
            S = 10;
            D = false;
            break;

            case 40: stringa = "carte/10_spade.png";
            V = 10;
            S = 10;
            D = false;
            break;
        }
        return stringa;
    }
*/
}
