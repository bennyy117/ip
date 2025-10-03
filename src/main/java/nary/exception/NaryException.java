package nary.exception;

/**
 * Represents custom exceptions specific to the Nary chatbot.
 * Used to indicate invalid commands or input errors encountered during execution.
 */
public class NaryException extends Exception {
    /**
     * Constructs a new {@code NaryException} with the specified detail message.
     *
     * @param message The detail message.
     */
    public NaryException(String message) {
        super(message);
    }
}
