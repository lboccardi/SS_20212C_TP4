import gravitational.Simulation4;
import models.Body;
import models.BodyType;
import simulation.Simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EJ2b {
    public static void main(String[] args) throws IOException {
        Body sun = new Body(0,0,0,0,1.989e30,70000000, BodyType.SUN);
        Body earth = new Body(1.500619962348151e8,2.288499248197072e6,-9.322979134387409e-1,2.966365033636722e1,5.97219e24,20000000,BodyType.EARTH);
        Body mars = new Body(-2.426617401833969e8,-3.578836154354768e7,4.435907910045917e0,-2.190044178514185e1,6.4171e23,10000000,BodyType.MARS);

//        double tf = 5;
//        double dt = tf/1000.0;
        double dt = 60*60*24;
        double tf = dt*365*2;

        double i = 0;
        double step = 0.001;
        List<Double> mins = new ArrayList<>();
        while(i<1){
            double lounchPctg = i;
            Simulation4 simulation = new Simulation4("output.txt",dt,tf,lounchPctg,sun,earth,mars);
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
            mins.add(simulation.getMinDist());
            i=i+step;
        }
        System.out.println(mins.stream().min(Double::compare));
    }



}
