package com.hasan.movieproject.log.base;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;

public class Operation<T> {
    private String statusMessage;
    private Boolean wasSuccessful;
    private HttpStatus httpStatus;
    private T operationObject;
    private static Logger logger = Logger.getLogger(Operation.class.getName());

    public Operation() {
    }

    public Operation(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Operation(String statusMessage, Boolean wasSuccessful, T operationObject) {
        this.statusMessage = statusMessage;
        this.wasSuccessful = wasSuccessful;
        this.operationObject = operationObject;
    }

    public Operation(OperationStatus operationStatus) {
        setOperation(operationStatus);
        this.operationObject = null;
    }

    public Operation(OperationStatus operationStatus, T operationObject) {
        setOperation(operationStatus);
        this.operationObject = operationObject;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Boolean getWasSuccessful() {
        return wasSuccessful;
    }

    public void setWasSuccessful(Boolean wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public T getOperationObject() {
        return operationObject;
    }

    public void setOperationObject(T operationObject) {
        this.operationObject = operationObject;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "statusMessage='" + statusMessage + '\'' +
                ", wasSuccessful=" + wasSuccessful +
                ", httpStatus=" + httpStatus +
                ", operationObject=" + operationObject +
                '}';
    }

    public static ResponseEntity<?> getOperationResult(Operation operation){
        logOperation(operation);
        if(operation.getWasSuccessful()){
            return new ResponseEntity<>(operation, operation.getHttpStatus());
        } else{
            return new ResponseEntity<>(operation, operation.getHttpStatus());
        }
    }

    public static Operation getOperation(Errors result){
        if(result.hasErrors()){
            if(result.hasFieldErrors("operation")){
                return (Operation<?>) result.getFieldValue("operation");
            } else{
                return new Operation<>(OperationStatus.EMAIL_VALIDATION_SUCCESS);
            }
        }

        return null;
    }

    public static void logOperation(Operation operation){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.debug("Operation done by " + principal.toString() + "\nOperation result:\n" + operation.toString());
    }

    public static void logOperation(String operationName){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.debug("Operation name: " + operationName + "\nCalled by: " + principal.toString());
    }


    private void setOperation(OperationStatus operationStatus){
        switch (operationStatus){

            case MOVIE_NOT_FOUND:
                statusMessage = "Movie(s) not found!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case MOVIE_FOUND:
                statusMessage = "Movie(s) found!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case MOVIE_ADD_FAILED:
                statusMessage = "Could not add the movie!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case MOVIE_ADD_SUCCESS:
                statusMessage = "New movie added!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case MOVIE_UPDATE_FAILED:
                statusMessage = "Could not update the movie!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case MOVIE_UPDATE_SUCCESS:
                statusMessage = "Movie updated!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case MOVIE_REMOVE_FAILED:
                statusMessage = "Could not remove the movie!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case MOVIE_REMOVE_SUCCESS:
                statusMessage = "Movie removed!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case MOVIE_DIRECTOR_ADD_FAILED:
                statusMessage = "Could not add director to the movie!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case MOVIE_DIRECTOR_ADD_SUCCESS:
                statusMessage = "Director added to the movie!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case MOVIE_EXISTS:
                statusMessage = "The movie already exists!";
                wasSuccessful = false;
                httpStatus = HttpStatus.CONFLICT;
                break;
            case DIRECTOR_NOT_FOUND:
                statusMessage = "Director(s) not found!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case DIRECTOR_FOUND:
                statusMessage = "Director(s) found!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case DIRECTOR_ADD_FAILED:
                statusMessage = "Could not add the director!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case DIRECTOR_ADD_SUCCESS:
                statusMessage = "New director added!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case DIRECTOR_UPDATE_FAILED:
                statusMessage = "Could not update the director!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case DIRECTOR_UPDATE_SUCCESS:
                statusMessage = "Director updated!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case DIRECTOR_REMOVE_FAILED:
                statusMessage = "Could not remove the director!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case DIRECTOR_REMOVE_SUCCESS:
                statusMessage = "Director removed!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case DIRECTOR_MOVIE_ADD_FAILED:
                statusMessage = "Could not add movie to the director!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case DIRECTOR_MOVIE_ADD_SUCCESS:
                statusMessage = "Movie added to the director!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case DIRECTOR_EXISTS:
                statusMessage = "The director already exists!";
                wasSuccessful = false;
                httpStatus = HttpStatus.CONFLICT;
                break;
            case LIST_ENTITY_NOT_FOUND:
                statusMessage = "List dao(s) not found!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case LIST_ENTITY_FOUND:
                statusMessage = "List dao(s) found!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case LIST_ENTITY_ADD_FAILED:
                statusMessage = "Could not add the movie to your list!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case LIST_ENTITY_ADD_SUCCESS:
                statusMessage = "The movie is added to your list!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case LIST_ENTITY_REMOVE_FAILED:
                statusMessage = "Could not remove the list dao!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case LIST_ENTITY_REMOVE_SUCCESS:
                statusMessage = "List dao removed!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case LIST_ENTITY_EXISTS:
                statusMessage = "The movie is already in your list!";
                wasSuccessful = false;
                httpStatus = HttpStatus.CONFLICT;
                break;
            case LIST_TYPE_NOT_FOUND:
                statusMessage = "List type not available for the user!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case LIST_TYPE_EXISTS:
                statusMessage = "List type already exists!";
                wasSuccessful = false;
                httpStatus = HttpStatus.CONFLICT;
                break;
            case LIST_TYPE_ADD_SUCCESS:
                statusMessage = "List type added!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case LIST_ENTITY_MOVIE_NOT_FOUND:
                statusMessage = "Movie(s) do not exist in the list!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case USER_NOT_FOUND:
                statusMessage = "User not found!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case USER_FOUND:
                statusMessage = "User found!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case USER_ADD_FAILED:
                statusMessage = "Could not add the user!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case USER_ADD_SUCCESS:
                statusMessage = "New user added!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case USER_EXISTS:
                statusMessage = "User with the same username already exists!";
                wasSuccessful = false;
                httpStatus = HttpStatus.CONFLICT;
                break;
            case USER_DISABLE_FAILED:
                statusMessage = "Could not disable the user!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case USER_DISABLE_SUCCESS:
                statusMessage = "User disabled!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case USER_UPDATE_FAILED:
                statusMessage = "Could not update the user!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case USER_UPDATE_SUCCESS:
                statusMessage = "User updated!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case USER_REMOVE_FAILED:
                statusMessage = "Could not remove the user!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case USER_REMOVE_SUCCESS:
                statusMessage = "User removed";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case PASSWORD_CONFIRMATION_FAILED:
                statusMessage = "Passwords do not match!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case PASSWORD_CONFIRMATION_SUCCESS:
                statusMessage = "Passwords do match!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case PASSWORD_WRONG:
                statusMessage = "Wrong password!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case PASSWORD_CHANGE_FAILED:
                statusMessage = "Could not change the password!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case PASSWORD_CHANGE_SUCCESS:
                statusMessage = "Password changed!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case EMAIL_VALIDATION_FAILED:
                statusMessage = "Invalid email!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case EMAIL_VALIDATION_SUCCESS:
                statusMessage = "Valid email!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case LOGIN_FAILED:
                statusMessage = "Could not login!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case LOGIN_SUCCESS:
                statusMessage = "Login successful!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case ADMIN:
                statusMessage = "User has an admin authority!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case NOT_ADMIN:
                statusMessage = "User does not have an admin authority!";
                wasSuccessful = false;
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case USER:
                statusMessage = "User has an user authority!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case NOT_USER:
                statusMessage = "User does not have an user authority!";
                wasSuccessful = false;
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;

            case ROLE_NOT_FOUND:
                statusMessage = "Role(s) not found!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case ROLE_FOUND:
                statusMessage = "Role(s) found!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case ROLE_EXISTS:
                statusMessage = "Role already exists!";
                wasSuccessful = false;
                httpStatus = HttpStatus.CONFLICT;
                break;
            case ROLE_ADD_SUCCESS:
                statusMessage = "Role added!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case ROLE_ADD_FAILED:
                statusMessage = "Could not add the role!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case ROLE_REMOVE_SUCCESS:
                statusMessage = "Role removed!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case ROLE_REMOVE_FAILED:
                statusMessage = "Could not remove the role!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
        }
    }
}
