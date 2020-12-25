/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import math.Matrix4Factories;
import math.Vector3;
import models.Function;
import util.GlobalVar;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Alexey
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AtomicReference<Timer> timer = new AtomicReference<>(new Timer());

        JFrame frame = new JFrame();
        frame.setSize(600, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel editPanel = new JPanel();
        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.X_AXIS));
        editPanel.setMaximumSize(new Dimension(GlobalVar.WINDOW_WIDTH, 100));

        DrawPanel drawPanel = new DrawPanel();

        JLabel functionLabel = new JLabel("Function:");
        JTextField functionTextField = new JTextField("e^sin(x*3)-cos (y^2)");

        JLabel xFromLabel = new JLabel("From X:");
        JLabel xToLabel = new JLabel("To X:");
        JLabel yFromLabel = new JLabel("From Y:");
        JLabel yToLabel = new JLabel("To Y:");

        final String basicFunctionSize = "10";

        JTextField xFromTextField = new JTextField("-" + basicFunctionSize);
        JTextField xToTextField = new JTextField(basicFunctionSize);
        JTextField yFromTextField = new JTextField("-" + basicFunctionSize);
        JTextField yToTextField = new JTextField(basicFunctionSize);

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(actionEvent -> {
            drawPanel.getScene().getModelsList().clear();
            drawPanel.getScene().getModelsList().add(
                    new Function(functionTextField.getText(),
                            Integer.parseInt(xFromTextField.getText()),
                            Integer.parseInt(xToTextField.getText()),
                            Integer.parseInt(yFromTextField.getText()),
                            Integer.parseInt(yToTextField.getText())
                    )
            );
            drawPanel.repaint();
        });

        editPanel.add(functionLabel);
        editPanel.add(functionTextField);
        editPanel.add(xFromLabel);
        editPanel.add(xFromTextField);
        editPanel.add(xToLabel);
        editPanel.add(xToTextField);
        editPanel.add(yFromLabel);
        editPanel.add(yFromTextField);
        editPanel.add(yToLabel);
        editPanel.add(yToTextField);
        editPanel.add(applyButton);

        JPanel rotatePanel = new JPanel();
        rotatePanel.setLayout(new BoxLayout(rotatePanel, BoxLayout.X_AXIS));
        rotatePanel.setMaximumSize(new Dimension(GlobalVar.WINDOW_WIDTH, 100));

        JLabel rotationDelayLabel = new JLabel("Delay:");
        JLabel xRotateLabel = new JLabel("X:");
        JLabel yRotateLabel = new JLabel("Y:");
        JLabel zRotateLabel = new JLabel("Z:");

        JTextField rotationDelayTextField = new JTextField("10");
        JTextField xTextField = new JTextField("1");
        JTextField yTextField = new JTextField("1");
        JTextField zTextField = new JTextField("0");

        JButton buttonStartRotation = new JButton("Rotate");
        AtomicBoolean rotating = new AtomicBoolean(false);
        buttonStartRotation.addActionListener(actionEvent -> {
            if (rotating.get()) {
                rotating.set(false);
                drawPanel.getScene().setRotationPoint(null);
                drawPanel.getScene().resetUgolPovorota();
                drawPanel.repaint();
                timer.get().purge();
                timer.get().cancel();
                timer.set(new Timer());
                return;
            }
            rotating.set(true);
            double x = Double.parseDouble(xTextField.getText());
            double y = Double.parseDouble(yTextField.getText());
            double z = Double.parseDouble(zTextField.getText());
            double length = Math.sqrt(x * x + y * y + z * z);
            // сделаем вектор единичным для избежания искажений
            drawPanel.getScene().setRotationPoint(new Vector3(
                    x / length,
                    y / length,
                    z / length
            ));
            timer.get().schedule(new TimerTask() {
                @Override
                public void run() {
                    drawPanel.getScene().increaseUgolPovorota();
                    drawPanel.getCamController().onRepaint();
                }
            }, 0, Long.parseLong(rotationDelayTextField.getText()));
        });

        rotatePanel.add(rotationDelayLabel);
        rotatePanel.add(rotationDelayTextField);
        rotatePanel.add(xRotateLabel);
        rotatePanel.add(xTextField);
        rotatePanel.add(yRotateLabel);
        rotatePanel.add(yTextField);
        rotatePanel.add(zRotateLabel);
        rotatePanel.add(zTextField);
        rotatePanel.add(buttonStartRotation);

        mainPanel.add(editPanel);
        mainPanel.add(rotatePanel);
        mainPanel.add(drawPanel);

        frame.add(mainPanel);

        frame.setVisible(true);
    }
}
/*
    (40) Написать программу динамического поворота 3D-графика функции. Варианты функций:
    z = e^sin(x*3)-cos(y^2)
    z = x^3-y^2
    z = x^(1/3)*sin(y)
    z = log(x^2+1)/(y^2+2) // ln заменен на log - парсер слишком тупенький для распознавания ln
    z = (1/(x^3+2))+log(y) // ln заменен на log - парсер слишком тупенький для распознавания ln
    z = abs(y^4-y^3+x^2-x)
    z = x^4+y^4*(x^2+y^2)
    (*) z = e^((sin(x)+cos(x))/(x^2+1)) // e^((sin(x)+cos(x))*((x^2+1)^-1)) - заменено на эквивалент ввиду тупизны парсера
    (*) Усложнённая версия этого задания подразумевает добавление цветовой градации по оси z.
    Набор цветов и интервалы применения задаются пользователем отдельно.
    Пользователь выбирает выводимый на экран график функции, выбирает трёхмерную область обзора,
    а также детализацию графика (количество опорных точек). После этого, пользователь задаёт угловую скорость и
    ось вращения (не обязательно одна из осей координат) и камера начинает своё вращение вокруг заданной оси.
    Для удобства стоит также как-то задавать точку, в которую при вращении смотрит камера.
 */
