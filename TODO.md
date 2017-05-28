# Recording http request

Im leaving the "as a" because its always me, the developer

In order to not hit the real third party REST-API and to suffer downtimes or slow responses
I want to record the response of the real API once and reuse the bottled response during future invocations

# Decisions to defer:
* Http lib? 
* Where to store the response
* In which format to store the response
* maven/gradle & project structure

# Starting

In order to replay a response
I need to store the response

# General notes
you can support all libraries that support setting a proxy. so in the @Before you set up your proxy and then do your stubbing.
