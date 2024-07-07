package com.socamaru.literalura.view;

import com.socamaru.literalura.helpers.BasicHelper;
import com.socamaru.literalura.repository.AuthorRepository;
import com.socamaru.literalura.repository.BookRepository;
import com.socamaru.literalura.repository.IdiomRepository;
import com.socamaru.literalura.repository.models.*;
import com.socamaru.literalura.services.GutendexApiService;

import java.util.*;

public class UserConsoleInterface {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final IdiomRepository idiomRepository;
    private final Scanner consoleInputReader = new Scanner(System.in);

    public UserConsoleInterface(
        AuthorRepository authorRepository,
        BookRepository bookRepository,
        IdiomRepository idiomRepository
    ) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.idiomRepository = idiomRepository;
    }

    public void runApplication() {
        boolean userWantsToExit = false;
        while (!userWantsToExit) {
            try {
                int numberOptions = showMenu();
                int userOption = readOption(numberOptions);
                switch (userOption) {
                    case 1:
                        registerBookOption();
                        break;
                    case 2:
                        showAuthorsOption();
                        break;
                    default:
                        userWantsToExit = true;
                        System.out.println("Bye, Thank you so much");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private int showMenu() {
        List<String> menuOptions = new LinkedList<>();
        menuOptions.add("1. Find and register book");
        menuOptions.add("2. To list authors");
        menuOptions.add("3. Exit");
        menuOptions.forEach(System.out::println);
        return menuOptions.size();
    }

    private int readOption(int maxNumberOptions) {
        try {
            System.out.print("Enter an option: ");
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
        System.out.print("Enter the name of the book: ");
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
        printBook(persistBook);
    }

    private void showAuthorsOption(){
        List<Author> persistAuthors = authorRepository.findAll();
        for (int i = 0; i < persistAuthors.size(); i++) {
            Author author = persistAuthors.get(i);
            System.out.printf("Author N° %d:%n", i + 1);
            printAuthor(author);
        }
        System.out.printf("count %d authors.%n", persistAuthors.size());
    }

    private Optional<BookDTO> findBookInGutenberg(String bookToFind) {
        GutendexApiService gutendex = new GutendexApiService();
        return gutendex.searchByAuthorOrTitle(bookToFind);
    }

    private Book saveBook(BookDTO bookDTO) {
        List<Author> persistAuthors = getAndSaveAuthors(bookDTO.authors());
        List<Idiom> persistIdioms = getAndSaveIdioms(bookDTO.languages());
        Book newBook = new Book(bookDTO.id(), bookDTO.title());
        newBook.setAuthors(persistAuthors);
        newBook.setIdioms(persistIdioms);
        return bookRepository.save(newBook);
    }

    private List<Idiom> getAndSaveIdioms(List<String> codeLanguages) {
        return codeLanguages.stream()
            .map(code -> {
                Optional<Idiom> idiom = idiomRepository.findByCode(code);
                return idiom.orElseGet(() -> idiomRepository.save(new Idiom(code)));
            })
            .toList();
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

    private void printBook(Book persistBook) {
        System.out.println("-------------");
        System.out.println("Title: " + persistBook.getTitle());
        System.out.println("gutenbergID: " + persistBook.getGutenbergId());
        System.out.println("languages: " + BasicHelper.formatArray(persistBook.getIdioms(), ",", "."));
        System.out.println("authors: " + BasicHelper.formatArray(persistBook.getAuthors(), ";", "."));
        System.out.println("-------------");
    }

    private void printAuthor(Author persistAuthor) {
        System.out.println("Name: " + persistAuthor.getName());
        System.out.println("birthYear: " + persistAuthor.getBirthYear());
        System.out.println("deathYear: " + persistAuthor.getDeathYear());
        System.out.println("-------------");
    }
}
