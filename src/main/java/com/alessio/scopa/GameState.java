package com.alessio.scopa;

import java.util.ArrayList;

public class GameState {
    // Carte sul tavolo
    private ArrayList<Carta> carteSulTavolo;

    // Carte in mano ai giocatori
    private ArrayList<Carta> carteGiocatore;
    private ArrayList<Carta> carteComputer;

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
    public ArrayList<Carta> getCarteSulTavolo() {
        return carteSulTavolo;
    }

    public void setCarteSulTavolo(ArrayList<Carta> carteSulTavolo) {
        this.carteSulTavolo = carteSulTavolo;
    }

    public ArrayList<Carta> getCarteGiocatore() {
        return carteGiocatore;
    }

    public void setCarteGiocatore(ArrayList<Carta> carteGiocatore) {
        this.carteGiocatore = carteGiocatore;
    }

    public ArrayList<Carta> getCarteComputer() {
        return carteComputer;
    }

    public void setCarteComputer(ArrayList<Carta> carteComputer) {
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
