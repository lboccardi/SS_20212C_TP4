import models.Body;

import java.awt.geom.Point2D;

public class EJ2 {
    public static void main(String[] args) {
        Body sun = new Body(0,0,0,0,(1989)*Math.pow(10,30));
        Body earth = new Body(1.493760537643713E+08,2.367961113604404E+06,-1.165906413255524E+00,2.768338796611168E+01,(5.97219)*Math.pow(10,24));
        Body mars = new Body(-2.433476826538407E+08,-3.570889967814035E+07,4.202299410229134E+00,-2.388070415539739E+01,(6.4171)*Math.pow(10,23));
        double earthRadio = 6371.01;
        double spaceshipPosition = 1500;
        double spaceshipVelocity = 8;
        double stationVelocity = 12;
        // TOMAMOS S - E - NAVE como eje X en tiempo 0
        Body spaceship = new Body(earth.getR().getX(),earth.getR().getX() + earthRadio + spaceshipPosition,0,earth.getV().getY() + stationVelocity + spaceshipVelocity,2*Math.pow(10,5));
        
        
    }
}
