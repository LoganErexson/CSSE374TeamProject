package problem.asm;

import java.util.ArrayList;

import org.junit.Before;

import problem.detector.AdapterDetector;
import problem.detector.CompositeDetector;
import problem.detector.DecoratorDetector;
import problem.detector.InterfaceDetector;
import problem.detector.SingletonDetector;

public class CompositeDetectorTest {

	private ArrayList<Object> detectors;

	@Before
	public void setUp(){
		this.detectors = new ArrayList<>();
		this.detectors.add(new SingletonDetector());
		this.detectors.add(new DecoratorDetector());
		this.detectors.add(new AdapterDetector());
		this.detectors.add(new InterfaceDetector());
		this.detectors.add(new CompositeDetector());
	}
}
