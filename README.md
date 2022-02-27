## Description
Remote controlled robot has been placed on surface. Write software that translate command send to robot, 
to actions which execution allow robot to reach out target position.

When robot touch the ground it's initialized with current coordinates adn the direction it's facing
in format `(x, y, direction)` example `(2, 3, EAST)`

### Problem 1
The robot give command string which contains multiple commands:
* F -> move forward on face direction
* B -> move backward, opposite to face direction
* L -> turn left
* R -> turn right

example `FLFFRBFFLF`

When full command is executed, robot report current coordinates and position in format `(2, 5) EAST`

### Problem 2
The robot give list of obstacles that may be encountered on it road. When robot discover obstacle on front
or back of it during move, it reports last position with `STOPED` postfix.

example `(1, 3) NORTH STOPPED`

### Problem 3
To effectively reach out target position, robot can use navigation which can analyze environment to found
optimal route. In case when there is possibility to reach out target, route should contain no steps or
navigation service should report that.

## How to run
* execute `mvn test` from command line to confirm that test scenarios are completed successfully
* find `NavitationServiceTest` or `SurfaceExplorerServiecTest` to analyze scenarios logic



