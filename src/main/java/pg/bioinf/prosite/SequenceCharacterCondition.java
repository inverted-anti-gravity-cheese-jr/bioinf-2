package pg.bioinf.prosite;

public interface SequenceCharacterCondition {

	boolean checkCondition(String sequence, int position);
	int getNextIndex(String sequence, int position);
	int getMaximumPossibleIndex(String sequence, int position);
}
