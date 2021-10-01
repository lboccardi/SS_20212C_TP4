package gravitational;

import models.Body;

public class BeemanGravitational {

    private final double G = 6.674E-11;
    private Body prevBody = null;
    private Body currBody;

    public BeemanGravitational(Body currBody) {
        this.currBody = currBody;
    }

    private void generatePrevBody(double dt) {
        this.prevBody = new Body(eulerPosition(-dt), eulerVelocity(-dt));
    }

    private double eulerPosition(double dt) {
        return currBody.getR() + currBody.getV() * dt +
                calculateAcceleration(currBody) * Math.pow(dt, 2) / 2;
    }

    private double eulerVelocity(double dt) {
        return currBody.getV() + calculateAcceleration(currBody) * dt;
    }

    private double calculateAcceleration(Body body) {
        return (-k * body.getR() - gamma * body.getV()) / mass;
    }

    private double predictVelocity(double dt) {
        double aCurr = calculateAcceleration(currBody);
        double aPrev = calculateAcceleration(prevBody);

        return currBody.getV() + 1.5 * aCurr * dt - 0.5 * aPrev * dt;
    }

    public double calculatePosition(double dt) {

        if (prevBody == null) {
            generatePrevBody(dt);
        }

        double aCurr = calculateAcceleration(currBody);
        double aPrev = calculateAcceleration(prevBody);

        return currBody.getR()
                + currBody.getV() * dt
                + (1.0 / 6) * (4 * aCurr - aPrev) * Math.pow(dt, 2);
    }

    public double calculateVelocity(double dt) {

        if (prevBody == null) {
            generatePrevBody(dt);
        }

        double aCurr = calculateAcceleration(currBody);
        double aPrev = calculateAcceleration(prevBody);

        Body predictBody = new Body(calculatePosition(dt), predictVelocity(dt));

        double aFut = calculateAcceleration(predictBody);

        return currBody.getV()
                + (1.0 / 6) * (2 * aFut + 5 * aCurr - aPrev) * dt;
    }

    public void updateBody(double newPosition, double newVelocity) {
        prevBody = new Body(currBody.getR(), currBody.getV());
        currBody = new Body(newPosition, newVelocity);
    }

    public Body getBody() {
        return currBody;
    }
}
