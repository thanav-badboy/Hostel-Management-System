/** A hostel complaint identified by its complaint ID. */
public final class Complaint {
    /** The supported complaint statuses and their display labels. */
    public enum Status {
        PENDING("Pending"),
        IN_PROGRESS("In Progress"),
        RESOLVED("Resolved");

        private final String label;

        Status(String label) {
            this.label = label;
        }

        /** Returns the label displayed to the user. */
        @Override
        public String toString() {
            return label;
        }
    }

    private final String complaintId;
    private final String studentId;
    private final String category;
    private final String description;
    private final Status status;

    Complaint(String complaintId, String studentId, String category, String description,
              Status status) {
        this.complaintId = complaintId;
        this.studentId = studentId;
        this.category = category;
        this.description = description;
        this.status = status;
    }

    /** Returns the unique complaint ID. */
    public String getComplaintId() {
        return complaintId;
    }

    /** Returns the shared student ID supplied at registration. */
    public String getStudentId() {
        return studentId;
    }

    /** Returns the complaint category. */
    public String getCategory() {
        return category;
    }

    /** Returns the complaint description. */
    public String getDescription() {
        return description;
    }

    /** Returns the status recorded in this complaint snapshot. */
    public Status getStatus() {
        return status;
    }
}
