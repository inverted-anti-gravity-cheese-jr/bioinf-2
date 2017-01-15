package pg.bioinf.prosite;

public class AminoacidRepetition implements SequenceCharacterCondition {
	
	private SequenceCharacterCondition condition;
	private int repMin;
	private int repMax;
	private int repeatedMin;
	private int repeatedMax;
	
	private AminoacidRepetition() {
	}
	
	public static AminoacidRepetition parse(String repetitionString) {
		repetitionString = repetitionString.substring(1, repetitionString.length() - 1); // ommit ( and )
		if(repetitionString.contains(",")) {
			AminoacidRepetition rep = new AminoacidRepetition();
			rep.repMin = Integer.parseInt(repetitionString.substring(0, repetitionString.indexOf(',')));
			rep.repMax = Integer.parseInt(repetitionString.substring(repetitionString.indexOf(',') + 1));
			return rep;
		}
		else {
			AminoacidRepetition rep = new AminoacidRepetition();
			rep.repMin = Integer.parseInt(repetitionString);
			rep.repMax = Integer.parseInt(repetitionString);
			return rep;
		}
	}
	
	
	@Override
	public boolean checkCondition(String sequence, int position) {
		for(int i = 0; i < repMin; i++) {
			if (condition.checkCondition(sequence, position + i)) {
				repeatedMin = 0;
				return false;
			}
		}
		repeatedMin = repMin;
		for(int i = 0; i < repMax - repMin; i++) {
			if(condition.checkCondition(sequence, position + repMin + i)) {
				repeatedMax = i;
			}
		}
		
		return true;
	}
	
	@Override
	public int getNextIndex(String sequence, int position) {
		return position + repeatedMin;
	}
	
	@Override
	public int getMaximumPossibleIndex(String sequence, int position) {
		return position + repeatedMax;
	}
}
