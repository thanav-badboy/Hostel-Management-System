# Hostel Complaint Management

Member 4's Core Java console module. It registers complaints as Pending, lists all
complaints, searches by complaint ID, updates status, deletes by complaint ID,
and shows a student's complaints using their shared student ID.

## Run in PowerShell

Use a JDK (Java 8 or newer) with `java` and `javac` on your PATH. From the
project directory, run:

```powershell
javac -Xlint:all -d out *.java
java -cp out Main
```

If the Oracle Java launcher on PATH hangs, use the installed JDK directly.
On this workstation the commands are:

```powershell
& 'C:\Program Files\Java\jdk-25\bin\javac.exe' -Xlint:all -d out *.java
& 'C:\Program Files\Java\jdk-25\bin\java.exe' -cp out Main
```

IDs are case-sensitive text. Spaces at the edges of inputs are trimmed. Data
is held in memory for the current run. Deleting a complaint also removes it
from the student's history. The status menu allows any of the three statuses
to be selected.

## Integration

Keep one service instance for the lifetime of the hostel application.

```java
ComplaintService complaints = new ComplaintManager();
```

Use your existing student ID when calling `registerComplaint`. The public
method contracts in `ComplaintService.java` document parameters, results and
exceptions. Use `Complaint.Status` for updates. The larger application can call
the service directly from its own menu and handle the checked exceptions there.

`Complaint` encapsulates its fields in immutable objects. `ComplaintService`
provides abstraction, and `Main` uses the interface polymorphically with a
`ComplaintManager` instance. The two custom checked exceptions inherit from
`Exception`. The manager stores records in an `ArrayList` and manually scans it
by complaint ID in O(n) worst-case time. Student history also uses a linear scan.

## Quick manual check

1. Register `C001` and `C002` for `S001`, then register `C003` for `S002`.
2. List all complaints and confirm all three start as Pending. Search for
   `C001` and check that all five fields are shown.
3. Register `C001` again and check for a duplicate error.
4. Update `C001` to In Progress, then Resolved, then Pending. Try an invalid
   status choice and confirm the status stays Pending.
5. View history for `S001` and confirm it contains only `C001` and `C002`.
6. Delete `C001`, confirm it is absent from the list and history, and search
   for it to see the missing complaint error.
