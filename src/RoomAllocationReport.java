import com.hostel.student.model.Student;
import com.hostel.student.service.StudentService;
import java.util.List;

/**
 * Generates combined hostel resident allocation reports mapping Room records
 * with live Student profile information.
 */
public class RoomAllocationReport {

    public static void generateReport(RoomService roomService, StudentService studentService) {
        System.out.println("\n==========================================================================================");
        System.out.println("                     HOSTEL RESIDENT ROOM ALLOCATION REPORT                                ");
        System.out.println("==========================================================================================");
        System.out.printf("| %-8s | %-12s | %-10s | %-20s | %-15s | %-10s |%n",
                "ROOM ID", "ROOM NUMBER", "STUDENT ID", "STUDENT NAME", "COURSE", "RENT");
        System.out.println("+----------+--------------+------------+----------------------+-----------------+------------+");

        List<Room> allRooms = roomService.getAllRooms();
        int totalOccupants = 0;

        for (Room r : allRooms) {
            List<String> occupants = r.getAllocatedStudents();
            if (occupants.isEmpty()) {
                System.out.printf("| %-8s | %-12d | %-10s | %-20s | %-15s | ₹%-9.2f |%n",
                        r.getRoomId(), r.getRoomNumber(), "[VACANT]", "-", "-", r.getMonthlyRent());
            } else {
                for (String stuId : occupants) {
                    totalOccupants++;
                    String name = "Registered Student";
                    String course = "-";
                    if (studentService != null) {
                        try {
                            Student s = studentService.getStudentById(stuId);
                            name = s.getName();
                            course = s.getCourse();
                        } catch (Exception ignored) {
                        }
                    }
                    System.out.printf("| %-8s | %-12d | %-10s | %-20s | %-15s | ₹%-9.2f |%n",
                            r.getRoomId(), r.getRoomNumber(), stuId, name, course, r.getMonthlyRent());
                }
            }
        }
        System.out.println("+----------+--------------+------------+----------------------+-----------------+------------+");
        System.out.println(" Total Rooms: " + allRooms.size() + " | Total Resident Occupants: " + totalOccupants);
        System.out.println("==========================================================================================\n");
    }
}
