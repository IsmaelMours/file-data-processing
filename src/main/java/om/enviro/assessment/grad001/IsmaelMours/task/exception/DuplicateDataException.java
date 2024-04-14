package om.enviro.assessment.grad001.IsmaelMours.task.exception;

public class DuplicateDataException extends RuntimeException {

    public DuplicateDataException(String message) {
        super(message);
    }

    public DuplicateDataException(String message, Throwable cause) {
        super(message, cause);
    }
}

