Welcome stranger! Good to have you here!

This is a homework task to be implemented before technical interview.

Fighting Armies
=======
You are a **General** of armed forces of People Republic of Swatemalu. You have **N** armies of **K** types, which are numbered from **1** to **N** under your command. Each army has a name and consists of a number of units of a type that is acceptable in that type of army. Each unit is assigned a number, representative of its combat power. Since, you are responsible for the entire armed forces, you want to give orders to your armies and query them about their current state.

   We have created a Spring Boot project to start you off. We have even implemented some REST APIs for you! :)  
   Now your turn to show us some awesome code! :)   

Please create a set of REST APIs for managing your armed forces which fulfill the following requirements:
1.	Fetch armed forces executive summary
2.	~~Create an army~~ ![implemented](http://www.myiconfinder.com/uploads/iconsets/16-16-218780527bb8acc76f78fecaca298342.png "implemented")
3.	~~List the summary of all the armies you have~~ ![implemented](http://www.myiconfinder.com/uploads/iconsets/16-16-218780527bb8acc76f78fecaca298342.png "implemented")
4.	List armies of a given type
5.	Fetch the army’ details
6.	Recruit a unit to the army
7.	~~Fetch all units of the army~~ ![implemented](http://www.myiconfinder.com/uploads/iconsets/16-16-218780527bb8acc76f78fecaca298342.png "implemented")
8.	Fetch all units of the army sorted by combat power descending
9.	Fetch those units of the army which have combat power 50 or more
10.	Fetch the unit details
11.	The given unit killed/destroyed (removed from the army)
12.	The strongest unit (by combat power) killed/destroyed (removed from the army)
13.	Merge one or more armies into the army identified by ID – this should be an asynchronous API, as merging armies takes between one and two minutes. The state of the armies cannot be modified while armies are being merged.

Constraints
-------
*	You can have up to **50** armies
*	There can be up to **100** units in an army
*	Combat power of the units should be a number between **1** and **100**
*	You can only add units to an army if the unit type is acceptable in that army
*	When the last unit is removed from the army – the army gets removed from the armed forces
*	You cannot remove an army having units in it

Freedom of creativity
-------
*	Please implement some or all REST APIs listed above. Feel free to refactor the provided code if needed. Write some unit tests. Your implementation will be discussed during the technical interview.
*	Requirements are intentionally incomplete. Feel free to make assumptions where needed.
*	Feel free to go beyond the requirements and suggest enhancements that you believe would improve the efficiency and fun managing the armed forces.
*	Persistence – you can use the collections API (provided) or use your preferred DB engine.

Project Setup
-------
  Once imported to your IDE - you can start the Spring Boot project. Swagger will become available on your local machine under: http://localhost:9099/swagger-ui.html#/armed-forces-controller.   

You will need to have [Lombok](https://projectlombok.org/features/all) integrated to your IDE. (instructions for [Eclipse](https://projectlombok.org/setup/eclipse) and [IntelliJ](https://projectlombok.org/setup/intellij))

Once done - please submit a pull request for us to enjoy reviewing your code.

Good Luck!
-------