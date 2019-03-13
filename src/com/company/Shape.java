package com.company;

import com.company.util.Pair;

import java.awt.*;

public class Shape {

    private int Xax, Yax, width, height;

    public Shape(Pair position, int width, int height) {
        this.Xax = (int) position.first;
        this.Yax = (int) position.second;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g, Color color) { // draw shape
        g.setColor(color);
        g.fillRect(Xax, Yax, 2 * width, 2 * height);
    }
}
