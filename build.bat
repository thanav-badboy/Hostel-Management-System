@echo off
echo =======================================================
echo   BUILDING COLLEGE HOSTEL MANAGEMENT SYSTEM
echo =======================================================

if not exist bin mkdir bin

echo [1/3] Compiling Member 1 (Student Module)...
javac -d bin src/com/hostel/student/model/*.java src/com/hostel/student/exception/*.java src/com/hostel/student/dsa/*.java src/com/hostel/student/util/*.java src/com/hostel/student/service/*.java src/com/hostel/student/main/*.java
if %errorlevel% neq 0 (
    echo [ERROR] Student module compilation failed!
    exit /b %errorlevel%
)

echo [2/3] Compiling Member 2 (Room Module) and Integration Hub...
javac -cp bin -d bin src/*.java
if %errorlevel% neq 0 (
    echo [ERROR] Room module compilation failed!
    exit /b %errorlevel%
)

echo [3/3] Build Completed Successfully!
echo =======================================================
echo Run 'run.bat' to launch the Unified Hostel Hub.
echo =======================================================
