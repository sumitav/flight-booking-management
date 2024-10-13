# Flight Booking Application

## Overview
We are required to build an app that lets customers 
search and book flights.
An airline register to the portal and declare their flight schedule for the given day.
Customers can search flights between two cities.Assume that the airlines declare the schedule
for a particular day and customers also book for the same day.

1.There can be multiple flights between a to b and often different prices

every city is unique and comb of three alpha Example:BOM,DEL 

Airline names are also unique have no spaces and greateer than length of 2 like JetAir,Indigo

cost of flight is positive value

input:
register flight->JetAir->DEL->BLR->500
op:JetAir DEL->BLR flight registered

register flight->JetAir->BLR->LON->1000
op: JetAi BLR->LON flight registered

register flight->Delta->DEL->LON->2000

register flight->JetAir->LON->NYC->2000


search flight DEL->NYC
it should give two outputs ->
op: Route with minimum hops:
DEL to LON via DELTA for 2000
LON to NYC via DELTA for 2000

Total Flights=2
Total Cost=4000


op: Cheapest Route:

Here it should show the route which cost is as low as possible for the trip amongst all 
airlines and route options

If there are multiple options with the same source and desitination and each option has same 
cost then return the one which has the minimum number of hops 


Also implement filter for search: Assume that all indigo flight serve meals

Customes when searching flights between citities need provide true/false ...this should be an extensible
feature to other properties like providing drinks,excess baggage,economy class,business class etc.

Ip: search flight->DEL->NYC->TRUE 
it should give two outputs ->
op: Route with minimum hops:
DEL to BLR via Indigo for 600
BLR to NYC via Indigo for 2000

Total Flights=2
Total Cost=2600


op: Cheapest Route:

Here it should show the route which cost is as low as possible for the trip amongst all 
airlines and route options where flight name is indigo as serve meals is true 


## Getting Started

To run the application:

1. Ensure you have Java installed.
2. Clone the repository.
3. Compile and run the `Driver` class.

## Conclusion

This Flight Booking Application is designed to be simple yet extensible, allowing for future enhancements such as additional filtering options or other features. Contributions and suggestions for improvement are welcome!