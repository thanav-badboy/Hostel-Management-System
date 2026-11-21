import java.util.ArrayList;
import java.util.List;

/**
 * Utility helper to perform batch room allocations for group admissions
 * while respecting room capacity and duplicate allocation guards.
 */
public class BatchAllocationHelper {

    public static class AllocationResult {
        private final List<String> successfulAllocations = new ArrayList<>();
        private final List<String> failedAllocations = new ArrayList<>();

        public void addSuccess(String studentId, String roomId) {
            successfulAllocations.add("Student " + studentId + " -> Room " + roomId);
        }

        public void addFailure(String studentId, String roomId, String reason) {
            failedAllocations.add("Student " + studentId + " -> Room " + roomId + " (Failed: " + reason + ")");
        }

        public List<String> getSuccessfulAllocations() {
            return successfulAllocations;
        }

        public List<String> getFailedAllocations() {
            return failedAllocations;
        }
    }

    /**
     * Allocates a list of students to a target room.
     */
    public static AllocationResult allocateBatch(RoomService roomService, String roomId, List<String> studentIds) {
        AllocationResult result = new AllocationResult();

        if (roomService == null || roomId == null || studentIds == null) {
            return result;
        }

        for (String studentId : studentIds) {
            try {
                boolean success = roomService.allocateRoom(roomId, studentId);
                if (success) {
                    result.addSuccess(studentId, roomId);
                }
            } catch (RoomNotFoundException | RoomFullException | DuplicateAllocationException e) {
                result.addFailure(studentId, roomId, e.getMessage());
            }
        }
        return result;
    }
}
