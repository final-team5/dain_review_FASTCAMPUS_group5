package kr.co.dain_review.be.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalController {
    // 특정 예외 처리
    @ExceptionHandler(SpecificException.class)
    public ResponseEntity<String> handleSpecificException(SpecificException ex) {
        // 로그 기록, 추가 작업 등
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Specific error occurred: " + ex.getMessage());
    }

    // NullPointerException 처리
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Null pointer exception: " + ex.getMessage());
    }

    // 모든 Exception 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + ex.getMessage());
    }

    public class SpecificException extends Exception {
        public SpecificException(String message) {
            super(message);
        }
    }
}
