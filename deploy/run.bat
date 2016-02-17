@echo off
set/p filename=Please Input File Name:
java -Dfile.encoding=UTF-8 -jar CfgGen.jar "F:\\work\\product\\数值表格" "D:\\config" "%filename%"
pause