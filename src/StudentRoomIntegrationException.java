/**
 * Custom Exception thrown when an integration operation between Student Management
 * and Room Management modules fails validation.
 */
public class StudentRoomIntegrationException extends Exception {

    public StudentRoomIntegrationException(String message) {
        super(message);
    }

    public StudentRoomIntegrationException(String studentId, String roomId, String reason) {
        super("Cross-module integration error [Student: " + studentId + ", Room: " + roomId + "]: " + reason);
    }
}
