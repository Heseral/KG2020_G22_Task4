/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package third;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import draw.IDrawer;
import math.Matrix4;
import math.Matrix4Factories;
import math.Vector3;
import models.Line3D;
import util.GlobalVar;

/**
 * Описывает трёхмерную со всеми объектами на ней
 *
 * @author Alexey
 */
public class Scene {
    private List<IModel> models = new ArrayList<>();
    // относительно какой точки будет вращаться сцена
    private Vector3 rotationPoint;

    public List<IModel> getModelsList() {
        return models;
    }

    private int backgroundColor;

    /**
     * Создаём сцену с заданным фоном
     *
     * @param backgroundColorRGB цвет фона.
     */
    public Scene(int backgroundColorRGB) {
        this.backgroundColor = backgroundColorRGB;
        this.showAxes = false;
    }

    private boolean showAxes;

    public boolean isShowAxes() {
        return showAxes;
    }

    public void setShowAxes(boolean showAxis) {
        this.showAxes = showAxis;
    }

    public void showAxes() {
        this.showAxes = true;
    }

    public void hideAxes() {
        this.showAxes = false;
    }

    private static final List<Line3D> axes = Arrays.asList(
            new Line3D(new Vector3(0, 0, 0), new Vector3(10, 0, 0), Color.RED),
            new Line3D(new Vector3(0, 0, 0), new Vector3(0, 10, 0), Color.GREEN),
            new Line3D(new Vector3(0, 0, 0), new Vector3(0, 0, 10), Color.BLUE)
    );

    /**
     * Рисуем сцену со всеми моделями
     *
     * @param drawer то, с помощью чего будем рисовать
     * @param camera камера для преобразования координат
     */
    public void drawScene(IDrawer drawer, ICamera camera) {
        List<PolyLine3D> lines = new LinkedList<>();
        LinkedList<Collection<? extends IModel>> allModels = new LinkedList<>();
        allModels.add(models);
        /*Если требуется, то добавляем оси координат*/
        if (isShowAxes())
            allModels.add(axes);
        Matrix4 rotationMatrix;
        Matrix4 pointCoordinatesAsMatrix;
        /*перебираем все полилинии во всех моделях*/
        for (Collection<? extends IModel> mc : allModels) {
            for (IModel model : mc) {
                for (PolyLine3D polyLine3D : model.getLines()) {
                    /*Все точки конвертируем с помощью камеры*/
                    List<Vector3> points = new LinkedList<>();
                    for (Vector3 point : polyLine3D.getPoints()) {
                        if (rotationPoint != null) {
                            pointCoordinatesAsMatrix = new Matrix4(new double[][]{
                                    {point.getX(), point.getY(), point.getZ(), 0},
                                    {0, 0, 0, 0},
                                    {0, 0, 0, 0},
                                    {0, 0, 0, 0}
                            });
                            rotationMatrix = new Matrix4(new double[][]{
                                    {
                                            GlobalVar.COS_UGLA_POVOROTA + (1 - GlobalVar.COS_UGLA_POVOROTA) * rotationPoint.getX() * rotationPoint.getX(),
                                            (1 - GlobalVar.COS_UGLA_POVOROTA) * rotationPoint.getX() * rotationPoint.getY() - GlobalVar.SIN_UGLA_POVOROTA * rotationPoint.getZ(),
                                            (1 - GlobalVar.COS_UGLA_POVOROTA) * rotationPoint.getX() * rotationPoint.getZ() + GlobalVar.SIN_UGLA_POVOROTA * rotationPoint.getY(),
                                            0
                                    },
                                    {
                                            (1 - GlobalVar.COS_UGLA_POVOROTA) * rotationPoint.getY() * rotationPoint.getX() + GlobalVar.SIN_UGLA_POVOROTA * rotationPoint.getZ(),
                                            GlobalVar.COS_UGLA_POVOROTA + (1 - GlobalVar.COS_UGLA_POVOROTA) * rotationPoint.getY() * rotationPoint.getY(),
                                            (1 - GlobalVar.COS_UGLA_POVOROTA) * rotationPoint.getY() * rotationPoint.getZ() - GlobalVar.SIN_UGLA_POVOROTA * rotationPoint.getX(),
                                            0
                                    },
                                    {
                                            (1 - GlobalVar.COS_UGLA_POVOROTA) * rotationPoint.getZ() * rotationPoint.getX() - GlobalVar.SIN_UGLA_POVOROTA * rotationPoint.getY(),
                                            (1 - GlobalVar.COS_UGLA_POVOROTA) * rotationPoint.getZ() * rotationPoint.getY() + GlobalVar.SIN_UGLA_POVOROTA * rotationPoint.getX(),
                                            GlobalVar.COS_UGLA_POVOROTA + (1 - GlobalVar.COS_UGLA_POVOROTA) * rotationPoint.getZ() * rotationPoint.getZ(),
                                            0
                                    },
                                    {0, 0, 0, 0}
                            });
                            Matrix4 matrix4 = pointCoordinatesAsMatrix.mul(rotationMatrix);
                            point = new Vector3(matrix4.getAt(0, 0), matrix4.getAt(0, 1), matrix4.getAt(0, 2));

                            /* // НЕРАБОЧИЙ ВАРИАНТ ЛЕКЦИИ
                            n1 = point.getX() / Math.sqrt(point.getX() + point.getY() + point.getZ());
                            n2 = point.getY() / Math.sqrt(point.getX() + point.getY() + point.getZ());
                            n3 = point.getZ() / Math.sqrt(point.getX() + point.getY() + point.getZ());
                            first = new Matrix4(new double[][]{
                                    {1, 0, 0, 0},
                                    {0, 1, 0, 0},
                                    {0, 0, 1, 0},
                                    {-point.getX(), -point.getY(), -point.getZ(), 1}
                            });
                            second = new Matrix4(new double[][]{
                                    {
                                            n1 * n1 + (1 - n1 * n1) * GlobalVar.COS_UGLA_POVOROTA,
                                            n1 * n2 * (1 - GlobalVar.COS_UGLA_POVOROTA) + n3 * GlobalVar.SIN_UGLA_POVOROTA,
                                            n1 * n3 * (1 - GlobalVar.COS_UGLA_POVOROTA) - n2 * GlobalVar.SIN_UGLA_POVOROTA,
                                            0
                                    },
                                    {
                                            n2 * n1 * (1 - GlobalVar.COS_UGLA_POVOROTA) - n3 * GlobalVar.SIN_UGLA_POVOROTA,
                                            n2 * n2 + (1 - n2 * n2) * GlobalVar.COS_UGLA_POVOROTA,
                                            n2 * n3 * (1 - GlobalVar.COS_UGLA_POVOROTA) + n1 * GlobalVar.SIN_UGLA_POVOROTA,
                                            0
                                    },
                                    {
                                            n3 * n1 * (1 - GlobalVar.COS_UGLA_POVOROTA) - n2 * GlobalVar.SIN_UGLA_POVOROTA,
                                            n3 * n2 * (1 - GlobalVar.COS_UGLA_POVOROTA) - n1 * GlobalVar.SIN_UGLA_POVOROTA,
                                            n3 * n3 + (1 - n3 * n3) * GlobalVar.COS_UGLA_POVOROTA,
                                            0
                                    },
                                    {
                                            0,
                                            0,
                                            0,
                                            1
                                    }
                            });
                            third = new Matrix4(new double[][]{
                                    {1, 0, 0, 0},
                                    {0, 1, 0, 0},
                                    {0, 0, 1, 0},
                                    {point.getX(), point.getY(), point.getZ(), 1}
                            });
                             */
                        }
                        points.add(camera.worldToScreen(point));
                    }
                    /*Создаём на их сонове новые полилинии, но в том виде, в котором их видит камера*/
                    lines.add(new PolyLine3D(points, polyLine3D.isClosed(), polyLine3D.getColor()));
                }
            }
        }

        /*Закрашиваем фон*/
        drawer.clear(backgroundColor);
        /*Рисуем все линии*/
        drawer.draw(lines);
    }

    public Vector3 getRotationPoint() {
        return rotationPoint;
    }

    public void setRotationPoint(Vector3 rotationPoint) {
        this.rotationPoint = rotationPoint;
    }
}
