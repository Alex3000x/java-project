package com.alessio.scopa;

import java.util.*;
public class Combinazioni  {
    /*
    private ArrayList arrayDentro = new ArrayList();
    private ArrayList ind = new ArrayList();
    private ArrayList combinazione = new ArrayList(0);
    private int[] somma = new int[255];
    private int indice = 0, numero = 0, sottr = 0;
    private boolean trovato = false;

    public Combinazioni(ArrayList array, ArrayList prese, int sommaDesiderata)
    {
        for(int i=0;i<array.size();i++)
            arrayDentro.add(array.get(i));

        tutteLeCombinazioni(array,sommaDesiderata);

        /*for(int i=0;i<ind.size()/2;i++)
        {
        Carta c = (Carta)array.get((int)ind.get(i) - sottr);
        c.setVisible(false);
        array.remove((int)ind.get(i) - sottr);
        sottr = sottr + (combinazione.size()-1);
        }
        if(combinazione.isEmpty())
        {}
        else
        {
            combinazione.clear();
            for(int i=0;i<ind.size()/2;i++)
            {
                Carta c = (Carta)array.get((int)ind.get(i));
                combinazione.add(c);
                if(c!=null)
                    prese.add(c);
                //c.setVisible(false);
            }
            array.removeAll(combinazione);
        }
    }

    public void tutteLeCombinazioni(ArrayList a, int s)
    { 
        for(int i=0;i<arrayDentro.size();i++)
        {
            if(trovato)
            {trovato = true;}
            else
            {
                combinazione = new ArrayList();
                for(int j=0;j<i;j++)
                    combinazione.add(0);
                nCombinazioni(arrayDentro,combinazione,i,0,s);
            }
        }
    }

    public void nCombinazioni(ArrayList arrayElementi, ArrayList combinazione, int n, int posizioneIniziale, int sommaDesiderata)
    {
        if(n>0)
        {
            for(int i=posizioneIniziale;i<=arrayElementi.size()-n;i++)
            {
                if(trovato)
                {}
                else
                {
                    combinazione.set(combinazione.size()-n,arrayElementi.get(i));
                    nCombinazioni(arrayElementi,combinazione,n-1,i+1,sommaDesiderata);
                    //ind.add(numero,i);
                    //numero ++;
                    for(int j=0;j<combinazione.size();j++)
                    {
                        Carta c = (Carta)combinazione.get(j);
                        somma[indice] += c.getValore();
                    }
                    if(sommaDesiderata==somma[indice])
                    {
                        for(int q=0;q<combinazione.size();q++)
                        {
                            Carta c = (Carta)combinazione.get(q);
                            ind.add(numero,arrayElementi.indexOf(c));
                            numero ++;
                        }
                        trovato = true;
                    }
                    indice ++;
                }
                //ind.clear();
                //numero = 0;
            }
        }
    }

    public boolean isTrovato()
    {
        if(trovato)
            return true;
        else
            return false;
    }*/
}