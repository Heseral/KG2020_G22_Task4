/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

/**
 * Класс, описывающий координаты экранной точки.
 * @author Alexey
 */
public class ScreenPoint {
    int x, y;

    public ScreenPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
}
