import java.util.List;

/**
 * Service Interface: RoomService
 * 
 * Demonstrates:
 * Abstraction: Exposes clean business operation signatures while hiding internal implementation details.
 */
public interface RoomService {

    /**
     * Feature 1: Add a new room to the hostel inventory.
     */
    void addRoom(Room room);

    /**
     * Feature 2a: Retrieve all registered rooms.
     */
    List<Room> getAllRooms();

    /**
     * Feature 2b: Retrieve only rooms that currently have available capacity.
     */
    List<Room> getAvailableRooms();

    /**
     * Feature 2c: Retrieve rooms that are fully occupied.
     */
    List<Room> getOccupiedRooms();

    /**
     * Feature 3a: Search room by its unique String Room ID.
     */
    Room searchByRoomId(String roomId) throws RoomNotFoundException;

    /**
     * Feature 3b: Search room by its integer Room Number (leveraging manual Binary Search).
     */
    Room searchByRoomNumber(int roomNumber) throws RoomNotFoundException;

    /**
     * Feature 4: Allocate a room to a student.
     * Prevents duplicate student allocation and allocation beyond capacity.
     */
    boolean allocateRoom(String roomId, String studentId)
            throws RoomNotFoundException, RoomFullException, DuplicateAllocationException;

    /**
     * Feature 5: Vacate a student from their allocated room.
     * Restores room capacity and updates status.
     */
    boolean vacateRoom(String roomId, String studentId) throws RoomNotFoundException;

    /**
     * Feature 6a: Retrieve all active and historical room allocation records.
     */
    List<RoomAllocation> getAllAllocations();

    /**
     * Feature 6b: Retrieve list of student IDs currently allocated to a specific room.
     */
    List<String> getStudentsInRoom(String roomId) throws RoomNotFoundException;

    /**
     * Helper check: Determine whether a student currently has an active room allocation.
     */
    boolean isStudentAllocated(String studentId);

    /**
     * Integration Method: Find the room ID where a specific student is staying.
     */
    default String findRoomByStudentId(String studentId) {
        if (studentId == null) return null;
        for (Room r : getAllRooms()) {
            if (r.getAllocatedStudents().contains(studentId.trim().toUpperCase())) {
                return r.getRoomId();
            }
        }
        return null;
    }
}
