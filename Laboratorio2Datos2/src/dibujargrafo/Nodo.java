
package dibujargrafo;

import java.awt.Color;

public class Nodo {
    private int x;
    private int y;
    private int d;
    private String nombre;
    private boolean isman;

    public Nodo(int x, int y, int d, String nombre, boolean isman) {
        this.x = x;
        this.y = y;
        this.d = d;
        this.nombre = nombre;
        this.isman = isman;
    }

    public Nodo(String nombre) {
        this.nombre = nombre;
    }
    
    
    public String getNombre() {
        return nombre;
    }

    public boolean isIsman() {
        return isman;
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


}
