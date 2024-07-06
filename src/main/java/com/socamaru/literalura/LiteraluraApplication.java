package com.socamaru.literalura;

import com.socamaru.literalura.repository.AuthorRepository;
import com.socamaru.literalura.repository.BookRepository;
import com.socamaru.literalura.repository.AuthorBookRepository;
import com.socamaru.literalura.view.UserConsoleInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private final AuthorRepository authorRepository;

	private final BookRepository bookRepository;

	private final AuthorBookRepository authorBookRepository;

	private final ConfigurableApplicationContext context;

	public LiteraluraApplication(
		AuthorRepository authorRepository,
		BookRepository bookRepository,
		AuthorBookRepository authorBookRepository,
		ConfigurableApplicationContext context
	){
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
		this.authorBookRepository = authorBookRepository;
		this.context = context;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String ... args) throws Exception{
		UserConsoleInterface view = new UserConsoleInterface(authorRepository, bookRepository, authorBookRepository);
		view.runApplication();
		context.close();
		System.exit(0);
	}
}
