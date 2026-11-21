import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service Implementation: RoomManager
 * 
 * Manages the hostel room inventory, allocation state, and queries.
 * Implements: RoomService
 * 
 * Demonstrates:
 * 1. Java Collections Framework:
 *    - ArrayList: Dynamic list to maintain ordered room entries.
 *    - HashMap: Key-value map for O(1) constant-time room lookup by Room ID.
 *    - HashSet: Set for O(1) duplicate allocation detection across students.
 * 2. Custom Exception Handling: Enforces business logic safety.
 * 3. DSA integration: Uses manual MergeSort and manual Binary Search from RoomSortAndSearch.
 */
public class RoomManager implements RoomService {

    // Dynamic list preserving insertion and custom sort order
    private final List<Room> roomList;

    // HashMap for O(1) instant lookup by Room ID
    private final Map<String, Room> roomMap;

    // HashSet for O(1) instantaneous duplicate student allocation check
    private final Set<String> activeAllocatedStudents;

    // History log of all allocations
    private final List<RoomAllocation> allocationHistory;

    /**
     * Default Constructor initializing collections.
     */
    public RoomManager() {
        this.roomList = new ArrayList<>();
        this.roomMap = new HashMap<>();
        this.activeAllocatedStudents = new HashSet<>();
        this.allocationHistory = new ArrayList<>();
    }

    // ==================== FEATURE 1: ADD ROOM ====================

    @Override
    public void addRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        if (roomMap.containsKey(room.getRoomId())) {
            throw new IllegalArgumentException("Room with ID " + room.getRoomId() + " already exists!");
        }

        // Check if room number is already taken
        for (Room existing : roomList) {
            if (existing.getRoomNumber() == room.getRoomNumber()) {
                throw new IllegalArgumentException("Room with Number " + room.getRoomNumber() + " already exists!");
            }
        }

        roomList.add(room);
        roomMap.put(room.getRoomId(), room);
    }

    // ==================== FEATURE 2: VIEW ROOMS ====================

    @Override
    public List<Room> getAllRooms() {
        // Return a copy sorted by room number using manual Merge Sort
        List<Room> sortedList = new ArrayList<>(roomList);
        RoomSortAndSearch.mergeSortByRoomNumber(sortedList);
        return sortedList;
    }

    @Override
    public List<Room> getAvailableRooms() {
        List<Room> availableList = new ArrayList<>();
        for (Room room : roomList) {
            if (room.isAvailable()) {
                availableList.add(room);
            }
        }
        // Sort using manual Merge Sort
        RoomSortAndSearch.mergeSortByRoomNumber(availableList);
        return availableList;
    }

    @Override
    public List<Room> getOccupiedRooms() {
        List<Room> occupiedList = new ArrayList<>();
        for (Room room : roomList) {
            if (room.getStatus() == RoomStatus.OCCUPIED) {
                occupiedList.add(room);
            }
        }
        // Sort using manual Merge Sort
        RoomSortAndSearch.mergeSortByRoomNumber(occupiedList);
        return occupiedList;
    }

    // ==================== FEATURE 3: CHECK ROOM AVAILABILITY ====================

    /**
     * Search room by Room ID using HashMap lookup (O(1) time complexity).
     */
    @Override
    public Room searchByRoomId(String roomId) throws RoomNotFoundException {
        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException("Room ID cannot be empty.");
        }
        Room room = roomMap.get(roomId.trim().toUpperCase());
        if (room == null) {
            throw new RoomNotFoundException("Room with ID '" + roomId + "' not found in the system.");
        }
        return room;
    }

    /**
     * Search room by Room Number using manual Binary Search (O(log n) time complexity).
     */
    @Override
    public Room searchByRoomNumber(int roomNumber) throws RoomNotFoundException {
        if (roomNumber <= 0) {
            throw new IllegalArgumentException("Room number must be positive.");
        }
        // Prepare sorted list for binary search
        List<Room> sortedList = new ArrayList<>(roomList);
        RoomSortAndSearch.mergeSortByRoomNumber(sortedList);

        // Perform manual Binary Search
        Room found = RoomSortAndSearch.binarySearchByRoomNumber(sortedList, roomNumber);
        if (found == null) {
            throw new RoomNotFoundException("Room with Number " + roomNumber + " not found in the system.");
        }
        return found;
    }

    // ==================== FEATURE 4: ALLOCATE ROOM ====================

    @Override
    public boolean allocateRoom(String roomId, String studentId)
            throws RoomNotFoundException, RoomFullException, DuplicateAllocationException {

        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty.");
        }

        String normalizedStudentId = studentId.trim().toUpperCase();

        // 1. Prevent duplicate allocation across the entire hostel (O(1) HashSet check)
        if (activeAllocatedStudents.contains(normalizedStudentId)) {
            throw new DuplicateAllocationException("Student '" + normalizedStudentId 
                    + "' already has an active room allocation in the hostel!");
        }

        // 2. Locate room (throws RoomNotFoundException if absent)
        Room room = searchByRoomId(roomId);

        // 3. Check capacity (throws RoomFullException if at capacity)
        if (!room.isAvailable()) {
            throw new RoomFullException("Room " + room.getRoomId() + " (No. " + room.getRoomNumber() 
                    + ") is already at full capacity (" + room.getCapacity() + " beds).");
        }

        // 4. Perform room-level allocation
        boolean success = room.allocateStudent(normalizedStudentId);

        if (success) {
            // Track in HashSet and history
            activeAllocatedStudents.add(normalizedStudentId);
            RoomAllocation record = new RoomAllocation(room.getRoomId(), normalizedStudentId);
            allocationHistory.add(record);
        }

        return success;
    }

    // ==================== FEATURE 5: VACATE ROOM ====================

    @Override
    public boolean vacateRoom(String roomId, String studentId) throws RoomNotFoundException {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty.");
        }

        String normalizedStudentId = studentId.trim().toUpperCase();

        // 1. Locate room
        Room room = searchByRoomId(roomId);

        // 2. Remove student from room
        boolean removed = room.vacateStudent(normalizedStudentId);
        if (!removed) {
            throw new IllegalArgumentException("Student '" + normalizedStudentId 
                    + "' is not currently allocated to Room '" + room.getRoomId() + "'.");
        }

        // 3. Remove from active allocation tracker
        activeAllocatedStudents.remove(normalizedStudentId);

        // 4. Update status in allocation history
        for (RoomAllocation allocation : allocationHistory) {
            if (allocation.isActive() 
                    && allocation.getRoomId().equalsIgnoreCase(room.getRoomId()) 
                    && allocation.getStudentId().equalsIgnoreCase(normalizedStudentId)) {
                allocation.setActive(false);
                break;
            }
        }

        return true;
    }

    @Override
    public boolean isStudentAllocated(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            return false;
        }
        return activeAllocatedStudents.contains(studentId.trim().toUpperCase());
    }

    // ==================== FEATURE 6: ROOM ALLOCATION DETAILS ====================

    @Override
    public List<RoomAllocation> getAllAllocations() {
        return Collections.unmodifiableList(allocationHistory);
    }

    @Override
    public List<String> getStudentsInRoom(String roomId) throws RoomNotFoundException {
        Room room = searchByRoomId(roomId);
        return room.getAllocatedStudents();
    }

    @Override
    public String findRoomByStudentId(String studentId) {
        if (studentId == null || !activeAllocatedStudents.contains(studentId.trim().toUpperCase())) {
            return null;
        }
        return RoomService.super.findRoomByStudentId(studentId);
    }
}
