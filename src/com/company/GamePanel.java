package com.company;

import com.company.util.Pair;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class GamePanel extends JPanel implements MouseListener {

    private int width, height; // width and height of panel
    private Random random;
    private static final int SPACE = 46;
    private static final int SQ_NUM = 14 * 14;
    private final boolean minePosition[][] = new boolean[14][14];
    private final int mineCount[][] = new int[14][14];
    private final boolean[][] clickPosition = new boolean[14][14];
    private final boolean[][] flagPosition = new boolean[14][14];
    private boolean gameOver = false;
    private int clickedCount = 0;

    public GamePanel(int width, int height) {

        this.height = height;
        this.width = width;
        random = new Random();
        addMouseListener(this);
        setFocusable(true);
        setPreferredSize(new Dimension(width, height)); // set size of panel
        setMinePosition();
        setMineCount();

    }

    private void setMineCount() {
        for (int y = 0; y < 14; y++) {
            for (int x = 0; x < 14; x++) {
                int minCounter = 0;

                if (!minePosition[y][x]) {

                    for (int i = y - 1; i <= y + 1; i++) {
                        for (int j = x - 1; j <= x + 1; j++) {
                            if (i >= 0 && i < 14 && j >= 0 && j < 14)
                                if (minePosition[i][j]) minCounter++;
                        }
                    }
                    mineCount[y][x] = minCounter;
                } else mineCount[y][x] = -1;
                //test
                System.out.printf("(%d , %d) => %d\n", x, y, minCounter);
            }
        }
    }

    private void setMinePosition() {
        System.out.println("mine");
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(14);
            int y = random.nextInt(14);
            minePosition[y][x] = true;
            //test
            System.out.printf("( %d , %d )\n", x, y);
            // MINE_POSITION.add(new Pair<>(x, y));
        }
    }

    @Override
    public void paint(@NotNull Graphics g) {
        drawGrid(g);
        play(g);


    }

    private void drawGrid(@NotNull Graphics g) {
        Shape shape;
        shape = new Shape(new Pair(0, 0), width, height);
        shape.draw(g, Color.BLACK);
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {

                shape = new Shape(new Pair(2 + i * SPACE, 2 + j * SPACE), 22, 22);

                shape.draw(g, Color.GRAY);
            }


        }
    }


    private void play(@NotNull Graphics g) {

        onLeftButtonClicked(g);
        onRightButtonClicked(g);

        if (isWinner()) {

            printString("You Won", 200, 300, g, 55);
            gameOver = true;
            //System.exit(0);
        }

    }

    private void printString(String string, int x, int y, @NotNull Graphics g, int size) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("sans-serif", Font.BOLD, size));
        g.drawString(string, x, y);
    }

    private void onRightButtonClicked(@NotNull Graphics g) {
        for (int y = 0; y < 14; y++) {
            for (int x = 0; x < 14; x++) {
                if (flagPosition[y][x] && !clickPosition[y][x]) {
                    Shape shape = new Shape(new Pair(2 + x * SPACE, 2 + y * SPACE), 22, 22);

                    shape.draw(g, Color.GREEN);
                }
            }
        }
    }


    private void onLeftButtonClicked(@NotNull Graphics g) {
        for (int y = 0; y < 14; y++) {
            for (int x = 0; x < 14; x++) {
                Shape shape = new Shape(new Pair(2 + x * SPACE, 2 + y * SPACE), 22, 22);
                if (clickPosition[y][x]) {
                    if (!minePosition[y][x]) {
                        shape.draw(g, Color.LIGHT_GRAY);
                        if (mineCount[y][x] != 0)
                            printString(Integer.toString(mineCount[y][x]), 15 + x * SPACE, 25 + y * SPACE, g, 18);
                        else if (mineCount[y][x] == 0) {
                            for (int i = y - 1; i <= y + 1; i++) {
                                for (int j = x - 1; j <= x + 1; j++) {
                                    if (i >= 0 && i < 14 && j >= 0 && j < 14)
                                        if (mineCount[i][j] == 0) {
                                            //clickPosition[i][j] = true;
                                            clickedCount++;
                                            shape = new Shape(new Pair(2 + j * SPACE, 2 + i * SPACE), 22, 22);
                                            shape.draw(g, Color.LIGHT_GRAY);
                                        }
                                }
                            }
                        }
                    } else {
                        printMine(g);
                        gameOver = true;
                        //printString("loser", 200, 200, g, 70);
                        break;

                    }

                }

            }

        }
    }

    private boolean isWinner() {
        if (clickedCount == (SQ_NUM - 30)) return true;

        return false;

    }

    private void printMine(Graphics g) {
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                if (minePosition[i][j]) {
                    Shape shape = new Shape(new Pair(2 + j * SPACE, 2 + i * SPACE), 22, 22);
                    shape.draw(g, Color.RED);
                }

            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver) {
            int x = e.getX() / SPACE;
            int y = e.getY() / SPACE;
            if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON2) {
                if (!clickPosition[y][x])
                    clickPosition[y][x] = true;
                clickedCount++;
            } else if (e.getButton() == MouseEvent.BUTTON3)

                flagPosition[y][x] = !flagPosition[y][x];
            repaint();
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
