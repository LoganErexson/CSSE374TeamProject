package problem.asm;


public class ModelPreVisit extends AbstractVisitMethod{

	public ModelPreVisit(StringBuffer buffer) {
		super(buffer);
	}

	@Override
	public void execute(ITraverser t){
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
		this.buffer.append(sb.toString());
		
	}

}
