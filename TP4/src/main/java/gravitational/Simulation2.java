package gravitational;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.Event;
import io.Output;
import models.Body;
import models.BodyType;

import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Simulation2 implements simulation.Simulation {

    private GearOrder5Gravitational schemeEarth;
    private GearOrder5Gravitational schemeSpaceship;
    private GearOrder5Gravitational schemeMars;
    private final String simulationFilename;
    private double t;
    private final double dt;
    private final double t_f;
    private final double lounchPctg;

    private Body sun;
    private Body earth;
    private Body mars;
    private Body spaceship;
    private List<Event> events;

    private FileWriter fileWriter;
    private  PrintWriter printWriter;

    private boolean spaceShipInitialized = false;

    private boolean spaceshipReachedMars;
    private double spaceshipMarsMinDist;
    private double spaceshipMarsMinDistCurrDist;
    private double marsRadious = 3396;

    public Simulation2(String simulationFilename, double dt, double t_f, double lounchPctg, Body sun, Body earth, Body mars) {
        this.simulationFilename = simulationFilename;
        this.t = 0;
        this.dt = dt;
        this.t_f = t_f;
        this.lounchPctg = lounchPctg;
        this.sun = sun;
        this.earth = earth;
        this.mars = mars;
        this.spaceship = null;

        spaceshipReachedMars = false;
        spaceshipMarsMinDist = calculateDistance(mars, earth);
        spaceshipMarsMinDistCurrDist = spaceshipMarsMinDist;
    }

    private double calculateDistance(Body b1, Body b2) {
        double dx = Math.abs(b2.getR().getX() - b1.getR().getX());
        double dy = Math.abs(b2.getR().getY() - b1.getR().getY());
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    @Override
    public void initializeSimulation() throws IOException {
        events = new ArrayList<>();
        schemeEarth = new GearOrder5Gravitational(earth, Arrays.asList(sun,mars));
        schemeMars = new GearOrder5Gravitational(mars, Arrays.asList(sun,earth));
        //schemeSpaceship = new GearOrder5Gravitational(spaceship, Arrays.asList(sun,mars,earth));
        fileWriter = new FileWriter(simulationFilename.replace(".txt", ".xyz"), false);
        printWriter = new PrintWriter(new BufferedWriter(fileWriter));

//        System.out.println(t);
//        for (Body b:Arrays.asList(mars,spaceship,earth)) {
//            System.out.println("    "+b.toString());
//        }

        printWriter.println(4);
        printWriter.println();
        printWriter.println("Sun 0 0 " + 696340);
        printWriter.println("Earth " + earth.getR().getX() + " " + earth.getR().getY() + " " + 6371);
        printWriter.println("Mars " + mars.getR().getX() + " " + mars.getR().getY() + " " + 3389.5);
        if(spaceship != null) {
            printWriter.println("Spaceship " + spaceship.getR().getX() + " " + spaceship.getR().getY() + " " + 100);
        }
    }

    @Override
    public void nextIteration() {
        if(t/t_f > lounchPctg && !spaceShipInitialized){
            spaceShipInitialized = true;
            spaceship = initializeSpaceship(earth,sun);
            schemeSpaceship = new GearOrder5Gravitational(spaceship, Arrays.asList(sun,mars,earth));
            spaceshipMarsMinDistCurrDist = calculateDistance(mars, spaceship);
            if(spaceshipMarsMinDistCurrDist < spaceshipMarsMinDist){
                spaceshipMarsMinDist = spaceshipMarsMinDistCurrDist;
                if(spaceshipMarsMinDistCurrDist < marsRadious){
                    spaceshipReachedMars = true;
                }
            }
        }

        Point2D r_earth = schemeEarth.calculatePosition(dt);
        Point2D v_earth = schemeEarth.calculateVelocity(dt);
        earth.setR(r_earth);
        earth.setV(v_earth);

        Point2D r_mars = schemeMars.calculatePosition(dt);
        Point2D v_mars = schemeMars.calculateVelocity(dt);
        mars.setR(r_mars);
        mars.setV(v_mars);

        if(spaceship != null) {
            Point2D r_spaceship = schemeSpaceship.calculatePosition(dt);
            Point2D v_spaceship = schemeSpaceship.calculateVelocity(dt);
            spaceship.setR(r_spaceship);
            spaceship.setV(v_spaceship);
        }

        t += dt;
    }

    private static Body initializeSpaceship(Body earth,Body sun) {

        double earthRadio = 6371.01;
        double spaceshipHigh = 1500;
        double spaceshipR = earth.calculateR()+ earthRadio + spaceshipHigh;

        Point2D earthSpaceshipNVector = sun.calculateNormalR(earth);

        Body spaceship = new Body(earthSpaceshipNVector.getX()*spaceshipR,earthSpaceshipNVector.getY()*spaceshipR,0,0,2*Math.pow(10,5), BodyType.SPACESHIP);

        double spaceshipVelocity = 8;
        double stationVelocity = 12;
        Point2D velocityTVector = earth.calculateTVector(spaceship);

        double spaceshipV = earth.calculateV()+ spaceshipVelocity + stationVelocity;

        spaceship.setV(new Point2D.Double(velocityTVector.getX()*spaceshipV,velocityTVector.getY()*spaceshipV));

        return spaceship;
    }

    @Override
    public void printIteration() throws IOException {
        return;
    }

    @Override
    public boolean isFinished() {
        return t_f < t;
    }

    @Override
    public void terminate() throws IOException {
        System.out.println("reached: " + spaceshipReachedMars + " - min dist: " + spaceshipMarsMinDist + " - lounchPCTG: " + lounchPctg);
    }
}
