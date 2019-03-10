package otello;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.JFrame;

/**
 *
 * @author tobias
 */
public class Otello extends Canvas implements MouseListener {

    boolean flipped;

    private static JLabel wpl = new JLabel();
    private static JLabel bpl = new JLabel();
    private static JLabel turn = new JLabel();

    private static final int boardSize = 8;
    private static final int width = 300;
    private static final int height = 300;
    private static final int boxSize = 30;
    static int board[][] = new int[boardSize][boardSize];

    int wp = 0;
    int bp = 0;

    int player = 1;
    int other = 2;

    public Otello() {
        addMouseListener(this);
        board[3][3] = 2;
        board[4][3] = 1;

        board[3][4] = 1;
        board[4][4] = 2;

        updatePoints();

        wpl.setText(String.valueOf("White points: " + wp));
        wpl.setBounds(250, 10, 100, 30);

        bpl.setText(String.valueOf("Black points: " + bp));
        bpl.setBounds(350, 10, 100, 30);

        turn.setText("Blakcs turn");
        turn.setBounds(250, 50, 100, 30);
        
        
        legalMoveLeft();

    }

    public void paint(Graphics g) {

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int posX = i * 30;
                int posY = j * 30;
                if (board[i][j] == 0) {
                    g.setColor(Color.GREEN);
                    g.fill3DRect(posX, posY, boxSize, boxSize, true);
                } else if (board[i][j] == 1) {
                    g.setColor(Color.BLACK);
                    g.fill3DRect(posX, posY, boxSize, boxSize, true);
                } else if (board[i][j] == 2) {
                    g.setColor(Color.WHITE);
                    g.fill3DRect(posX, posY, boxSize, boxSize, true);
                }else if (board[i][j] == 3) {
                    g.setColor(Color.RED);
                    g.fill3DRect(posX, posY, boxSize, boxSize, false);
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame win = new JFrame("Otello");

        Container c = win.getContentPane();

        c.setPreferredSize(new Dimension(width + 200, height - 70));
        win.pack();
        win.setResizable(false);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = 0;
            }
        }

        win.add(wpl);
        win.add(bpl);
        win.add(turn);

        Otello canvas = new Otello();
        win.add(canvas);
        win.setVisible(true);
        win.setLayout(null);
    }
    
    
    /*
    *   Flips all of the rows for the tiles in the list
    */
    private void flipRow(ArrayList<tile> tiles) throws ArrayIndexOutOfBoundsException {
        flipped = false;

        for (tile tile : tiles) {

            int x = tile.x + tile.dX;
            int y = tile.y + tile.dY;

            while (board[x][y] == other) {
                board[x][y] = player;

                x += tile.dX;
                y += tile.dY;
                repaint();
                flipped = true;
            }
        }
        
        
        if (flipped) {

            board[tiles.get(0).x][tiles.get(0).y] = player;
            int temp = player;
            player = other;
            other = temp;

        }
        

    }

    
    /*
    *   Returns true if the tile has an end
    */
    private boolean hasEnd(tile tile) {

        int x = tile.x + tile.dX;
        int y = tile.y + tile.dY;

        while (board[x][y] == other) {
            //System.out.println(board[x][y]);

            x += tile.dX;
            y += tile.dY;

        }
        if (board[x][y] == player) {

            return true;

        }
        return false;
    }

    
    /*
    *   Returns true if there is any legal move left
    *   and false if there is nun
    */
    private boolean legalMoveLeft() throws ArrayIndexOutOfBoundsException {
        
        ArrayList<tile> list = new ArrayList<tile>();
        
        boolean legalMoveLeft = false;
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                
                list = surroundingTilesWithEnd(i,j);
                
                for( tile tile : list){
                    
                    
                    
                    if(hasEnd(tile) && emptyTile(tile.x,tile.y)){
                        
                        board[i][j] = 3;
                        legalMoveLeft = true;
                        
                    }
                    
                }
                
                

            }
        }
        
        if(legalMoveLeft){
            repaint();
            return true;
        }    
            
        
        return legalMoveLeft;
    }
    
    
    /*
    *   Returns a list of tiles that the
    *   player can place tiles on
    */
    private ArrayList<tile> surroundingTilesWithEnd(int xPos, int yPos) {

        ArrayList<tile> list = new ArrayList<tile>();

        boolean hadEnd = false;

        // koller på omgivnigen 
        for (int x = xPos - 1; x <= xPos + 1; x++) {
            for (int y = yPos - 1; y <= yPos + 1; y++) {
                if (!(x == xPos && y == yPos)) {
                    try {
                        // om en ruta runt din är andra spelaren och det finns en av dina på slutet byt ut andra spelars pjäser mot dina
                        if (board[x][y] == other && hasEnd(new tile(xPos, yPos, x - xPos, y - yPos))) {

                            list.add(new tile(xPos, yPos, x - xPos, y - yPos));

                        }
                    } catch (Exception ex) {

                    }
                }
            }
        }

        return list;
    }
    
    
    /*
    *   Redraws the text that needs to be redrwan
    *   Points 
    *   Turn
    */
    private void updateText() {
        updatePoints();

        wpl.setText(String.valueOf("White points: " + wp));
        bpl.setText(String.valueOf("Black points: " + bp));

        if (player == 1) {
            turn.setText("Black's turn");
        } else {
            turn.setText("White's turn");
        }

    }
    
    
    /*
    * Looks through the board and
    * counts the points
    * and updates the point variables
    */
    private void updatePoints() {

        bp = wp = 0;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == 1) {
                    bp++;
                }
                if (board[i][j] == 2) {
                    wp++;
                }

            }
        }

    }
    
    
    /*
    *   Sets all "3" in board to 0
    */
    private void resetLegalMoves(){
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j] == 3){
                    board[i][j] = 0;
                }
            }
        }
        repaint();
    }
    
    
    /*
    *  Return true if the asked tile 
    *  is etiher 0 (empty) or
    *  3 (can be placed on)
    */
    private boolean emptyTile(int x, int y){
        
        
        if(board[x][y] == 0 || board[x][y] == 3)
            return true;
            
        
        
        return false;
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        resetLegalMoves();
        
        int posX = e.getX();
        int posY = e.getY();
        int gridPosX = posX / boxSize;
        int gridPosY = posY / boxSize;

        // om du klickar på en tomm ruta titta på omgivnigen
        if (emptyTile(gridPosX,gridPosY)) {

            System.out.println("Clicked x : " + gridPosX + " Clicked y: " + gridPosY);

            System.out.println("Player: " + player);
            flipRow(surroundingTilesWithEnd(gridPosX, gridPosY));

        }

        updatePoints();
        System.out.println("White Points: " + wp + " Black points: " + bp);

        System.out.println(legalMoveLeft());
        
        
        
        if (!legalMoveLeft()) {
            turn.setText("gg");
        } else {
            updateText();
        }


        System.out.println();

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
