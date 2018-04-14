# Sprint 4 - *t09* - *OVER 9 THOUSAND!?*

## Goal

### Worldwide Trip Planning! Improved Usability, Speed, and Testing.
### Sprint Leader: *Drew Boston*

## Definition of Done

* Web application deployed on the production server (kiwis.cs.colostate.edu).
* Sprint Review and Restrospectives completed (sprint.md).
* Product Increment release `v5.0` created on GitHub with appropriate version number and name, a description based on the Release Notes template, and the archived files.
* Version in pom.xml should be `<version>5.0</version>`.
* Javadoc and unit tests for public methods.
* System testing for database REST APIs, database, and JavaScript
* Coverage at least 60% overall and for each class.
* Code Maintainability at A


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
* *save my Options as Defaults for future use*


Surprisingly, this sprint has 65 story points, as compared to 52 for last sprint. Unfortunately, we were not able to accomplish all story points for last sprint; however, we have discussed this as a team and feel we are fully capable of taking on this amount. Spring break was tough, and we had a few discussions that leads us to believe this is very achievable.

## Metrics

Statistic | Planned | Completed
--- | ---: | ---:
Tasks |  *30*   | ** 
Story Points |  *64*  | ** 

## Daily Scrums

Date | Tasks done  | Tasks in progress | Impediments 
:--- | :--- | :--- | :--- 
*4/17* | ** | ** | 
 | | | 
 

## Review

#### Completed epics in Sprint Backlog 
* *Let user reverse order of trip*:  *Very quick to render new trip, good client side UI!*
* *Speed up computation on server*:  *Our 2-Opt and NN is now very, very quick. Only further improvements will be threads.*
* *Build a trip from existing information*:  *We now accept multiple TFFI files, differing distance units, optimization levels, etc.*
* *Design a trip from scratch / manually*:  *You can now create a trip entirely from locations either queried from a database(s), or manually input locations.*
* *Plan trips outside of Colorado*:  *We can now plan trips all over the world! All features that used to be exclusive to Colorado have been scaled to global proportions.*
* *Filtered Search*:  *Not only can you query for locations, you can filter the search results!*
* *Client-side UI improvements*:  *We made MASSIVE improvements to our UI and user-friendliness. Although still not perfect, we eliminated all known bugs, worked on better test cases, and streamlined the user experience.*
* *Let the user choose a new starting location*:  *Pretty self explanatory, but adds to the user-experience.*
* *Shorter trips #2*:  *Nearest Neighbor and 2-Opt are not only accurate, but fast. Also, we implemented a cache for previously calculated optimizations on the same trip; so, moving from NN - 2Opt back to NN is instantaneous.*
* *Distance Units*:  *We now support miles, kilometers, nautical miles, and user-inputted distance units, all within a user-friendly UI.*
*

#### Incomplete epics in Sprint Backlog 
* *Zoom and pan the map*: *Unfortunately, this rather large part of the sprint was left uncompleted. We will be setting up a meeting with Dave to discuss the reasons why.*
* *Give the user the option of what to display in the itinerary*: *I think we just overestimated what we could do with only three people. We were able to accomplish a significant amount, but this was one of the lower-priority epics we decided to do without.*
*

#### What went well
* *We did a significant amount of work in a short amount of time with only three people! I think what we were able to accomplish was pretty impressive, especially considering what we did complete is bug-free for casual use (I'm sure there are ways for a dedicated person to break our server, but a casual user will not encounter any unexpected results). All in all, it was a really good sprint, with great communication and cooperation between most of us!*
*

#### Problems encountered and resolutions
* *The only problem we ran into is something we will be meeting with Dave to discuss. Other than that, things actually went pretty smoothly!*
*

## Retrospective

Topic | Teamwork | Process | Tools
:--- | :--- | :--- | :---
What we will change this time | We really need to work on communicating things that are not getting done. Unfortunately, this ended up in another late Wednesday rush, trying to implement last minute epics whose progress was not communicated well. | I think our process was actually great this sprint. Luke and Jake both caught bugs in branches before they were pushed, which was great, and all-in-all we did get a lot done. Perhaps better focus on the daily scrums, but that's all I can think of. | I think we are using our tools pretty well, but I do think we should emphasize continuing code climate coverage, and implementing system/javascript testing.
What we did well | We mostly had awesome teamwork this sprint! Great communication, frequent meetings, cooperation on different aspects of the code; it definitely helped us accomplish as much as we did! | Our burndown chart is probably the best we've had, especially considering the long period in the front is due to the planning period. If shifted, we are pretty close to optimal! | We were great with maintaining code climate coverages, writing new tests as code was developed, etc. We definitely made use of the tools available to us. 
What we need to work on | This is something we will discuss with Dave. | Our process was mostly great, but it has seemed to be a frequent issue that uncompleted epics are left until Wednesday. I think better communication can fix this, but we will get Dave's input as well. | I would say SQL queries are still a challenge, as is javascript. We will be working on both this next sprint, in an effort to massively streamline user-friendliness.
What we will change next time | Mostly we will stay the same, as most of us had a fantastic sprint and accomplished a lot. However, depending on our meeting with Dave, we definitely will be changing some things, but we'll wait for his input. | As always, minimizing periods of inactivity is a good thing. We did pretty good this sprint, but we can keep focusing on it! | Utilizing system testing and javascript testing will definitely help expose any obscure bugs or issues we haven't experienced yet.