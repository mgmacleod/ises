package ises.system;

import ises.rest.entities.SimulationConfiguration;

public interface Evolver extends Runnable {

    void initializeForRun(SimulationConfiguration config);

    void cancelSimulationWithId(Long idToCancel);

    void start();

    void pause();

    void stop();

    boolean isRunning();

    void setRunning(boolean running);

}