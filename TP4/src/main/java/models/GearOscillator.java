package models;

public class GearOscillator {

    private double r;
    private double r1;
    private double r2;
    private double r3;
    private double r4;
    private double r5;

    public GearOscillator(double r, double v) {
        this.r = r;
        this.r1 = v;
        this.r2 = 0;
        this.r3 = 0;
        this.r4 = 0;
        this.r5 = 0;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getV() {
        return r1;
    }

    public void setV(double v) {
        this.r1 = r1;
    }

    public double getR1() {
        return r1;
    }

    public void setR1(double r1) {
        this.r1 = r1;
    }

    public double getR2() {
        return r2;
    }

    public void setR2(double r2) {
        this.r2 = r2;
    }

    public double getR3() {
        return r3;
    }

    public void setR3(double r3) {
        this.r3 = r3;
    }

    public double getR4() {
        return r4;
    }

    public void setR4(double r4) {
        this.r4 = r4;
    }

    public double getR5() {
        return r5;
    }

    public void setR5(double r5) {
        this.r5 = r5;
    }

    @Override
    public String toString() {
        return String.format("%.5f %.5f", r, r1);
    }
}
