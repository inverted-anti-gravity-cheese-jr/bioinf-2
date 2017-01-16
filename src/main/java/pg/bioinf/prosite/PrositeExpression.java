package pg.bioinf.prosite;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.diogonunes.jcdp.bw.Printer;
import com.diogonunes.jcdp.bw.Printer.Types;
import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi.BColor;

public class PrositeExpression {
	
	private List<SequenceCharacterCondition> conditions;

	private PrositeExpression(List<SequenceCharacterCondition> conditions) {
		this.conditions = conditions;
	}
	
	public static PrositeExpression parse(String expression) throws InvalidPrositeExpressionException {
		if(!PrositeValidator.validateExpression(expression)) {
			throw new InvalidPrositeExpressionException();
		}
		
		List<SequenceCharacterCondition> conditions = new ArrayList<SequenceCharacterCondition>();
		
		String[] conditionsStr = expression.split("-");
		for(String conditionStr : conditionsStr) {
			List<SequenceCharacterCondition> condition;
			if(conditionStr.contains("(")) {
				String mainCond = conditionStr.substring(0, conditionStr.indexOf('('));
				String repCond = conditionStr.substring(conditionStr.indexOf('('));
				condition = getCondition(mainCond);
				SequenceCharacterCondition mainCondition = condition.get(condition.size() - 1);
				for(int i = 0; i < condition.size(); i++) {
					conditions.add(condition.get(i));
				}
				conditions.add(AminoacidRepetitionCondition.parse(repCond, mainCondition));
			}
			else {
				condition = getCondition(conditionStr);
				conditions.addAll(condition);
			}
		}
		return new PrositeExpression(conditions);
	}
	
	public void printSubstring(String sequence) {
		List<ExpressionRange> ranges = findSubstring(sequence);
		if(ranges.isEmpty()) {
			System.out.println(sequence);
		}
		else {
			ColoredPrinter printer = new ColoredPrinter.Builder(1, false).build();
			for(int i = 0; i < sequence.length(); i++) {
				char c = sequence.charAt(i);
				boolean printRed = false;
				for(ExpressionRange range: ranges) {
					if(i >= range.begin && i <= range.end) {
						printRed = true;
						break;
					}
				}
				
				if(!printRed) {
					printer.clear();
				}
				else {
					printer.setBackgroundColor(BColor.RED);
				}
				printer.print(c);
			}
			printer.clear();
			printer.println("");
		}
	}
	
	public List<ExpressionRange> findSubstring(String sequence) {
		List<ExpressionRange> ranges = new ArrayList<ExpressionRange>();
		int cutoff = 0;
		
		do {
			ExpressionRange range = findSubstring(sequence, -1, 0, 0);
			if(range == null) {
				break;
			}
			sequence = sequence.substring(range.begin + 1);
			
			range.begin += cutoff;
			range.end += cutoff;
			cutoff = range.begin + 1;
			ranges.add(range);
		} while(sequence.length() >= conditions.size());
		
		return ranges;
	}
	
	private ExpressionRange findSubstring(String sequence, int startPosition, int position, int usedConditions) {
		if(usedConditions != conditions.size() && position == sequence.length()) {
			return null;
		}
		if(startPosition < 0 && position == sequence.length()) {
			return null;
		}
		if(usedConditions == conditions.size() || position == sequence.length()) {
			return new ExpressionRange(startPosition, position - 1);
		}
		SequenceCharacterCondition condition = conditions.get(usedConditions);
		if(condition.checkCondition(sequence, position)) {
			int nextIndex = condition.getNextIndex(sequence, position);
			int maxIndex = condition.getMaximumPossibleIndex(sequence, position);
			usedConditions++;
			
			if(startPosition < 0) {
				startPosition = position;
			}
			
			if(maxIndex > nextIndex) {
				return findMaxInRange(sequence, startPosition, nextIndex, maxIndex, usedConditions);
			}
			else {
				return findSubstring(sequence, startPosition, nextIndex, usedConditions);
			}
		}
		else {
			if(usedConditions > 0) {
				usedConditions = 0;
				startPosition = -1;
			}
			return findSubstring(sequence, startPosition, position + 1, usedConditions);
		}
		
	}
	
	private ExpressionRange findMaxInRange(String sequence, int startPosition, int minPos, int maxPos, int usedConditions) {
		ExpressionRange range = null;
		for(int i = minPos; i <= maxPos; i++) {
			ExpressionRange tmp = findSubstring(sequence, startPosition, i, usedConditions);
			if(tmp != null && (range == null || tmp.length() > range.length())) {
				range = tmp;
			}
		}
		return range;
	}
	
	public class ExpressionRange {
		public int begin;
		public int end;
		
		public ExpressionRange(int begin, int end) {
			this.begin = begin;
			this.end = end;
		}
		
		public int length() {
			return end - begin;
		}

		@Override
		public String toString() {
			return "ExpressionRange [begin=" + begin + ", end=" + end + "]";
		}
	}
	
	private static List<SequenceCharacterCondition> getCondition(String condition) {
		if(condition.startsWith("[")) {
			return Arrays.asList(AminoacidIncludeCondition.parse(condition));
		}
		if(condition.startsWith("{")) {
			return Arrays.asList(AminoacidExcludeCondition.parse(condition));
		}
		if("x".equals(condition)) {
			return Arrays.asList(AminoacidIncludeCondition.anySymbol());
		}
		ArrayList<SequenceCharacterCondition> syms = new ArrayList<>();
		for(int i = 0; i < condition.length(); i++) {
			syms.add(new AminoacidSymbolCondition(condition.charAt(i)));
		}
		return syms;
	}
	
}
