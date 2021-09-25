package simulation;

import oscillators.IntegrationScheme;

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
//    private double prev_r;

    private FileWriter fileWriter;
    private  PrintWriter printWriter;

    public OscilatorSimulation(IntegrationScheme scheme, String simulationFilename, double dt,double t_f) {
        this.scheme = scheme;
        this.simulationFilename = simulationFilename;
        this.t = 0;
        this.dt = dt;
        this.t_f = t_f;
//        this.prev_r = -1;
    }

    @Override
    public void initializeSimulation() throws IOException {
        fileWriter = new FileWriter(simulationFilename, false);
        printWriter = new PrintWriter(new BufferedWriter(fileWriter));
        System.out.printf("%.2f - x: %.5f - v: %.5f\n",t, scheme.getOscillator().getR(),scheme.getOscillator().getV());
        printWriter.println(t);
        printWriter.println(scheme.getOscillator());
    }

    @Override
    public void nextIteration() {
//        double r = scheme.calculatePosition(oscilator,prev_r,dt);
//        double v = scheme.calculateVelocity(oscilator,prev_r,dt);
        double r = scheme.calculatePosition(dt);
        double v = scheme.calculateVelocity(dt);
//        prev_r = oscilator.getR();
//        oscilator.setR(r);
//        oscilator.setV(v);
        scheme.updateOscillator(r, v);
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


        printWriter.println((float) t);

        printWriter.println(scheme.getOscillator());

        System.out.printf("%.2f - x: %.5f - v: %.5f\n",t, scheme.getOscillator().getR(), scheme.getOscillator().getV());
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
