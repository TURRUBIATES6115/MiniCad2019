package Primitivas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ivan
 */
public class PizarraC extends javax.swing.JFrame {

    /**
     * Creates new form PizarraC
     */
    Point p1, p2;
    boolean bDibujar, bDibujar2;

    Raster raster;
    Opciones op = new Opciones();
    boolean firstTime = true;

    public PizarraC() {
        initComponents();
        p1 = new Point();
        p2 = new Point();
        bDibujar = false;
        this.setMaximumSize(new Dimension(640, 480));
        this.setMinimumSize(new Dimension(640, 480));
        raster = new Raster(640, 480);
        this.setLocationRelativeTo(null);
        op.setLocation(this.getX() - op.getWidth() - 15, this.getY());
        op.setVisible(true);
    }

    public void lineFast(int x0, int y0, int x1, int y1, Color color) {
        int pix = color.getRGB();
        int dy = y1 - y0;
        int dx = x1 - x0;
        int stepx, stepy;
        if (dy < 0) {
            dy = -dy;
            stepy = -raster.width;
        } else {
            stepy = raster.width;
        }
        if (dx < 0) {
            dx = -dx;
            stepx = -1;
        } else {
            stepx = 1;
        }
        dy <<= 1;
        dx <<= 1;
        y0 *= raster.width;
        y1 *= raster.width;
        raster.pixel[x0 + y0] = pix;
        if (dx > dy) {
            int fraction = dy - (dx >> 1);
            while (x0 != x1) {
                if (fraction >= 0) {
                    y0 += stepy;
                    fraction -= dx;
                }
                x0 += stepx;
                fraction += dy;
                raster.pixel[x0 + y0] = pix;
            }
        } else {
            int fraction = dx - (dy >> 1);
            while (y0 != y1) {
                if (fraction >= 0) {
                    x0 += stepx;
                    fraction -= dy;
                }
                y0 += stepy;
                fraction += dx;
                raster.pixel[x0 + y0] = pix;
            }
        }
    }

    void PlotPoint(Graphics g, int xc, int yc, int x, int y) {
        g.setColor(Color.red);
        g.drawRect(xc + x, yc + y, 1, 1);
        g.drawRect(xc - x, yc + y, 1, 1);
        g.drawRect(xc + x, yc - y, 1, 1);
        g.drawRect(xc - x, yc - y, 1, 1);
        g.drawRect(xc + y, yc + x, 1, 1);
        g.drawRect(xc - y, yc + x, 1, 1);
        g.drawRect(xc + y, yc - x, 1, 1);
        g.drawRect(xc - y, yc - x, 1, 1);
    }

    void CircleMidPoint(Point p1, Point p2) {
        int x, y, p, xc = p1.x, yc = p1.y;
        int r = (int) Math.sqrt(((p2.x - p1.x) * (p2.x - p1.x)) + ((p2.y - p1.y) * (p2.y - p1.y)));
        Graphics g = this.getGraphics();
        g.setColor(Color.red);
        x = 0;
        y = r;
        p = 1 - r;
        PlotPoint(g, xc, yc, x, y);
        while (x < y) {
            x = x + 1;
            if (p < 0) {
                p = p + 2 * x + 1;
            } else {
                y = y - 1;
                p = p + 2 * (x - y) + 1;
            }
            PlotPoint(g, xc, yc, x, y);
        }
    }
    /*
    public void Elipse(Point p1, Point p2) {
        Graphics g = this.getGraphics();
        int x, y, p, px, py, xc = p1.x, yc = p1.y, rx = p2.x, ry = p2.y;
        int rx2, ry2, tworx2, twory2;
        ry2 = ry * ry;
        rx2 = rx * rx;
        twory2 = 2 * ry2;
        tworx2 = 2 * rx2;        
        x = 0;
        y = ry;
        PlotPoint(g, x, y, xc, yc);
        p = (int) Math.round(ry2 - rx2 * ry + 0.25 * rx2);
        px = 0;
        py = tworx2 * y;
        while (px < py) {            
            x = x + 1;
            px = px + twory2;
            if (p < 0) {
                p = p + ry2 + px;
            } else {
                y = y - 1;
                py = py - tworx2;
                p = p + ry2 + px - py;
            }
            PlotPoint(g, xc, yc, x, y);
        }
        p = (int) Math.round(ry2 * (x + 0.5) * (x + 0.5) + rx2 * (y - 1) * (y - 1) - rx2 * ry2);
        px = 0;
        py = tworx2 * y;
        while (y > 0) {
            y = y - 1;
            py = py - tworx2;
            if (p > 0) {
                p = p + rx2 - py;
            } else {
                x = x + 1;
                px = px + twory2;
                p = p + rx2 + py + px;
            }
            PlotPoint(g, xc, yc, x, y);
        }
    }
     */
    public void Elipse2(Point p1, Point p2) {
        Graphics g = this.getGraphics();
        g.setColor(Color.red);
        int an = (int) Math.sqrt(((p2.x - p1.x) * (p2.x - p1.x)) + ((p2.y - p1.y) * (p2.y - p1.y)));
        g.drawOval(p1.x, p1.y, an * 2, an);
    }

    @Override
    public void update(Graphics g) {
        paint(g);

    }

    @Override
    public void paint(Graphics g) {
        if (firstTime) {
            firstTime = false;
            super.paint(g);
        }

        Image output = raster.toImage(this);
        g.drawImage(output, 0, 0, this);
    }

    public void clear() {
        int s = raster.size();
        for (int i = 0; i < s; i++) {
            raster.pixel[i] ^= 0x00ffffff;
        }
        repaint();
        return;
    }

    private void dibujarLinea(Point _p1, Point _p2, Color color) {
        lineFast(_p1.x, _p1.y, _p2.x, _p2.y, color);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pizarra");
        setResizable(false);
        setSize(new java.awt.Dimension(640, 480));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        if (op.getb1()) {
            if (!bDibujar) {
                p1.x = evt.getX();
                p1.y = evt.getY();
                bDibujar = true;
            } else {
                p2.x = evt.getX();
                p2.y = evt.getY();
                dibujarLinea(p1, p2, Color.red);
                bDibujar = false;
            }
        } else if (op.getb2()) {
            if (!bDibujar) {
                p1.x = evt.getX();
                p1.y = evt.getY();
                bDibujar = true;
            } else {
                p2.x = evt.getX();
                p2.y = evt.getY();
                CircleMidPoint(p1, p2);
                bDibujar = false;
            }
        } else if (op.getb3()) {
            if (!bDibujar) {
                p1.x = evt.getX();
                p1.y = evt.getY();
                bDibujar = true;
            } else {
                p2.x = evt.getX();
                p2.y = evt.getY();
                Elipse2(p1, p2);
                bDibujar = false;
            }
        }
    }//GEN-LAST:event_formMouseClicked

    private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
        // TODO add your handling code here:
        this.op.setLocation(this.getX() - op.getWidth() - 15, this.getY());
    }//GEN-LAST:event_formComponentMoved

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PizarraC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PizarraC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PizarraC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PizarraC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PizarraC().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
