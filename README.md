This compiled Logic Language allows for the creation of digital "logic circuits", a java analogue to digital circuits that take some set of binary values as inputs and return some set of binary values as outputs based on their internal logc. Each logic circuit file (denoted with the extension ".lc") begins with a "header" in the first line. The header begins with the name of the circuit, which can be used to reference it in other circuits, along with a ":" symbol. After the colon, a comma-separated list of input variable names is provided (remember that each one represents a single binary output). Note: No spaces are used anywhere in the syntax, and will be misinterpreted by the compiler if included, leading to errors.
After the header comes the body of the circuit. Each line of the body represents an individual binary output of the circuit. The main syntax used is the "," symbol. When two variables are put on either side of a comma in the body of the circuit (e.g. x,y), this is interpreted as a logical NAND operation on the two variables. Statements can be included in parentheses in order to indicate prescedence in the order of operations. For example, (x,y),z would be interpreted as a NAND between x and y, followed by a NAND of the output of that operation and z. The total binary operation of a single line is computed to calculate the binary output of the line. If the first line of our circuit was (x,x),(y,y), and we passed in 0,1 as inputs, the output of the first bit would be (0,0),(1,1)->1,0->1 (This is, in fact, a logical OR operation).

Basic operations are provided for reference:  
NOT(x) - x,x  
OR(x,y) - (x,x),(y,y)  
AND(x,y) - (x,y),(x,y)  
XOR(x,y) - (x,(x,y)),(y,(x,y))  

Once a circuit file has been created, it can be accessed in another file using a "Reference Call", denoted by "[]". The usual syntax for a reference call is:
[name,output,input1,input2,...]
where "name" is the name of the circuit being called, "output" indicates which output bit of the circuit is desired (0 means the first, 1 means the second, etc.), and input1,input2,... are the inputs being given to the circuit (the number depends on how many the circuit takes).
  
Example Circuit Files:  
and:x,y  
(x,y),(x,y)  
  
xor:x,y  
(x,(x,y)),(y,(x,y))  
  
half_adder:x,y  
[xor,0,x,y]  
[and,0,x,y]  
  
Compiling a Logic Circuit is done with the "im.java" program. Open a command window in the directory of im.java (make sure interp.java is also in the same directory), and use the command "java im.java dir" where "dir" is replaced with the relative path of the directory containing the files you wish to compile. Upon compilation, a folder will be created in "dir" called "compiled", containing the compiled files corresponding to each logic circuit in "dir". These compiled files can be used in future java programs by referecing the name.funci method, where "name" is replaced with the name of the circuit and "i" is replaced with the specific output bit desired. The inputs are booleans corresponding to the binary inputs of the circuit, and the output will be a boolean corresponding to the output of the circuit's logic.
