package com.example.jmsserviceexample.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnvironmentState {
    private int id;
    private int energy;
    private int food;
}
