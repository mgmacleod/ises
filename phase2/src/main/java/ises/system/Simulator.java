package ises.system;

import ises.model.cellular.Model;
import ises.rest.entities.SimulationConfiguration;

public interface Simulator {

    void initializeForRun(SimulationConfiguration config);

    void start();

    void flipFoodProbs();

    void setCurrModel(Model currModel);

    boolean isRunning();

}