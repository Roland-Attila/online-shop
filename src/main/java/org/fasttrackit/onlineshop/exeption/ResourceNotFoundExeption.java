package org.fasttrackit.onlineshop.exeption;

public class ResourceNotFoundExeption extends RuntimeException{

    public ResourceNotFoundExeption(String message) {
        super(message);
    }
}
