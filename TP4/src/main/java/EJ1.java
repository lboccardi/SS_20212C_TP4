import models.Oscillator;
import oscillators.*;
import simulation.AnalyticSimulation;
import simulation.OscilatorSimulation;
import simulation.Simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EJ1 {

    private static double mass;
    private static double k;
    private static double gamma;
    private static double t_f;
    private static double r_0;
    private static double v_0;
    private static String path;

    public static void main(String[] args) throws IOException {

        if (args.length != 7) {
            System.err.println("Wrong amount of arguments");
            return;
        }

        parseArguments(args);

        IntegrationScheme gearScheme = new GearOrder5(mass, k, gamma, new Oscillator(r_0, v_0));
        IntegrationScheme beemanScheme = new Beeman(mass, k, gamma, new Oscillator(r_0, v_0));
        IntegrationScheme verletScheme = new OriginalVerlet(mass, k, gamma, new Oscillator(r_0, v_0));
        AnalyticSolution analyticSolution =  new AnalyticSolution(mass, k, gamma, new Oscillator(r_0,v_0));

        List<Simulation> simulations = new ArrayList<>();
        double dt = t_f /1000;
        simulations.add( new OscilatorSimulation(gearScheme, path.replace(".txt", "_gear.txt"), dt, t_f ));
        simulations.add( new OscilatorSimulation(beemanScheme, path.replace(".txt", "_beeman.txt"), dt, t_f ));
        simulations.add( new OscilatorSimulation(verletScheme, path.replace(".txt", "_verlet.txt"), dt, t_f ));
        simulations.add( new AnalyticSimulation(analyticSolution, path.replace(".txt", "_analytic.txt"), dt, t_f ));

        /* Change for your own Integration Scheme */

        for (Simulation s : simulations) {
            s.initializeSimulation();

            long startTime = System.currentTimeMillis();

            while (!s.isFinished()) {
                s.nextIteration();
                try {
                    s.printIteration();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            s.terminate();

            long elapsedTime = System.currentTimeMillis() - startTime;
            long elapsedSeconds = elapsedTime / 1000;
            long secondsDisplay = elapsedSeconds % 60;
            long elapsedMinutes = elapsedSeconds / 60;

            System.out.printf("Total time: %d:%d\n", elapsedMinutes, secondsDisplay);
        }

    }

    public static void parseArguments(String[] args) {
        mass = Double.parseDouble(args[0]);
        k = Double.parseDouble(args[1]);
        gamma = Double.parseDouble(args[2]);
        t_f = Double.parseDouble(args[3]);
        r_0 = Double.parseDouble(args[4]);
        double A = Double.parseDouble(args[5]);
        v_0 = - A * gamma / (2 * mass);
        path = args[6];
    }
}
