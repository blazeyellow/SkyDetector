# SkyDetector

## Hacker detection system

A system provided by a company allows customers to sign in using their username and password. There is a requirement for an automated system to be developed to help identify attempts to hack the system and compromise accounts. Activity log files are recorded and the new system will need to process these logs to identify suspicious activity.

### General requirements

Write a Java (or Groovy or Scala) program implementing the HackerDetector interface (outlined below) which defines a single method 'parseLine'. The method should take one line of the log file at a time and return the IP address if any suspicious activity is identified or null if the activity appears to be normal.

```java


package com.sky.detector;
public interface HackerDetector {
public String parseLine(String line);
}
```
The parseline method will be called each time a new log line is produced, log lines are passed in chronological order.

The log lines will be in the following format: ip,date,action,username

IP look like 80.238.9.179 Date is in the epoch format like 1336129471 Action is one of the following: SIGNIN_SUCCESS or SIGNIN_FAILURE Username is a String like Dave.Branning A log line will therefore look like this: 80.238.9.179,133612947,SIGNIN_SUCCESS,Dave.Branning

The first detection method will be to identify a single IP address that has attempted a failed login 5 or more times within a 5 minute period. On detection you should return the suspicious IP.

### Scenario

Here is an example:

|Input (log line)|	Expected Output	|Comments|
|----------------|------------------|--------|
|80.238.9.179,1000000,SIGNIN_FAILURE,Dave.Branning| |[1/5] First encounter|
|80.238.9.179,1010000,SIGNIN_FAILURE,Dave.Brand| |[2/5] Same ip, different username|
|80.238.9.179,1020000,SIGNIN_FAILURE,Mark.Branning| |[3/5] Same ip, other username. Maybe this guy is bruteforcing. I'll just wait.|
|192.168.0.1,1020000,SIGNIN_FAILURE,localhost| |[1/5] Someone is playing on the localhost. Never mind.|
|80.238.9.179,1025000,SIGNIN_SUCCESS,Joe.Branning| |Successful login. Don't bother with it.|
|80.238.9.179,1030000,SIGNIN_FAILURE,Joe.Branning| |[4/5] Same ip, didn't this guy already logged in ? Let's see what he does.|
|80.238.9.179,1040000,SIGNIN_FAILURE,Andy.Branning|80.238.9.179|[5/5] Fifth time in the last five minutes. I'm going to report you !|
|80.238.9.179,2000000,SIGNIN_FAILURE,Andy.Branning| |[1/5] Long time since we last saw you. I guess you are alright.|
