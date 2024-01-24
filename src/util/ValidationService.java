package util;


import exception.InvalidInputException;

public class ValidationService {
    public static void validateInput(boolean condition, String errorMessage) throws InvalidInputException {
        if (!condition) {
            throw new InvalidInputException(errorMessage);
        }
    }

    // Additional validation methods can be added based on your requirements
}