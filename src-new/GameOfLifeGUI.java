/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.SwingUtilities;

public class GameOfLifeGUI extends JFrame implements ActionListener {

    public static void main(String[] args) {
        GameOfLifeGUI gui = new GameOfLifeGUI();
    }

    /**
     * Icon for an empty position in the grid and where there is a "thing"
     */
    private final ImageIcon II_EMPTY = new ImageIcon("src/sempty.gif");
    private final ImageIcon II_THING = new ImageIcon("src/unknown.gif");
    /**
     * The gGameOfLife-graph this class should display
     */
    private final GameOfLifeGraph golGraph;
    /**
     * The grid, i.e., the field containing the images to display.
     */
    private JLabel[][] grid;
    /**
     * The button for starting the simulation.
     */

    private final JButton startButton = new JButton("Start");
    private final JButton stopButton = new JButton("Stop");
    private final JButton exitButton = new JButton("Exit");
    private final JButton randomseed = new JButton("randomseed");
    private final JButton editMode = new JButton("edit");
    private final JButton clear = new JButton("clear");

    JPanel field = new JPanel();
    boolean value = false;

    public GameOfLifeGUI() {
        golGraph = new GameOfLifeGraph(this);
        initGameOfLifeGUI();
        golGraph.setSeed();
        updateAll();
    }

    private void initGameOfLifeGUI() {
        setSize(golGraph.getWidth() * 30, golGraph.getHeight() * 30);

        startButton.addActionListener(this);
        stopButton.addActionListener(this);
        exitButton.addActionListener(this);
        randomseed.addActionListener(this);
        editMode.addActionListener(this);
        clear.addActionListener(this);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 3));
        buttons.add(startButton);
        buttons.add(stopButton);
        buttons.add(randomseed);
        buttons.add(clear);
        buttons.add(editMode);
        buttons.add(exitButton);

        field.setBackground(new Color(27, 204, 89));
        field.setLayout(new GridLayout(golGraph.getHeight(),
                golGraph.getWidth()));
        grid = new JLabel[golGraph.getWidth()][golGraph.getHeight()];

        for (int y = 0; y < golGraph.getHeight(); y++) {
            for (int x = 0; x < golGraph.getWidth(); x++) {
                grid[x][y] = new JLabel(II_EMPTY);
                grid[x][y].setVisible(true);
                field.add(grid[x][y]);
            }
        }

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {

                if (!value) {
                    return;
                }

                int x = (int) e.getX();
                int y = (int) e.getY();

                toggleCell(x, y);
                System.out.println("=====-ClickPos-=====");
                System.out.println("X: " + x + "\t" + "Y: " + y);
                System.out.println("====================");
            }

        });

        Container display = getContentPane();
        display.setBackground(new Color(27, 204, 89));
        display.setLayout(new BorderLayout());
        display.add(field, BorderLayout.CENTER);
        display.add(buttons, BorderLayout.SOUTH);

        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        exitButton.setEnabled(true);
        updateAll();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            exitButton.setEnabled(false);
            editMode.setEnabled(false);
            randomseed.setEnabled(false);
            clear.setEnabled(false);
            golGraph.start();

        }

        if (e.getSource() == stopButton) {
            stopButton.setEnabled(false);
            startButton.setEnabled(true);
            exitButton.setEnabled(true);
            editMode.setEnabled(true);
            randomseed.setEnabled(true);
            clear.setEnabled(true);
            golGraph.stop();

        }

        if (e.getSource() == exitButton) {
            golGraph.stop();
            System.exit(0);

        }

        if (e.getSource() == clear) {
            golGraph.clearWorld();
            updateAll();
        }

        if (e.getSource() == randomseed) {
            golGraph.clearWorld();
            golGraph.setSeed();
            updateAll();

        }

        if (e.getSource() == editMode) {
            value = !(value);
            startButton.setEnabled(!value);
            exitButton.setEnabled(!value);
        }
    }

    public void updateAll() {
        int width = golGraph.getWidth();
        int height = golGraph.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ImageIcon icon;

                char whatIsHere = golGraph.world[x][y];

                if (whatIsHere == 'X') {
                    icon = II_THING;
                } else {
                    icon = II_EMPTY;
                }

                grid[x][height - y - 1].setIcon(icon);
            }
        }

    }

    public void toggleCell(int x, int y) {
        int height = (int) getInnerSize(this).getHeight();
        int width = (int) getInnerSize(this).getWidth();

        int rutansLängd = (int) width / golGraph.getWidth();
        int rutansHöjd = (int) height / golGraph.getHeight();

        int newX = 0, newY = 0;
        int temp = 0;

        /* för x */
        System.out.println("Rutans Längd: " + rutansLängd);
        for (int i = 0; i < golGraph.getWidth(); i++) {

            System.out.println("x: " + x + "\t" + "rs: " + temp + "\t" + "rs*2: " + rutansLängd * i);
            if ((x > temp) && (x < (rutansLängd * (i + 1)))) {
                newX = i;
                System.out.println("ksy");
            }
            temp += rutansLängd;
        }
        temp = 0;
        /* för y */
        for (int i = 0; i < golGraph.getHeight(); i++) {
            if ((y > temp) && (y < (rutansHöjd * (i + 1)))) {
                newY = i;

            }
            temp += rutansHöjd;
        }

        System.out.println("newX: " + newX + "\t" + "newY; " + newY);
        golGraph.world[newX][golGraph.getHeight() - newY - 1] = 'X';
        updateAll();

    }

    public static Dimension getInnerSize(Frame frame) {
        Dimension size = frame.getSize();
        Insets insets = frame.getInsets();
        if (insets != null) {
            size.height -= insets.top + insets.bottom;
            size.width -= insets.left + insets.right;
        }
        return size;
    }

}
