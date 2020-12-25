/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

/**
 * Класс, хранящий трёхмерный вектор / точку в трёхмерном пространстве.
 * @author Alexey
 */
public class Vector3 {
    private double[] values; /*Значения хранятся в виде массива из трёх элементов*/
    
    /**
     * Создаёт экземпляр вектора на основе значений трёх составляющих
     * @param x первая составляющая, описывающая X-координату
     * @param y вторая составляющая, описывающая Y-координату
     * @param z третья составляющая, описывающая Z-координату
     */
    public Vector3(double x, double y, double z) {
        values = new double[]{x, y, z};
    }

    /**
     * X-составляющая вектора
     * @return X-составляющая вектора
     */
    public double getX() {
        return values[0];
    }

    /**
     * Y-составляющая вектора
     * @return Y-составляющая вектора
     */
    public double getY() {
        return values[1];
    }

    /**
     * Z-составляющая вектора
     * @return Z-составляющая вектора
     */
    public double getZ() {
        return values[2];
    }
    
    /**
     * Метод, возвращающий составляющую вектора по порядковому номеру
     * @param idx порядковый номер
     * @return значение составляющей вектора
     */
    public double at(int idx) {
        return values[idx];
    }
    
    private static final double EPSILON = 1e-10f;
    /**
     * Метод, возвращающий длину вектора
     * @return длина вектора
     */
    public double length() {
        double lenSqr = values[0] * values[0] + values[1] * values[1] + values[2] * values[2];
        if (lenSqr < EPSILON)
            return 0;
        return (double)Math.sqrt(lenSqr);
    }
    
}
