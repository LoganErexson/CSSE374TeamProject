# CSSE374TeamProject

Milestone 1:
This tool creates a .gv file usable for GVEdit for the classes in the folder
specified in the driver class. This file is used by GVEdit to create a UML. 
The tool works by compiling a list of classes to include in the diagram and using a
visitor pattern to obtain information about each class. The ClassVisitor is decorated 
with other classes to obtain different pieces of information (e.g. the fields, the
methods). The decorated ClassVisitor stores this information in a storage class, 
ClassData. A list of instances of this object are stored in a list and passed to
GraphVisPrinter, which organizes the information in about the classes into a format
usable by GVEdit. It does this by calling functions in ClassData that build strings
for representing the class itself, its interfaces, and its superclass.

Manual UML Project Code
![alt M1-Manual](https://raw.githubusercontent.com/EruditeEnterprises/CSSE374TeamProject/master/docs/UMLdesignM1.png)
Manual UML 1-3 Code
![alt M1-Generated](https://raw.githubusercontent.com/EruditeEnterprises/CSSE374TeamProject/master/docs/1-3-Manual.png)

Logan:
- Created ClassData class and String building methods in ClassData
- Added GraphVisPrinter class for GVEdit formating and printing into document
- Added AbstractClassDataVisitor and made ClassVisitor's extend it
- Altered GraphVisPrinter to include interfaces and superclass
- Altered DesignParser to iterate through a given folder instead of using 
	listed classes
- Migrated code for printing information about interfaces/superclasses into ClassData
- Capturing pictures of generated UML's for Lab 1-3 and project code.

Jack:
- Adapted files from lab 3-1 for base of design
- Created FieldData and MethodData classes
- Reformatted ClassMethodVisitor and ClassFieldVisitor to use new abstract 
	class and ClassData
- Cleaned up formatting excess code
- Added Diagram.gv file to output formatted text into
- Migrated 1-3 files into project
- Wrote all automated tests.
- Created manual UML diagrams for Lab 1-3 and project code.

To use this tool, set the parameters for the call to getClasses in the main function
in DesignParser to the desired folder.
For project files: ("./src/problem/asm", "problem.asm")
For Lab 1-3 files: ("./src/lab1_3", "lab1_3")
Run the DesignParser

Milestone 2:
Adding uses and association did not require large modifications to the design. 
The only large modifications were in the ClassData, MethodData, and FieldData classes. 
The primary design change was a move of the arrow drawing methods in the GraphVizPrinter 
to ClassData. This allowed all of the primary changes to be made to classData for the actual
implementation of the uses and association arrows. 

Manual UML Project Code
![alt M2-Manual-Project](https://raw.githubusercontent.com/EruditeEnterprises/CSSE374TeamProject/master/docs/UMLdesignM2.png)

Jack: 
- Added getAssociationArrows method and functionality to ClassData.
- Adjusted FieldData's toString to produce optimized output for ClassData and GraphViz.
- Added tests for associations and uses
- Added M2 report to the Readme
- Revised handmade UML diagram.

Logan:
- Changed how collections are checked for in ClassData
- Handled uses and association arrows not pointing to extraneaus classes
- Handled folder selection for DesignParser 
- Fixed minor issues in GraphViz input
- Fixed minor issues with the way arrows format.

Milestone 3:
For milestone 3, there have been many changes to the design. Firstly, interfaces 
have been added to avoid violating the dependency inversion principle. FieldData, 
MethodData, and ClassData all now implement an interface. Each of the interfaces 
extends the interface IData. Additionally a new class MethodCallData implements an
interface extending from IData. The new class holds data about individual method calls
GraphVisPrinter also now implements an interface, IClassStructurePrinter. A new class,
SDEditPrinter also implements this interface. SDEditPrinter is used for printing into 
file in the form of a sequence diagram. Two static classes have been created to get rid of 
duplicate code. StringParser converts strings for class names into something easily 
readible and parses types out of signatures. VisitorManager handles the code for 
visiting classes.

Manual UML Project Code
![alt M3-Manual-UML](https://raw.githubusercontent.com/EruditeEnterprises/CSSE374TeamProject/master/docs/UMLdesignM3.png)

Jack: 
- Added interfaces for ClassData, MethodData, FieldData
- Updated other classes to use data interfaces
- Removed reliance on signature in data classes
- Fixed bug with angle brackets in graphVis
- Updated and created new tests
- Created SDEditPrinter and integrated use of MethodCallData into it
- Updated UMLs
- Added more tests
- Updated Readme (with embedded images)

Logan:
- Added interface for GraphVisPrinter
- Added StringParser class and update other classes to use it
- Moved code for creating arrow strings in graphVisPrinter
- Added MethodBodyVisitor and changed ClassMethodVisitor to use it
- Added Command line functionality for running program
- Added MethodCallData and updated  ClassMethodVisitor and MethodBodyVisitor to use it
- Added VisitorManager
- Updated DesignParser to use VisitorManager and SDEditPrinter
- Wrote Tests for Data Classes
- Moved functionality of ClassData into AbstractClassDataVisitor
- Refactored code to removed unneccessary classes and functions

							
Milestone 4:
For milestone 4 the changes made involve decorating each visitor as they complete their visit of each class. The decorator for this milestone will be
used to detect the necessary requirements for a singleton and then add a flag to indicate that the proper formating(In this case the color blue for the box) should
be addeded to the string representation of the class. By decorating the visitor no changes should need to be made within any classes but DesignParser and GraphVisPrinter. 

Manual UML Project Code
![alt M4-Manual-UML](https://raw.githubusercontent.com/EruditeEnterprises/CSSE374TeamProject/master/docs/UMLdesignM4.png)

Jack: 
- Created logic for Singleton Detection
- Created Detector class
- Added Singleton Detector classes
- Added PackageModel
- Changed how Singleton status is displayed

Logan:
- Added Model/Data Visitor system
- Updated Data to accept visitor
- Added IVisitMethods
- Updated DesignParser to use Visitors

Milestone 5:
For milestone 5 new implementations of IPatternDetector were implemented for the Decorator and Adaptor patterns and checked for in the PackageModel class. 
The Dectectors update IClassData objects to have pattern data if they are a part of a pattern and then flag that data to be printed when that class is accepted during a 
PackageModel's accept call. 

Jack:
- Updated how detectors interact with PackageModel and ClassData
- Developed DecoratorDetector
- Added Tests for new Decorators
- Generated and manually built new diagrams

Logan:
- Developed AdapterDetector
- Updated sequence diagram generation to use visitors
- Cleaned out unneccessary and unused code
- Implemented Labels for arrows
- Added config file style of choosing classes

Manual UML Project Code
![alt M5-Manual-UML](https://raw.githubusercontent.com/EruditeEnterprises/CSSE374TeamProject/master/docs/UMLdesignM5.png)

To use this tool, run DesignParser with arguments in one of the three following formats:
uml  #Used for creating .dot files
	 #A file chooser will prompt the the user to select the files to include in the diagram
uml [config_file_path]	#Used for creating specially configured .dot files
					  	#Config file lists classes to include in the uml diagram generation.
sd [method_signature] [depth]   #Used for creating .sd files. 
								#"depth" here is optional and will default to 5
								#<method_signature> is in the format:
[package_name].[class_name].[method_name]([parameter_type1];[parameter_type2];...)

Milestone 6:
For milestone 6 the pattern of the week was the composite pattern. Our design suffered very minimal changes as we were largely able to just write our compositeDetector and simply
add it into a list of detectors to create at runtime and pass into the model to check for patterns. The list of detectors that are ran is configuarable at runtime. 

Jack:
- Added composite detector framework
- Tested Composite Detector with Lab 7-2 files
- Updated Project UML and added new UMLS

Logan:
- Reorganized project code into packages and created config file for project files
- Converted PackageModel to take in a list of detectors
- Implemented Composite Detector Logic
- Updated and added tests

Manual UML Project Code
![alt M6-Manual-UML](https://raw.githubusercontent.com/EruditeEnterprises/CSSE374TeamProject/master/docs/UMLdesignM6.png)

To use this tool, run DesignParser with arguments in one of the three following formats:
uml  #Used for creating .dot files
	 #A file chooser will prompt the the user to select the files to include in the diagram
	 #This only works on a single folder in its current state.
uml [config_file_path]	#Used for creating specially configured .dot files
					  	#Config file lists classes to include in the uml diagram generation.
sd [method_signature] [depth]   #Used for creating .sd files. 
								#"depth" here is optional and will default to 5
								#<method_signature> is in the format:
[package_name].[class_name].[method_name]([parameter_type1];[parameter_type2];...)

Milestone 7:
This milestone led us to revive several aspects of our design to make in more accessible for
our UI and any other external uses. In particular, the detector's became more standardized 
and now have ways to identify them to work with the the config file. Additionally, a change
was made in our PackageModel class to account for classes being inactive. For the milestone,
we created a number of new classes to work with the UI including a parser for the config files 
and a DesignParser class to run the phases.

Jack:
- Added Config file reader
- Implemented checkboxes in gui
- Implemented Output.png updating when checkboxes for classes are switched on or off
- Updated manual UML

Logan:
- Made Basic Framework of UI
- Altered Detectors to be more usable with UI
- Formatted UI
- Created Button functionality

Manual UML Project Code
![alt M7-Manual-UML](https://raw.githubusercontent.com/EruditeEnterprises/CSSE374TeamProject/master/docs/UMLdesignM7.png)

To use this tool, run DesignParser with arguments in one of the three following formats:
- uml  #Used for creating .dot files
-  	 #A GUI will appear with options to load a config file and to analyze the code the config directs to
-  	 #Config file format is:
-  	 	INPUT-FOLDER: Main directory from which to generate uml for
- 	 	INPUT-CLASSES: Additional classes desired to be in the diagram that are not part of the main directory
- 	 	OUTPUT-FILE: Path to where the input for the uml should go.
-	 	DOT-PATH: Path to where dot.exe is
- 	 	PHASES: Which detectors to run when generating uml. 
- 	 #Generated image will be displayed in the right window pane, and can be altered by unchecking class checkboxes and refreshing the window.
- 	 #To load a new UML close the window and relaunch the client. 
- sd [method_signature] [depth]   #Used for creating .sd files. 
- 								#"depth" here is optional and will default to 5
- 								#<method_signature> is in the format:
- [package_name].[class_name].[method_name]([parameter_type1];[parameter_type2];...)