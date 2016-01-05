package problem.asm;

import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class GraphVisPrinter {
	
	public static void makeUML(String file, List<ClassData> classes ){
		try {
			StringBuilder sb = new StringBuilder();
			OutputStream out = new FilterOutputStream(new FileOutputStream(file));
			sb.append("digraph G {\n");
			sb.append("fontname = \" Bitstream Vera San\"\n");
			sb.append("fontsize =8\n");
			
			sb.append("node [\n");
			sb.append("fontname = \"Bitstream Vera Sans\"\n");
			sb.append("fontsize =8\n");
			sb.append("shape = \"record\"");
			sb.append("]\n");
			
			sb.append("edge [\n");
			sb.append("fontname = \"Bitstream Vera Sans\"\n");
			sb.append("fontsize =8\n");
			sb.append("]\n");
			for(ClassData currentData: classes){
				sb.append(currentData.toString());
				sb.append(currentData.getSuperClassString(classes));
				sb.append(currentData.getInterfacesString());
			}
			sb.append("}\n");
			out.write(sb.toString().getBytes());
			out.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

}
