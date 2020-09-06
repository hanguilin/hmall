@echo off
title pig-auth
:: 项目基础绝对路径，由start-up.bat传入
set BASE=%1
java -Dfile.encoding=utf-8 -jar %BASE%pig-auth\target\pig-auth.jar