title all-base-service
set pwd=%~dp0
set BASE=%pwd%\bat
start cmd /k %BASE%\pig-register.bat %pwd%
:: 等待两分钟
choice /t 120 /d y
start cmd /k %BASE%\pig-gateway.bat %pwd%
start cmd /k %BASE%\pig-upms-biz.bat %pwd%
start cmd /k %BASE%\pig-auth.bat %pwd%
exit