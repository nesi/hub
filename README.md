# Overview

Hub is a lean and mean little service, designed to consolidate, process and publish different sources of models, data 
and statuses, across all sites. Think Enterprise-service-bus for multi-site science collaborations.

# Getting started

    git clone https://github.com/nesi/hub.git
    cd hub
    ./gradlew clean build 
    # to create a distribution zip file:
    ./gradlew clean build dist
    
# Starting

Unzip the distribution zip file in build/distributions, and execute:

    bin/hub start
    
You can add an init.d script by issuing:

    bin/hub install
    
(this might require some tweaking of certain properties, for example RUN_AS). Make sure the user
who runs the daemon has write access to /var/log/hub and /var/run/hub.
    
# Properties files

There are 2 properties files that are used to configure the applications behaviour. Lookup path for those 
files are (in that order):

 - $HOME/.hub
 - /etc/hub

## hub.properties

Example:

    # pandora audit db config (not used anymore, but needs to be present for now)
    pan.db.url=jdbc:mysql://localhost:3306/pandora_audit
    pan.db.username=markus
    pan.db.password=password
    
    # ssh key setup to be able to retrieve running jobs for users
    ssh.username=user.markus
    ssh.host=login.uoa.nesi.org.nz
    ssh.port=22
    ssh.key.path=/home/mbin029/.ssh/id_markus
    
    # project db credentials, used to create a tree of persons
    projectDB.db.url=jdbc:mysql://localhost:3306/projectdb
    projectDB.db.username=markus
    projectDB.db.password=password
    
    # db credentials to be used for storing hub-internal data
    thingDB.db.url=jdbc:mysql://localhost:3306/hub
    thingDB.db.username=markus
    thingDB.db.password=7Sp5xn7CFpn5mJaF
    thingDB.db.driver=com.mysql.jdbc.Driver
    
    # db credentials for pentaho job database, used to get job history/audit data for users
    dim_job.jooq.sql.dialect=POSTGRES
    dim_job.db.driver=org.postgresql.Driver
    dim_job.db.url=jdbc:postgresql://127.0.0.1/pentaho
    dim_job.db.username=mbin029
    dim_job.db.password=pwd
    
    # whether to enable/disable auth (disabling is only recommended for development)
    enableAuth=false

## admins.conf

Key/values for usernames/passwords. username needs to be the 'unique' Person key for a user.

Example:

    markus_binsteiner=password
    nick_young=password2

If auth is enabled, one of those usernames needs to be used in basic auth when connecting to the service.

# Miscellaneous

## Auth

If auth is enabled (see hub.properties), only users who have a password set for the 'nesi' service
or administrators (see admins.conf) can access this service (at the moment there is no fine-grained permission check).
If a user is admin and the password for the 'nesi' service and the one in the admins.conf file
are different, both can be used.

All auth is done via basic auth at the moment.

Hashed passwords are stored in the 'hub' database, in the 'passwords' table. The 'admins.conf' file
is necessary for bootstrapping, in order to be able to put passwords for other users into the db
(see 'passwords' section below).

## 'Things'

TODO

## Globs

Types and keys can be queried via 'globs', which more or less follow the bash-glob format.
This feature is not very advanced yet, but putting one of more '*' somewhere in a key or value string should work.

## Rest url schema

TODO

# Hub models/objects

Base url for our dev server is: 

    http://saga.exp.cer.auckland.ac.nz:8084/rest

## Person

Persons are the central object for this service. They have a few basic fields, everything more 
special can be added through properties (see below).

Here is a list of the basic fields:

    person: {
        alias: "String"
        emails: "Set"
        first_name: "String"
        lastModified: "Instant"
        last_name: "String"
        middle_names: "String"
        preferred_name: "String"
        properties: "Set"
        roles: "Multimap"
        usernames: "SetMultimap"
    }
    
Properties, roles and usernames are important, those indicate properties and relationships of 
a Person.

Each person has a unique key that is stored in the 'alias' field. At the moment, if it is not set explicitely (and if there is no
danger of merging multiple persons with the same name) it is of the format

    [first_name]_[last_name]
    
(all lowercase)

### Admin users

TODO

### Fields

#### Properties

A property is a free-style key/value object, that also has a 'service' property, which is basically
a 2nd key. A person can have zero to multiple properties with the same key/service combination, but
only 1 property where all three fields are equal.

The service property is mainly used so 3rd party services can tag properties as belonging to them.
An example of a 'service' can be a group name ('University of Auckland'), an actual service
('irods', 'projectdb') or anything else.

#### Roles

This is arguably the most important field for a Person. It is implemented as a String multimap,
with the key being the name of the service (which should be equal to the 'service' field in the 
properties model, if applicable).

The value of each role entry is a set of role names ('Member' being a generic one, but normally it
would be a free-style string (which should be treated as enum) that are specific to the service
they relate to ('Primary Adviser', 'CeR Contact', ...).

#### Usernames

This is again a multimap, with a name of a service as the key. The value is a set of strings indicating
the username(s) (mostly it'll be one, but it is possible to have multiple usernames for one service)
that person has on that service (example: UPI at the 'University of Auckland')

The person key/alias is also a username for the 'nesi' service. 

### Queries

#### Get a list of all person objects

    GET /get/every/person
    
Gets, obviously, all users.

#### Get person with alias/key

    GET /get/person/[alias]
    
Gets the person with the specified alias. The alias can also be a glob, but in case multiple
matches are found an error is thrown.

#### Get persons where the alias matches a glob

    GET /get/every/person/[glob]

Gets a list of all Persons that have an alias that matches the specified glob.

#### Find a person with a username

    POST /query/username
    
Query body example (application/json):

    [{
    "value": {
      "service":"University of Auckland",
      "username":"mbin029"
    },
    "type": "username"
    }]
    
Queries all users and returns the ones that match the username specified in the query body.
The 'username' field in that can also be a glob.

#### Find persons with a property

    POST /query/property
    
Query body example (application/json):

    [{
    "value": {
      "service":"University of Auckland",
      "key":"DN",
      "value":"*Landcare*"
    },
    "type": "property"
    }]
    
Queries all users and returns the ones that have a property that matches the one specified in 
the query body. The 'value' field can also be a glob.

    
## Passwords

### Queries

#### Check password

    GET /query/password
    
Query body example (application/json):
 
    [{
    "value": {
    "service": "University of Auckland",
      "person":"markus_binsteiner",
      "password":"password"
    },
    "type": "password"
    }]
    
The return value is a Person object that has a username for the service specified, and also
a password that matches the provided one.

If no such person can be found, the return value is an empty list.

#### Set password

    POST /set_password
    
Query body example (application/json)

    [{
    "value": {
    "service": "nesi",
      "username":"markus_binsteiner",
      "password":"pw"
    },
    "type": "password"
    }]

## Jobs

### Queries

#### Get jobs for alias

    GET /query/jobs/for/person/[alias]
    
Gets every job (currently only from LoadLeveler) for the person with that alias. If that 
person has multiple usernames on Pan, jobs for all of those will be returned.

#### Get jobs for all aliases matching a glob

    GET /query/jobs/for/every/person/[glob]
    
Similar to the above, but returns all jobs for every person that has an alias matching that glob.
