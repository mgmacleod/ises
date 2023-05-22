package ises;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class IsesApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsesApplication.class, args);
	}

	@Bean(name = "simulationRunExecutor")
	@Scope("prototype")
	AsyncTaskExecutor simulationRunExecutor(@Value("${simulation.executor.core.pool.size}") int poolSize,
			@Value("${simulation.executor.queue.capacity}") int queueCapacity) {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setAllowCoreThreadTimeOut(true);
		executor.setCorePoolSize(poolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix("simulation_run_thread_pool");
		executor.initialize();

		return executor;
	}

	@Bean(name = "dataStorageExecutor")
	@Scope("prototype")
	AsyncTaskExecutor dataStorageExecutor(@Value("${storage.executor.core.pool.size}") int poolSize,
			@Value("${storage.executor.queue.capacity}") int queueCapacity) {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setAllowCoreThreadTimeOut(true);
		executor.setCorePoolSize(poolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix("data_storage_thread_pool");
		executor.initialize();

		return executor;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.httpBasic(withDefaults()).csrf(csrf -> csrf.disable());
		return http.build();
	}

}
