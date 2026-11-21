/** Indicates that a complaint ID is already registered. */
public class DuplicateComplaintException extends Exception {
    private static final long serialVersionUID = 1L;

    /** Creates an exception identifying the duplicate complaint. */
    public DuplicateComplaintException(String complaintId) {
        super("Complaint ID already exists: " + complaintId);
    }
}
