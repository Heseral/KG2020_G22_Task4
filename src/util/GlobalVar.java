package util;

import math.Vector3;
import org.matheclipse.parser.client.eval.DoubleEvaluator;

public class GlobalVar {
    public static final DoubleEvaluator DOUBLE_EVALUATOR = new DoubleEvaluator(true);
    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 600;
    public static final double UGOL_POVOROTA = 0.01;
    public static final double COS_UGLA_POVOROTA = Math.cos(UGOL_POVOROTA);
    public static final double SIN_UGLA_POVOROTA = Math.cos(UGOL_POVOROTA);
}
