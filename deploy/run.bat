@echo off
set/p filename=Please Input File Name:
java -Dfile.encoding=UTF-8 -jar CfgGen.jar "F:\\work\\product\\��ֵ���" "D:\\config" "%filename%"
pause