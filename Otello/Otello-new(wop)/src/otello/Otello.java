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


    public static final int BOARD_SIZE = 8;
    static int board[][] = new int[BOARD_SIZE][BOARD_SIZE];

    int wp = 0;
    int bp = 0;

    int player = 1;
    int other = 2;

    OtelloGUI gui;

    public Otello(OtelloGUI gui) {
        this.gui = gui;
        //addMouseListener(this);
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
    private void flipRow(ArrayList<tile> tiles) throws ArrayIndexOutOfBoundsException {
        flipped = false;

        tiles.forEach((tile) -> {
            int x = tile.x + tile.dX;
            int y = tile.y + tile.dY;

            while (board[x][y] == other) {
                board[x][y] = player;

                x += tile.dX;
                y += tile.dY;

                flipped = true;
            }
        });

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

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                list = surroundingTilesWithEnd(i, j);

                for (tile tile : list) {

                    if (hasEnd(tile) && emptyTile(tile.x, tile.y)) {

                        board[i][j] = 3;
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

        gui.wpl.setText(String.valueOf("White points: " + wp));
        gui.bpl.setText(String.valueOf("Black points: " + bp));

        if (player == 1) {
            gui.turn.setText("Black's turn");
        } else {
            gui.turn.setText("White's turn");
        }

    }

    /*
    * Looks through the board and
    * counts the points
    * and updates the point variables
     */
    public void updatePoints() {

        bp = wp = 0;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
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

        return board[x][y] == 0 || board[x][y] == 3;
    }

    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();

        }
    }

    public void clicked(int x, int y) {
        resetLegalMoves();

        // om du klickar på en tomm ruta titta på omgivnigen
        System.out.println(emptyTile(x, y));
        if (emptyTile(x, y)) {

            System.out.println("Clicked x : " + x + " Clicked y: " + y);

            System.out.println("Player: " + player);
            flipRow(surroundingTilesWithEnd(x, y));

        }

        updatePoints();
        System.out.println("White Points: " + wp + " Black points: " + bp);

        System.out.println(legalMoveLeft());

        if (!legalMoveLeft()) {
        gui.turn.setText("gg");
        } else {
        updateText();
        }

        System.out.println();
    }

}
