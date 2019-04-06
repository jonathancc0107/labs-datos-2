
package dibujargrafo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Metodos {

    public static void dibujarIcono(BufferedImage icono, int x, int y, JPanel panel) {
        x = x-(icono.getWidth()/2);
        y = y - (icono.getHeight()/2);
        Graphics g = panel.getGraphics();
        g.drawImage(icono, x, y, icono.getWidth(), icono.getHeight(),panel);
        g.dispose();
        
    }
    public static void dibujarImagen(BufferedImage icono, int x, int y, JPanel panel) {
        Graphics g = panel.getGraphics();
        g.drawImage(icono, x, y, icono.getWidth(), icono.getHeight(),panel);
        g.dispose();
        
    }
    public static void dibujarCirculo(int x, int y, int d, Color c,JPanel panel){
        int r=d/2;
        Graphics g = panel.getGraphics();
        g.setColor(c);
        g.fillOval(x-r, y-r, d, d);
        g.dispose();
    }
    public static void dibujarLinea(int xi, int yi, int xf, int yf, JPanel panel){
        Graphics g = panel.getGraphics();
        g.setColor(Color.black);
        g.drawLine(xi, yi, xf, yf);
        g.dispose();
    } 
    public static boolean sePuedeDibujar(int x, int y, int r, int ancho, int alto){
        return x>r && y>r && x<ancho-r && y<alto-r;
    }
    public static boolean colisiona(int x, int y, int d, ArrayList<Nodo> lista){
        int distE;
        for (Nodo nodo : lista) {
            distE = (int)Math.sqrt((int)(Math.pow((nodo.getX()-x),2))+(int)(Math.pow((nodo.getY()-y),2)));                    
            if (distE<d) {
                return true;
            }
        }
        return false;
    }
    public static Nodo seleccionar(int x, int y, int d, ArrayList<Nodo> lista){
        int distE;
        for (Nodo nodo : lista) {
            distE = (int)Math.sqrt((int)(Math.pow((nodo.getX()-x),2))+(int)(Math.pow((nodo.getY()-y),2)));                    
            if (distE<d/2) {
                return nodo;
            }
        }
        return null;
    }
    
    public static BufferedImage cargarImagen(String nombre){
        try{
            return ImageIO.read(new File("src/images/"+nombre+".png"));
        }catch(IOException e){
            return null;
        }
    }


}
