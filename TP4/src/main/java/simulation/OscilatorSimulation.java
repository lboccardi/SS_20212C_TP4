package simulation;

import oscilators.IntegrationScheme;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OscilatorSimulation implements Simulation {

    private IntegrationScheme scheme;
    private String simulationFilename;

    private FileWriter fileWriter;
    private  PrintWriter printWriter;

    public OscilatorSimulation(IntegrationScheme scheme, String simulationFilename) {
        this.scheme = scheme;
        this.simulationFilename = simulationFilename;
    }

    @Override
    public void initializeSimulation() throws IOException {
        fileWriter = new FileWriter(simulationFilename, true);
        printWriter = new PrintWriter(new BufferedWriter(fileWriter));
    }

    @Override
    public void nextIteration() {
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


//        printWriter.println(time);
//
//        for (Particle p : prevParticles) {
//            printWriter.println(p.toString());
//        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void terminate() throws IOException {
        printWriter.close();
        fileWriter.close();
    }
}
