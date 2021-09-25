package oscilators;

import models.Oscilator;

//public class OriginalVerlet implements IntegrationScheme {
//    private final double mass;
//    private final double k;
//    private final double gamma;
//
//    public OriginalVerlet(double mass, double k, double gamma) {
//        this.mass = mass;
//        this.k = k;
//        this.gamma = gamma;
//    }
//
//    @Override
//    public double calculatePosition(Oscilator oscilator, double prev_r,double dt) {
//        double f = calculateForce(oscilator);
//        double r_prev = prev_r ==-1? eulerMethod(oscilator,-dt,f) : prev_r;
//        return 2 * oscilator.getR() - r_prev + Math.pow(dt,2) * f / mass;
//    }
//
//    private double eulerMethod(Oscilator oscilator, double dt,double f) {
//        return oscilator.getR() + dt * oscilator.getV() + Math.pow(dt,2) * f /(2*mass);
//    }
//
//    private double calculateForce(Oscilator oscilator) {
//        return -k * oscilator.getR() - gamma * oscilator.getV();
//    }
//
//    @Override
//    public double calculateVelocity(Oscilator oscilator,double prev_r, double dt) {
//        double f = calculateForce(oscilator);
//        prev_r = prev_r ==-1? eulerMethod(oscilator,-dt,f):prev_r;
//        return (calculatePosition(oscilator, prev_r, dt)-prev_r)/(2*dt);
//    }
//}
