package Primitivas;

import java.awt.Color;
import java.awt.Point;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author LaboratorioU005_11
 */
public class Figura {

    Color color;
    Point puntos[];
    int id;
    double d;

    public Figura(Point punto[], int id,double d, Color c) {
        puntos = new Point[punto.length];
        for (int i = 0; i < puntos.length; i++) {
            this.puntos[i] = new Point(punto[i].x,punto[i].y);
        }
        this.id = id;
        this.color = c;
        this.d= d;
    }

    public Figura() {
    }
}
