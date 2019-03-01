package otello;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

/**
 *
 * @author tobias
 */
public class Otello extends Canvas implements MouseListener {

    static int boardSize = 8;
    static private int width = 300;
    static private int height = 300;
    static private int boxSize = 30;
    static int board[][] = new int[boardSize][boardSize];
    

    int player = 1;
    int other = 2;

    public Otello() {
        addMouseListener(this);
        board[3][3] = 2;
        board[4][3] = 1;

        board[3][4] = 1;
        board[4][4] = 2;

    }

    public void paint(Graphics g) {
        int boxWidth = width / 10 - 2;
        int boxHeight = height / 10 - 2;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int posX = i * width / 10 + 1;
                int posY = j * height / 10 + 1;
                if (board[i][j] == 0) {
                    g.setColor(Color.GREEN);
                    g.fill3DRect(posX, posY, boxWidth, boxHeight, false);
                } else if (board[i][j] == 1) {
                    g.setColor(Color.BLACK);
                    g.fill3DRect(posX, posY, boxWidth, boxHeight, true);
                } else if (board[i][j] == 2) {
                    g.setColor(Color.WHITE);
                    g.fill3DRect(posX, posY, boxWidth, boxHeight, true);
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame win = new JFrame("Otello");
        win.setSize(width + 1, height + height / 10);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = 0;
            }
        }
        Otello canvas = new Otello();
        win.add(canvas);
        win.setVisible(true);
    }

    public void flipRow(int dirX, int dirY, int posX, int posY) {
        System.out.println("Flipping in direction  x: " + dirX + " y: " + dirY + " from pos x:" + posX + " y: " + posY);

        int x = posX + dirX;
        int y = posY + dirY;
        while (board[x][y] == other) {
            board[x][y] = player;
            x += dirX;
            y += dirY;
            repaint();
        }

    }

    
    // tittar om det finns en av dina pjäser på andra sidan raden
    public boolean hasEnd(int dirX, int dirY, int posX, int posY) {

        int x = posX + dirX;
        int y = posY + dirY;

        
        
        while (board[x][y] == other) {
            System.out.println(board[x][y]);

            x += dirX;
            y += dirY;

        }
        if (board[x][y] == player) {
            System.out.println("It has end");
            return true;

        }
        return false;
    }

    public void CheckSurrounding(int xPos, int yPos) {

        boolean hadEnd = false;
        
        
        // koller på omgivnigen 
        for (int x = xPos - 1; x <= xPos + 1; x++) {
            for (int y = yPos - 1; y <= yPos + 1; y++) {
                if (!(x == xPos && y == yPos)) {
                    try {
                        // om en ruta runt din är andra spelaren och det finns en av dina på slutet byt ut andra spelars pjäser mot dina
                        if (board[x][y] == other && hasEnd(x - xPos, y - yPos, xPos, yPos)) {
                            flipRow(x - xPos, y - yPos, xPos, yPos);
                            board[xPos][yPos] = player;
                            hadEnd = true;
                        }
                    } catch (Exception ex) {

                    }
                }
            }
        }
        
        if (hadEnd) {
            int temp = player;
            player = other;
            other = temp;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int posX = e.getX();
        int posY = e.getY();
        int gridPosX = posX / boxSize;
        int gridPosY = posY / boxSize;

        // om du klickar på en tomm ruta titta på omgivnigen
        if (board[gridPosX][gridPosY] == 0) {
            CheckSurrounding(gridPosX, gridPosY);
        }

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
