# College Hostel Management System - Room Management Module (Member 2)

**Author:** Member 2  
**Role:** Lead Developer - Room Management Module  
**Course Project:** College Hostel Management System (Console-based Java Application)  
**Assigned Module:** Room Management Module ONLY  

---

## 📌 1. Project Overview & Scope
This project implements the independent **Room Management Module** for a 4-member distributed College Hostel Management System.

### Team Responsibilities:
- **Member 1:** Student Management (Student registration, profile, contact info)
- **Member 2 (This Repository):** Room Management (Room inventory, categories, allocations, capacity, DSA)
- **Member 3:** Fee Management
- **Member 4:** Complaint Management

> **Integration Anchor:** `studentId` is used as the common identifier between Member 1 and Member 2. No student records are duplicated in this module.

---

## 🚀 2. Implemented Features
1. **Add Room:**
   - Single Room (Capacity: 1, Rent: ₹7,500)
   - Double Room (Capacity: 2, Rent: ₹4,500)
   - Triple Room (Capacity: 3, Rent: ₹3,000)
   - Validation for duplicate Room IDs and duplicate Room Numbers.
2. **View Rooms:**
   - View All Rooms (Sorted by room number using **manual Merge Sort**).
   - View Available Rooms (Displays rooms with available beds).
   - View Occupied Rooms (Displays rooms that are completely full).
3. **Check Room Availability:**
   - Search by Room ID ($O(1)$ HashMap lookup).
   - Search by Room Number ($O(\log n)$ **manual Binary Search**).
   - Displays occupancy, remaining capacity, status, and rent.
4. **Allocate Room:**
   - Assigns student to room using `studentId`.
   - Prevents duplicate allocation across the entire hostel ($O(1)$ HashSet check).
   - Prevents allocation beyond room capacity.
   - Automatically synchronizes room status to `OCCUPIED` when full.
5. **Vacate Room:**
   - Removes a student from a room.
   - Automatically restores room availability to `AVAILABLE`.
6. **Room Allocation Details:**
   - Lists all rooms with student occupants.
   - Comprehensive allocation audit log.
7. **Member 1 Integration Helper:**
   - Mock student roster with IDs (`STU101` through `STU105`) for live demonstration.

---

## ☕ 3. Technical Requirements & Concepts Demonstrated

### A. Object-Oriented Programming (OOP)
1. **Abstraction:**
   - Abstract class `Room` defines the abstract template (`getRoomType()`, `getMonthlyRent()`, `displayRoomDetails()`) while hiding internal status synchronization.
   - Interface `RoomService` defines pure business contracts decoupled from implementation.
2. **Inheritance:**
   - `SingleRoom extends Room`
   - `DoubleRoom extends Room`
   - `TripleRoom extends Room`
3. **Polymorphism:**
   - **Runtime Polymorphism (Method Overriding):** Subclasses override `displayRoomDetails()`, `getRoomType()`, and `getMonthlyRent()`. In `Main.java`, calling `room.displayRoomDetails()` dynamically executes the subclass behavior.
   - **Compile-time Polymorphism (Method Overloading):** Search capability overloaded by `searchByRoomId(String)` and `searchByRoomNumber(int)`.
4. **Encapsulation:**
   - All state fields (`roomId`, `roomNumber`, `capacity`, `status`, `allocatedStudents`) are `private`.
   - Accessed exclusively through getters/setters with strict boundary validation.

### B. Data Structures & Algorithms (DSA - Implemented Manually)
Located in `src/RoomSortAndSearch.java` (implemented completely from scratch):
1. **Merge Sort Algorithm:**
   - **Implementation:** Manual divide-and-conquer recursion and merge helper.
   - **Time Complexity:** $O(n \log n)$ best, average, and worst case.
   - **Usage:** Sorts rooms by `roomNumber` in Feature 2 (View Rooms) and prepares data for Binary Search.
2. **Binary Search Algorithm:**
   - **Implementation:** Manual iterative binary search on sorted rooms.
   - **Time Complexity:** $O(\log n)$.
   - **Usage:** Feature 3 (Search Room Availability by Room Number).

### C. Java Collections Framework
- **`ArrayList<Room>`:** Maintains dynamic, resizable ordered lists of rooms and allocation audit records.
- **`HashMap<String, Room>`:** Maps `roomId -> Room` for instantaneous $O(1)$ lookup.
- **`HashSet<String>`:** Stores all active allocated `studentId`s to enforce $O(1)$ duplicate allocation checks.

### D. Custom Exceptions
1. **`RoomNotFoundException`:** Thrown when a room ID or number does not exist.
2. **`RoomFullException`:** Thrown when an allocation exceeds room capacity.
3. **`DuplicateAllocationException`:** Thrown when a student attempts to occupy multiple rooms.

---

## 📂 4. File Structure
```
Hostel-Management-Room-Module/
├── .gitignore
├── README.md
├── MEMBER_1_INTEGRATION.md
└── src/
    ├── Room.java                     # Abstract base model (Abstraction & Encapsulation)
    ├── SingleRoom.java               # Concrete subclass (Inheritance & Polymorphism)
    ├── DoubleRoom.java               # Concrete subclass (Inheritance & Polymorphism)
    ├── TripleRoom.java               # Concrete subclass (Inheritance & Polymorphism)
    ├── RoomStatus.java               # Enum for room state (AVAILABLE, OCCUPIED, etc.)
    ├── RoomAllocation.java           # DTO model for allocation records
    ├── RoomSortAndSearch.java        # Manual DSA: Merge Sort & Binary Search
    ├── RoomNotFoundException.java    # Custom Exception
    ├── RoomFullException.java        # Custom Exception
    ├── DuplicateAllocationException.java # Custom Exception
    ├── RoomService.java              # Interface defining module operations
    ├── RoomManager.java              # Implementation using Collections & DSA
    ├── StudentIntegrationHelper.java # Member 1 integration adapter & mock data
    └── Main.java                     # Interactive console application (Scanner)
```

---

## 💻 5. Compilation & Execution Commands

### To Compile:
```powershell
javac src/*.java
```

### To Run:
```powershell
java -cp src Main
```

---

## 🎯 6. Quick Viva / Demonstration Walkthrough
When demonstrating to your faculty or examiner:
1. **Option 2 -> 1:** View All Rooms (Show that rooms are neatly sorted by Room Number via manual **Merge Sort**).
2. **Option 3 -> 2:** Search Room Number `102` (Explain manual **Binary Search** running in $O(\log n)$ time).
3. **Option 4:** Allocate `STU103` into room `R102`. Show capacity decrease.
4. **Option 4 (Duplicate Check):** Try to allocate `STU103` again into another room. Show custom **`DuplicateAllocationException`**.
5. **Option 4 (Full Capacity Check):** Allocate `STU104` to `R101` (Single Room, already full). Show custom **`RoomFullException`**.
6. **Option 5:** Vacate `STU101` from `R101`. Show that room status resets to `Available`.
7. **Option 6:** Show Allocation Details & Audit logs.
8. **Option 7:** Show Member 1 Integration contract.

---

## 🌐 8. Unified System Architecture & Master Execution Guide

With the integration of **Member 1 (Student Management Module)** and **Member 2 (Room Management Module)**, the College Hostel Management System operates as an interconnected multi-module ecosystem.

### A. Master Console Entry Point (`UnifiedHostelApp`)
To launch the integrated multi-module dashboard:
```powershell
# Using the automated build and run scripts:
.\build.bat
.\run.bat

# Or manually:
javac -d bin (Get-ChildItem -Recurse -Filter *.java src).FullName
java -cp bin UnifiedHostelApp
```

### B. Master Automated Verification Suite
Run all unit and integration tests across both modules in one command:
```powershell
java -cp bin MasterTestSuite
```
This automatically verifies:
1. **Manual Singly Linked List DSA:** Tail insertion, node traversal search, and pointer-relinking deletion.
2. **Student Management Business Logic:** Unique ID assignment, duplicate prevention, regex input validation, and custom exceptions.
3. **Cross-Module Student-Room Integration:** Real-time student verification, room allocation, duplicate blocking, and reverse student-to-room searching.

### C. Future Module Integration Hooks
- **Fee Management (Member 3):** Hooks into `FeeModuleHook` to query student billing records and calculate room rent.
- **Complaint Management (Member 4):** Hooks into `ComplaintModuleHook` to authenticate resident complainants.

