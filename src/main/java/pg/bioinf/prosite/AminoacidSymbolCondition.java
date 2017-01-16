package pg.bioinf.prosite;

class AminoacidSymbolCondition implements SequenceCharacterCondition {
	
	char symbol;
	
	AminoacidSymbolCondition(char symbol) {
		this.symbol = symbol;
	}

	@Override
	public boolean checkCondition(String sequence, int position) {
		return sequence.charAt(position) == symbol;
	}

	@Override
	public int getNextIndex(String sequence, int position) {
		return position + 1;
	}

	@Override
	public int getMaximumPossibleIndex(String sequence, int position) {
		return position + 1;
	}

}
