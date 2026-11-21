param(
    [string]$JdkBin
)

$ErrorActionPreference = 'Stop'
$javac = 'javac'
$java = 'java'
if ($JdkBin) {
    $javac = Join-Path $JdkBin 'javac.exe'
    $java = Join-Path $JdkBin 'java.exe'
}

$projectRoot = Split-Path -Parent $PSScriptRoot
$buildDir = Join-Path $projectRoot 'out/complaint-smoke'
$sources = Get-ChildItem -LiteralPath $projectRoot -Filter '*.java' -File
& $javac -Xlint:all -d $buildDir @($sources.FullName)
if ($LASTEXITCODE -ne 0) {
    throw "Compilation failed with exit code $LASTEXITCODE."
}

# Each string supplies one line of input to the Scanner menu.
$menuInput = @(
    '1', 'C001', 'S001', 'Plumbing', 'Tap leaks'
    '2'
    '5', 'C001'
    '3', 'C001'
    '0'
)
$outputLines = $menuInput | & $java -cp $buildDir Main
if ($LASTEXITCODE -ne 0) {
    throw "The console app exited with code $LASTEXITCODE."
}
$output = $outputLines -join "`n"

# Check each result in order, including the status shown by the list command.
$expectedResults = @(
    'Complaint registered with status Pending.'
    "Complaint ID: C001`nStudent ID: S001`nCategory: Plumbing`nDescription: Tap leaks`nStatus: Pending`n"
    'Complaint deleted.'
    'Error: Complaint not found: C001'
    'Choose an option: Exiting complaint management.'
)
$position = 0
foreach ($expected in $expectedResults) {
    $match = $output.IndexOf($expected, $position, [StringComparison]::Ordinal)
    if ($match -lt 0) {
        throw "Smoke test failed. Expected output: $expected`n`nActual output:`n$output"
    }
    $position = $match + $expected.Length
}

Write-Output 'PASS: Registered, listed as Pending, deleted, confirmed missing, and exited.'
