# Hungry Hippo Fall
[![Build Status](https://travis-ci.com/246overclocked/HungryHippoFall.svg?token=thBQUnyouNyEykbcSsVn&branch=master)](https://travis-ci.com/246overclocked/HungryHippoFall)
[![Stories in Ready](https://badge.waffle.io/246overclocked/hungryhippofall.svg?label=ready&title=Ready)](https://waffle.io/246overclocked/hungryhippofall)

Overclocked's Demo code for our 2016 competition robot!

## Installation
All FRC projects use **Apache Ant** to build and deploy code for the robot. In addition, this project also uses Ant to run unit tests. All libraries and dependencies are included in the project, so no external downloads are necessary. The only requirement is to have Ant installed on your computer.

**Determine if you already have Ant installed:** open Terminal (Mac) or Command Line (Windows) and type `ant -version`. If that outputs a version of Ant, then it is already installed and you can skip down to [usage](#usage). If you get something else, you need to install Ant.

### Installing Ant on Mac

On OS X, the easiest way is to [install Homebrew](http://brew.sh), open up a terminal, and type `brew install ant`. (If you have MacPorts installed, you can install Ant with `sudo port install apache-ant`.)

### Installing Ant on Windows

[Download Ant](http://ant.apache.org/bindownload.cgi) and follow the installation instructions, then set up Ant for the command line on Windows (cmd or powershell): 

  1. Add new environment variable `ANT_HOME` and point it to the place where Ant is installed (as Eclipse Plugin or standalone). You can do this temporarily (or for a single session) by using the command `set [variable]=[path]` in cmd or `$env:Variable = 'path'` in powershell, or you can do this permanently by right-clicking on 'My Computer' (or 'This PC') and going to Properties > Advanced system settings (on the left) > Environment Variables. In the window that pops up, you can add the variable `ANT_HOME` equivalent to the Ant installation folder un System variables. 

  1. Append `%ANT_HOME%\bin` to the `Path` envoronment variable under System variables (see the step above) by selecting it in the list of System variables and clicking Edit... - if it is blank, or if you could not find this variable and create it you can simply add `%ANT_HOME%\bin` as the value. If you found it containing a value already, however, **do not delete its contents**, but add `;%ANT_HOME%\bin` to its contents (with a **semi-colon** and **no spaces**). 

## Usage

### Running Ant from the Terminal (Mac) or Command Line (Windows)
Open the terminal (command line on Windows) and `cd` to the root directory of the project (where `build.xml` is located), in this case the `hungryhippofall` folder. All Ant commands are of the form `ant [target]` and run in the root directory of the project. Run `ant -p` to see a description of available targets:
```
hungryhippofall$ ant -p
Buildfile: [...]/hungryhippofall/build.xml
Trying to override old definition of task classloader

Main targets:

 clean         Clean up all build and distribution artifacts.
 compile       Compile the source code.
 debug-deploy  Deploy the jar and start the program running.
 deploy        Deploy the jar and start the program running.
 test          Compile source code and run all junit tests.
Default target: deploy
```
Each "target" is a subcommand you can run. For example `ant deploy` deploys the code to the robot, and is equivallent to deploying from the Eclipse. Most importantly, `ant test` will run automatically run all JUnit tests in the `hungryhippofall/test` source directory. If no target is specified (by just running `ant` without a target), Ant will default to 'deploy'.

### Running Ant from Eclipse

  1. First make sure you have no Ant Build configurations already set (you can check by going to Run > External Tools > External Tools Configurations... and looking on the left of the window that pops up) and deleting any ones that have already been set. 

  1. Go to File > Import... > select Launch Configurations under Run/Debug > check off the 'hungryhippofall' directory on the left (you should see two configuration files checked off on the right) > Finish. 
  **Note:** you may notice that running `git status` after this step reveals that the two configuration files you just imported were deleted - this is completely normal, but be sure to run `git stash` in order to restore these files. 

  1. Right-click on the build.xml file in the hungryhippofall directory in the package explorer, select Run As > 1 Ant Build (the first option on the list). 

  1. You should now see two Ant Build configurations on the left of the window that pops up: 'hungryhippofall_build.xml' (this one deploys the code to the RoboRIO) and 'hungryhippofall_test.xml' (this one just runs all the tests) - select one of them and click 'Run' at the bottom of the window. You will see the output of the Ant Build in the Eclipse output console. 

  1. You can select configurations you've run previously by clicking on the drop-down list next to the deploy button with a red tool chest under it (located just to the right of the usual deploy button) or going back the the 'External Tools Configurations' menu mentioned above. 

##Using RoboScripting
The RoboScripting library is a tool used to test and debug registered motors, methods, and commands on the robot without having to map these to any joystick controls. Motor objects (CANTalon246), method references, and command objects (Command) are stored in the static `CallReference.java`. `RoboScripting.java` acts as the access class to receive, parse, and execute commands from the python client, as well as to add motor, method, and command references to `CallReference.java`.

###Adding a reference for RoboScripting

**Motors:** `RoboScripting.addMotor(String motorName, CANTalon246 motorReference)` is the preferred method of adding a motor to RoboScripting. Ex. `RoboScripting.addMotor("testMotor1", testMotor1)`.

**Methods:** "References" to methods are stored by means of a helper inline class. `RoboScripting.addMethod(String methodName, RoboScripting.MethodHolder methodReference)` should be called to add a method to RoboScripting. The class **RoboScripting.MethodHolder** requires the user to complete two methods `requiredParams` and `callMethod`.
  
  **String requiredParams()**: Returns a String meant to help the user determine which parameters the method needs. The String should
  look like "{Type}, {Type}, ..." where {Type} is the primitive or object name as seen in Java, ex. "double, CANTalon246,
  String". If no parameters are required, return "None". Ex. `return "double, String, CANTalon246`.
  
  **void callMethod(String[] args)**: Calls the method with any parameters. All user specified parameters will be passed to
  this method as part of args. This method should then handle any necessary parameters and call the wanted method. An example
  implementation is below:
  
  ```
  RoboScripting.addMethod("stopAll", new MethodHolder() {
			
			@Override
			public String requiredParams() {
				return "None";
			}
			
			@Override
			public void callMethod(String[] args) throws ArrayIndexOutOfBoundsException, NumberFormatException {
				stopAllMotors();
			}
		});
    
    private void stopAllMotors() {
      //stops all motors
    }
```
    
**Commands:** `RoboScripting.addCommand(String commandName, Command commandReference)` is the preferred method of adding a motor to RoboScripting. Ex. `RoboScripting.addCommand("testCommand", testCommand)`.

###Running the client and calling commands

1. Use ant to deploy the latest version to the roboRIO.
2. Enable the robot from the FRC driverstation.
3. Make sure the client computer is on the 246 network, then run the file `client.py`. I.e. `python client.py` from terminal. The client should automatically connect successfully and display a command prompt `>`.
4. You are now able to freely use commands. If you forget a command, use `help` for a list of all possible commands or `motors`, `methods`, `commands` for a list of possible command specific to a category. Typing `list` will list all registered items in RoboScripting, or calling `{Category}:list`, i.e. `motors:list`, will list all registered items for the category. Item and category names are case-sensitive, ex. `testMotor1` or `commands`, everything else is not. Sample commands:
  
  `motors:testMotor1:set_power:0.1`- Sets the raw power of testMotor1 to 0.1
  
  `methods:runAllMotors`- Runs the method `runAllMotors`
  
  `commands:testCommand:run`- Runs the command `testCommand`
  
  `commands:testCommand:stop`- Stops running the command `testCommand`
  
  `methods:printTheseThings:overclocked:246`- Calls the method `printTheseThings`, which requires a String and an integer
  
###Socket timeouts and hanging commands
There is currently an issue where the the client may randomly be unable to communicate with the roboRIO. If this happens, it is advised that the user wait until either a connection timeout occurs or the client successfully receives data. In the case that either a timeout occurs or hanging commands become too frequent, the most reliable solution is to quit the client, then redeploy the code to the roboRIO and start again from step 1. 

## Contributing
Please see [CONTRIBUTING](CONTRIBUTING.md) for more information.

## Credit
  * Thank you to team 4931 for your [awesome Wiki](https://github.com/frc-4931/2014/wiki/Java) explaining FRC Ant usage and installation.
  * Thank you to [Kostya Nazarenko](https://github.com/knazaren) for help in these instructions.
