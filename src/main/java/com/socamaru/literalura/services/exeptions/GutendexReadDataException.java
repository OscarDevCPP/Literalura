package com.socamaru.literalura.services.exeptions;

public class GutendexReadDataException extends RuntimeException{

	public GutendexReadDataException(String message){
		super(message);
	}

	public GutendexReadDataException(String message, Throwable cause){
		super(message, cause);
	}

}
