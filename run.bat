@echo off
if not exist bin (
    echo [INFO] Bin folder not found. Building project first...
    call build.bat
)

echo Starting College Hostel Management System...
java -cp bin UnifiedHostelApp
pause
