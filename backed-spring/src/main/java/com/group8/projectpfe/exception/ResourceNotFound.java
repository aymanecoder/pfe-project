package com.group8.projectpfe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResourceNotFound extends RuntimeException{
    private boolean status;
    private String message;
}
