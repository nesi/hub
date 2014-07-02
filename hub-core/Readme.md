NeSI hub
========

The purpose of this project is to have a service that can talk to other services, independent of their location, 
and consolidate data in-between them.

For example, the most important part is the user-/group-management module. We talk to the underlying database
of the projectDB service, in order to get information about all existing users, their usernames on different
 services and sites and also their groups and roles. 
 
This can be, in turn, used by a possible 'job-lister' module, by looking up the local username(s) for a
certain person, and in turn querying the job scheduler at each site to return all running jobs of that 
person.
Or, it can be used to query available filesystems and associated quotas. Or job history/accounting data.
 
 
Overview
========


Types
-----

### person

A person is the central user entity. It can be have the Adviser and/or Researcher-Role in the
projectdb, but does not need to (although, most of the time that'll be the case).

### identity

Each person needs to be associated with an identity. At the moment, the identity class is a 
helper class that stores a unique uuid, possible adviser and researcher ids from the projectdb
and also an alias. The alias is used as a unique, user-friendly key. And the table is used to be
able to always (re-)connect an alias with a person. The __identity__ class is only used internally,
and probably does not need to be exposed.

### group

'groups' are collections of people, who each can have one or multiple roles within this group.
The main set of groups will be our projects from the projectdb, with the roles specified in there
 (Primary adviser, Project owner, ...).
But it's perfectly possible to have a group for other group of persons (i.e. "The University of Auckland"
is one group, also all Advisers in the projectdb).


Rest API Queries
----------------

The __hub__ exposes a Restful API ( base url on the saga host is: http://saga.exp.cer.auckland.ac.nz:8084/rest )

Here is a list of useful queries:

### Persons

#### get all persons

    /get/every/person
    
#### get person with alias 'markus_binsteiner'

    /get/person/markus_binsteiner
    
#### get all persons where the alias matches 'markus'

    /get/every/person/*markus*
    
### Groups

#### get all groups

    /get/every/group
    
#### get group 'uoa00001'

    /get/group/uoa00001
    
#### get every group where the groupname starts with 'uoa'

    /get/every/group/uoa*
    
### Jobs

#### get all currently running jobs for person with alias 'markus_binsteiner'

    /query/jobs/for/person/markus_binsteiner
    

