import models.Body;
import models.BodyType;
import simulation.OscilatorSimulation;
import simulation.Simulation;

import java.awt.geom.Point2D;
import java.io.IOException;

public class EJ2 {
    public static void main(String[] args) throws IOException {
        Body sun = new Body(0,0,0,0,(1989)*Math.pow(10,30), BodyType.SUN);
        Body earth = new Body(1.493760537643713E+08 * 1000,2.367961113604404E+06 * 1000,-1.165906413255524E+00 * 1000,2.768338796611168E+01 * 1000,(5.97219)*Math.pow(10,24),BodyType.EARTH);
        Body mars = new Body(-2.433476826538407E+08 * 1000,-3.570889967814035E+07 * 1000,4.202299410229134E+00 * 1000,-2.388070415539739E+01 * 1000,(6.4171)*Math.pow(10,23),BodyType.MARS);
        Body spaceship = initializeSpaceship(earth,sun);
//        double tf = 5;
//        double dt = tf/1000.0;
        double dt = 60*60*24;
        double tf = dt*365;
        Simulation simulation = new gravitational.Simulation("output.txt",dt,tf,sun,earth,mars,spaceship);
        simulation.initializeSimulation();

//        simulation.nextIteration();
//        simulation.printIteration();

        while (!simulation.isFinished()) {
            simulation.nextIteration();
            try {
                simulation.printIteration();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        simulation.terminate();
    }

    private static Body initializeSpaceship(Body earth,Body sun) {

        double earthRadio = 6371.01;
        double spaceshipHigh = 1500;
        double spaceshipR = earth.calculateR()+ earthRadio + spaceshipHigh;

        Point2D earthSpaceshipNVector = sun.calculateNormalR(earth);

        Body spaceship = new Body(earthSpaceshipNVector.getX()*spaceshipR,earthSpaceshipNVector.getY()*spaceshipR,0,0,2*Math.pow(10,5),BodyType.SPACESHIP);

        double spaceshipVelocity = 8;
        double stationVelocity = 12;
        Point2D velocityTVector = earth.calculateTVector(spaceship);

        double spaceshipV = earth.calculateV()+ spaceshipVelocity + stationVelocity;

        spaceship.setV(new Point2D.Double(velocityTVector.getX()*spaceshipV,velocityTVector.getY()*spaceshipV));

        return spaceship;
    }

}
