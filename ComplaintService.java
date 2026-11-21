import java.util.List;

/** Public API for using complaint management without its console menu. */
public interface ComplaintService {
    /**
     * Registers a complaint with Pending status. Text fields are trimmed and
     * must be nonblank. IDs are case-sensitive.
     * @throws DuplicateComplaintException if the complaint ID already exists
     * @throws IllegalArgumentException if a field is null or blank
     */
    Complaint registerComplaint(String complaintId, String studentId, String category,
                                String description) throws DuplicateComplaintException;

    /** Returns a read-only snapshot of all complaints in registration order. */
    List<Complaint> viewComplaints();

    /**
     * Returns the complaint matching the trimmed ID.
     * @throws ComplaintNotFoundException if the ID is absent
     * @throws IllegalArgumentException if the ID is null or blank
     */
    Complaint searchComplaint(String complaintId) throws ComplaintNotFoundException;

    /**
     * Sets the complaint's status and returns its updated snapshot.
     * @throws ComplaintNotFoundException if the ID is absent
     * @throws IllegalArgumentException if the ID is null or blank or the status is null
     */
    Complaint updateComplaintStatus(String complaintId, Complaint.Status status)
            throws ComplaintNotFoundException;

    /**
     * Removes the complaint identified by the trimmed ID.
     * @throws ComplaintNotFoundException if the ID is absent
     * @throws IllegalArgumentException if the ID is null or blank
     */
    void deleteComplaint(String complaintId) throws ComplaintNotFoundException;

    /**
     * Returns a read-only snapshot of the student's current complaints in registration order.
     * @throws IllegalArgumentException if the student ID is null or blank
     */
    List<Complaint> getComplaintHistory(String studentId);
}
