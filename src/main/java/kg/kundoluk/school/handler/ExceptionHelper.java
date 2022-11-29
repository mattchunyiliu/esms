package kg.kundoluk.school.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import kg.kundoluk.school.dto.ApiError;
import kg.kundoluk.school.exception.*;
import kg.kundoluk.school.service.telegram.TelegramBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHelper {

    @Autowired
    private TelegramBotService telegramBotService;

    @ExceptionHandler(value = { PermissionException.class})
    public ResponseEntity<Object> handlePermissionException(Exception ex, HttpServletRequest request) throws JsonProcessingException {

        ApiError apiError = new ApiError(String.valueOf(HttpStatus.FORBIDDEN.value()), ex.getMessage(), HttpStatus.FORBIDDEN, LocalDateTime.now().toString(), request.getServletPath(), getDevice(request));
        telegramBotService.send(apiError);
        return new ResponseEntity<>(apiError, apiError.getError());
    }

    @ExceptionHandler(value = { AlreadyExistException.class, AuthenticationException.class, ConstraintMappingException.class})
    public ResponseEntity<Object> handleAlreadyExistException(Exception ex, HttpServletRequest request) throws JsonProcessingException {

        ApiError apiError = new ApiError(String.valueOf(HttpStatus.CONFLICT.value()), ex.getMessage(), HttpStatus.CONFLICT, LocalDateTime.now().toString(), request.getServletPath(), getDevice(request));
        telegramBotService.send(apiError);
        return new ResponseEntity<>(apiError, apiError.getError());
    }

    @ExceptionHandler(value = { PersistenceException.class, ResourceNotFoundException.class})
    public ResponseEntity<Object> handlePersistenceException(Exception ex, HttpServletRequest request) throws JsonProcessingException {
        ApiError apiError = new ApiError(String.valueOf(HttpStatus.NOT_FOUND.value()), ex.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now().toString(), request.getServletPath(), getDevice(request));
        telegramBotService.send(apiError);
        return new ResponseEntity<>(apiError, apiError.getError());
    }

    @ExceptionHandler(value = { NullPointerException.class})
    public ResponseEntity<Object> handleNPEException(Exception ex, HttpServletRequest request) throws JsonProcessingException {
        ApiError apiError = new ApiError(String.valueOf(HttpStatus.NOT_FOUND.value()), ex.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now().toString(), request.getServletPath(), getDevice(request));
        telegramBotService.send(apiError);
        return new ResponseEntity<>(apiError, apiError.getError());
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleException(Exception ex, HttpServletRequest request) throws JsonProcessingException {
        ApiError apiError = new ApiError(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now().toString(), request.getServletPath(), getDevice(request));
        telegramBotService.send(apiError);
        return new ResponseEntity<>(apiError, apiError.getError());
    }

    private int getDevice(HttpServletRequest request){
        int device = 1; //DEFAULT BROWSER
        if(request.getHeader("User-Agent").contains("Mobile")) {
            device = 2;
        }
        return device;
    }
}
