package com.alessio.scopa;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;
public class Punteggio
{/*
    static private Main main;
    static private Tavolo tavolo;
    static private Gestione gestione;
    static private JFrame finestra = new JFrame("SCOPA");
    static private JPanel pannello = new JPanel();
    static private JLabel tu = new JLabel("TU"), computer = new JLabel("CPU"), pG = new JLabel(), pC = new JLabel();
    static private int punteggioGiocatore = 0, punteggioComputer = 0;
    

    public static void main(String args[])
    {
        finestra.setLayout(null);
        finestra.setSize(450,190);
        finestra.setLocation(540,75);
        finestra.setBackground(Color.BLACK);
        finestra.setResizable(false);
        
        pannello.setLayout(new GridLayout(2,2,5,5));
        pannello.setOpaque(true);
        pannello.setBackground(Color.BLACK);
        
        tu.setBackground(Color.GREEN);
        tu.setOpaque(true);
        pannello.add(tu);
        
        computer.setBackground(Color.GREEN);
        pannello.add(computer);
        
        pG.setBackground(Color.GREEN);
        pannello.add(pG);
        
        pC.setBackground(Color.GREEN);
        pannello.add(pC);
        
        finestra.add(pannello);
        
        finestra.setVisible(true);
        
        main.main(null);
        
        tavolo = main.getTavolo();
        gestione = main.getGestione();
        
        calcolaPunteggio();
    }

    static public void calcolaPunteggio()
    {
        if(gestione.getFine())
        {   
            calcolaMaggioranzaCarte();
            calcolaDenari();
            calcolaScope();
            calcolaSettebello();
            calcolaSettanta();
        
            System.out.println("Punteggio Giocatore: " + getPunteggioG());
            System.out.println("Punteggio Computer: " + getPunteggioC());
        }
    }
    static public void calcolaMaggioranzaCarte()
    {
        if(tavolo.getPreseSud().size()>tavolo.getPreseNord().size())
            punteggioGiocatore ++;
        else if (tavolo.getPreseSud().size()<tavolo.getPreseNord().size())
            punteggioComputer ++;
        else if (tavolo.getPreseSud().size()==tavolo.getPreseNord().size())
        {}
    }
    static public void calcolaDenari()
    {
        int nDenariG = 0, nDenariC = 0;
        for(int i=0;i<tavolo.getPreseSud().size();i++)
        {
            Carta carta = (Carta)tavolo.getPreseSud().get(i);
            if(carta.getDenari()==true)
                nDenariG ++;
        }
        for(int i=0;i<tavolo.getPreseNord().size();i++)
        {
            Carta carta = (Carta)tavolo.getPreseNord().get(i);
            if(carta.getDenari()==true)
                nDenariC ++;
        }
        if(nDenariG>nDenariC)
            punteggioGiocatore ++;
        else if (nDenariG<nDenariC)
            punteggioComputer ++;
        else if (nDenariG==nDenariC)
        {}
    }
    static public void calcolaScope()
    {
        int nScopeG = 0, nScopeC = 0;
        for(int i=0;i<tavolo.getPreseSud().size();i++)
        {
            Carta carta = (Carta)tavolo.getPreseSud().get(i);
            if(carta.getScopa()==true)
                nScopeG ++;
        }
        for(int i=0;i<tavolo.getPreseNord().size();i++)
        {
            Carta carta = (Carta)tavolo.getPreseNord().get(i);
            if(carta.getScopa()==true)
                nScopeC ++;
        }
        if(nScopeG>nScopeC)
            punteggioGiocatore ++;
        else if (nScopeG<nScopeC)
            punteggioComputer ++;
        else if (nScopeG==nScopeC)
        {}
    }
    static public void calcolaSettebello()
    {
        boolean settebello = false;
        for(int i=0;i<tavolo.getPreseSud().size();i++)
        {
            Carta carta = (Carta)tavolo.getPreseSud().get(i);
            if(carta.getDenari()==true && carta.getValore()==7)
                settebello = true;
        }
        if(settebello)
            punteggioGiocatore ++;
        else 
            punteggioComputer ++;
    }
    static public void calcolaSettanta()
    {
        //
    }
    static public int getPunteggioG()
    {
        return punteggioGiocatore;
    }
    static public int getPunteggioC()
    {
        return punteggioComputer;
    }*/
}