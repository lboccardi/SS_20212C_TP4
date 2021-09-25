package oscilators;

import models.Oscilator;

public class Beeman implements IntegrationScheme {

    private final double mass;
    private final double k;
    private final double gamma;
    private Oscilator prevOscillator = null;
    private Oscilator currOscillator;

    public Beeman(double mass, double k, double gamma, Oscilator currOscillator) {
        this.mass = mass;
        this.k = k;
        this.gamma = gamma;
        this.currOscillator = currOscillator;
    }

    private void generatePrevOscillator(double dt) {
        this.prevOscillator = new Oscilator(eulerPosition(-dt), eulerVelocity(-dt));
    }

    private double eulerPosition(double dt) {
        return currOscillator.getR() + currOscillator.getV() * dt +
                calculateAcceleration(currOscillator) * Math.pow(dt, 2) / 2;
    }

    private double eulerVelocity(double dt) {
        return currOscillator.getV() + calculateAcceleration(currOscillator) * dt;
    }

    private double calculateAcceleration(Oscilator oscilator) {
        return (-k * oscilator.getR() - gamma * oscilator.getV()) / mass;
    }

    private double predictVelocity(double dt) {
        double aCurr = calculateAcceleration(currOscillator);
        double aPrev = calculateAcceleration(prevOscillator);

        return currOscillator.getV() + 1.5 * aCurr * dt - 0.5 * aPrev * dt;
    }

    @Override
    public double calculatePosition(double dt) {

        if (prevOscillator == null) {
            generatePrevOscillator(dt);
        }

        double aCurr = calculateAcceleration(currOscillator);
        double aPrev = calculateAcceleration(prevOscillator);

        return currOscillator.getR()
                + currOscillator.getV() * dt
                + (1.0 / 6) * (4 * aCurr - aPrev) * Math.pow(dt, 2);
    }

    @Override
    public double calculateVelocity(double dt) {

        if (prevOscillator == null) {
            generatePrevOscillator(dt);
        }

        double aCurr = calculateAcceleration(currOscillator);
        double aPrev = calculateAcceleration(prevOscillator);

        Oscilator predictOscillator = new Oscilator(calculatePosition(dt), predictVelocity(dt));

        double aFut = calculateAcceleration(predictOscillator);

        return currOscillator.getV()
                + (1.0 / 6) * (2 * aFut + 5 * aCurr - aPrev) * dt;
    }

    @Override
    public void updateOscillator(double newPosition, double newVelocity) {
        prevOscillator = new Oscilator(currOscillator.getR(), currOscillator.getV());
        currOscillator = new Oscilator(newPosition, newVelocity);
    }

    @Override
    public Oscilator getOscillator() {
        return currOscillator;
    }
}
