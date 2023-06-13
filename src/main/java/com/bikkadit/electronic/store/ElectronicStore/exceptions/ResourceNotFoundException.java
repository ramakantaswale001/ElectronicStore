package com.bikkadit.electronic.store.ElectronicStore.exceptions;

import lombok.Builder;

public class ResourceNotFoundException extends RuntimeException{

    @Builder
    public ResourceNotFoundException(){
        super("Resource not found !!");
    }
    public ResourceNotFoundException(String message){
        super(message);
    }
}
