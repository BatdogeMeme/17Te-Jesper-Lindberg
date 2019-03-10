/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class GameOfLifeGraph implements ActionListener {

    /**
     * A reference to use when setting the speed.
     */
    private final int SPEED_REFERENCE = 1000;
    /**
     * The speed of this simulation.
     */
    private int speed = 3;
    /**
     * The timer that triggers ticks to be sent out to the entities.
     */
    private Timer timer = new Timer(SPEED_REFERENCE / speed, this);
    /**
     * The width of this world
     */
    private int width = 25;
    /**
     * The height of this world
     */
    private int height = 20;

    /**
     * Our Game Of Life-world
     */
    public char[][] world = new char[width][height];

    private GameOfLifeGUI gui;

    public GameOfLifeGraph(GameOfLifeGUI gui) {
        this.gui = gui;
        this.initWorld();
// setSeed();
    }

    private int checkSurroundings(int xPos, int yPos) {

        int countNeighbours = 0;
        for (int x = xPos - 1; x <= xPos + 1; x++) {
            for (int y = yPos - 1; y <= yPos + 1; y++) {
                if (!(x == xPos && y == yPos)) {
                    try {
                        if (world[x][y] == 'X') {
                            countNeighbours++;
                        }
                    } catch (Exception ex) {

                    }
                }
            }
        }
        return countNeighbours;
    }

    private void updateWorld() {
// Skapa en temporär värld.
        char[][] newWorld = new char[width][height];

// Gå igenom den gamla världen, punkt för punkt och tillämpa reglerna.
        int border = 0;

        for (int i = 0 + border; i < width - border; i++) {
            for (int j = 0 + border; j < height - border; j++) {
                newWorld[i][j] = '.';
// Räkna antalet grannar.
//
                int neighbours = checkSurroundings(i, j);

                if (world[i][j] == 'X') {

// Any live cell with fewer than two live neighbors dies.
                    if (neighbours < 2) {
                        newWorld[i][j] = '.';
                    }

// Any live cell with two or three live neighbors lives on.
                    if (neighbours >= 2 && neighbours <= 3) {
                        newWorld[i][j] = 'X';
                    }

// Any live cell with more than three live neighbors dies.
                    if (neighbours > 3) {
                        newWorld[i][j] = '.';
                    }
                }

// Any dead cell with exactly three live neighbors becomes a live cell.
                if (neighbours == 3 && world[i][j] == '.') {
                    newWorld[i][j] = 'X';
                }
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[i][j] = newWorld[i][j];
            }
        }
    }
    public void clearWorld(){
        int border = 0;
        for(int i = 0 + border; i < width - border; i++){
            for(int j = 0 + border; j < height - border; j++){
                world[i][j] = '.';
        }
        }
        
        
        
    }
    public void actionPerformed(ActionEvent e) {

// Calculate next iteration of the world.
        updateWorld();
        gui.updateAll();
    }

    public void initWorld() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[i][j] = '.';
            }
        }
    }

    public void setSeed() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (Math.random() < 0.2) {
                    world[i][j] = 'X';
                }
            }
        }
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
