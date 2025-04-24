@echo off
echo Generating Allure report...
call mvn test -DsuiteXmlFile=testng.xml
call mvn allure:report
echo Report generated at target/allure-reports
echo Copying report to shared folder...
xcopy /E /I /Y target\allure-reports Reports\latest-run
echo Done! Report is available at Reports\latest-run