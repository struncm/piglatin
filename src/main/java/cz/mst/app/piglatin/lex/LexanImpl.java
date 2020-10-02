package cz.mst.app.piglatin.lex;

import static cz.mst.app.piglatin.lex.TokenType.DOTHYPHEN;
import static cz.mst.app.piglatin.lex.TokenType.WHITESPACE;
import static cz.mst.app.piglatin.lex.TokenType.WORD;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cz.mst.app.piglatin.PiglatinException;

@Component
public class LexanImpl implements Lexan, InitializingBean {

	@Value("${piglatin.input.file}") private String inputFileName;
	
	private StringBuilder input = new StringBuilder();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// read content of the file to string builder
		input.append(Files.readString(Paths.get(inputFileName)));
		/*
		try (Stream<String> st = Files.lines(Paths.get(inputFileName))) {
			st.forEach(input::append);
		} catch (IOException ex) {
			throw new PiglatinException(ex);
		}
		*/
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Stream<Token> stream() {
		return Stream
			.generate(() -> next())
			.takeWhile(Objects::nonNull);
	}
	
	/**
	 * Method read next token from input
	 * @return nex token from input, may be null
	 */
	private Token next() {
		// if we have input try to find token
		if (input.length() > 0) {
			// value of the next token
			StringBuilder tokenValue = new StringBuilder();
			// type of the next token (first found in begin on input)
			TokenType tokenType = null;
			
			for (int i = 0; i < input.length(); i++) {
				// next char in input
				char nextChar = input.charAt(i);
				// lookahead on next type by next char in input
				TokenType lookAheadTokenType = null;
				
				// categorize character 
				if (nextChar == '.' || nextChar == '-') { // all charaters which keeps their position (improvement: check whitespaces around hyphens)
					lookAheadTokenType = DOTHYPHEN;
				} else if (Character.isLetter(nextChar) || nextChar == '\'') { // letters means words
					lookAheadTokenType = WORD;
				} else if (Character.isWhitespace(nextChar)) { // whitespaces
					lookAheadTokenType = WHITESPACE;
				} else {
					throw new PiglatinException("Unsupported character '" + nextChar + "'!");
				}
				
				// if token type not yet set, use lookahead token type
				if (tokenType == null) {
					tokenType = lookAheadTokenType;
				}
				
				// when token type isn't match lookahead token type break without adding next char to token value
				if (tokenType != lookAheadTokenType) {
					break;
				} else {
					// add next char to token value
					tokenValue.append(nextChar);
					// if token type is punctuation also break (only one char)
					if (DOTHYPHEN == tokenType) {
						break;
					}
				}
			}
			
			// create token
			final Token token = new Token(tokenType, tokenValue.toString());
			// delete token value from input
			input.delete(0, tokenValue.length());
			// return token
			return token;
		}
		// otherwise return null
		return null;
	}

	
}
