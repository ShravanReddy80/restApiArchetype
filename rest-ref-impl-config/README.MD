All the configuration necessary for the project is in this module. I have done this so that all the configuration information is in one place. Spring and Maven are  smart enough to figure it out.

NOTE: Spring is using the first part of the property names in the '.properties' file before the period(".") as the prefix for figuring out which property names in the .properties files map to the variable names in the '.java' classes. If we have multiple periods in the property names in the properties files, the properties will not be loaded.
