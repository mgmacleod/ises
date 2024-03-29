package ises.system.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.jms.ConnectionFactory;

@Configuration
@EnableJms
public class IsesConfig {

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
		http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.anyRequest().authenticated())
				.httpBasic(withDefaults()).csrf(csrf -> csrf.disable());

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	JmsListenerContainerFactory<?> simFactory(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		return factory;
	}

}
