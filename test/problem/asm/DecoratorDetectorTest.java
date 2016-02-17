package problem.asm;

import static org.junit.Assert.assertFalse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import problem.detector.AdapterDetector;
import problem.detector.DecoratorDetector;
import problem.detector.IPatternDetector;
import problem.detector.InterfaceDetector;
import problem.detector.SingletonDetector;
import problem.model.data.IPackageModel;
import problem.model.data.PackageModel;
import problem.model.visit.IVisitor;
import problem.model.visit.UMLVisitor;

public class DecoratorDetectorTest {
private List<IPatternDetector> detectors;
	
	@Before
	public void setUp(){
		this.detectors = new ArrayList<>();
		this.detectors.add(new SingletonDetector(false));
		this.detectors.add(new DecoratorDetector(1));
		this.detectors.add(new AdapterDetector(2));
		this.detectors.add(new InterfaceDetector());
	}
	
	@Test
	public final void testDecoratorInputStreamReader() throws IOException {
		IPackageModel m = new PackageModel(this.detectors);
		String[] c = {"java.lang.AutoCloseable", "java.io.InputStreamReader", "java.io.FileReader", "java.io.Closeable", "java.lang.Readable", "java.io.Reader"};
		for (String s : c){
			AbstractASMVisitor visitor = VisitorManager.visitClass(s);
			m.addClass(visitor.getClassData());
		}
		IVisitor umlVisitor = new UMLVisitor();
		OutputStream out = new ByteArrayOutputStream();
		m.accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertFalse(out.toString().contains("decorator"));
	}
	
	@Test
	public final void testDecoratorOutputStreamWriter() throws IOException {
		IPackageModel m = new PackageModel(this.detectors);
		String[] c = {"java.lang.AutoCloseable", "java.io.OutputStreamWriter", "java.io.Flushable", 
				"java.lang.Appendable", "java.io.Writer", "sun.nio.cs.StreamEncoder"};
		for (String s : c){
			AbstractASMVisitor visitor = VisitorManager.visitClass(s);
			m.addClass(visitor.getClassData());
		}
		IVisitor umlVisitor = new UMLVisitor();
		OutputStream out = new ByteArrayOutputStream();
		m.accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertFalse(out.toString().contains("decorator"));
	}
	
	@Test
	public final void testDecoratorMouseAdapter() throws IOException {
		IPackageModel m = new PackageModel(this.detectors);
		String[] c = {"java.awt.event.MouseListener", "java.awt.event.MouseWheelListener", "java.util.EventListener", "java.awt.event.MouseMotionListener", "java.awt.event.MouseAdapter",
				"java.awt.event.MouseEvent"};
		for (String s : c){
			AbstractASMVisitor visitor = VisitorManager.visitClass(s);
			m.addClass(visitor.getClassData());
		}
		IVisitor umlVisitor = new UMLVisitor();
		OutputStream out = new ByteArrayOutputStream();
		m.accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertFalse(out.toString().contains("decorator"));
	}

}
