package problem.asm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import problem.detector.CompositeDetector;
import problem.detector.IPatternDetector;
import problem.detector.InterfaceDetector;
import problem.model.data.IClassData;
import problem.model.data.IPackageModel;
import problem.model.data.PackageModel;
import problem.model.visit.IVisitor;
import problem.model.visit.UMLVisitor;

public class CompositeDetectorTest {

	private String[] classes = {"problem.composite.AbstractComponent", "problem.composite.Composite", "problem.composite.IComponent",
			"problem.composite.Leaf", "problem.composite.NoFieldFalseComposite", "problem.composite.NoMethodFalseComposite", 
			"problem.composite.ExtendedComposite", "problem.composite.NonSubclassFalseComposite"};
	private IPackageModel model;
	
	@Before
	public void setUp() throws IOException{
		List<IPatternDetector> detectors = new ArrayList<>();
		detectors.add(new InterfaceDetector());
		detectors.add(new CompositeDetector());
		IPackageModel m = new PackageModel(detectors);
		for (String s : this.classes){
			AbstractASMVisitor visitor = VisitorManager.visitClass(s);
			m.addClass(visitor.getClassData());
		}
		IVisitor umlVisitor = new UMLVisitor();
		m.accept(umlVisitor);
		this.model = m;
	}
	
	@Test
	public void testComposite(){
		IClassData compositeData = this.model.getClassDataFromName("Composite");
		
		assertEquals("\\n\\<\\<composite\\>\\>\\n", compositeData.getPattern());
		assertEquals("fillcolor = pink\n", compositeData.getFill());
	}
	
	@Test
	public void testComponent(){
		IClassData componentData = this.model.getClassDataFromName("AbstractComponent");
		assertEquals("\\n\\<\\<component\\>\\>\\n", componentData.getPattern());
		assertEquals("fillcolor = pink\n", componentData.getFill());
	}
	
	@Test
	public void testLeaf(){
		IClassData leafData = this.model.getClassDataFromName("Leaf");
		assertEquals("\\n\\<\\<leaf\\>\\>\\n", leafData.getPattern());
		assertEquals("fillcolor = pink\n", leafData.getFill());
	}
	@Test
	public void testExtendedComposite(){
		IClassData extendedData = this.model.getClassDataFromName("ExtendedComposite");
		assertEquals("\\n\\<\\<composite\\>\\>\\n", extendedData.getPattern());
		assertEquals("fillcolor = pink\n", extendedData.getFill());
	}
	/**
	 * 
	 * Tests whether subclasses with no methods to take in IComponents are considered components
	 * (They should not be)
	 *
	 */
	@Test
	public void testNoMethodFalseComposite(){
		IClassData falseComposite = this.model.getClassDataFromName("NoMethodFalseComposite");
		assertFalse("\\n\\<\\<composite\\>\\>\\n".equals(falseComposite.getPattern()));

		assertEquals("\\n\\<\\<leaf\\>\\>\\n", falseComposite.getPattern());
		assertEquals("fillcolor = pink\n", falseComposite.getFill());
	}
	
	/**
	 * 
	 * Tests whether subclasses with no storage of IComponents are considered components
	 * (They should not be)
	 *
	 */
	@Test
	public void testNoFieldFalseComposite(){
		IClassData falseComposite = this.model.getClassDataFromName("NoFieldFalseComposite");
		assertFalse("\\n\\<\\<composite\\>\\>\\n".equals(falseComposite.getPattern()));

		assertEquals("\\n\\<\\<leaf\\>\\>\\n", falseComposite.getPattern());
		assertEquals("fillcolor = pink\n", falseComposite.getFill());
	}
	
	/**
	 * 
	 * Tests whether subclasses with no storage of IComponents are considered components
	 * (They should not be)
	 *
	 */
	@Test
	public void testNonSubclassFalseComposite(){
		IClassData falseComposite = this.model.getClassDataFromName("NonSubclassFalseComposite");
		assertFalse("\\n\\<\\<composite\\>\\>\\n".equals(falseComposite.getPattern()));

		assertFalse(falseComposite.getPattern().equals("\\n\\<\\<leaf\\>\\>\\n"));
		assertFalse(falseComposite.getFill().equals("fillcolor = pink\n"));
	}
	
}
