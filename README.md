# TrackWildLife

## Author

* Kibet Ronald

## Description

* A java web application to allow rangers report: a new animal, sighting for a spotted animal,a new location and also their account in addition to viewing records of reported sightings of animals.

### Prerequisites

* To run this software, you need an editor with java support, java- version 11 or higher,heroku cli.
* Install java 11 using the commands:
  `$ sudo apt update`
  `$ sudo apt install openjdk-11-jre-headless`
* Install heroku using the commands:
  `$ sudo snap install --classic heroku`
* Install postgresql:
  `$ sudo apt-get install postgresql postgresql-contrib libpq-dev`


### Setup

* To access this project locally in your machine,use the command:

`$ git clone https://github.com/ronah289/TrackWildlife.git`
to clone to your machine.
* `$ cd TrackWildlife`
* `$ psql < create.sql`
* Open the application in a java-supporting IDE...

## Behaviour Driven Development
#### TrackWildLife
<ol>
<li>Create a new animal</li>
- record presence of an animal not seen before
<li>Create a sighting</li>
- report an animal's presence
<li>Create a ranger</li>
- create a new ranger's account in order to ease use of the software
<li>Create a location</li>
- create a new location for monitoring of the animals
<li>View animals</li>
- see list of all animals in the park
<li>View sightings</li>
- view spotted animals in different locations
<li>View rangers</li>
- check list of all the rangers in the park
<li>View locations</li>
- view all designated locations in the park
</ol>

## Technologies Used

* Java (version 11)
* Gradle (7.1)
* Spark
* Heroku


## License Information

* This software is licensed under MIT License.
* [Read More](https://choosealicense.com/licenses/mit/) on the license.
