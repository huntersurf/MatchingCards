package com.company;
// View class

import javax.imageio.ImageReader;
import javax.swing.*;
import javax.swing.plaf.PopupMenuUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;


public class MatchingGame implements ActionListener {
    private JFrame mainFrame;
    private Container mainContentPane;
    private ImageIcon[] cardIcon; //0-7 front side of card; 8 back side

    public MatchingGame(){
        // main window
        this.mainFrame = new JFrame("Matching Game");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setSize(650, 850);
        this.mainContentPane = this.mainFrame.getContentPane();
        this.mainContentPane.setLayout(new BoxLayout(this.mainContentPane, BoxLayout.PAGE_AXIS));
        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        this.mainFrame.setJMenuBar(menuBar);
        // Game Menu
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        // Create generic submenu
        newMenuItem("New Game", gameMenu, this);
        newMenuItem("Exit", gameMenu, this);
        // About Menu
        JMenu aboutMenu = new JMenu("About");
        menuBar.add(aboutMenu);
        newMenuItem("Help", aboutMenu, this);
        newMenuItem("etc...", aboutMenu, this);
        // Load the Cards
        this.cardIcon = loadCardIcons();

    }

    private ImageIcon[] loadCardIcons() {
        ImageIcon[] icon = new ImageIcon[8];
        for(int i = 0; i < 8; i++) {
            String str = i + "Clubs.png";
            icon[i] = new ImageIcon(str);
            URL img = ClassLoader.getSystemResource(str);

            if(img != null) {
                icon[i] = new ImageIcon(img, "i + Clubs");
            } else {
                System.err.println("Couldn't find file");
                System.exit(0);
            }
        }
        return icon;
    }

    public JPanel makeCards() throws IOException {
        JPanel panel = new JPanel(new GridLayout(4,4));
        // all cards have same back side
        String joker = "0Joker.png";
        ImageIcon back = new ImageIcon(joker);
        URL img = ClassLoader.getSystemResource(joker);

        CardController controller = new CardController();



        if(img != null) {
            back = new ImageIcon(img, "Joker");
        } else {
            System.err.println("Couldn't find file");
            System.exit(0);
        }

        int[] cardsToAdd = new int[16]; // 4x4 grid = 16 cards
        for(int i = 0; i < 8; i++) {
            cardsToAdd[2*i] = i;
            cardsToAdd[2*i+1] = i;

        }
        // Randomize the card array
        randomizeCardArray(cardsToAdd);
        // Make card object
        for(int i = 0; i <cardsToAdd.length; i++) {
            int num = cardsToAdd[i];
            Card newCard = new Card(controller, this.cardIcon[num], back, num);
            panel.add(newCard);
        }
        return panel;
    }

    private void randomizeCardArray(int[] T) {
        Random randomizer = new Random();
        for(int i = 0; i < T.length; i++) {
            int d = randomizer.nextInt(T.length);
            // swap
            int s=T[d];
            T[d] = T[i];
            T[i] = s;
        }
        // Randomizer is working
        // so we need a controller that control the card
        // behaviors and also the game
    }

    private void newMenuItem(String str, JMenu menu, ActionListener listener) {
        JMenuItem newItem = new JMenuItem(str);
        newItem.setActionCommand(str);
        newItem.addActionListener(listener);
        menu.add(newItem);
    }

    public void newGame() {
        this.mainContentPane.removeAll();
        // Make a new card set visible
        try {
            this.mainContentPane.add(makeCards());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // show main in window
        this.mainFrame.setVisible(true);
    }

    public void gameWin() {
        CardController controller = new CardController();
        JPopupMenu popup = new JPopupMenu();
        popup.setSize(300,300);
        popup.setEnabled(true);
        popup.setLabel("You completed the matching game in " + controller.getNumTries() + "moves!");
        JButton buttonOne = new JButton("New Game");
        JButton buttonTwo = new JButton("Exit");
        popup.add(buttonOne);
        popup.add(buttonTwo);
        if(controller.getPairs() >4) {
            popup.show(mainFrame, mainFrame.getX(), mainFrame.getY());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("New Game")) newGame();
        if(e.getActionCommand().equals("Exit")) System.exit(0);
        CardController controller = new CardController();
        if(controller.getPairs() > 2) {
            gameWin();
        }
    }
}
