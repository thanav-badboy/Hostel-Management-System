import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Implements the complaint API using an in-memory list. */
public final class ComplaintManager implements ComplaintService {
    private final List<Complaint> complaints = new ArrayList<>();

    /** Creates an empty complaint manager. */
    public ComplaintManager() {
    }

    @Override
    public Complaint registerComplaint(String complaintId, String studentId, String category,
                                       String description) throws DuplicateComplaintException {
        String id = requireText(complaintId, "Complaint ID");
        if (findIndex(id) >= 0) {
            throw new DuplicateComplaintException(id);
        }

        Complaint complaint = new Complaint(id, requireText(studentId, "Student ID"),
                requireText(category, "Category"), requireText(description, "Description"),
                Complaint.Status.PENDING);
        complaints.add(complaint);
        return complaint;
    }

    @Override
    public List<Complaint> viewComplaints() {
        return Collections.unmodifiableList(new ArrayList<>(complaints));
    }

    @Override
    public Complaint searchComplaint(String complaintId) throws ComplaintNotFoundException {
        return complaints.get(requireIndex(complaintId));
    }

    @Override
    public Complaint updateComplaintStatus(String complaintId, Complaint.Status status)
            throws ComplaintNotFoundException {
        if (status == null) {
            throw new IllegalArgumentException("Status is required.");
        }
        int index = requireIndex(complaintId);
        Complaint existing = complaints.get(index);
        Complaint updated = new Complaint(existing.getComplaintId(), existing.getStudentId(),
                existing.getCategory(), existing.getDescription(), status);
        complaints.set(index, updated);
        return updated;
    }

    @Override
    public void deleteComplaint(String complaintId) throws ComplaintNotFoundException {
        complaints.remove(requireIndex(complaintId));
    }

    @Override
    public List<Complaint> getComplaintHistory(String studentId) {
        String id = requireText(studentId, "Student ID");
        List<Complaint> history = new ArrayList<>();
        for (Complaint complaint : complaints) {
            if (complaint.getStudentId().equals(id)) {
                history.add(complaint);
            }
        }
        return Collections.unmodifiableList(history);
    }

    private int requireIndex(String complaintId) throws ComplaintNotFoundException {
        String id = requireText(complaintId, "Complaint ID");
        int index = findIndex(id);
        if (index < 0) {
            throw new ComplaintNotFoundException(id);
        }
        return index;
    }

    // Linear search checks each ID in order. Worst-case time is O(n).
    private int findIndex(String complaintId) {
        for (int index = 0; index < complaints.size(); index++) {
            if (complaints.get(index).getComplaintId().equals(complaintId)) {
                return index;
            }
        }
        return -1;
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " is required.");
        }
        return value.trim();
    }
}
