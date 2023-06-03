package com.example.jmsserviceexample.domain;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Data
@Slf4j
public class Cell {
    private static final String ENERGY_TOPIC = "cell.energy";
    private static final String FOOD_TOPIC = "cell.energy";

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = Environment.FOOD_TOPIC)
    public void receiveFoodFromEnvironment(String message) {
        log.info("Receiving food from env: {}", message);
        jmsTemplate.convertAndSend(FOOD_TOPIC, "receive food: " + message);
    }

    @JmsListener(destination = ENERGY_TOPIC)
    public void expendEnergyIntoEnvironment(String message) {
        log.info("Transmitting energy to env: {}", message);
        jmsTemplate.convertAndSend(Environment.ENERGY_TOPIC, "transmit energy: " + message);
    }

    @JmsListener(destination = FOOD_TOPIC)
    public void convertFoodIntoEnergy(String message) {

        log.info("Converting food to energy in cell");
    }
    
}
