package com.alessio.scopa;

import java.util.ArrayList;

public class GameState {
    // Carte sul tavolo
    private ArrayList<Card> carteSulTavolo;

    // Carte in mano ai giocatori
    private ArrayList<Card> carteGiocatore;
    private ArrayList<Card> carteComputer;

    // Punteggi
    private int punteggioGiocatore;
    private int punteggioComputer;

    // Turno attuale (true= Turno del giocatore, false = turno del computer)
    private boolean turnoGiocatore;

    // Costruttore
    public GameState() {
        carteSulTavolo = new ArrayList<>();
        carteGiocatore = new ArrayList<>();
        carteComputer = new ArrayList<>();
        punteggioGiocatore = 0;
        punteggioComputer = 0;
        turnoGiocatore = true; //inizia il giocatore
    }

    // Getter e Setter
    public ArrayList<Card> getCarteSulTavolo() {
        return carteSulTavolo;
    }

    public void setCarteSulTavolo(ArrayList<Card> carteSulTavolo) {
        this.carteSulTavolo = carteSulTavolo;
    }

    public ArrayList<Card> getCarteGiocatore() {
        return carteGiocatore;
    }

    public void setCarteGiocatore(ArrayList<Card> carteGiocatore) {
        this.carteGiocatore = carteGiocatore;
    }

    public ArrayList<Card> getCarteComputer() {
        return carteComputer;
    }

    public void setCarteComputer(ArrayList<Card> carteComputer) {
        this.carteComputer = carteComputer;
    }

    public int getPunteggioGiocatore() {
        return punteggioGiocatore;
    }

    public void setPunteggioGiocatore(int punteggioGiocatore) {
        this.punteggioGiocatore = punteggioGiocatore;
    }

    public int getPunteggioComputer() {
        return punteggioComputer;
    }

    public void setPunteggioComputer(int punteggioComputer) {
        this.punteggioComputer = punteggioComputer;
    }

    public boolean isTurnoGiocatore() {
        return turnoGiocatore;
    }

    public void setTurnoGiocatore(boolean turnoGiocatore) {
        this.turnoGiocatore = turnoGiocatore;
    }
}
