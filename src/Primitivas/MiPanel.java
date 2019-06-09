/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Primitivas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Ivan
 */
public class MiPanel extends JPanel {

    Raster raster;
    Dimension prefSize;

    public MiPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public MiPanel(Raster _raster) {
        prefSize = new Dimension(_raster.width, _raster.height);
        this.setSize(prefSize);
        this.raster = _raster;
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize() {
        return prefSize;
    }

    public void setRaster(Raster _raster) {
        this.raster = _raster;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image output = raster.toImage(this);
        g.drawImage(output, 0, 0, this);
    }
}
