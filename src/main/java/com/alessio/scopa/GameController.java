package com.alessio.scopa;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.event.InputEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
public class GameController extends JFrame implements ActionListener
{/*
    private ImageIcon exit1 = new ImageIcon("grafica/exit.png");
    private ImageIcon delete1 = new ImageIcon("grafica/delete.png");
    private ImageIcon move1 = new ImageIcon("grafica/move.png");

    private Image verde = Toolkit.getDefaultToolkit().createImage("grafica/green.jpg");

    private Sfondo green = new Sfondo(verde,200,849);

    private JButton exit = new JButton(exit1);
    private JButton delete = new JButton(delete1);
    private JButton move = new JButton(move1);

    private Object precedente;

    private Tavolo tavolo;

    private int[] numeriCentro = {0};
    private int nMazzoIntero = 0; 
    
    private boolean continua = false, fine = false;

    public Gestione(Tavolo t)
    {
        tavolo = t;

        setLayout(null);
        setSize(200,714);
        setLocation(1221,75);
        setResizable(false);
        //setFont(font1);

        green.setSize(184,810);  
        green.setLocation(0,0);  
        add(green);

        exit.setSize(142,50);
        exit.setLocation(0,10);
        exit.setOpaque(false);
        exit.setContentAreaFilled(false);
        exit.setBorderPainted(false);
        exit.setFocusable(false);
        exit.addActionListener(this);
        green.add(exit);

        delete.setSize(142,70);
        delete.setLocation(0,670);
        delete.setOpaque(false);
        delete.setContentAreaFilled(false);
        delete.setBorderPainted(false);
        delete.setFocusable(false);
        delete.addActionListener(this);
        green.add(delete);

        move.setSize(142,70);
        move.setLocation(0,735);
        move.setOpaque(false);
        move.setContentAreaFilled(false);
        move.setBorderPainted(false);
        move.setFocusable(false);
        move.addActionListener(this);
        green.add(move);

        tavolo.getButtonN().setSize(1275,155);
        tavolo.getButtonN().setLocation(0,0);
        tavolo.getButtonN().setOpaque(false);
        tavolo.getButtonN().setContentAreaFilled(false);
        tavolo.getButtonN().setFocusable(false);
        tavolo.getButtonN().addActionListener(this);
        tavolo.getButtonN().setVisible(false);
        tavolo.getSfondo().add(tavolo.getButtonN());

        tavolo.getButtonS().setSize(1275,155);
        tavolo.getButtonS().setLocation(0,655);
        tavolo.getButtonS().setOpaque(false);
        tavolo.getButtonS().setContentAreaFilled(false);
        tavolo.getButtonS().setFocusable(false);
        tavolo.getButtonS().addActionListener(this);
        tavolo.getButtonS().setVisible(false);
        tavolo.getSfondo().add(tavolo.getButtonS());

        tavolo.getButtonO().setSize(297,496);
        tavolo.getButtonO().setLocation(0,157);
        tavolo.getButtonO().setOpaque(false);
        tavolo.getButtonO().setContentAreaFilled(false);
        tavolo.getButtonO().setFocusable(false);
        tavolo.getButtonO().addActionListener(this);
        tavolo.getButtonO().setVisible(false);
        tavolo.getSfondo().add(tavolo.getButtonO());

        tavolo.getButtonE().setSize(305,496);
        tavolo.getButtonE().setLocation(1143,157);
        tavolo.getButtonE().setOpaque(false);
        tavolo.getButtonE().setContentAreaFilled(false);
        tavolo.getButtonE().setFocusable(false);
        tavolo.getButtonE().addActionListener(this);
        tavolo.getButtonE().setVisible(false);
        tavolo.getSfondo().add(tavolo.getButtonE());

        tavolo.getButtonC().setSize(842,496);
        tavolo.getButtonC().setLocation(299,157);
        tavolo.getButtonC().setOpaque(false);
        tavolo.getButtonC().setContentAreaFilled(false);
        tavolo.getButtonC().setFocusable(false);
        tavolo.getButtonC().addActionListener(this);
        tavolo.getButtonC().setVisible(false);
        tavolo.getSfondo().add(tavolo.getButtonC());

        nMazzoIntero = 30;

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        tavolo.abilitaMazzo("mazzoCentro",tavolo.getMazzoCentro().getMazzo().size());
        if(e.getSource()==exit)
            System.exit(-1);
        if(e.getSource()==delete)
        {
            outer:
            if(tavolo.getNSelezionate()==0 && tavolo.getNDaPrendere()==0)
            {}
            else if(tavolo.getNDaPrendere()==0)
            {
                Carta carta = (Carta)tavolo.getSelezionate().get(0);
                carta.setBorder(null);
                tavolo.setNSelezionate(0);
            }
            else
            {
                int somma = 0;
                Carta carta = (Carta)tavolo.getSelezionate().get(0);
                for(int i=0;i<tavolo.getDaPrendere().size();i++)
                {
                    Carta c = (Carta)tavolo.getDaPrendere().get(i);
                    somma = somma + c.getValore();
                }
                if(somma==carta.getValore())
                {
                    for(int i=0;i<tavolo.getDaPrendere().size();i++)
                    {
                        Carta c = (Carta)tavolo.getDaPrendere().get(i);
                        for(int j=0;j<tavolo.getMazzoCentro().getMazzo().size();j++)
                        {
                            if((Carta)tavolo.getDaPrendere().get(i)==(tavolo.getMazzoCentro().getCarta(j)))
                            {
                                tavolo.getMazzoCentro().getCarta(j).setVisible(false);
                                tavolo.getMazzoCentro().getMazzo().remove(j);
                            }
                        }
                    }

                    for(int i=0;i<tavolo.getMazzoSud().getMazzo().size();i++)
                    {
                        if(tavolo.getMazzoSud().getCarta(i).equals(carta))
                        {
                            tavolo.getMazzoSud().getCarta(i).setVisible(false);
                            tavolo.getMazzoSud().getMazzo().remove(i);
                        }
                    }
                    tavolo.getSelezionate().clear();
                    tavolo.setNSelezionate(0);
                    tavolo.setNDaPrendere(0);
                }
                else
                {
                    for(int i=0;i<tavolo.getDaPrendere().size();i++)
                    {
                        Carta c = (Carta)tavolo.getDaPrendere().get(i);
                        c.setBorder(null);
                    }
                    carta.setBorder(null);
                }

                Carta casuale = (Carta)tavolo.getMazzoNord().getCarta(numeroCasuale(0,tavolo.getMazzoNord().getMazzo().size()-1));
                Combinazioni combinazioniN = new Combinazioni(tavolo.getMazzoCentro().getMazzo(),null,casuale.getValore());
                if(combinazioniN.isTrovato())
                {
                    for(int i=0;i<tavolo.getMazzoNord().getMazzo().size();i++)
                    {
                        if(tavolo.getMazzoNord().getCarta(i).equals(casuale))
                        {
                            tavolo.getMazzoNord().getCarta(i).setVisible(false);
                            tavolo.getMazzoNord().getMazzo().remove(i);
                        }
                    }
                    for(int j=0;j<tavolo.getMazzoCentro().getMazzo().size();j++)
                        tavolo.getMazzoCentro().getCarta(j).setBorder(null);
                    tavolo.getSelezionate().clear();
                    tavolo.setNSelezionate(0);
                    tavolo.setNDaPrendere(0);
                    tavolo.getButtonC().setVisible(false);
                    break outer;
                }

                for(int i=0;i<tavolo.getSelezionate().size();i++)
                {
                    for(int j=0;j<tavolo.getMazzoNord().getMazzo().size();j++)
                    {
                        if(tavolo.getMazzoNord().getCarta(j).equals(tavolo.getSelezionate().get(i)))
                        {
                            tavolo.getMazzoNord().getCarta(j).setBorder(null);
                            tavolo.getPanelCentro().add((Carta)tavolo.getMazzoNord().getCarta(j));
                            tavolo.getMazzoCentro().aggiungiCarta((Carta)tavolo.getMazzoNord().getMazzo().remove(j));
                        }
                    }  
                }

                for(int j=0;j<tavolo.getMazzoCentro().getMazzo().size();j++)
                    tavolo.getMazzoCentro().getCarta(j).setBorder(null);

                tavolo.getButtonC().setVisible(false);
                tavolo.getSelezionate().clear();

                repaint();
                revalidate();
                repaint();
                revalidate(); 
                
                continua = true;
            }

            //tavolo.getDaPrendere().clear();
            //tavolo.getSelezionate().clear();
            //tavolo.setNSelezionate(0);
            //tavolo.setNDaPrendere(0);
        }
        //----------------------------------------------------------------------------------------------------------------------
        outer:
        if(e.getSource()==delete)
        {
            if(continua)
            {
                continua = false;
                Carta casuale = (Carta)tavolo.getMazzoNord().getCarta(numeroCasuale(0,tavolo.getMazzoNord().getMazzo().size()-1));
                int somma = 0;
                Combinazioni combinazioniN = new Combinazioni(tavolo.getMazzoCentro().getMazzo(),null,casuale.getValore());
                if(combinazioniN.isTrovato())
                {
                    for(int i=0;i<tavolo.getMazzoNord().getMazzo().size();i++)
                    {
                        if(tavolo.getMazzoNord().getCarta(i).equals(casuale))
                        {
                            tavolo.getMazzoNord().getCarta(i).setVisible(false);
                            tavolo.getMazzoNord().getMazzo().remove(i);
                        }
                    }
                    for(int k=0;k<tavolo.getMazzoCentro().getMazzo().size();k++)
                    {
                        Carta c = (Carta)tavolo.getMazzoCentro().getCarta(k);
                        if(c.getValore()==casuale.getValore())
                        {
                            tavolo.getMazzoCentro().getCarta(k).setVisible(false);
                            tavolo.getMazzoCentro().getMazzo().remove(k);
                            k = 1000;
                        }
                    }
                    for(int j=0;j<tavolo.getMazzoCentro().getMazzo().size();j++)
                        tavolo.getMazzoCentro().getCarta(j).setBorder(null);
                    tavolo.getSelezionate().clear();
                    tavolo.setNSelezionate(0);
                    tavolo.setNDaPrendere(0);
                    tavolo.getButtonC().setVisible(false);
    
                    break outer;
                }
    
                for(int j=0;j<tavolo.getMazzoNord().getMazzo().size();j++)
                {
                    if(tavolo.getMazzoNord().getCarta(j).equals(casuale))
                    {
                        tavolo.getPanelCentro().add((Carta)tavolo.getMazzoNord().getCarta(j));
                        tavolo.getMazzoCentro().aggiungiCarta((Carta)tavolo.getMazzoNord().getMazzo().remove(j));
                    }
                }  
    
                for(int j=0;j<tavolo.getMazzoCentro().getMazzo().size();j++)
                    tavolo.getMazzoCentro().getCarta(j).setBorder(null);
    
                String s = "" + tavolo.getMazzoNord().getMazzo().size();
                tavolo.getHide().setText(s);
                tavolo.getButtonC().setVisible(false);
                tavolo.getSelezionate().clear();    
                    
                tavolo.getButtonC().setVisible(false);
                tavolo.getSelezionate().clear();
    
                repaint();
                revalidate();
                repaint();
                revalidate();
            }
            
            tavolo.getDaPrendere().clear();
            tavolo.getSelezionate().clear();
            tavolo.setNSelezionate(0);
            tavolo.setNDaPrendere(0);
        }
        if(e.getSource()==move)
        {
            tavolo.getButtonC().setVisible(true);
        }
        outer:
        if(e.getSource()==tavolo.getButtonC() && precedente.equals(move) && tavolo.getNSelezionate()==1)
        {
            Carta carta = (Carta)tavolo.getSelezionate().get(0);
            for(int k=0;k<tavolo.getMazzoCentro().getMazzo().size();k++)
            {
                Carta c = (Carta)tavolo.getMazzoCentro().getCarta(k);
                if(c.getValore()==carta.getValore())
                {
                    tavolo.getMazzoCentro().getCarta(k).setVisible(false);
                    tavolo.getPreseSud().add(tavolo.getMazzoCentro().getCarta(k));
                    tavolo.getMazzoCentro().getMazzo().remove(k);
                    k = 1000;
                    for(int i=0;i<tavolo.getMazzoSud().getMazzo().size();i++)
                    {
                        if(tavolo.getMazzoSud().getCarta(i).equals(carta))
                        {
                            tavolo.getMazzoSud().getCarta(i).setVisible(false);
                            tavolo.getPreseSud().add(tavolo.getMazzoSud().getCarta(i));
                            if(tavolo.getMazzoCentro().getMazzo().isEmpty())
                                tavolo.getMazzoSud().getCarta(i).setScopa(true);
                            tavolo.getMazzoSud().getMazzo().remove(i);
                        }
                    }
                    for(int j=0;j<tavolo.getMazzoCentro().getMazzo().size();j++)
                        tavolo.getMazzoCentro().getCarta(j).setBorder(null);
                    tavolo.getSelezionate().clear();
                    tavolo.setNSelezionate(0);
                    tavolo.setNDaPrendere(0);
                    tavolo.getButtonC().setVisible(false);
                    break outer;
                }
            }

            Combinazioni combinazioni = new Combinazioni(tavolo.getMazzoCentro().getMazzo(),tavolo.getPreseSud(),carta.getValore());
            if(combinazioni.isTrovato())
            {
                for(int i=0;i<tavolo.getMazzoSud().getMazzo().size();i++)
                {
                    if(tavolo.getMazzoSud().getCarta(i).equals(carta))
                    {
                        tavolo.getMazzoSud().getCarta(i).setVisible(false);
                        tavolo.getPreseSud().add(tavolo.getMazzoSud().getCarta(i));
                        if(tavolo.getMazzoCentro().getMazzo().isEmpty())
                                tavolo.getMazzoSud().getCarta(i).setScopa(true);
                        tavolo.getMazzoSud().getMazzo().remove(i);
                    }
                }
                for(int j=0;j<tavolo.getMazzoCentro().getMazzo().size();j++)
                    tavolo.getMazzoCentro().getCarta(j).setBorder(null);
                tavolo.getSelezionate().clear();
                tavolo.setNSelezionate(0);
                tavolo.setNDaPrendere(0);
                tavolo.getButtonC().setVisible(false);
                break outer;
            }

            for(int i=0;i<tavolo.getSelezionate().size();i++)
            {
                for(int j=0;j<tavolo.getMazzoSud().getMazzo().size();j++)
                {
                    if(tavolo.getMazzoSud().getCarta(j).equals(tavolo.getSelezionate().get(i)))
                    {
                        tavolo.getMazzoSud().getCarta(j).setBorder(null);
                        tavolo.getPanelCentro().add((Carta)tavolo.getMazzoSud().getCarta(j));
                        tavolo.getMazzoCentro().aggiungiCarta((Carta)tavolo.getMazzoSud().getMazzo().remove(j));
                    }
                }  
            }

            for(int j=0;j<tavolo.getMazzoCentro().getMazzo().size();j++)
                tavolo.getMazzoCentro().getCarta(j).setBorder(null);

            tavolo.getButtonC().setVisible(false);
            tavolo.getSelezionate().clear();

            repaint();
            revalidate();
            repaint();
            revalidate();

            tavolo.setNSelezionate(0);
        }
        //----------------------------------------------------------------------------------------------------------------------
        outer:
        if(e.getSource()==tavolo.getButtonC() && precedente.equals(move))
        {
            Carta casuale = (Carta)tavolo.getMazzoNord().getCarta(numeroCasuale(0,tavolo.getMazzoNord().getMazzo().size()-1));
            int somma = 0;
            Combinazioni combinazioniN = new Combinazioni(tavolo.getMazzoCentro().getMazzo(),tavolo.getPreseNord(),casuale.getValore());
            if(combinazioniN.isTrovato())
            {
                for(int i=0;i<tavolo.getMazzoNord().getMazzo().size();i++)
                {
                    if(tavolo.getMazzoNord().getCarta(i).equals(casuale))
                    {
                        tavolo.getMazzoNord().getCarta(i).setVisible(false);
                        tavolo.getPreseNord().add(tavolo.getMazzoNord().getCarta(i));
                        if(tavolo.getMazzoCentro().getMazzo().isEmpty())
                                tavolo.getMazzoSud().getCarta(i).setScopa(true);
                        tavolo.getMazzoNord().getMazzo().remove(i);
                    }
                }
                for(int k=0;k<tavolo.getMazzoCentro().getMazzo().size();k++)
                {
                    Carta c = (Carta)tavolo.getMazzoCentro().getCarta(k);
                    if(c.getValore()==casuale.getValore())
                    {
                        tavolo.getMazzoCentro().getCarta(k).setVisible(false);
                        tavolo.getPreseNord().add(tavolo.getMazzoCentro().getCarta(k));
                        if(tavolo.getMazzoCentro().getMazzo().isEmpty())
                                tavolo.getMazzoSud().getCarta(k).setScopa(true);
                        tavolo.getMazzoCentro().getMazzo().remove(k);
                        k = 1000;
                    }
                }
                
                for(int j=0;j<tavolo.getMazzoCentro().getMazzo().size();j++)
                    tavolo.getMazzoCentro().getCarta(j).setBorder(null);
                tavolo.getSelezionate().clear();
                tavolo.setNSelezionate(0);
                tavolo.setNDaPrendere(0);
                tavolo.getButtonC().setVisible(false);

                break outer;
            }

            for(int j=0;j<tavolo.getMazzoNord().getMazzo().size();j++)
            {
                if(tavolo.getMazzoNord().getCarta(j).equals(casuale))
                {
                    tavolo.getPanelCentro().add((Carta)tavolo.getMazzoNord().getCarta(j));
                    tavolo.getMazzoCentro().aggiungiCarta((Carta)tavolo.getMazzoNord().getMazzo().remove(j));
                }
            }  

            for(int j=0;j<tavolo.getMazzoCentro().getMazzo().size();j++)
                tavolo.getMazzoCentro().getCarta(j).setBorder(null);

            String s = "" + tavolo.getMazzoNord().getMazzo().size();
            tavolo.getHide().setText(s);
            tavolo.getButtonC().setVisible(false);
            tavolo.getSelezionate().clear();

            repaint();
            revalidate();
            repaint();
            revalidate();

            tavolo.setNSelezionate(0);
        }
        if(tavolo.getMazzoSud().getMazzo().isEmpty() && tavolo.getMazzoNord().getMazzo().isEmpty())
        {
            nMazzoIntero = nMazzoIntero - 6;
            tavolo.getHide().setVisible(false);
            tavolo.remove(tavolo.getHide());
        }
        if(nMazzoIntero==0)
        {
            String s = "" + nMazzoIntero;
            tavolo.getMazzoHide().setText(s);
        }
        if(tavolo.getMazzoSud().getMazzo().isEmpty() && tavolo.getMazzoNord().getMazzo().isEmpty() && nMazzoIntero!=0)
        {
            tavolo.aggiungiMazzo(tavolo.getMazzoNord(),1,3,true);
            tavolo.aggiungiMazzo(tavolo.getMazzoSud(),2,3,false);
            tavolo.abilitaMazzo("mazzoSud",3);
            String s = "" + tavolo.getMazzoIntero().getMazzo().size();
            tavolo.getMazzoHide().setText(s);
        }
        else if(nMazzoIntero==0)
        {
            fine = true;
            //Main main;
            //Main.main(null);
            //tavolo.setVisible(false);
            //tavolo.dispose();
            //setVisible(false);
            //dispose();
        }

        repaint();
        revalidate();
        repaint();
        revalidate();

        precedente = e.getSource();
    }

    private int numeroCasuale(int min, int max)
    {
        return (int)(Math.random()*((max-min)+1))+min;
    }
    
    public boolean getFine()
    {
        return fine;
    }*/
    public void actionPerformed(ActionEvent e)
    {}
}