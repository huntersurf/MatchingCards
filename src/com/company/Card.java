package com.company;
// Model class

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class Card extends JLabel implements MouseListener {

    Icon faceIcon;
    Icon backIcon;
    boolean faceUp = false;
    int num;
    int iconWidthHalf;
    int iconHeightHalf;
    boolean mousePressed = false;
    CardController controller;
    public static int size;

    public Card(CardController controller, Icon face, Icon back, int num) {
        super(back);
        this.faceIcon = face;
        this.num = num;
        // catch mouse clicks
        this.addMouseListener(this);
        this.iconWidthHalf = back.getIconHeight() / 2;
        this.iconHeightHalf = face.getIconHeight() / 2;
        this.controller = controller;
    }

    public int getNum() {return num;}

    private boolean overIcon(int x, int y) {
        int distX = Math.abs(x-(this.getWidth()/2));
        int distY = Math.abs(y-(this.getHeight()/2));
        // outside
        if(distX > this.iconHeightHalf || distY > this.iconWidthHalf) {
            return false;
        }
        return true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(overIcon(e.getX(),e.getY())) {
            this.turnUp();
        }
    }

    public void turnUp() {
        if(this.faceUp) return;
        // test purpose
        //this.faceUp = true;
        this.faceUp = this.controller.turnUp(this);
        if(this.faceUp) {
            size++;
            this.setIcon(this.faceIcon);
        }
    }

    public void turnDown() {
        if(!this.faceUp) return;
        String joker = "0Joker.png";
        ImageIcon back = new ImageIcon(joker);
        URL img = ClassLoader.getSystemResource(joker);

        if(img != null) {
            back = new ImageIcon(img, "Joker");
        } else {
            System.err.println("Couldn't find file");
            System.exit(0);
        }
        this.setIcon(back);
        this.faceUp = false; // card face down
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(overIcon(e.getX(), e.getY())) {
            this.mousePressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(this.mousePressed) {
            this.mousePressed = false;
            this.mouseClicked(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.mousePressed = false;
    }
}
