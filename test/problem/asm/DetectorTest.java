package problem.asm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

public class DetectorTest {
	@Test
	public final void testRuntimeSingleton() throws IOException {
		AbstractClassDataVisitor visitor = VisitorManager.visitClass("java.lang.Runtime");
		OutputStream out = new ByteArrayOutputStream();
		IVisitor umlVisitor = new UMLVisitor();
		visitor.getClassData().accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertTrue(out.toString().contains("\\<\\<singleton\\>\\>"));
	}
	
	@Test
	public final void testDesktopSingleton() throws IOException {
		AbstractClassDataVisitor visitor = VisitorManager.visitClass("java.awt.Desktop");
		OutputStream out = new ByteArrayOutputStream();
		IVisitor umlVisitor = new UMLVisitor();
		visitor.getClassData().accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertFalse(out.toString().contains("\\<\\<singleton\\>\\>"));
	}
	
	@Test
	public final void testCalendarSingleton() throws IOException {
		AbstractClassDataVisitor visitor = VisitorManager.visitClass("java.util.Calendar");
		OutputStream out = new ByteArrayOutputStream();
		IVisitor umlVisitor = new UMLVisitor();
		visitor.getClassData().accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertFalse(out.toString().contains("\\<\\<singleton\\>\\>"));
	}
	
	@Test
	public final void testFilterInStreamSingleton() throws IOException {
		AbstractClassDataVisitor visitor = VisitorManager.visitClass("java.io.FilterInputStream");
		OutputStream out = new ByteArrayOutputStream();
		IVisitor umlVisitor = new UMLVisitor();
		visitor.getClassData().accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertFalse(out.toString().contains("\\<\\<singleton\\>\\>"));
	}
}
