package com.example.jmsserviceexample.domain;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// @NoArgsConstructor
@Data
public class Environment {
    public static final String ENERGY_TOPIC = "env.energy";
    public static final String FOOD_TOPIC = "env.food";
    public static final String STRESS1_TOPIC = "env.stress1";
    public static final String STRESS2_TOPIC = "env.stress2";

    private final JmsTemplate jmsTemplate;

}
