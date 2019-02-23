# TripCo - T14 - Team XIV

## Overview
A remote server hosts Itinerary planning software.  The trips can now be shown in either Miles or Kilometers.  There are 3 choices on how to optimize the trip, either no optimization, nearest neighbor, or 2-Opt.  Trips can be saved to the users computer, and loaded from the saved file.  The trip view is rendered through Google Maps.

## What's New
* Changed optimization options to more easily readable None, Low, Medium, High.
* Miles/Kilometers button selector UI experience improved.

## Improvements
* 2-Opt optimized further - now uses arrays instead of ArrayLists
* UI experience improved
* Ease of use improved.
* Improved maintainability and reduced churn in several Java files.

## Fixes
* Optimization buttons didn't show what was currently selected, they now show the current selected option.
* Maintainability issues reduced in several Java class files.

## Outstanding Issues
* 3-Opt doesn't perform very well
* Selecting starting locations 
* KML files

## Installation and Deployment
#### Getting the code:
* Download TripCo-5.0.0-jar-with-dependencies.jar from below

#### Connecting to the SQL server
* If you are connected to CSU local network, the following steps can be skipped.
* If you are not connected to CSU local network follow these steps:
  * ssh into a CSU CS machine such as denver.cs.colostat.edu using the following command to create an ssh tunnel
    * ssh -D 9000
  * Leave this process running while doing the following:

#### Running the Jar
  * Browse to the directory where you downloaded the TripCo-5.0.0-jar-with-dependencies.jar jar file
  * Run the TripCo-5.0.0-jar-with-dependencies.jar file using the following command: 
  * If remote connected using an ssh tunnel use the following command:
    * ```java -DsocksProxyHost=localhost -DsocksProxyPort=9000 -cp ./TripCo-5.0.0-jar-with-dependencies.jar edu.csu2017fa314.T14.TripCo -p (Custom Port Number)```
  * The default port number will be 4567 if you don't specify one above
  * If connected CSU local network use the following command
    * ```java -cp ./TripCo-5.0.0-jar-with-dependencies.jar edu.csu2017fa314.T14.TripCo -p (Custom Port Number)```
  * The default port number will be 4567 if you don't specify one above


#### Running the React front end
* Download SourceCode.zip file from below and extract this file
* Using a terminal, browse to the `T14-5.0/web` directory
* Browse to the src/ directory
* Open App.js file in a text editor of your choice.
* Edit the line 14 variable this.url to be the external IP address or domain name where you will be hosting this app. Also change the port number to reflect the port you are hosting the Jar file above at (Custom Port Number or 4567)
* Edit the web/app.js line two 8080 default port to a port of your choosing
* navigate to web directory and run the following commands:
  * npm run build
  * npm run start

## Dependencies
* Gson
* Spark
* react-google-maps
* Junit

## References
None
