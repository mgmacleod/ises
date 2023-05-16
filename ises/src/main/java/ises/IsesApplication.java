package ises;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class IsesApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsesApplication.class, args);
	}

	@Bean(name = "simulationRunExecutor")
	@Scope("prototype")
	AsyncTaskExecutor callbackRequestExecutor(@Value("${simulation.executor.core.pool.size}") int poolSize,
			@Value("${simulation.executor.queue.capacity}") int queueCapacity) {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setAllowCoreThreadTimeOut(true);
		executor.setCorePoolSize(poolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix("callback_request_thread_pool");
		executor.initialize();

		return executor;
	}

}
