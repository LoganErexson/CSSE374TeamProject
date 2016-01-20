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

![alt tag](https://raw.githubusercontent.com/EruditeEnterprises/CSSE374TeamProject/master/docs/UMLdesignM1.png)
![alt tag](https://raw.githubusercontent.com/EruditeEnterprises/CSSE374TeamProject/master/docs/Diagram.png)

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

![alt tag](https://raw.githubusercontent.com/EruditeEnterprises/CSSE374TeamProject/master/docs/Milestone-2-Revised-Generated-code.png)
![alt tag](https://raw.githubusercontent.com/EruditeEnterprises/CSSE374TeamProject/master/docs/UUMLdesignM2.png)
![alt tag](https://raw.githubusercontent.com/EruditeEnterprises/CSSE374TeamProject/master/docs/PizzaafRevisedGeneratedCode.png)

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

Jack: 
- Added interfaces for ClassData, MethodData, FieldData
- Updated other classes to use data interfaces
- Removed reliance on signature in data classes
- Fixed bug with angle brackets in graphVis
- Updated and created new tests
- Created SDEditPrinter and integrated use of MethodCallData into it
- Updated UMLs

Logan:
- Added interface for GraphVisPrinter
- Added StringParser class and update other classes to use it
- Moved code for creating arrow strings in graphVisPrinter
- Added MethodBodyVisitor and changed ClassMethodVisitor to use it
- Added Command line functionality for running program
- Added MethodCallData and updated  ClassMethodVisitor and MethodBodyVisitor to use it
- Added VisitorManager
- Updated DesignParser to use VisitorManager and SDEditPrinter



To use this tool, run DesignParser with arguments in one of the two following formats:
uml <path_to_folder_of_classes> <package_name>   #Used for creating .dot files
sd <method_signature> <depth>                    #Used for creating .sd files. 
   #"depth" here is optional and will default to 5
   #<method_signature> is in the format:
     <package_name>.<class_name>.<method_name>(<parameter_type1>;<parameter_type2>;...)
