package pg.bioinf.prosite;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProteinDictionary {

	private static final List<String> alphabet = Collections.unmodifiableList(Arrays.asList("A", "C", "D", "E", "F", "G", "H", "I", "K", "L", "M", "N", "P", "Q",
			"R", "S", "T", "V", "W", "Y"));
	
	public static List<String> getAlphabet() {
		return alphabet;
	}
	
	public static boolean checkIfProtein(String symbol) {
		return alphabet.contains(symbol);
	}
}
