package problem.asm;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class GraphVisPrinter implements IClassStructurePrinter{

	private IPackageModel model;
	
	public GraphVisPrinter(List<IClassData> classes){
		this.model = new PackageModel();
		this.model.setClasses(classes);
		this.model.setClassRelations();
	}
	
	@Override
	public void printToFile(OutputStream out){
		try {
			StringBuilder sb = new StringBuilder();
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
			
			for(IClassData currentData: this.model.getClasses()){
				sb.append(currentData.getUMLString());
			}
			sb.append(this.model.createArrows());
			sb.append("}\n");
			out.write(sb.toString().getBytes());
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}
