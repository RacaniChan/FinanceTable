$logs = @(
    'C:\Users\Yhane\scoop\apps\gradle\current\\.gradle\\daemon\\9.5.1\\daemon-26676.out.log',
    'C:\Users\Yhane\scoop\apps\gradle\current\\.gradle\\daemon\\9.5.1\\daemon-14192.out.log'
)

Write-Output "Watching Gradle daemon logs..."
while ($true) {
    foreach ($log in $logs) {
        if (Test-Path $log) {
            $tail = Get-Content $log -Tail 80 -ErrorAction SilentlyContinue | Out-String
            if ($tail -match 'BUILD SUCCESSFUL') {
                Write-Output "FOUND: BUILD SUCCESSFUL in $log"
                exit 0
            } elseif ($tail -match 'BUILD FAILED') {
                Write-Output "FOUND: BUILD FAILED in $log"
                Write-Output $tail
                exit 2
            } elseif ($tail -match 'Stop requested' -or $tail -match 'Daemon vm is shutting down' -or $tail -match 'Could not resolve' -or $tail -match 'Resource missing' -or $tail -match 'ERROR') {
                Write-Output "FOUND: ERROR/STOP in $log"
                Write-Output $tail
                exit 3
            }
        }
    }
    Start-Sleep -Seconds 5
}
