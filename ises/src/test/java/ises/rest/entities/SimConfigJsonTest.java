package ises.rest.entities;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class SimConfigJsonTest {

	/*
	 * This isn't really a test per se, but rather a convenient way to generate a JSON body
	 */
	@Disabled
	@Test
	void generateJsonBody() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		SimulationConfiguration config = new SimulationConfiguration();
		String json;
		try {
			json = mapper.writeValueAsString(config);
			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
