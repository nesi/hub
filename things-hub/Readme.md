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
 
 