package pg.bioinf.prosite;

class AminoacidRepetitionCondition implements SequenceCharacterCondition {
	
	private SequenceCharacterCondition condition;
	private int repMin;
	private int repMax;
	private int repeatedMin;
	private int repeatedMax;
	
	private AminoacidRepetitionCondition() {
	}
	
	public static AminoacidRepetitionCondition parse(String repetitionString, SequenceCharacterCondition precedingCondition) {
		repetitionString = repetitionString.substring(1, repetitionString.length() - 1); // ommit ( and )
		AminoacidRepetitionCondition rep = new AminoacidRepetitionCondition();
		rep.condition = precedingCondition;
		if(repetitionString.contains(",")) {
			rep.repMin = Integer.parseInt(repetitionString.substring(0, repetitionString.indexOf(',')));
			rep.repMax = Integer.parseInt(repetitionString.substring(repetitionString.indexOf(',') + 1));
			return rep;
		}
		else {
			rep.repMin = Integer.parseInt(repetitionString);
			rep.repMax = Integer.parseInt(repetitionString);
			return rep;
		}
	}
	
	
	@Override
	public boolean checkCondition(String sequence, int position) {
		if(sequence.length() < position + repMin) {
			return false;
		}
		int i;
		for(i = 0; i < repMin; i++) {
			if (position + i >= sequence.length()) {
				return false;
			}
			if (!condition.checkCondition(sequence, position + i)) {
				repeatedMin = 0;
				return false;
			}
		}
		repeatedMin = repMin;
		repeatedMax = repMin;
		for(; i < repMax; i++) {
			if (position + i >= sequence.length()) {
				break;
			}
			if(condition.checkCondition(sequence, position + i)) {
				repeatedMax = i + 1;
			}
			else {
				break;
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
