package models;

public class Oscilator {

    private double r;
    private double v;

    public Oscilator(double r, double v) {
        this.r = r;
        this.v = v;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }
}
