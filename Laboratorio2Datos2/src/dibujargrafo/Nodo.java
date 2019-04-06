
package dibujargrafo;

import java.awt.Color;

public class Nodo {
    private int x;
    private int y;
    private int d;
    private Color color;

    public Nodo(int x, int y, int d, Color color) {
        this.x = x;
        this.y = y;
        this.d = d;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getD() {
        return d;
    }

    public Color getColor() {
        return color;
    }
}
