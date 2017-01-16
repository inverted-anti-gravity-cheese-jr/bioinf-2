package pg.bioinf.prosite;

import java.util.ArrayList;
import java.util.List;

class AminoacidExcludeCondition implements SequenceCharacterCondition {
	
	private List<String> symbols;
	
	private AminoacidExcludeCondition(List<String> symbols) {
		this.symbols = symbols;
	}
	
	public static AminoacidExcludeCondition parse(String symbolList) {
		symbolList = symbolList.substring(1, symbolList.length() - 1); // ommit { and }
		ArrayList<String> symbols = new ArrayList<String>();
		for(int i = 0; i < symbolList.length(); i++) {
			String symbol = String.valueOf(symbolList.charAt(i));
			if(ProteinDictionary.checkIfProtein(symbol)) {
				symbols.add(symbol);
			}
		}
		return new AminoacidExcludeCondition(symbols);
	}
	
	public void setSymbolList(List<String> symbols) {
		this.symbols = symbols;
	}

	@Override
	public boolean checkCondition(String sequence, int position) {
		String symbol = String.valueOf(sequence.charAt(position));
		return ProteinDictionary.checkIfProtein(symbol) && !symbols.contains(symbol);
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
