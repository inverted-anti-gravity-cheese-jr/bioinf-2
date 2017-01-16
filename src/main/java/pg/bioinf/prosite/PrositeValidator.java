package pg.bioinf.prosite;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class PrositeValidator {
	
	private static final List<String> prositeSpecialCharacters = Collections.unmodifiableList(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-", "(", ")", "[", "]", "{", "}", "x", ","));
	
	static boolean validateExpression(String expression) {
		boolean bracketOpen = false;
		boolean curlyBracketOpen = false;
		boolean squareBracketOpen = false;
		
		for(int i = 0; i < expression.length(); i++) {
			char chr = expression.charAt(i);
			boolean validCharacter = ProteinDictionary.checkIfProtein(String.valueOf(chr)) || prositeSpecialCharacters.contains(String.valueOf(chr));
			
			if(!validCharacter) {
				return false;
			}
			
			if(chr == '(') {
				if(bracketOpen)
					return false;
				bracketOpen = true;
			}
			if(chr == ')') {
				bracketOpen = false;
			}
			if(chr == '[') {
				if(curlyBracketOpen)
					return false;
				curlyBracketOpen = true;
			}
			if(chr == ']') {
				curlyBracketOpen = false;
			}
			if(chr == '{') {
				if(squareBracketOpen)
					return false;
				squareBracketOpen = true;
			}
			if(chr == '}') {
				squareBracketOpen = false;
			}
		}
		
		return !bracketOpen && !curlyBracketOpen && !squareBracketOpen;
	}
	
	
}
