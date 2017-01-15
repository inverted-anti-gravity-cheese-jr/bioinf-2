package pg.bioinf.prosite;

import java.util.ArrayList;
import java.util.List;

public class AminoacidInclude implements SequenceCharacterCondition {
	
	private List<String> symbols;
	
	private AminoacidInclude(List<String> symbols) {
		this.symbols = symbols;
	}
	
	public static AminoacidInclude anySymbol() {
		return new AminoacidInclude(ProteinDictionary.getAlphabet());
	}
	
	public static AminoacidInclude parse(String symbolList) {
		symbolList = symbolList.substring(1, symbolList.length() - 1); // ommit [ and ]
		ArrayList<String> symbols = new ArrayList<String>();
		for(int i = 0; i < symbolList.length(); i++) {
			String symbol = String.valueOf(symbolList.indexOf(i));
			if(ProteinDictionary.checkIfProtein(symbol)) {
				symbols.add(symbol);
			}
		}
		return new AminoacidInclude(symbols);
	}
	
	public void setSymbolList(List<String> symbols) {
		this.symbols = symbols;
	}

	@Override
	public boolean checkCondition(String sequence, int position) {
		return symbols.contains(String.valueOf(sequence.charAt(position)));
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
