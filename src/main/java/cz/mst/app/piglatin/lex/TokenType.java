package cz.mst.app.piglatin.lex;

/**
 * Token types enum 
 * @author Martin Strunc
 */
public enum TokenType {

	WORD,			// letters sequence (with apostrophe)
	DOTHYPHEN,		// punctuation: allowed is dot, and hyphen
	WHITESPACE;		// white space sequence
	
}
