package problem.model.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import problem.detector.IPatternDetector;
import problem.model.visit.ITraverser;

public interface IPackageModel extends ITraverser {
	
	List<IClassData> classes = null;
	Map<String, List<String>> classToInterfaces = new HashMap<>();
	Map<String, String> classToSuperclass = new HashMap<>();
	Map<String, List<String>> classToAssociatedClasses = new HashMap<>();
	Map<String, List<IMethodData>> classToMethods = new HashMap<>();
	List<String> classNames = null;
	
	public IClassData getClassDataFromName(String name);
	
	public List<IClassData> getClasses();
	
	public void addClass(IClassData d);
	
	public void setClasses(List<IClassData> classes);

	public void setClassRelations();
	
	public Map<String, List<String>> getClassToInterfaces();

	public void setClassToInterfaces(Map<String, List<String>> classToInterfaces);

	public Map<String, String> getClassToSuperclass();

	public void setClassToSuperclass(Map<String, String> classToSuperclass);

	public Map<String, List<String>> getClassToAssociatedClasses();

	public void setClassToAssociatedClasses(
			Map<String, List<String>> classToAssociatedClasses);

	public Map<String, List<IMethodData>> getClassToMethods();

	public void setClassToMethods(Map<String, List<IMethodData>> classToMethods);

	public List<String> getClassNames();

	public void setClassNames(List<String> classNames);
	
	public List<IPatternDetector> getDetectors();

	public void setDetectors(List<IPatternDetector> detectors);
	
	public String createArrows();
	
	public String getInheritanceArrows();
	
	public String getImplementsArrows();
	
	public String getAssociationArrows();
	
	public String getUsedClassesArrows();

	public void addSpecialArrow(SpecialArrowKey specialArrowKey, String string);
}
