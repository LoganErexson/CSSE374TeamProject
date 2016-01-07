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

To use this tool, set the parameters for the call to getClasses in the main function
in DesignParser to the desired folder.
For project files: ("./src/problem/asm", "problem.asm")
For pizzaaf files: ("./src/headfirst/factory/pizzaf", "headfirst.factory.pizzaf")
For Lab 1-3 files: ("./src/lab1_3", "lab1_3")
Run the DesignParser