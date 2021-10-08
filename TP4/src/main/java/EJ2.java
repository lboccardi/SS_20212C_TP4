import gravitational.Simulation3;
import models.Body;
import models.BodyType;
import simulation.OscilatorSimulation;
import simulation.Simulation;

import java.awt.geom.Point2D;
import java.io.IOException;

public class EJ2 {
    public static void main(String[] args) throws IOException {
        Body sun = new Body(0,0,0,0,1.989 * Math.pow(10,30), BodyType.SUN);
        Body earth = new Body(1.493760537643713E+08,2.367961113604404E+06,-1.165906413255524E+00,2.768338796611168E+01,(5.97219)*Math.pow(10,24),BodyType.EARTH);
        Body mars = new Body(-2.433476826538407E+08,-3.570889967814035E+07,4.202299410229134E+00,-2.388070415539739E+01,(6.4171)*Math.pow(10,23),BodyType.MARS);
        double falopa = 1;
        double dt = 24 * 60 * 60 / falopa;
        double tf = dt * 365 * falopa;

        double lounchPctg = 0.5;
        Simulation simulation = new Simulation3("output.txt",dt,tf,lounchPctg,sun,earth,mars);
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



}
