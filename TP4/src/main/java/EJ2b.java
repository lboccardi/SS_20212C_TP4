import models.Body;
import models.BodyType;
import simulation.Simulation;

import java.io.IOException;

public class EJ2b {
    public static void main(String[] args) throws IOException {
        Body sun = new Body(0,0,0,0,(1989)*Math.pow(10,30),696340, BodyType.SUN);
        Body earth = new Body(1.493760537643713E+08,2.367961113604404E+06,-1.165906413255524E+00,2.768338796611168E+01,(5.97219)*Math.pow(10,24),6371.01,BodyType.EARTH);
        Body mars = new Body(-2.433476826538407E+08,-3.570889967814035E+07,4.202299410229134E+00,-2.388070415539739E+01,(6.4171)*Math.pow(10,23),3389.5,BodyType.MARS);
//        double tf = 5;
//        double dt = tf/1000.0;
        double dt = 60*60*24;
        double tf = dt*365;

        double i = 0;
        double step = 0.001;
        while(i<1){
            double lounchPctg = i;
            Simulation simulation = new gravitational.Simulation2("output.txt",dt,tf,lounchPctg,sun,earth,mars);
            simulation.initializeSimulation();

            while (!simulation.isFinished()) {
                simulation.nextIteration();
                try {
                    simulation.printIteration();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            simulation.terminate();
            i=i+step;
        }

    }



}
