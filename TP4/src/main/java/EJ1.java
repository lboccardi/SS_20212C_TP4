import models.Oscillator;
import oscillators.AnalyticSolution;
import oscillators.Beeman;
import oscillators.IntegrationScheme;
import oscillators.OriginalVerlet;
import simulation.OscilatorSimulation;
import simulation.Simulation;

import java.io.IOException;

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

        /* Change for your own Integration Scheme */
        IntegrationScheme scheme = new Beeman(mass, k, gamma, new Oscillator(r_0, v_0));
        AnalyticSolution analyticSolution =  new AnalyticSolution(mass, k, gamma, new Oscillator(r_0,v_0));
        double dt = t_f /1000;
        Simulation simulation = new OscilatorSimulation(scheme, path, dt, t_f);

        simulation.initializeSimulation();
        double time = 0;
        System.out.println("Analytic Solution for x: " + (float) analyticSolution.calculatePosition(time));
        time += dt;

        long startTime = System.currentTimeMillis();

        while (!simulation.isFinished()) {
            simulation.nextIteration();
            try {
                simulation.printIteration();
                System.out.println("Analytic Solution for x: " + (float) analyticSolution.calculatePosition(time));
                time += dt;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        simulation.terminate();

        long elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedTime / 1000;
        long secondsDisplay = elapsedSeconds % 60;
        long elapsedMinutes = elapsedSeconds / 60;

        System.out.printf("Total time: %d:%d\n", elapsedMinutes, secondsDisplay);
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
