package pg.bioinf.prosite;

import java.util.List;

public class PrositeExpression {
	
	private List<SequenceCharacterCondition> conditions;

	private PrositeExpression(List<SequenceCharacterCondition> conditions) {
		this.conditions = conditions;
	}
	
	public static PrositeExpression parse(String expression) {
		// TODO
		return null;
	}
	
	public static ExpressionRange findSubstring(String sequence) {
		// TODO
		return null;
	}
	
	public class ExpressionRange {
		public int begin;
		public int end;
		
		@Override
		public String toString() {
			return "ExpressionRange [begin=" + begin + ", end=" + end + "]";
		}
	}
	
}
