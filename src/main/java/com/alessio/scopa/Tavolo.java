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
public class Tavolo extends JFrame
{/*
    static final private Font font1 = new Font("Comic Sans", Font.BOLD, 32);

    private Color bianco = new Color(255,255,255);

    private Border bordoCiano = new LineBorder(Color.cyan, 4, true);
    private Border bordoRosso = new LineBorder(Color.red, 4, true);

    private Image background = Toolkit.getDefaultToolkit().createImage("grafica/sfondo.jpg");

    private ImageIcon dorso = new ImageIcon("carte/vuota.png");

    private JLabel hide;
    private JLabel mazzoHide;

    private Sfondo sfondo = new Sfondo(background,1456,849);

    private JPanel panelNord = new JPanel();
    private JPanel panelSud = new JPanel();
    private JPanel panelEst = new JPanel();
    private JPanel panelOvest = new JPanel();
    private JPanel panelCentro = new JPanel();

    private Mazzo mazzoIntero = new Mazzo();

    private ArrayList selezionate = new ArrayList(1);
    private ArrayList daPrendere = new ArrayList();
    private ArrayList preseNord = new ArrayList();
    private ArrayList preseSud = new ArrayList();
    private ArrayList preseEst = new ArrayList();
    private ArrayList preseOvest = new ArrayList();

    private Mazzo mazzoNord;
    private Mazzo mazzoSud;
    private Mazzo mazzoEst;
    private Mazzo mazzoOvest;
    private Mazzo mazzoCentro;

    private JButton buttonN = new JButton();
    private JButton buttonS = new JButton();
    private JButton buttonE = new JButton();
    private JButton buttonO = new JButton();
    private JButton buttonC = new JButton();

    static private int CENTER;
    private int nSelezionate = 0;
    private int nDaPrendere = 0;

    public Tavolo()
    {
        setLayout(null);
        setSize(1216,714);
        setLocation(20,75);
        setResizable(false);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setUndecorated(true);
        setFont(font1);

        sfondo.setSize(1200,675);             
        sfondo.setLayout(new BorderLayout());
        add(sfondo);

        modPanel(panelNord);
        sfondo.add(panelNord,BorderLayout.NORTH);

        modPanel(panelSud);
        sfondo.add(panelSud,BorderLayout.SOUTH);

        modPanel(panelEst);
        panelEst.setLayout(new FlowLayout(FlowLayout.CENTER,5,113));
        sfondo.add(panelEst,BorderLayout.EAST);

        modPanel(panelOvest);
        panelOvest.setLayout(new FlowLayout(FlowLayout.CENTER,5,113));
        sfondo.add(panelOvest,BorderLayout.WEST);

        modPanel(panelCentro);
        panelCentro.setLayout(new FlowLayout(FlowLayout.CENTER,5,255));
        sfondo.add(panelCentro,BorderLayout.CENTER);

        getButtonC().setOpaque(true);
        getButtonC().setOpaque(false);

        setVisible(true);
    }

    public void aggiungiMazzoIntero(int posizione)
    {
        String s = "" + mazzoIntero.getMazzo().size();
        mazzoHide = new JLabel(dorso);
        mazzoHide.setText(s);
        mazzoHide.setFont(font1);
        mazzoHide.setForeground(Color.white);
        mazzoHide.setOpaque(false);
        mazzoHide.setVisible(true);
        switch (posizione)
        {
            case 1: panelNord.add(mazzoHide);
            break;

            case 2: panelSud.add(mazzoHide);
            break;

            case 3: panelOvest.add(mazzoHide);
            break;

            case 4: panelEst.add(mazzoHide);
            break;

            case 5: panelCentro.add(mazzoHide);
            break;
        }
    }

    public void aggiungiMazzo(Mazzo m, int posizione, int nCarte, boolean coperto)
    {
        if(coperto)
        {
            String s = "" + nCarte;    
            hide = new JLabel(dorso);
            hide.setText(s);
            hide.setFont(font1);
            hide.setForeground(Color.white);
            hide.setOpaque(false);
            hide.setVisible(true);
            switch (posizione)
            {
                case 1: m = new Mazzo(mazzoIntero.getMazzo(),nCarte);
                mazzoNord = m;
                panelNord.add(hide);
                break;

                case 2: m = new Mazzo(mazzoIntero.getMazzo(),nCarte);
                mazzoSud = m;
                panelSud.add(hide);
                break;

                case 3: m = new Mazzo(mazzoIntero.getMazzo(),nCarte);
                mazzoEst = m;
                panelEst.add(hide);
                break;

                case 4: m = new Mazzo(mazzoIntero.getMazzo(),nCarte);
                mazzoOvest = m;
                panelOvest.add(hide);
                break;

                case 5: m = new Mazzo(mazzoIntero.getMazzo(),nCarte);
                mazzoCentro = m;
                panelCentro.add(hide);
                break;
            }
        }
        else
        {
            switch (posizione)
            {
                case 1: m = new Mazzo(mazzoIntero.getMazzo(),nCarte);
                mazzoNord = m;
                for(int i=0;i<nCarte;i++)
                {
                    m.getCarta(i).setOpaque(false);
                    m.getCarta(i).setVisible(true);
                    panelNord.add(m.getCarta(i));
                }
                break;

                case 2: m = new Mazzo(mazzoIntero.getMazzo(),nCarte);
                mazzoSud = m;
                for(int i=0;i<nCarte;i++)
                {
                    m.getCarta(i).setOpaque(false);
                    m.getCarta(i).setVisible(true);
                    panelSud.add(m.getCarta(i));
                }
                break;

                case 3: m = new Mazzo(mazzoIntero.getMazzo(),nCarte);
                mazzoEst = m;
                for(int i=0;i<nCarte;i++)
                {
                    m.getCarta(i).setOpaque(false);
                    m.getCarta(i).setVisible(true);
                    panelEst.add(m.getCarta(i));
                }
                break;

                case 4: m = new Mazzo(mazzoIntero.getMazzo(),nCarte);
                mazzoOvest = m;
                for(int i=0;i<nCarte;i++)
                {
                    m.getCarta(i).setOpaque(false);
                    m.getCarta(i).setVisible(true);
                    panelOvest.add(m.getCarta(i));
                }
                break;

                case 5: m = new Mazzo(mazzoIntero.getMazzo(),nCarte);
                mazzoCentro = m;
                for(int i=0;i<nCarte;i++)
                {
                    m.getCarta(i).setOpaque(false);
                    m.getCarta(i).setVisible(true);
                    panelCentro.add(m.getCarta(i));
                }
                break;
            }
        }
    }

    public void abilitaMazzo(String m, int nCarte)
    {
        if(m.equals("mazzoNord"))
        {
            for(int i=0;i<nCarte;i++)
            {
                mazzoNord.getCarta(i).addMouseListener(this);
            }
        }
        if(m.equals("mazzoSud"))
        {
            for(int i=0;i<nCarte;i++)
            {
                mazzoSud.getCarta(i).addMouseListener(this);
            }
        }
        if(m.equals("mazzoEst"))
        {
            for(int i=0;i<nCarte;i++)
            {
                mazzoEst.getCarta(i).addMouseListener(this);
            }
        }
        if(m.equals("mazzoOvest"))
        {
            for(int i=0;i<nCarte;i++)
            {
                mazzoOvest.getCarta(i).addMouseListener(this);
            }
        }
        if(m.equals("mazzoCentro"))
        {
            for(int i=0;i<nCarte;i++)
            {
                mazzoCentro.getCarta(i).addMouseListener(this);
            }
        }
    }

    public void disabilitaMazzo(String m, int nCarte)
    {
        if(m.equals("mazzoNord"))
        {
            for(int i=0;i<nCarte;i++)
            {
                mazzoNord.getCarta(i).removeMouseListener(this);
            }
        }
        if(m.equals("mazzoSud"))
        {
            for(int i=0;i<nCarte;i++)
            {
                mazzoSud.getCarta(i).removeMouseListener(this);
            }
        }
        if(m.equals("mazzoEst"))
        {
            for(int i=0;i<nCarte;i++)
            {
                mazzoEst.getCarta(i).removeMouseListener(this);
            }
        }
        if(m.equals("mazzoOvest"))
        {
            for(int i=0;i<nCarte;i++)
            {
                mazzoOvest.getCarta(i).removeMouseListener(this);
            }
        }
        if(m.equals("mazzoCentro"))
        {
            for(int i=0;i<nCarte;i++)
            {
                mazzoCentro.getCarta(i).removeMouseListener(this);
            }
        }
    }

    public void mouseClicked (MouseEvent e)
    {
        int indice = -1;
        Carta centro = (Carta)e.getSource();
        for(int i=0;i<mazzoCentro.getMazzo().size();i++)
        {
            if(mazzoCentro.getCarta(i).equals(centro))
                indice = i;
        }
        Carta oggetto = (Carta)e.getSource(); 
        if(indice>=0)
        {
            for(int i=0;i<mazzoCentro.getMazzo().size();i++)
            {
                if(mazzoCentro.getCarta(i).equals(centro))
                    if((centro.getBorder()==null) && nSelezionate==1)
                    {
                        centro.setBorder(bordoRosso);
                        daPrendere.add(centro);
                        nDaPrendere ++;
                    }
                    else if(nSelezionate==1)
                    {
                        centro.setBorder(null);
                        daPrendere.remove(centro);
                        nDaPrendere --;
                    }
                    else
                    {}
            }
        }
        else
        {
            if(e.getSource()==oggetto)
            {
                if((oggetto.getBorder()==null))
                {
                    if(nSelezionate==0 )
                    {
                        oggetto.setBorder(bordoCiano);
                        selezionate.add(oggetto);
                        nSelezionate ++;
                    }
                }
                else
                {
                    if(nDaPrendere==0)
                    {
                        oggetto.setBorder(null);
                        selezionate.remove(oggetto);
                        nSelezionate --;
                    }
                }
            }
        }

        sfondo.repaint();
        sfondo.revalidate();
        repaint();
        revalidate();
    }

    private void modPanel(JPanel panel)             
    {
        panel.setVisible(true);                     // Il seguente metodo serve soltanto ad accorciare le righe di codice,
        panel.setOpaque(false);                     // infatti esso è racchiude alcune istruzioni che vanno ad apportare modifiche
        panel.setLayout(new FlowLayout());          // ad un pannello qualsiasi ed è per questo che appunto è stato posto come
        // metodo privato, così non essere visto e utilizzato dall'utente

        //panel.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Sfondo getSfondo()
    {
        return sfondo;
    }

    public JPanel getPanelNord()
    {
        return panelNord;
    }

    public JPanel getPanelSud()
    {
        return panelSud;
    }

    public JPanel getPanelEst()
    {
        return panelEst;
    }

    public JPanel getPanelOvest()
    {
        return panelOvest;
    }

    public JPanel getPanelCentro()
    {
        return panelCentro;
    }

    public ArrayList getSelezionate()
    {
        return selezionate;
    }

    public void setNSelezionate(int n)
    {
        nSelezionate = n;
    }

    public int getNSelezionate()
    {
        return nSelezionate;
    }

    public ArrayList getDaPrendere()
    {
        return daPrendere;
    }

    public void setNDaPrendere(int n)
    {
        nDaPrendere = n;
    }

    public int getNDaPrendere()
    {
        return nDaPrendere;
    }
    
    public ArrayList getPreseNord()
    {
        return preseNord;
    }
    
    public ArrayList getPreseSud()
    {
        return preseSud;
    }
    
    public ArrayList getPreseEst()
    {
        return preseEst;
    }
    
    public ArrayList getPreseOvest()
    {
        return preseOvest;
    }

    public Mazzo getMazzoIntero()
    {
        return mazzoIntero;
    }

    public int getNMazzoIntero()
    {
        return mazzoIntero.getMazzo().size();
    }

    public Mazzo getMazzoNord()
    {
        return mazzoNord;
    }

    public Mazzo getMazzoSud()
    {
        return mazzoSud;
    }

    public Mazzo getMazzoEst()
    {
        return mazzoEst;
    }

    public Mazzo getMazzoOvest()
    {
        return mazzoOvest;
    }

    public Mazzo getMazzoCentro()
    {
        return mazzoCentro;
    }

    public JButton getButtonN()
    {
        return buttonN;
    }

    public JButton getButtonS()
    {
        return buttonS;
    }

    public JButton getButtonE()
    {
        return buttonE;
    }

    public JButton getButtonO()
    {
        return buttonO;
    }

    public JButton getButtonC()
    {
        return buttonC;
    }
    
    public JLabel getHide()
    {
        return hide;
    }
    
    public JLabel getMazzoHide()
    {
        return mazzoHide;
    }

    public void mousePressed (MouseEvent e){}

    public void mouseReleased (MouseEvent e){}

    public void mouseEntered (MouseEvent e){}

    public void mouseExited (MouseEvent e){}
    */
}