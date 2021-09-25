package oscillators;

import models.Oscillator;

public interface IntegrationScheme {
    double calculatePosition(double dt);
    double calculateVelocity(double dt);
    void updateOscillator(double newPosition, double newVelocity);
    Oscillator getOscillator();
//    public double calculatePosition(Oscilator oscilator,double prev_r,double dt);
//    public double calculateVelocity(Oscilator oscilator,double prev_r, double dt);

}
