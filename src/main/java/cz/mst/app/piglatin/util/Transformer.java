package cz.mst.app.piglatin.util;

import static cz.mst.app.piglatin.Constants.AY;
import static cz.mst.app.piglatin.Constants.VOWELS;
import static cz.mst.app.piglatin.Constants.WAY;

import cz.mst.app.piglatin.lex.Token;
import cz.mst.app.piglatin.lex.TokenType;

public class Transformer {
	
	private Transformer() { /* util class, we don't want instances */ }

	/**
	 * Transform tokens to string
	 * @param token input token
	 * @return transformed value of token
	 */
	public static String transform(final Token token) {
		// token value
		final String tokenValue = token.getValue();
		// if token is word, do transformation (except where word ends with way)
		if (TokenType.WORD == token.getType() && !tokenValue.endsWith(WAY)) {
			StringBuilder str = new StringBuilder();
			char firstChar = tokenValue.charAt(0);

			// do transformation
			if (VOWELS.contains(String.valueOf(firstChar).toLowerCase())) {
				// words starting with vowels, add to output with "way" at the end
				str.append(tokenValue).append(WAY);
			} else {
				// cut first letter and add remaining letters to output, with correct case 
				for (int i = 1; i < tokenValue.length(); i++) {
					char prevChar = tokenValue.charAt(i - 1);
					char currChar = tokenValue.charAt(i);
					// append letter to output with case correction
					str.append(correctCase(currChar, Character.isUpperCase(prevChar)));
				}
				//  first chat at the end + "ay"
				str.append(correctCase(firstChar, Character.isUpperCase(tokenValue.charAt(tokenValue.length() - 1)))).append(AY);
			}
			
			// correct apostrophe position (improvement: will work correctly only for one apostrophe in word)
			int apoIndex = tokenValue.indexOf("'");
			if (apoIndex != -1) {
				// apostrophe position relative to end of word
				int relPosIndex = (tokenValue.length() - 1) - apoIndex;
				// remove apostrophe
				str.deleteCharAt(str.indexOf("'"));
				// put it on right position
				str.insert(str.length() - relPosIndex, "'"); 
			}
			
			// return as string
			return str.toString();
		}
		// otherwise return unmodified value
		return tokenValue;
	}
	
	/**
	 * Correction for letter case by character on the same positoin in original word
	 * @param ch charater to correct
	 * @param wasUpper flag if previous char was upper
	 * @return corrected character
	 */
	private static String correctCase(final char ch, final boolean wasUpper) {
		if (wasUpper) {
			return String.valueOf(ch).toUpperCase();
		}
		return String.valueOf(ch).toLowerCase();
	}
	
}
