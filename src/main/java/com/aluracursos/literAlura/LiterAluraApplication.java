package com.aluracursos.literAlura;

import com.aluracursos.literAlura.principal.Principal;
import com.aluracursos.literAlura.repository.IAutorRepository;
import com.aluracursos.literAlura.repository.ILibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication  implements CommandLineRunner {

	@Autowired
	private ILibroRepository repositoryLibro;
	@Autowired
	private IAutorRepository repositoryAutor;
	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositoryLibro, repositoryAutor);
		principal.inicio();
	}
}
