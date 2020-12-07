/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import draw.IDrawer;
import draw.SimpleEdgeDrawer;
import screen.ScreenConverter;
import third.Camera;
import third.Scene;

/**
 *
 * @author Alexey
 */
public class DrawPanel extends JPanel implements CameraController.RepaintListener {
    private Scene scene;
    private ScreenConverter sc;
    private Camera cam;
    private CameraController camController;
    
    public DrawPanel() {
        super();
        sc = new ScreenConverter(-1, 1, 2, 2, 1, 1);
        cam = new Camera();
        setCamController(new CameraController(cam, sc));
        setScene(new Scene(Color.WHITE.getRGB()));
        getScene().showAxes();

        getCamController().addRepaintListener(this);
        addMouseListener(getCamController());
        addMouseMotionListener(getCamController());
        addMouseWheelListener(getCamController());
    }
    
    @Override
    public void paint(Graphics g) {
        sc.setScreenSize(getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D)bi.getGraphics();
        IDrawer dr = new SimpleEdgeDrawer(sc, graphics);
        getScene().drawScene(dr, cam);
        g.drawImage(bi, 0, 0, null);
        graphics.dispose();
    }

    @Override
    public void shouldRepaint() {
        repaint();
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public CameraController getCamController() {
        return camController;
    }

    public void setCamController(CameraController camController) {
        this.camController = camController;
    }
}
