import com.hostel.student.model.Student;
import com.hostel.student.service.StudentService;
import com.hostel.student.service.StudentServiceImpl;

/**
 * Automated end-to-end integration test validating collaboration between
 * Member 1 (Student Management) and Member 2 (Room Management).
 */
public class StudentRoomIntegrationTest {

    public static void main(String[] args) {
        System.out.println(">>> Running Student-Room Integration Test Suite <<<");

        StudentService studentService = new StudentServiceImpl();
        RoomService roomService = new RoomManager();

        try {
            // 1. Setup sample room inventory
            Room r101 = new SingleRoom("R101", 101);
            Room r102 = new DoubleRoom("R102", 102);
            roomService.addRoom(r101);
            roomService.addRoom(r102);

            // 2. Register student in Member 1 module
            Student s1 = new Student("STU201", "Tanmay Joshi", 20, "Male", "9876543210", "tanmay@college.edu", "B.Tech CSE");
            studentService.registerStudent(s1);
            assertCondition(studentService.existsById("STU201"), "Student STU201 must be registered.");

            // 3. Allocate student into Room R101
            boolean allocated = roomService.allocateRoom("R101", "STU201");
            assertCondition(allocated, "Room allocation should succeed.");
            assertCondition(roomService.isStudentAllocated("STU201"), "Student must be marked as allocated.");

            // 4. Test reverse room search by student ID
            String foundRoom = roomService.findRoomByStudentId("STU201");
            assertCondition("R101".equals(foundRoom), "findRoomByStudentId should return R101.");

            // 5. Duplicate allocation guard check
            boolean duplicateBlocked = false;
            try {
                roomService.allocateRoom("R102", "STU201");
            } catch (DuplicateAllocationException e) {
                duplicateBlocked = true;
                System.out.println("[PASS] DuplicateAllocationException caught as expected: " + e.getMessage());
            }
            assertCondition(duplicateBlocked, "Cannot allocate same student to another room.");

            // 6. Vacate student and verify capacity restoration
            boolean vacated = roomService.vacateRoom("R101", "STU201");
            assertCondition(vacated, "Vacate should succeed.");
            assertCondition(!roomService.isStudentAllocated("STU201"), "Student must no longer have room.");
            assertCondition(roomService.findRoomByStudentId("STU201") == null, "Reverse room search should return null.");

            System.out.println(">>> ALL STUDENT-ROOM INTEGRATION TESTS PASSED! <<<\n");

        } catch (Exception e) {
            System.err.println("[FAIL] Integration test failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertCondition(boolean condition, String message) {
        if (!condition) {
            System.err.println("[FAIL] " + message);
            throw new AssertionError(message);
        } else {
            System.out.println("[PASS] " + message);
        }
    }
}
