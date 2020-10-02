package cz.mst.app.piglatin.lex;

import java.util.stream.Stream;

/**
 * Lexical analyzer for application input
 * @author Martin Strunc
 */
public interface Lexan {

	/**
	 * Method returns stream of tokens read from input
	 * @return stream of tokens
	 */
	Stream<Token> stream();
	
}
