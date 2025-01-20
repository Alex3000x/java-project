package com.alessio.scopa;

public class Main
{/*
    static private Tavolo tavolo;
    static private Gestione gestione;
    static private Punteggio punteggio;

    static private int carte = 40;

    public static void main(String args[])
    {
        tavolo = new Tavolo();
        gestione = new Gestione(tavolo);
        tavolo.repaint();
        tavolo.validate();
        tavolo.revalidate();
        tavolo.getButtonC().setVisible(true);
        tavolo.getButtonC().setVisible(false);
        tavolo.aggiungiMazzo(tavolo.getMazzoNord(),1,3,true);
        tavolo.aggiungiMazzo(tavolo.getMazzoSud(),2,3,false);
        //tavolo.aggiungiMazzo(tavolo.getMazzo(),3,3,false);
        //tavolo.aggiungiMazzo(mazzoOvest,4,3,false);
        tavolo.aggiungiMazzo(tavolo.getMazzoCentro(),5,4,false);
        //tavolo.getPanelEst().add(tavolo.getMazzoIntero(),BorderLayout.WEST);
        tavolo.aggiungiMazzoIntero(3);

        tavolo.abilitaMazzo("mazzoSud",3);
        //tavolo.abilitaMazzo("mazzoNord",3);
        //tavolo.abilitaMazzo("mazzoEst",3);
        //tavolo.abilitaMazzo("mazzoOvest",3);
        tavolo.abilitaMazzo("mazzoCentro",4);
    }

    public static int casualeInt()
    {
        int numeroCasuale = (int)(Math.random() * 10) + 1;
        while(carte<numeroCasuale)
            numeroCasuale = (int)(Math.random() * 10) + 1;
        carte = carte - numeroCasuale;    
        return numeroCasuale;
    }

    public static boolean casualeBoolean()
    {
        int numeroCasuale = (int)(Math.random() * 2) + 1;

        if(numeroCasuale==1)
            return true;
        else
            return false;
    }

    //public static Tavolo getTavolo() {return tavolo;}

    //public static Gestione getGestione() {return gestione;}*/
}
