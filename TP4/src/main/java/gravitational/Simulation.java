package gravitational;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.Circles;
import io.Event;
import io.Output;
import models.Body;
import oscillators.IntegrationScheme;

import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Simulation implements simulation.Simulation {

    private GearOrder5Gravitational schemeEarth;
    private GearOrder5Gravitational schemeSpaceship;
    private GearOrder5Gravitational schemeMars;
    private final String simulationFilename;
    private double t;
    private final double dt;
    private final double t_f;

    private Body mars;
    private Body spaceship;
    private Body earth;
    private Body sun;
    private List<Event> events;

    private FileWriter fileWriter;
    private  PrintWriter printWriter;



    public Simulation(String simulationFilename, double dt, double t_f, Body sun, Body earth, Body mars, Body spaceship) {

        this.simulationFilename = simulationFilename;
        this.t = 0;
        this.dt = dt;
        this.t_f = t_f;
        this.earth = earth;
        this.spaceship = spaceship;
        this.mars = mars;
        this.sun = sun;

    }

    @Override
    public void initializeSimulation() throws IOException {
        events = new ArrayList<>();
        schemeEarth = new GearOrder5Gravitational(earth, Arrays.asList(sun,mars));
        schemeMars = new GearOrder5Gravitational(mars, Arrays.asList(sun,earth));
        schemeSpaceship = new GearOrder5Gravitational(spaceship, Arrays.asList(sun,mars,earth));
        fileWriter = new FileWriter(simulationFilename.replace(".txt", ".xyz"), false);
        printWriter = new PrintWriter(new BufferedWriter(fileWriter));

        System.out.println(t);
        for (Body b:Arrays.asList(mars,spaceship,earth)) {
            System.out.println("    "+b.toString());
        }

        printWriter.println(4);
        printWriter.println();
        printWriter.println("Sun 0 0 " + 696340);
        printWriter.println("Earth " + earth.getR().getX() + " " + earth.getR().getY() + " " + 6371);
        printWriter.println("Mars " + mars.getR().getX() + " " + mars.getR().getY() + " " + 3389.5);
        printWriter.println("Spaceship " + spaceship.getR().getX() + " " + spaceship.getR().getY() + " " + 100);
    }

    @Override
    public void nextIteration() {

        Point2D r_earth = schemeEarth.calculatePosition(dt);
        Point2D v_earth = schemeEarth.calculateVelocity(dt);

        Point2D r_mars = schemeMars.calculatePosition(dt);
        Point2D v_mars = schemeMars.calculateVelocity(dt);

        Point2D r_spaceship = schemeSpaceship.calculatePosition(dt);
        Point2D v_spaceship = schemeSpaceship.calculateVelocity(dt);

        earth.setR(r_earth);
        earth.setV(v_earth);

        mars.setR(r_mars);
        mars.setV(v_mars);

        spaceship.setR(r_spaceship);
        spaceship.setV(v_spaceship);

        t += dt;
    }

    @Override
    public void printIteration() throws IOException {
        Event event = new Event(Arrays.asList(spaceship.getAsCircle(),earth.getAsCircle(),mars.getAsCircle(),sun.getAsCircle()),dt,t);
        events.add(event);

        printWriter.println(4);
        printWriter.println();
        printWriter.println("Sun 0 0 " + 696340 * 1000);
        printWriter.println("Earth " + earth.getR().getX() + " " + earth.getR().getY() + " " + 696340 * 1000);
        printWriter.println("Mars " + mars.getR().getX() + " " + mars.getR().getY() + " " + 696340 * 1000);
        printWriter.println("Spaceship " + spaceship.getR().getX() + " " + spaceship.getR().getY() + " " + 66340 * 1000);

        System.out.println(t);
        for (Body b:Arrays.asList(mars,spaceship,earth)) {
            System.out.println("    "+b.toString());
        }
    }

    @Override
    public boolean isFinished() {
        return t_f < t;
    }

    @Override
    public void terminate() throws IOException {
        try {
            final Output output = new Output(events);
            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(Paths.get(simulationFilename.replace(".txt", "_light.json")).toFile(), output);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        printWriter.close();
        fileWriter.close();
    }
}
