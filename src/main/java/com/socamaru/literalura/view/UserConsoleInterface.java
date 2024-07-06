package com.socamaru.literalura.view;

import com.socamaru.literalura.repository.AuthorRepository;
import com.socamaru.literalura.repository.BookRepository;
import com.socamaru.literalura.repository.AuthorBookRepository;
import com.socamaru.literalura.repository.models.*;
import com.socamaru.literalura.services.GutendexApiService;

import java.util.*;

public class UserConsoleInterface {

	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;
	private final AuthorBookRepository authorBookRepository;
	private final Scanner consoleInputReader = new Scanner(System.in);

	public UserConsoleInterface(
		AuthorRepository authorRepository,
		BookRepository bookRepository,
		AuthorBookRepository authorBookRepository
	) {
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
		this.authorBookRepository = authorBookRepository;
	}

	public void runApplication() {
		boolean userWantsToExit = false;
		while (!userWantsToExit) {
			try{
				int numberOptions = showMenu();
				int userOption = readOption(numberOptions);
				switch (userOption) {
					case 1:
						registerBookOption();
						break;
					default:
						userWantsToExit = true;
						System.out.println("Bye, Thank you so much");
				}
			}catch (Exception e){
				System.out.println(e.getMessage());
			}
		}

	}

	private int showMenu() {
		List<String> menuOptions = new LinkedList<>();
		menuOptions.add("1. Buscar y registrar libro");
		menuOptions.add("2. Salir");
		menuOptions.forEach(System.out::println);
		return menuOptions.size();
	}

	private int readOption(int maxNumberOptions) {
		try {
			System.out.print("Elije una opción: ");
			int userOption = consoleInputReader.nextInt();
			consoleInputReader.nextLine(); // consume el salto de linea
			boolean isOptionInValidRange = userOption >= 1 && userOption <= maxNumberOptions;
			if (!isOptionInValidRange) {
				throw new RuntimeException(String.format("option: %d doesn't exists", userOption));
			}
			return userOption;
		} catch (InputMismatchException e) {
			consoleInputReader.nextLine();
			throw new RuntimeException("Input doesn't valid number");
		} catch (Exception e) {
			consoleInputReader.nextLine();
			throw new RuntimeException("Unknown error: " + e.getMessage(), e);
		}
	}

	private void registerBookOption() {
		System.out.print("Ingresa el nombre del libro a buscar: ");
		String bookToFind = consoleInputReader.nextLine();
		Optional<BookDTO> optionalBookDTO = findBookInGutenberg(bookToFind);
		if (optionalBookDTO.isEmpty()) {
			System.out.println("Book not found");
			return;
		}
		BookDTO bookDTO = optionalBookDTO.get();
		Optional<Book> optionalPersistBook = bookRepository.findByGutenbergId(bookDTO.id());
		if (optionalPersistBook.isPresent()) {
			System.out.printf("Book: '%s' already exists in your database.%n", bookDTO.title());
			return;
		}
		Book persistBook = saveBook(bookDTO);
		List<Author> persistAuthors = getAndSaveAuthors(bookDTO.authors());
		linkBookAndAuthors(persistBook, persistAuthors);
		printBook(persistBook, persistAuthors);
	}

	private Optional<BookDTO> findBookInGutenberg(String bookToFind) {
		GutendexApiService gutendex = new GutendexApiService();
		return gutendex.searchByAuthorOrTitle(bookToFind);
	}

	private Book saveBook(BookDTO bookDTO) {
		Book newBook = new Book(bookDTO.id(), bookDTO.title(), bookDTO.languages());
		return bookRepository.save(newBook);
	}

	private List<Author> getAndSaveAuthors(List<AuthorDTO> authorsData) {
		return authorsData.stream()
			.map(authorDTO -> {
				Optional<Author> author = authorRepository.findByNameIgnoreCase(authorDTO.name());
				if (author.isEmpty()) {
					Author newAuthor = new Author(
						authorDTO.name(),
						authorDTO.birthYear(),
						authorDTO.deathYear()
					);
					return authorRepository.save(newAuthor);
				}
				return author.get();
			})
			.toList();
	}

	private void linkBookAndAuthors(Book persistBook, List<Author> persistAuthors) {
		for (Author persistAuthor : persistAuthors) {
			if (!authorBookRepository.existsByAuthorAndBook(persistAuthor, persistBook)) {
				AuthorBook authorBook = new AuthorBook(persistAuthor, persistBook);
				authorBookRepository.save(authorBook);
			}
		}
	}

	private void printBook(Book persistBook, List<Author> persistAuthors) {
		System.out.println("-------------");
		System.out.println("Title: " + persistBook.getTitle());
		System.out.println("gutenbergID: " + persistBook.getGutenbergId());
		System.out.println("languages: " + persistBook.getLanguages());
		System.out.print("authors: ");
		for (int i = 0 ; i < persistAuthors.size() ; i++) {
			Author author = persistAuthors.get(i);
			System.out.print(author.getName());
			if(i < persistAuthors.size() - 1) {
				System.out.print(" - ");
			}else{
				System.out.println(".");
			}
		}
		System.out.println("-------------");
	}
}
