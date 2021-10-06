package oscillators;

import models.Oscillator;

public class GearOrder5 implements IntegrationScheme {

    private final double mass;
    private final double k;
    private final double gamma;

    private double[] previous;
    private double[] currents;
    private final double [] coefficients = new double[] { 3.0/16, 251.0/360, 1, 11.0/18, 1.0/6, 1.0/60};

    public GearOrder5(double mass, double k, double gamma, Oscillator currOscillator) {
        this.mass = mass;
        this.k = k;
        this.gamma = gamma;
        currents = new double[] {currOscillator.getR(), currOscillator.getV(),0,0,0,0};
        previous = new double[] {currents[0],currents[1], calculateAcceleration(currOscillator.getR(),currOscillator.getV())
                ,0,0,0};
    }

    private double calculateAcceleration(double r, double v) {
        return calculateForce(r,v) / mass;
    }

    private double calculateForce(double r, double v) {
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
        double[] currRps = new double[6];

        for (int i = 0 ; i < 6; i++) {
            currRps[0] += previous[i] * Math.pow(dt, i) / factorial(i);
        }

        for (int i = 0 ; i < 5; i++) {
            currRps[1] += previous[i + 1] * Math.pow(dt, i) / factorial(i);
        }

        for (int i = 0 ; i < 4; i++) {
            currRps[2] += previous[i + 2] * Math.pow(dt, i) / factorial(i);
        }

        for (int i = 0 ; i < 3; i++) {
            currRps[3] += previous[i + 3] * Math.pow(dt, i) / factorial(i);
        }

        for (int i = 0 ; i < 2; i++) {
            currRps[4] += previous[i + 4] * Math.pow(dt, i) / factorial(i);
        }

        currRps[5] = previous[5];

        double deltaA = calculateAcceleration(currRps[0], currRps[1]) - currRps[2];
        double deltaR2 = (deltaA * Math.pow(dt, 2)) / 2;

        for (int i = 0 ; i < currents.length ; i++) {
            currents[i] = currRps[i] + coefficients[i] * deltaR2 * factorial(i) / Math.pow(dt, i);
            previous[i] = currents[i];
        }

        return currents[0];
    }

    @Override
    public double calculateVelocity(double dt) {
        return currents[1];
    }

    @Override
    public void updateOscillator(double newPosition, double newVelocity) {
        return;
    }

    @Override
    public Oscillator getOscillator() {
        return new Oscillator(currents[0], currents[1]);
    }

    @Override
    public double getMass() {
        return mass;
    }

    @Override
    public double getK() {
        return k;
    }

    @Override
    public double getGamma() {
        return gamma;
    }
}
