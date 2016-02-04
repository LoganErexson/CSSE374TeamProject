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
		IPackageModel m = new PackageModel();
		m.addClass(visitor.getClassData());
		IVisitor umlVisitor = new UMLVisitor();
		OutputStream out = new ByteArrayOutputStream();
		m.accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertTrue(out.toString().contains("\\<\\<singleton\\>\\>"));
	}
	
	@Test
	public final void testDesktopSingleton() throws IOException {
		AbstractClassDataVisitor visitor = VisitorManager.visitClass("java.awt.Desktop");
		IPackageModel m = new PackageModel();
		m.addClass(visitor.getClassData());
		IVisitor umlVisitor = new UMLVisitor();
		OutputStream out = new ByteArrayOutputStream();
		m.accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertFalse(out.toString().contains("\\<\\<singleton\\>\\>"));
	}
	
	@Test
	public final void testCalendarSingleton() throws IOException {
		AbstractClassDataVisitor visitor = VisitorManager.visitClass("java.util.Calendar");
		IPackageModel m = new PackageModel();
		m.addClass(visitor.getClassData());
		IVisitor umlVisitor = new UMLVisitor();
		OutputStream out = new ByteArrayOutputStream();
		m.accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertFalse(out.toString().contains("\\<\\<singleton\\>\\>"));
	}
	
	@Test
	public final void testFilterInStreamSingleton() throws IOException {
		AbstractClassDataVisitor visitor = VisitorManager.visitClass("java.io.FilterInputStream");
		IPackageModel m = new PackageModel();
		m.addClass(visitor.getClassData());
		IVisitor umlVisitor = new UMLVisitor();
		OutputStream out = new ByteArrayOutputStream();
		m.accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertFalse(out.toString().contains("\\<\\<singleton\\>\\>"));
	}
	
	@Test
	public final void testDecoratorInputStreamReader() throws IOException {
		IPackageModel m = new PackageModel();
		String[] c = {"java.lang.AutoCloseable", "java.io.InputStreamReader", "java.io.FileReader", "java.io.Closeable", "java.lang.Readable", "java.io.Reader"};
		for (String s : c){
			AbstractClassDataVisitor visitor = VisitorManager.visitClass(s);
			m.addClass(visitor.getClassData());
		}
		IVisitor umlVisitor = new UMLVisitor();
		OutputStream out = new ByteArrayOutputStream();
		m.accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertTrue(out.toString().contains("decorator"));
	}
	
	@Test
	public final void testAdapterInputStreamReader() throws IOException {
		IPackageModel m = new PackageModel();
		String[] c = {"java.lang.AutoCloseable", "java.io.InputStreamReader", "java.io.FileReader", "java.io.Closeable", "java.lang.Readable", "java.io.Reader"};
		for (String s : c){
			AbstractClassDataVisitor visitor = VisitorManager.visitClass(s);
			m.addClass(visitor.getClassData());
		}
		IVisitor umlVisitor = new UMLVisitor();
		OutputStream out = new ByteArrayOutputStream();
		m.accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertTrue(out.toString().contains("adapter"));
	}
	
	@Test
	public final void testDecoratorOutputStreamWriter() throws IOException {
		IPackageModel m = new PackageModel();
		String[] c = {"java.lang.AutoCloseable", "java.io.OutputStreamWriter", "java.io.Flushable", "java.lang.Appendable", "java.io.Writer"};
		for (String s : c){
			AbstractClassDataVisitor visitor = VisitorManager.visitClass(s);
			m.addClass(visitor.getClassData());
		}
		IVisitor umlVisitor = new UMLVisitor();
		OutputStream out = new ByteArrayOutputStream();
		m.accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertTrue(out.toString().contains("decorator"));
	}
	
	@Test
	public final void testAdapterOutputStreamWriter() throws IOException {
		IPackageModel m = new PackageModel();
		String[] c = {"java.lang.AutoCloseable", "java.io.OutputStreamWriter", "java.io.Flushable", "java.lang.Appendable", "java.io.Writer"};
		for (String s : c){
			AbstractClassDataVisitor visitor = VisitorManager.visitClass(s);
			m.addClass(visitor.getClassData());
		}
		IVisitor umlVisitor = new UMLVisitor();
		OutputStream out = new ByteArrayOutputStream();
		m.accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertTrue(out.toString().contains("adapter"));
	}
	
	@Test
	public final void testAdapterMouseAdapter() throws IOException {
		IPackageModel m = new PackageModel();
		String[] c = {"java.awt.event.MouseListener", "java.awt.event.MouseWheelListener", "java.util.EventListener", "java.awt.event.MouseMotionListener", "java.awt.event.MouseAdapter",
				"java.awt.event.MouseEvent"};
		for (String s : c){
			AbstractClassDataVisitor visitor = VisitorManager.visitClass(s);
			m.addClass(visitor.getClassData());
		}
		IVisitor umlVisitor = new UMLVisitor();
		OutputStream out = new ByteArrayOutputStream();
		m.accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertTrue(out.toString().contains("adapter"));
	}
	
	@Test
	public final void testDecoratorMouseAdapter() throws IOException {
		IPackageModel m = new PackageModel();
		String[] c = {"java.awt.event.MouseListener", "java.awt.event.MouseWheelListener", "java.util.EventListener", "java.awt.event.MouseMotionListener", "java.awt.event.MouseAdapter",
				"java.awt.event.MouseEvent"};
		for (String s : c){
			AbstractClassDataVisitor visitor = VisitorManager.visitClass(s);
			m.addClass(visitor.getClassData());
		}
		IVisitor umlVisitor = new UMLVisitor();
		OutputStream out = new ByteArrayOutputStream();
		m.accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertFalse(out.toString().contains("decorator"));
	}
}
