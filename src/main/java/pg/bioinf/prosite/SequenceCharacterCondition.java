package pg.bioinf.prosite;

interface SequenceCharacterCondition {

	boolean checkCondition(String sequence, int position);
	int getNextIndex(String sequence, int position);
	int getMaximumPossibleIndex(String sequence, int position);
}
