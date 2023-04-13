package com.example.myawesomepastebin.exception.handlers;

import com.example.myawesomepastebin.exception.InvalidParametersExeption;
import com.example.myawesomepastebin.exception.PasteNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundPasteController {
    @ExceptionHandler(PasteNotFoundException.class)
    public ResponseEntity<?> notFound() {
        return ResponseEntity.status(404).body("Такой не найден");
    }

    @ExceptionHandler(InvalidParametersExeption.class)
    public ResponseEntity<?> invalidParam() {
        return ResponseEntity.status(400).body("Неверно введены данные");
    }

}
