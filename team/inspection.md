# Inspection - Over9000 *T09* 
 
Inspection | Details
----- | -----
Subject | *Microserver.java lines 0-328*
Meeting | *04/24/18, 10:01, CS120 Lab*
Checklist | *http://users.csc.calpoly.edu/~jdalbey/301/Forms/CodeReviewChecklistJava.pdf*

### Roles
Name | Role | Preparation Time
---- | ---- | ----
Luke Burford | Moderator | 1 hour
Drew Boston | Tester | 50 mins
Jake Marrapode | Maintainer | 45 mins
Jake Servaty | End User | 35 mins

### Log
file:line | defect | h/m/l | who found | github# 
--- | --- |:---:|:---:| ---
 MicroServer.java:139-142| Ambigous statement return of response.| m | Jake Marrapode | #323
 MicroServer.java: all javadoc comments| missing return statements.| l | Drew Boston | #324
 MicroServer.java | Remove custom RestAPIs for interopt.| m | Jake Servaty | #325
 MicroServer.java | no error status code for query, optimize and edit.| m | Jake Marrapode | #326
 MicroServer.java:317-326| Number of optimizations is hard coded.| m | Drew Boston | #327
 MicroServer.java:316 | No javadoc for helper method.| l | Luke Burford | #328
 MicroServer.java:271-294 | Complexity of nested if statements is too high.| m | Jake Servaty | #329
