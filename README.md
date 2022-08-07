# Simple CronExpressionParser
The purpose of this application is to parse cron like syntax and print all possible
values for each group.

Parser expect that all 6 parameters would be provided. Each different parameters 
separated by space:

**Parameter 1 - Minute (range between 0 - 59)**

**Parameter 2 - Hour (range between 0 - 23)**

**Parameter 3 - Day Month (range between 1 - 31)**

**Parameter 4 - Month (range between 1 - 12)**

**Parameter 5 - Day (range between 0 - 6)**

**Parameter 6 - Command (any string including whitespaces)**

Each parameter accept following expressions:
{number} - any number in the range defined in for a parameter 
(e.g. for a parameter 2 range is between 0 - 23)

#No spaces allowed in expression!!!

```
'*' - every single value in the defined range 
(e.g. for parameter 5 it would be 0,1,2,3,4,5,6)
```
```
'*/{number}' - all step values in the defined range
(e.g. for parameter 5 and the {number} = 2 it would be 0,2,4,6)
```
```
'*/{number1},{number2},{number3}' - all values listed in the defined range 
(e.g. for parameter 5 and {number1} = 2, {number2} = 3 it would be 0,2,3,4,6)
'*/{number}-{number}'
```
```
'*/{number1}-{number2}' - NOT SUPPORTED
```
```
'{number}' - value listed in the defined range
(e.g. for paramter 5 and {number} = 3 it would be 3)
```
```
'{number1},{number2},{number3}' - all values listed in the defined range 
(e.g. for parameter 5 and {number1} = 1, {number2} = 2, {number3} = 4 it would be 1,2,4)
'*/{number}-{number}'- 
```
```
'{number1}-{number2}'  - all values inclusing in the range between {number1} and {number2}
(e.g. for parameter 5 and {number1} = 1, {number2} = 3 it would be 1,2,3)
```

#What would happen if the expression is wrong?
An application will throw exception an exception and finish a work.

##What is required for an application to work?
*Java 16
*Maven 3.8.1

Please be aware that your system should have JAVA_HOME path configured

##How to compile project?
```
mvn clean install
```
##How to run tests?
```
mvn clean test
```
##How to run an application?
```
java -jar target/cronexpressionparser-1.0-SNAPSHOT-jar-with-dependencies.jar "*/15 0 1,15 * 1-5 /usr/bin/find"
```
##How the results should look like ? 
```
Cron Job expression: */15 0 1,15 * 1-5 /usr/bin/find 

minute        0  15 30 45 
hour          0  
day of month  1  15 
month         1  2  3  4  5  6  7  8  9  10 11 12 
day of week   1  2  3  4  5  
command       /usr/bin/find
```

