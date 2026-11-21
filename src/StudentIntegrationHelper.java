import java.util.HashMap;
import java.util.Map;

/**
 * Team Integration Helper: StudentIntegrationHelper
 * 
 * Provides mock student validation and integration contracts for Member 1 (Student Management Module).
 * 
 * Architecture Note:
 * - Common Identifier: Student ID (e.g., "STU101", "STU102")
 * - Member 2 (Room Management) does NOT store or manage student profiles.
 * - This helper simulates Member 1's API during standalone testing and viva demonstration.
 */
public class StudentIntegrationHelper {

    // Pre-populated mock students for demonstration and testing
    private static final Map<String, String> MOCK_STUDENTS = new HashMap<>();

    static {
        MOCK_STUDENTS.put("STU101", "Rahul Sharma (CSE)");
        MOCK_STUDENTS.put("STU102", "Priya Patel (ECE)");
        MOCK_STUDENTS.put("STU103", "Amit Verma (IT)");
        MOCK_STUDENTS.put("STU104", "Sneha Kulkarni (Mech)");
        MOCK_STUDENTS.put("STU105", "Vikas Nair (Civil)");
    }

    /**
     * Checks if a Student ID is structurally valid or recognized in the test roster.
     * Accepted format: STU followed by digits (e.g. STU101, STU202), or any recognized mock student.
     *
     * @param studentId Student identifier
     * @return true if valid
     */
    public static boolean isValidStudentId(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            return false;
        }
        String cleanId = studentId.trim().toUpperCase();
        // Accepts pre-configured mock students or any valid student format (e.g., STU101, STU999)
        return MOCK_STUDENTS.containsKey(cleanId) || cleanId.matches("^STU[0-9]{3,}$");
    }

    /**
     * Retrieves student profile details from mock records (or default fallback).
     *
     * @param studentId Student identifier
     * @return Student name & department string
     */
    public static String getStudentDisplayName(String studentId) {
        if (studentId == null) {
            return "Unknown Student";
        }
        String cleanId = studentId.trim().toUpperCase();
        return MOCK_STUDENTS.getOrDefault(cleanId, "Registered Student (" + cleanId + ")");
    }

    /**
     * Displays available test student IDs for quick testing during demonstration.
     */
    public static void printAvailableTestStudents() {
        System.out.println("------------------ TEST STUDENT ROSTER (For Member 1 Integration) ------------------");
        for (Map.Entry<String, String> entry : MOCK_STUDENTS.entrySet()) {
            System.out.println("  • " + entry.getKey() + " -> " + entry.getValue());
        }
        System.out.println("  (Note: Any ID matching pattern 'STU###' is also accepted as a valid test ID)");
        System.out.println("------------------------------------------------------------------------------------");
    }

    /**
     * Validates student ID against Member 1's live StudentService if available.
     * Fallback to mock roster if service instance is null.
     */
    public static boolean isStudentRegistered(String studentId, com.hostel.student.service.StudentService service) {
        if (service != null) {
            return service.existsById(studentId);
        }
        return isValidStudentId(studentId);
    }

    /**
     * Retrieves live student name and course from StudentService if present.
     */
    public static String getLiveStudentDetails(String studentId, com.hostel.student.service.StudentService service) {
        if (service != null) {
            try {
                com.hostel.student.model.Student s = service.getStudentById(studentId);
                return s.getName() + " (" + s.getCourse() + ")";
            } catch (com.hostel.student.exception.StudentNotFoundException ignored) {
                return "Unregistered Student (" + studentId + ")";
            }
        }
        return getStudentDisplayName(studentId);
    }
}
