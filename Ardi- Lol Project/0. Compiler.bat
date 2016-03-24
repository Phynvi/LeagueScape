@echo off
title [ARDI] Compile
:c
echo [ARDI] The classes is compiling, please wait...
"C:\arquivos de programas\Java\jdk1.7.0_51\bin\javac.exe" -classpath deps/log4j-1.2.15.jar;deps/jython.jar;deps/xstream.jar;deps/netty.jar;deps/mysql.jar;deps/poi.jar;deps/slf4j.jar;deps/slf4j-nop.jar -d bin src\ardi\event\*.java src\ardi\model\items\*.java src\ardi\model\minigames\*.java src\ardi\model\npcs\*.java src\ardi\model\objects\*.java src\ardi\model\players\*.java src\ardi\model\players\skills\*.java src\ardi\model\players\packets\*.java src\ardi\model\shops\*.java src\ardi\task\*.java src\ardi\util\*.java src\ardi\world\*.java src\ardi\util\log\*.java src\ardi\*.java src\ardi\content\skill\*.java src\ardi\net\login\*.java src\ardi\clip\*.java src\ardi\clip\region\*.java src\ardi\net\*.java src\ardi\net\login\*.java src\ardi\model\players\achievement\*.java src\ardi\model\players\packets\action\*.java src\ardi\model\players\combat\range\*.java src\ardi\model\players\combat\melee\*.java src\ardi\model\players\combat\magic\*.java src\ardi\model\players\combat\*.java
pause
echo [ARDI] The classes has been compiled sucessfully!