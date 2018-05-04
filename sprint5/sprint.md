# Sprint 5 - *t09* - *OVER 9 THOUSAND!?*

## Goal

### Worldwide Trip Planning! Improved Usability, Speed, and Testing.
### Sprint Leader: *Jake Marrapode*

## Definition of Done

* Web application deployed on the production server (kiwis.cs.colostate.edu).
* Sprint Review and Restrospectives completed (sprint.md).
* Product Increment release `v5.0` created on GitHub with appropriate version number and name, a description based on the Release Notes template, and the archived files.
* Version in pom.xml should be `<version>5.0</version>`.
* Javadoc and unit tests for public methods.
* System testing for database REST APIs, database, and JavaScript
* Coverage at least 60% overall and for each class.
* Code Maintainability rated at an A


## Policies

* Code adheres to Google style guides for Java and JavaScript.
* Tests and Javadoc are written before/with code.  
* All pull requests include tests for the added or modified code.
* Master is never broken.  If broken, it is fixed immediately.
* Always check for new changes in master to resolve merge conflicts locally before committing them.
* All changes are built and tested before they are committed.
* Continuous integration successfully builds and tests pull requests for master branch always.
* All commits with more than 1 line of change include a task/issue number.
* All Java dependencies in pom.xml.

## Plan

Epics planned for this release.

* *Update TFFI*
* *Zoom and Pan the Map*
* *Save my Options as Defaults for future use*
* *Shorter Trips #3*
* *Interop*
* *System testing*
* *Staff Page*
* *Speed up the computation on the server*
* *View trip in other tools*
* *Improve the user experience*
* *Give the user the option to choose what information to display in the itinerary*


## Metrics

Statistic | Planned | Completed
--- | ---: | ---:
Tasks |  *34*   | *26* 
Story Points |  *61*  | *44* 

## Daily Scrums

Date | Tasks done  | Tasks in progress | Impediments 
:--- | :--- | :--- | :--- 
*4/17* | *0* | *13* | Node issues
*4/19* | *0* | *13* | Node issues
*4/24* | *0* | *13* | Node issues, working on code inspections
*4/25* | *0* | *13* | Fixed node issues
*5/1*  | *3* | *20* | Not enough time to complete epics testing or display additional information
*5/2*  | *4* | *22* | Not enough time to finish epic of displaying trip in other applications
 

## Review

#### Completed epics in Sprint Backlog 
* *Update TFFI*
* *Zoom and Pan the Map*
* *Save my Options as Defaults for future use*
* *Shorter Trips #3*
* *Interop*
* *Staff Page*
* *Speed up the computation on the server*
* *Improve the user experience*

#### Incomplete epics in Sprint Backlog 
* *System testing*
* *View trip in other tools*
* *Give the user the option to choose what information to display in the itinerary*

#### What went well
* *We were able to combine what work we had done the first two weeks of the sprint into a working product very quickly before the end of the sprint. While there wasn't a lot of GitHub activity,
much of the work on the more complicated parts of the sprint was being done and we were able to combine it with a fairly small amount of difficulty at the end.*
* *Great teamwork and communciation on the last day of the sprint to get everything together. We had the whole team working on fixing setbacks and every member was able to change what they were 
doing in order to help out with what was needed the most.*

#### Problems encountered and resolutions
* *With this being the last sprint and at the end of the semester, we ran into time issues where the team had to prioritize some other classes and assignments. This led us to struggle with the 
process as we were unable to get the project done at a consistent pace over the course of the sprint and had to rely on a rush in the last few days to get done what we could.*
* *There were a few issues with some of the technologies being used, where differing installations meant some features like cookies would work differently on different environments.*
* *For the first week of the sprint, Jake M.'s project would not build due to some node.js issues. This was resolved with the help of Chris, but it hindered his progress significantly at the 
beginning of the sprint.*

## Retrospective

Topic | Teamwork | Process | Tools
:--- | :--- | :--- | :---
What we changed for this Sprint | Better communication when we needed help, and the team was willing to all commit to fixing a problem that was holding back part of the project. | We didn't change much of the process this sprint. We still used the process for reviewing code and that worked just as well as it had in the past, but our process issues remained the same. | The team switched around a lot of which technologies each member had primarily focused on last sprint. People who did a lot of client side work switched to more server side efforts, which gave us a bit more flexibility in what each person was able to do and a better understanding of how the project was integrated. 
What we did well | Our teamwork at the end of the sprint to get all the work we had done mostly on our own integrated as great. We were able to put the pieces together and all contribute into fixing whatever issues came up during that process. | We continued with our consistent use of feature priorization, and while our burndown chart wasn't the best in this sprint a large reason why we were able to integrate our work came from good initial sprint planning. | We picked up new technologies fairly quickly in this sprint, being able to get KML, GoogleMaps, Interopt and concurrency all working well.
What we need to work on | This is a repeat from last sprint, where that status of the project was unclear and we didn't always know what needed more time/people to help get done. We again ended up having a rush to finish in the last couple of days. | The part of the process we overlooked most this sprint was the daily scrums, we sort of lost focus on the point of them and often didn't discuss what is intended to be discussed and largely they consisted of informal conversations about what was going on. | Our capabilities in JavaScript were the primary limiting factor in this sprint/project. None of the team really had much JS experience coming in, and so most of it was learned on the fly. While we were able to get most of the features working, they aren't always stable or conducive to a good user experience.
What we will change next time | This is the final sprint of the semester, but regardless what we would change if we had another sprint would but mostly what is explained in the what we need to work on row. We needed to better communicate the status of the project and get some of the smaller things working earlier on. | Focusing on the important points of the process and not overlooking the small things that make it worthwhile and useful. Especially true for daily scrums, which would help us understand the status of the project better. | Using some of the IntelliJ integration for the maintainability would help us improve our code, as well as implementing other testing technologies that are not only part of the project but would ensure that our project is running correctly and in a stable fashion.