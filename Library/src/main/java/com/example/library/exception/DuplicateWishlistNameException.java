package com.example.library.exception;

public class DuplicateWishlistNameException extends RuntimeException{
    public DuplicateWishlistNameException(String message)
    {
        super(message);
    }

}
