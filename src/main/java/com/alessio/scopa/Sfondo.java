package com.alessio.scopa;

import javax.swing.JPanel;
import java.awt.image.*;
import java.awt.*;
public class Sfondo extends JPanel
{
    private Image img;
    private int x;
    private int y;

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        setOpaque(false);
        g.drawImage(img, 0, 0,1220,675, this);
    }

    public Sfondo(Image i, int x1, int y1)
    {
        img = i;
        x = x1;
        y = y1;
    }
}