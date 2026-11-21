import com.hostel.student.dsa.DsaTest;
import com.hostel.student.service.StudentServiceTest;

/**
 * Master Test Suite runner executing all module-level and integration-level tests.
 */
public class MasterTestSuite {

    public static void main(String[] args) {
        System.out.println("===================================================================");
        System.out.println("       RUNNING MASTER SYSTEM TEST SUITE (ALL MODULES)              ");
        System.out.println("===================================================================\n");

        int passed = 0;
        int failed = 0;

        // Test 1: Manual DSA Linked List
        try {
            System.out.println("--- [TEST 1/3] Custom DSA Linked List Verification ---");
            DsaTest.main(new String[]{});
            passed++;
        } catch (Throwable t) {
            System.err.println("[FAIL] DSA Test Suite failed: " + t.getMessage());
            failed++;
        }

        // Test 2: Student Management Business Logic
        try {
            System.out.println("\n--- [TEST 2/3] Student Service & Exception Verification ---");
            StudentServiceTest.main(new String[]{});
            passed++;
        } catch (Throwable t) {
            System.err.println("[FAIL] Student Service Test Suite failed: " + t.getMessage());
            failed++;
        }

        // Test 3: Student-Room Cross-Module Integration
        try {
            System.out.println("\n--- [TEST 3/3] Cross-Module Student-Room Integration Verification ---");
            StudentRoomIntegrationTest.main(new String[]{});
            passed++;
        } catch (Throwable t) {
            System.err.println("[FAIL] Student-Room Integration Test Suite failed: " + t.getMessage());
            failed++;
        }

        System.out.println("===================================================================");
        System.out.printf(" MASTER TEST SUITE RESULTS: %d PASSED | %d FAILED%n", passed, failed);
        System.out.println("===================================================================");

        if (failed > 0) {
            System.exit(1);
        }
    }
}
