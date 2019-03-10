/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otello;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.JFrame;

/**
 *
 * @author swagmajster
 */
public class OtelloGUI extends Canvas implements MouseListener {

    public static void main(String[] args) {
        OtelloGUI canvas = new OtelloGUI();

    }

    static JLabel wpl = new JLabel();
    static JLabel bpl = new JLabel();
    static JLabel turn = new JLabel();


    private static final int BOX_SIZE = 30;

    
    public void paint(Graphics g) {

        for (int i = 0; i < Otello.BOARD_SIZE; i++) {
            for (int j = 0; j < Otello.BOARD_SIZE; j++) {
                int posX = (i * BOX_SIZE);
                int posY = (j * BOX_SIZE)+26;
                switch (Otello.board[i][j]) {
                    case 0:
                        g.setColor(Color.GREEN);
                        g.fill3DRect(posX, posY, BOX_SIZE, BOX_SIZE, true);
                        break;
                    case 1:
                        g.setColor(Color.BLACK);
                        g.fill3DRect(posX, posY, BOX_SIZE, BOX_SIZE, true);
                        break;
                    case 2:
                        g.setColor(Color.WHITE);
                        g.fill3DRect(posX, posY, BOX_SIZE, BOX_SIZE, true);
                        break;
                    case 3:
                        g.setColor(Color.RED);
                        g.fill3DRect(posX, posY, BOX_SIZE, BOX_SIZE, false);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private final Otello otello;

    public OtelloGUI() {
        
        otello = new Otello(this);
        


        wpl.setText(String.valueOf("White points: " + 2));
        wpl.setBounds(300, 10, 100, 30);

        bpl.setText(String.valueOf("Black points: " + 2));
        bpl.setBounds(400, 10, 100, 30);

        turn.setText("Blakcs turn");
        turn.setBounds(300, 50, 100, 30);
        
        initOtelloGUI();
     

    }

    private void initOtelloGUI() {
        addMouseListener(this);
        
        JFrame win = new JFrame("Otello");
        
        Container panel = win.getContentPane();
        
        panel.setPreferredSize(new Dimension(300 + 200, 300 - 70));
        
        
        
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.pack();
        
        win.add(wpl);
        win.add(bpl);
        win.add(turn);
        
 
        
        win.setResizable(false);
        win.setLayout(null);
        win.setVisible(true);
        
        

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / BOX_SIZE;
        int y = (e.getY() / BOX_SIZE)-1;
        otello.clicked(x, y);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
