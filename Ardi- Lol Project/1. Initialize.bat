@echo off
title Ardi 3.0
"C:\arquivos de programas\Java\jdk1.7.0_51\bin\java.exe" -Xmx1024m -cp bin;deps/poi.jar;deps/netty.jar;deps/mysql.jar;deps/slf4j.jar;deps/slf4j-nop.jar;deps/jython.jar;log4j-1.2.15.jar; ardi.Server
pause