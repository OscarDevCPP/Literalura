package com.socamaru.literalura.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.socamaru.literalura.helpers.GsonHelper;
import com.socamaru.literalura.helpers.HttpHelper;
import com.socamaru.literalura.repository.models.AuthorDTO;
import com.socamaru.literalura.repository.models.BookDTO;
import com.socamaru.literalura.services.exeptions.GutendexReadDataException;

import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class GutendexApiService {

    public Optional<BookDTO> searchByAuthorOrTitle(String searchString) {
        searchString = String.join("%20", searchString.trim().split("\\s+"));
        String BASE_URL = "https://gutendex.com/books/";
        String endpoint = String.format("%s?search=%s", BASE_URL, searchString);
        HttpResponse<String> response = HttpHelper.httpGet(endpoint);
        JsonObject body = GsonHelper.toJsonObject(response.body());
        JsonArray results = body.get("results").getAsJsonArray();
        if (!results.isEmpty()) {
            JsonObject firstResult = results.get(0).getAsJsonObject();
            List<AuthorDTO> authors = readAuthors(firstResult);
            List<String> languages = readLanguages(firstResult);
            BookDTO bookDTO = readBook(firstResult, authors, languages);
            return Optional.of(bookDTO);
        }
        return Optional.empty();
    }

    private List<AuthorDTO> readAuthors(JsonObject bookObject) {
        if (bookObject.has("authors") && !bookObject.get("authors").isJsonArray()) {
            throw new GutendexReadDataException("Doesn't exists authors or isn't Array Type in gutendex response");
        }
        JsonArray authorsArrayData = bookObject.getAsJsonArray("authors");
        List<AuthorDTO> authors = new LinkedList<>();
        for (JsonElement element : authorsArrayData) {
            AuthorDTO authorDTO = getAuthorDTO(element);
            authors.add(authorDTO);
        }
        return authors;
    }

    private static AuthorDTO getAuthorDTO(JsonElement element) {
        JsonObject author = element.getAsJsonObject();
        Long birthYear = null, deathYear = null;
        if (author.has("birth_year") && author.get("birth_year").isJsonPrimitive()) {
            birthYear = author.get("birth_year").getAsLong();
        }
        if (author.has("death_year") && author.get("death_year").isJsonPrimitive()) {
            deathYear = author.get("death_year").getAsLong();
        }
        String name = author.get("name").getAsString();
        return new AuthorDTO(name, birthYear, deathYear);
    }

    private List<String> readLanguages(JsonObject bookObject) {
        if (bookObject.has("languages") && !bookObject.get("languages").isJsonArray()) {
            throw new GutendexReadDataException("Doesn't exists languages or isn't Array Type in gutendex response");
        }
        JsonArray languagesArrayData = bookObject.getAsJsonArray("languages");
        List<String> languages = new LinkedList<>();
        for (JsonElement element : languagesArrayData) {
            if (element.isJsonPrimitive())
                languages.add(element.getAsString());
        }
        return languages;
    }

    private BookDTO readBook(JsonObject bookObject, List<AuthorDTO> authors, List<String> languages) {
        Long id = bookObject.get("id").getAsLong();
        String title = bookObject.get("title").getAsString();
        Long downloadCount = bookObject.get("download_count").getAsLong();
        return new BookDTO(id, title, authors, languages, downloadCount);
    }

}
