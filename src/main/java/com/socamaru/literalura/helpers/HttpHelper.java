package com.socamaru.literalura.helpers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpHelper {
	public static HttpResponse<String> httpGet(String url) {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.build();
			return client.send(request, HttpResponse.BodyHandlers.ofString());
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}
}
