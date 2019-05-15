package otello;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
import java.util.ArrayList;

/**
 *
 * @author tobias
 */
public class Otello {

    boolean flipped;
    boolean gg = false;
    
    private static final int boardSize = 8;
    private static final int boxSize = 30;
    static int board[][] = new int[boardSize][boardSize];
    
    
    /*
         Traks poits for player
         w for white
         b for black
    */
    int wp = 0;
    int bp = 0;
    
    int player = 1;
    int other = 2;
    
    
    /* Constructor */
    public Otello() {

        board[3][3] = 2;
        board[4][3] = 1;

        board[3][4] = 1;
        board[4][4] = 2;

        updatePoints();

        legalMoveLeft();

    }

    /*
    *   Flips all of the rows for the tiles in the list
     */
    private void flipRow(ArrayList<rowStart> tiles) throws ArrayIndexOutOfBoundsException {
        flipped = false;

        for (rowStart tile : tiles) {

            int x = tile.x + tile.dX;
            int y = tile.y + tile.dY;

            while (board[x][y] == other) {
                board[x][y] = player;

                x += tile.dX;
                y += tile.dY;
                
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
    private boolean hasEnd(rowStart tile) {

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
    public boolean legalMoveLeft() throws ArrayIndexOutOfBoundsException {

        ArrayList<rowStart> list = new ArrayList<rowStart>();

        boolean legalMoveLeft = false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                list = surroundingTilesWithEnd(i, j);

                for (rowStart tile : list) {

                    if (hasEnd(tile) && emptyTile(tile.x, tile.y)) {

                        
                        legalMoveLeft = true;

                    }

                }

            }
        }

        if (legalMoveLeft) {
            
            return true;
        }

        return legalMoveLeft;
    }

    /*
    *   Returns a list of rowStarts that the
    *   player can place on.
    *   Also set the coordinate to 3 set its board color to red (Red means that a player can place on it)
     */
    private ArrayList<rowStart> surroundingTilesWithEnd(int xPos, int yPos) {

        ArrayList<rowStart> list = new ArrayList<rowStart>();

        boolean hadEnd = false;

        // Checks suroundings
        for (int x = xPos - 1; x <= xPos + 1; x++) {
            for (int y = yPos - 1; y <= yPos + 1; y++) {
                if (!(x == xPos && y == yPos)) {
                    try {
                        // 
                        if (board[x][y] == other && hasEnd(new rowStart(xPos, yPos, x - xPos, y - yPos))) {
                            
                            list.add(new rowStart(xPos, yPos, x - xPos, y - yPos));
                            
                            // Makes sure that it sets a 3 on only emptytiles
                            if(emptyTile(xPos,yPos)){
                                board[xPos][yPos] = 3;
                            }
                            
                        }
                    } catch (Exception ex) {

                    }
                }
            }
        }

        return list;
    }

    /*
    * Update the points
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
    private void resetLegalMoves() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 3) {
                    board[i][j] = 0;
                }
            }
        }
    }

    /*
    *  Return true if the asked tile 
    *  is etiher 0 (empty) or
    *  3 (can be placed on)
     */
    private boolean emptyTile(int x, int y) {

        if (board[x][y] == 0 || board[x][y] == 3) {
            return true;
        }

        return false;
    }

    
    /*
    *   Gets called from OtelloGUI when mouse is clicked on
    *   the board
    */
    public void clicked(int x, int y) {

        resetLegalMoves();

        // om du klickar på en tomm ruta titta på omgivnigen
        if (emptyTile(x, y)) {

            System.out.println("Clicked x : " + x + " Clicked y: " + y);

            System.out.println("Player: " + player);
            flipRow(surroundingTilesWithEnd(x, y));

        }

        updatePoints();
        System.out.println("White Points: " + wp + " Black points: " + bp);

        if (!legalMoveLeft()) {
            gg = true;
            
        } 
        System.out.println();

    }
    
    
    
    
}
