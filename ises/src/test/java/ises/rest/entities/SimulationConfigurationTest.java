package ises.rest.entities;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class SimulationConfigurationTest {

	@Disabled
	@Test
	void test() {
		ObjectMapper mapper = new ObjectMapper();
		SimulationConfiguration config = new SimulationConfiguration();
		String json;
		try {
			json = mapper.writeValueAsString(config);
			System.out.println(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
