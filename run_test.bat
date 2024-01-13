@echo off
echo Executing Selenium tests using Maven

cd %~dpO

mvn clean install

mvn test -DsuiteXmlFile=parameter.xml

echo Selenium tests execution completed.
pause