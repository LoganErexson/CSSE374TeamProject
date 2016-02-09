package problem.asm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

public class SingletonDetectorTest {
	private List<IPatternDetector> detectors;
	
	@Before
	public void setUp(){
		this.detectors = new ArrayList<>();
		this.detectors.add(new SingletonDetector());
		this.detectors.add(new DecoratorDetector());
		this.detectors.add(new AdapterDetector());
		this.detectors.add(new InterfaceDetector());
	}
	@Test
	public final void testRuntimeSingleton() throws IOException {
		AbstractASMVisitor visitor = VisitorManager.visitClass("java.lang.Runtime");
		IPackageModel m = new PackageModel(this.detectors);
		m.addClass(visitor.getClassData());
		IVisitor umlVisitor = new UMLVisitor();
		OutputStream out = new ByteArrayOutputStream();
		m.accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertTrue(out.toString().contains("\\<\\<singleton\\>\\>"));
	}
	
	@Test
	public final void testDesktopSingleton() throws IOException {
		AbstractASMVisitor visitor = VisitorManager.visitClass("java.awt.Desktop");
		IPackageModel m = new PackageModel(this.detectors);
		m.addClass(visitor.getClassData());
		IVisitor umlVisitor = new UMLVisitor();
		OutputStream out = new ByteArrayOutputStream();
		m.accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertFalse(out.toString().contains("\\<\\<singleton\\>\\>"));
	}
	
	@Test
	public final void testCalendarSingleton() throws IOException {
		AbstractASMVisitor visitor = VisitorManager.visitClass("java.util.Calendar");
		IPackageModel m = new PackageModel(this.detectors);
		m.addClass(visitor.getClassData());
		IVisitor umlVisitor = new UMLVisitor();
		OutputStream out = new ByteArrayOutputStream();
		m.accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertFalse(out.toString().contains("\\<\\<singleton\\>\\>"));
	}
	
	@Test
	public final void testFilterInStreamSingleton() throws IOException {
		AbstractASMVisitor visitor = VisitorManager.visitClass("java.io.FilterInputStream");
		IPackageModel m = new PackageModel(this.detectors);
		m.addClass(visitor.getClassData());
		IVisitor umlVisitor = new UMLVisitor();
		OutputStream out = new ByteArrayOutputStream();
		m.accept(umlVisitor);
		umlVisitor.printToOutput(out);
		assertFalse(out.toString().contains("\\<\\<singleton\\>\\>"));
	}

}
