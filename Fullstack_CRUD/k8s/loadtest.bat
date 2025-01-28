@echo off
setlocal enabledelayedexpansion

:: URL ko set karen
set URL=http://backend.local:8081/api/v1/employees

:: Total requests kitni bhejni hain
set requests=1000

:: Loop se requests bhejne ka kaam
for /L %%i in (1,1,%requests%) do (
    echo Request %%i Successfully Request Send...
    curl -s -o nul %URL%
)

echo Testing complete!
pause
