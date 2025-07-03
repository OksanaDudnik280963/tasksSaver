package com.example.tasks.saver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//@EnableJpaRepositories("com.example.tasks.saver.repositories")
@EntityScan("com.example.tasks.saver.dto")
@SpringBootApplication
public class TasksSaverApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasksSaverApplication.class, args);
	}

}
