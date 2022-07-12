package com.itiad.iziland;


import com.itiad.iziland.repositories.UtilisateurRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UtilisateurRepository.class)
public class IzilandApplication {

	public static void main(String[] args) {
		SpringApplication.run(IzilandApplication.class, args);
	}

	}

