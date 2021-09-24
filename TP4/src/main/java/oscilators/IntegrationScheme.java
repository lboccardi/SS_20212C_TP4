package oscilators;

import models.Oscilator;

public interface IntegrationScheme {
    public double calculatePosition(Oscilator oscilator,double prev_r,double dt);
    public double calculateVelocity(Oscilator oscilator,double prev_r, double dt);
}
