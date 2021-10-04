import models.Body;
import models.BodyType;

import java.awt.geom.Point2D;

public class EJ2 {
    public static void main(String[] args) {
        Body sun = new Body(0,0,0,0,(1989)*Math.pow(10,30), BodyType.SUN);
        Body earth = new Body(1.493760537643713E+08,2.367961113604404E+06,-1.165906413255524E+00,2.768338796611168E+01,(5.97219)*Math.pow(10,24),BodyType.EARTH);
        Body mars = new Body(-2.433476826538407E+08,-3.570889967814035E+07,4.202299410229134E+00,-2.388070415539739E+01,(6.4171)*Math.pow(10,23),BodyType.MARS);


        Body spaceship = initializeSpaceship(earth,sun);

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
