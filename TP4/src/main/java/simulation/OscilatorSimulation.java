package simulation;

import models.Oscilator;
import oscilators.IntegrationScheme;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OscilatorSimulation implements Simulation {

    private final IntegrationScheme scheme;
    private final String simulationFilename;
    private double t;
    private final double dt;
    private final double t_f;
    private double prev_r;
    private final Oscilator oscilator;

    private FileWriter fileWriter;
    private  PrintWriter printWriter;

    public OscilatorSimulation(IntegrationScheme scheme, String simulationFilename, double dt,double t_f, Oscilator oscilator) {
        this.scheme = scheme;
        this.simulationFilename = simulationFilename;
        this.t = 0;
        this.dt = dt;
        this.t_f = t_f;
        this.oscilator = oscilator;
        this.prev_r = -1;
    }

    @Override
    public void initializeSimulation() throws IOException {
        fileWriter = new FileWriter(simulationFilename, true);
        printWriter = new PrintWriter(new BufferedWriter(fileWriter));
        System.out.printf("%.2f - x: %.5f - v: %.5f\n",t,oscilator.getR(),oscilator.getV());

    }

    @Override
    public void nextIteration() {
        double r = scheme.calculatePosition(oscilator,prev_r,dt);
        double v = scheme.calculateVelocity(oscilator,prev_r,dt);
        prev_r = oscilator.getR();
        oscilator.setR(r);
        oscilator.setV(v);
        t += dt;
    }

    @Override
    public void printIteration() throws IOException {
        // Poner acá los headers, si los necesitamos :D
//        if (iteration == 1) {
//            String header = "N: " + N + ' ' +
//                    "Lx: " + Lx + ' ' +
//                    "Ly: " + Ly + ' ' +
//                    "gap: " + gap + ' ' +
//                    "rmin: " + rmin + ' ' +
//                    "rmax: " + rmax + ' ' +
//                    "v: " + v + ' ';
//            printWriter.println(header);
//        }


        printWriter.println(t);

        String particle= "x "+oscilator.getR()+" v "+oscilator.getV();
        printWriter.println(particle);

        System.out.printf("%.2f - x: %.5f - v: %.5f\n",t,oscilator.getR(),oscilator.getV());
    }

    @Override
    public boolean isFinished() {
        return t_f < t;
    }

    @Override
    public void terminate() throws IOException {
        printWriter.close();
        fileWriter.close();
    }
}
