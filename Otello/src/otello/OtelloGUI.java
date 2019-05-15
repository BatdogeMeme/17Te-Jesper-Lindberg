/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otello;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
//import static otello.Otello.board;

/**
 *
 * @author jesper.lindberg3
 */
public class OtelloGUI extends JPanel implements MouseListener {

    public static void main(String[] args) {
        OtelloGUI gui = new OtelloGUI();
    }

    private static final int boardSize = 8;
    private static final int width = 300;
    private static final int height = 300;
    private static final int boxSize = 30;
    
    Otello otello;
    
    /*
    *   Paint method takes care of all paiting
    */
    @Override
    public void paint(Graphics g) {

        g.clearRect(0, 0, width + 300, height - 31);
        System.out.print(otello.gg);
        if (!otello.gg) {
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    int posX = i * 30;
                    int posY = j * 30;
                    switch (otello.board[i][j]) {
                        case 0:
                            g.setColor(Color.GREEN);
                            g.fill3DRect(posX, posY, boxSize, boxSize, true);
                            break;
                        case 1:
                            g.setColor(Color.BLACK);
                            g.fill3DRect(posX, posY, boxSize, boxSize, true);
                            break;
                        case 2:
                            g.setColor(Color.WHITE);
                            g.fill3DRect(posX, posY, boxSize, boxSize, true);
                            break;
                        case 3:
                            g.setColor(Color.GREEN);
                            g.fill3DRect(posX, posY, boxSize, boxSize, true);
                            g.setColor(Color.RED);
                            g.fill3DRect(posX + 6, posY + 6, boxSize - 12, boxSize - 12, false);
                            break;
                        default:
                            break;
                    }
                }
            }

            g.setColor(Color.BLACK);
            g.drawString("White's point's: " + otello.wp, 250, 20);
            g.drawString("Black's point's: " + otello.bp, 370, 20);

            if (otello.player == 1) {
                g.drawString("It's black's turn", 250, 40);
            } else {
                g.drawString("It's whit's turn", 250, 40);
            }
        } else {
            if (otello.wp > otello.bp) {
                g.drawString("White won!", 250, 40);
            } else {
                g.drawString("Black won!", 250, 40);
            }
        }

    }

    
    /*
        Constructor
        Gives he otello variable "otello" value to start the game
    */
    public OtelloGUI() {
        addMouseListener(this);
        otello = new Otello();
        
        initOtelloGUI();
    }
    /*
        Creates a frame and sets propeties for it
    */
    private void initOtelloGUI() {
        JFrame frame = new JFrame("Welecome to JavaTutorial.net");
        frame.getContentPane().add(this);
        frame.setSize(width + 300, height - 31);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
    
    /*
        Repaints the frame
    */
    public void updateGUI() {
        repaint();
    }
    
    
    /*
        calls the method clicked from otello
    */
    @Override
    public void mouseClicked(MouseEvent e) {

        int x = e.getX() / boxSize;
        int y = e.getY() / boxSize;

        otello.clicked(x, y);
        repaint();
        
        
    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

}
