@echo off
title pig-gateway
:: 项目基础绝对路径，由start-up.bat传入
set BASE=%1
java -Dfile.encoding=utf-8 -jar %BASE%pig-gateway\target\pig-gateway.jar