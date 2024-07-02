package com.socamaru.literalura.helpers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class HttpHelper {
	public static CompletableFuture<HttpResponse<String>> getAsync(String url) {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.build();
			return client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}
}
