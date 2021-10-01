package models;

import java.awt.geom.Point2D;

public class Body {
    private Point2D r;
    private Point2D v;
    private double mass = 0;

    public Body(double rx,double ry,double vx, double vy,double mass) {
        this.r = new Point2D.Double(rx, ry);
        this.v = new Point2D.Double(vx,vy);
        this.mass = mass;
    }

    public Point2D getR() {
        return r;
    }

    public void setR(Point2D r) {
        this.r = r;
    }

    public Point2D getV() {
        return v;
    }

    public void setV(Point2D v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return String.format("r:(%.5f %.5f) v:(%.5f %.5f)", r.getX(),r.getY(), v.getX(),v.getY());
    }
}
