package problem.asm;

public class SpecialArrowKey {
	
	private String toClass;
	private String fromClass;
	private String lineType;
	
	public SpecialArrowKey(String fromClass, String toClass, String lineType){
		this.fromClass=fromClass;
		this.toClass=toClass;
		this.lineType=lineType;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpecialArrowKey other = (SpecialArrowKey) obj;
		if(!this.toClass.equals(other.toClass))
			return false;
		if(!this.fromClass.equals(other.fromClass))
			return false;
		if(!this.lineType.equals(other.lineType))
			return false;
		
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.fromClass == null) ? 0 : this.fromClass.hashCode())
				+((this.toClass == null) ? 0 : this.toClass.hashCode())
				+((this.lineType == null) ? 0 : this.lineType.hashCode());
		return result;
	}

}
