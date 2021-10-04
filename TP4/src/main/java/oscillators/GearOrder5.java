package oscillators;

import models.Oscillator;

public class GearOrder5 implements IntegrationScheme {

    private final double mass;
    private final double k;
    private final double gamma;

    private double prev_rc;
    private double prev_r1c;
    private double prev_r2c;
    private double prev_r3c;
    private double prev_r4c;
    private double prev_r5c;

    private double curr_rc = 0;
    private double curr_r1c = 0;
    private double curr_r2c = 0;
    private double curr_r3c = 0;
    private double curr_r4c = 0;
    private double curr_r5c = 0;

    public GearOrder5(double mass, double k, double gamma, Oscillator currOscillator) {
        this.mass = mass;
        this.k = k;
        this.gamma = gamma;
        this.curr_rc = currOscillator.getR();
        this.curr_r1c = currOscillator.getV();
        this.prev_rc = currOscillator.getR();
        this.prev_r1c = currOscillator.getV();
        this.prev_r2c = calculateAcceleration(currOscillator.getR(),currOscillator.getV());
        this.prev_r3c = 0;
        this.prev_r4c = 0;
        this.prev_r5c = 0;
    }

    private double calculateAcceleration(double v, double r) {
        return calculateForce(v,r) / mass;
    }

    private double calculateForce(double v, double r) {
        return -k * r - gamma * v;
    }

    private static int factorial(int n){
        if (n == 0)
            return 1;
        else
            return(n * factorial(n-1));
    }

    @Override
    public double calculatePosition(double dt) {
        double curr_rp = prev_rc
                + prev_r1c * dt
                + prev_r2c * Math.pow(dt, 2) / 2
                + prev_r3c * (Math.pow(dt, 3) / factorial(3))
                + prev_r4c * (Math.pow(dt, 4) / factorial(4))
                + prev_r5c * (Math.pow(dt, 5) / factorial(5));

        double curr_r1p = prev_r1c
                + prev_r2c * dt
                + prev_r3c * Math.pow(dt, 2) / 2
                + prev_r4c * (Math.pow(dt, 3) / factorial(3))
                + prev_r5c * (Math.pow(dt, 4) / factorial(4));

        double curr_r2p = prev_r2c
                + prev_r3c * dt
                + prev_r4c * Math.pow(dt, 2) / 2
                + prev_r5c * (Math.pow(dt, 3) / factorial(3));

        double curr_r3p = prev_r3c
                + prev_r4c * dt
                + prev_r5c * Math.pow(dt, 2) / 2;

        double curr_r4p = prev_r4c
                + prev_r5c * dt;

        double curr_r5p = prev_r5c;

        double delta_a = calculateAcceleration(curr_r1p, curr_rp) - curr_r2p;
        double delta_R2 = (delta_a * Math.pow(dt, 2)) / 2;

        curr_rc = curr_rp + (3.0/16) * delta_R2;
        curr_r1c = curr_r1p + (251.0/360) * delta_R2 / dt;
        curr_r2c = curr_r2p + 1 * delta_R2 * 2 /Math.pow(dt, 2);
        curr_r3c = curr_r3p + (11.0/18) * delta_R2 * factorial(3) / Math.pow(dt, 3);
        curr_r4c = curr_r4p + (1.0/6) * delta_R2 * factorial(4) / Math.pow(dt, 4);
        curr_r5c = curr_r5p + (1.0/60) * delta_R2 * factorial(5) / Math.pow(dt, 5);

        prev_rc = curr_rc;
        prev_r1c = curr_r1c;
        prev_r2c = curr_r2c;
        prev_r3c = curr_r3c;
        prev_r4c = curr_r4c;
        prev_r5c = curr_r5c;

        return curr_rc;
    }

    @Override
    public double calculateVelocity(double dt) {
        return curr_r1c;
    }

    @Override
    public void updateOscillator(double newPosition, double newVelocity) {
        return;
    }

    @Override
    public Oscillator getOscillator() {
        return new Oscillator(curr_rc, curr_r1c);
    }
}
