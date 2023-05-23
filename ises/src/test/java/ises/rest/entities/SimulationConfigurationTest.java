package ises.rest.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class SimulationConfigurationTest {

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();

	@Test
	void testValidation_shouldPass() {
		SimulationConfiguration config = new SimulationConfiguration();
		Set<ConstraintViolation<SimulationConfiguration>> violations = validator.validate(config);
		assertTrue(violations.isEmpty(), "Violations should be empty");
	}

	@Test
	void testValidation_shouldFailForNullValue() {
		SimulationConfiguration config = new SimulationConfiguration();
		config.setShapeMax(null);
		Set<ConstraintViolation<SimulationConfiguration>> violations = validator.validate(config);
		assertEquals(1, violations.size(), "Violations size mismatch");
		violations.forEach(v -> {
			assertEquals("shapeMax", v.getPropertyPath().toString(), "Property path mismatch");
			assertEquals("must not be null", v.getMessage(), "Message mismatch");
		});
	}

	@Test
	void testValidation_shouldFailForNegativeValue() {
		SimulationConfiguration config = new SimulationConfiguration();
		config.setDistanceMax(-1);
		Set<ConstraintViolation<SimulationConfiguration>> violations = validator.validate(config);
		assertEquals(1, violations.size(), "Violations size mismatch");
		violations.forEach(v -> {
			assertEquals("distanceMax", v.getPropertyPath().toString(), "Property path mismatch");
			assertEquals("must be greater than 0", v.getMessage(), "Message mismatch");
		});
	}

}
