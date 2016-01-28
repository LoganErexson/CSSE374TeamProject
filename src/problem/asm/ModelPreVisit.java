package problem.asm;

import java.io.IOException;
import java.io.OutputStream;

public class ModelPreVisit  implements IVisitMethod{

	private OutputStream out;
	public ModelPreVisit(OutputStream out) {
		this.out = out;
	}
	@Override
	public void execute(ITraverser t) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph G {\n");
		sb.append("fontname = \" Bitstream Vera San\"\n");
		sb.append("fontsize =8\n");
		
		sb.append("node [\n");
		sb.append("style=filled\n");
		sb.append("fillcolor=white\n");
		sb.append("fontname = \"Bitstream Vera Sans\"\n");
		sb.append("fontsize =8\n");
		sb.append("shape = \"record\"");
		sb.append("]\n");
		
		sb.append("edge [\n");
		sb.append("fontname = \"Bitstream Vera Sans\"\n");
		sb.append("fontsize =8\n");
		sb.append("]\n");
		this.out.write(sb.toString().getBytes());
		
	}

}
