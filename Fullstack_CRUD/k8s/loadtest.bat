@echo off
setlocal enabledelayedexpansion

:: URL ko set karen
set URL=http://localhost:7001/api/v1/employees

:: Total requests kitni bhejni hain
set requests=1000

:: Loop se requests bhejne ka kaam
for /L %%i in (1,1,%requests%) do (
    echo Request %%i bheja ja raha hai...
    curl -s -o nul %URL%
)

echo Testing complete!
pause
