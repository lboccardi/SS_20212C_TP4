package gravitational;

import models.Body;

import java.awt.geom.Point2D;

public class BeemanGravitational {

//    private final double G = 6.674E-20;
//    private Body prevBody = null;
//    private Body currBody;
//
//    public BeemanGravitational(Body currBody) {
//        this.currBody = currBody;
//    }
//
//    private void generatePrevBody(double dt) {
//        this.prevBody = new Body(eulerPosition(-dt), eulerVelocity(-dt));
//    }
//
//    private double eulerPosition(double dt) {
//        return currBody.calculateR() + currBody.calculateR() * dt +
//                calculateAcceleration(currBody) * Math.pow(dt, 2) / 2;
//    }
//
//    private double eulerVelocity(double dt) {
//        return currBody.calculateV() + calculateAcceleration(currBody) * dt;
//    }
//
//    private Point2D calculateDistance(Body b1, Body b2) {
//        double dx = b2.getR().getX() - b1.getR().getX();
//        double dy = b2.getR().getY() - b1.getR().getY();
//        return new Point2D.Double(dx, dy);
//    }
//
//    private Point2D calculateGravitationalForce(Body b1, Body b2) {
//        Point2D distances = calculateDistance(b1, b2);
//        double force = G * (b1.getMass() * b2.getMass()) / Math.sqrt(Math.pow(distances.getX(), 2) +
//                Math.pow(distances.getY(), 2));
//        double fx = force * distances.getX();
//        double fy = force * distances.getY();
//        return new Point2D.Double(fx, fy);
//    }
//
//    private double predictVelocity(double dt) {
//        double aCurr = calculateAcceleration(currBody);
//        double aPrev = calculateAcceleration(prevBody);
//
//        return currBody.getV() + 1.5 * aCurr * dt - 0.5 * aPrev * dt;
//    }
//
//    public double calculatePosition(double dt) {
//
//        if (prevBody == null) {
//            generatePrevBody(dt);
//        }
//
//        double aCurr = calculateAcceleration(currBody);
//        double aPrev = calculateAcceleration(prevBody);
//
//        return currBody.getR()
//                + currBody.getV() * dt
//                + (1.0 / 6) * (4 * aCurr - aPrev) * Math.pow(dt, 2);
//    }
//
//    public double calculateVelocity(double dt) {
//
//        if (prevBody == null) {
//            generatePrevBody(dt);
//        }
//
//        double aCurr = calculateAcceleration(currBody);
//        double aPrev = calculateAcceleration(prevBody);
//
//        Body predictBody = new Body(calculatePosition(dt), predictVelocity(dt));
//
//        double aFut = calculateAcceleration(predictBody);
//
//        return currBody.getV()
//                + (1.0 / 6) * (2 * aFut + 5 * aCurr - aPrev) * dt;
//    }
//
//    public void updateBody(double newPosition, double newVelocity) {
//        prevBody = new Body(currBody.getR().getX(),currBody.getR().getY(), currBody.getV().getX(),currBody.getV().getY(),currBody.getMass(),currBody.getType());
//        currBody = new Body(newPosition, newVelocity);
//    }
//
//    public Body getBody() {
//        return currBody;
//    }
}
