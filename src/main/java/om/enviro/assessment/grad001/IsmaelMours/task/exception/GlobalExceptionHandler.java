package om.enviro.assessment.grad001.IsmaelMours.task.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidFileFormatException(InvalidFileFormatException ex) {
        logger.error("Invalid file format exception: {}", ex.getMessage());
        ExceptionResponse response = ExceptionResponse.builder()
                .error("Invalid file format")
                .businessErrorDescription(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ExceptionResponse> handleMultipartException(MultipartException ex) {
        logger.error("Multipart exception: {}", ex.getMessage());
        ExceptionResponse response = ExceptionResponse.builder()
                .error("Multipart exception")
                .businessErrorDescription(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateDataException(DuplicateDataException ex) {
        logger.error("Duplicate data exception: {}", ex.getMessage());
        ExceptionResponse response = ExceptionResponse.builder()
                .error("Duplicate data")
                .businessErrorDescription(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(Exception ex) {
        logger.error("Internal server error: {}", ex.getMessage(), ex);
        ExceptionResponse response = ExceptionResponse.builder()
                .error("Internal server error")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
