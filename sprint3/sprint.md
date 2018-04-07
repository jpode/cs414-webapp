# Sprint 3 - *t09* - *OVER NINE THOUSAND!?*

## Goal

### A mobile, responsive map and itinerary!
### Shorter trips, and more places to go!
### Cleaner Code, with improved Coverage!
### Allow reverse order!

### Sprint Leader: *Jacob Servaty*

## Definition of Done

* Web application deployed on the production server (kiwis.cs.colostate.edu:31409)
* Sprint Review and Restrospectives completed (sprint.md).
* Product Increment release `v3.0` created on GitHub with appropriate version number and name, a description based on the Release Notes template, and the arhived files.
* Version in pom.xml should be `<version>3.0.0</version>`.
* Javadoc and unit tests for public methods.
* Coverage at least 50% overall and for each class.

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

* *#139 Clean Code*
* *#140 Code Coverage*
* *#148 Update Itinerary from database selection*
* *#159 Give the user an option to view a shorter trip*
* *#162 Let the user reverse the order of the trip*

In Sprint2 we began with around 38/39 initial tasks to complete, and we've started with a similar amount for Sprint3. If our velocity stays the same, then we should keep our predictable pace.

## Metrics

Statistic | Planned | Completed
--- | ---: | ---:
Tasks |  *28*   | *25* 
Story Points |  *57*  | *52* 

## Daily Scrums

Date | Tasks done  | Tasks in progress | Impediments 
:--- | :--- | :--- | :--- 
*2/28* | *5* | *12* | none
*3/7* | *7* | *11* | none
*3/21* | *18* | *9* | Spring Break pushed us back farther than expected
 | | | 
 

## Review

#### Completed epics in Sprint Backlog 
* *#139 Clean Code: Improve the quality of our code by lowering our technical debt ratio so our code is more maintainable, Adopt Google coding standards*
* *#140 Code Coverage: Improve our code coverage*
* *#159 Give the user an option to view a shorter trip: Implement Nearest Neighbor and 2-opt algorithms*
* 

#### Incomplete epics in Sprint Backlog 
* *#162 Let the user reverse the order of the trip: Wasn't prioritized, and assignee had poor time management*
* *#148 Update Itinerary from database selection: was mostly completed, but client-side bugs caused us from including it in our release and demo*
*

#### What went well
* *We were able to utilize our previous (and concurrent) experience from CS320 (Algorithms) to implement the Nearest Neighbor and 2-opt algorithms, and we're already far along with 3-opt!*
* *We have RESTAPI down, and are improving our understanding of Client-Server communications*
*

#### Problems encountered and resolutions
* *Spring Break actually hurt us more than we expected. Dave was right!* 
* *We had almost completed our Database Epic (#148), but were derailed by confusing Client-side bugs*
*

## Retrospective

Topic | Teamwork | Process | Tools
:--- | :--- | :--- | :---
What we will change this time | Ask for help from teammates sooner | Splitting up tasks/issues into even smaller, more managable chunks | Focus less on Code Climate, Utilize Intellij functionality more, better understanding of SQL and MariaDB 
What we did well | Better communication over the course of each week | Good job of adding additional tasks as Issues grew | Good use of ZenHub! Improved use of Intellij
What we need to work on | Cycling tasks between teammates, so workload rotates | Improve initial estimates for time on ZenHub, but producing smaller chunks | better understanding of SQL and MariaDB
What we will change next time | More consistent inperson meetings/daily scrums | Take longer estimating time for tasks | Improve DB utilization
