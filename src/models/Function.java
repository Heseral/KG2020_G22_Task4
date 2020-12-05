package models;

import math.Vector3;
import org.matheclipse.parser.client.Parser;
import org.matheclipse.parser.client.ast.ASTNode;
import org.matheclipse.parser.client.operator.ASTNodeFactory;
import third.IModel;
import third.PolyLine3D;
import util.GlobalVar;

import java.util.ArrayList;
import java.util.List;

public class Function implements IModel {
    private List<PolyLine3D> polygons = new ArrayList<>();

    public Function(String function, int xFrom, int xTo, int yFrom, int yTo) {
        if (xFrom > xTo) {
            int temp = xFrom;
            xFrom = xTo;
            xTo = temp;
        }
        if (yFrom > yTo) {
            int temp = yFrom;
            yFrom = yTo;
            yTo = temp;
        }

        function = function.replaceAll("e", String.valueOf(Math.E)).replaceAll(" ", "");

        int z;
        PolyLine3D polyLine3D;
        for (int y = yFrom + 1; y < yTo; y++) {
            for (int x = xFrom + 1; x < xTo; x++) {
                polyLine3D = new PolyLine3D(new ArrayList<>(), true);
                z = (int) Math.round(GlobalVar.DOUBLE_EVALUATOR.evaluate(
                        function.replaceAll("x", String.valueOf(x))
                                .replaceAll("y", String.valueOf(y))
                                .replaceAll("--", "+"))
                );
                polyLine3D.getPoints().add(new Vector3(x, y, z));
                z = (int) Math.round(GlobalVar.DOUBLE_EVALUATOR.evaluate(
                        function.replaceAll("x", String.valueOf(x - 1))
                                .replaceAll("y", String.valueOf(y))
                                .replaceAll("--", "+"))
                );
                polyLine3D.getPoints().add(new Vector3(x - 1, y, z));
                z = (int) Math.round(GlobalVar.DOUBLE_EVALUATOR.evaluate(
                        function.replaceAll("x", String.valueOf(x - 1))
                                .replaceAll("y", String.valueOf(y - 1))
                                .replaceAll("--", "+"))
                );
                polyLine3D.getPoints().add(new Vector3(x - 1, y - 1, z));
                z = (int) Math.round(GlobalVar.DOUBLE_EVALUATOR.evaluate(
                        function.replaceAll("x", String.valueOf(x))
                                .replaceAll("y", String.valueOf(y - 1))
                                .replaceAll("--", "+"))
                );
                polyLine3D.getPoints().add(new Vector3(x, y - 1, z));
                polygons.add(polyLine3D);
            }
        }
        /*
            используя глобалварный парсер мат формул вычислять значения координат в цикле.
            Но вопрос а как цикл делать, если будут в графике разрывы тогда?(не знаем какая линия вертикальная,
            горизонтальная, глубинная)? Как понять какую часть графика рисовать, если мы рисуем заготовленный
            список полилиний? Не забивать же память на 146%
         */
    }

    @Override
    public List<PolyLine3D> getLines() {
        return polygons;
    }
}
