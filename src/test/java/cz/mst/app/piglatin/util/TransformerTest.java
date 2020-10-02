package cz.mst.app.piglatin.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cz.mst.app.piglatin.Constants;
import cz.mst.app.piglatin.lex.Token;
import cz.mst.app.piglatin.lex.TokenType;

public class TransformerTest {

	@Test
	public void noModifyWhitespace() {
		final Token wsToken = new Token(TokenType.WHITESPACE, "  ");
		assertEquals(wsToken.getValue(), Transformer.transform(wsToken));
	}
	
	@Test
	public void noModifyPunct() {
		final Token wsToken = new Token(TokenType.DOTHYPHEN, ".");
		assertEquals(wsToken.getValue(), Transformer.transform(wsToken));
	}

	@Test
	public void noModifyEndingWay() {
		final Token wsToken = new Token(TokenType.WORD, "highway");
		assertEquals(wsToken.getValue(), Transformer.transform(wsToken));
	}
	
	@Test
	public void modifyConsonant() {
		final Token wsToken = new Token(TokenType.WORD, "template");
		assertEquals("emplate" + "t" + Constants.AY, Transformer.transform(wsToken));
	}
	
	@Test
	public void modifyVowel() {
		final Token wsToken = new Token(TokenType.WORD, "earth");
		assertEquals("earth" + Constants.WAY, Transformer.transform(wsToken));
	}
	
	@Test
	public void modifyConsonantKeepCase() {
		final Token wsToken = new Token(TokenType.WORD, "TeMpLaTe");
		assertEquals("EmPlAtE" + "t" + Constants.AY, Transformer.transform(wsToken));
	}
	
	@Test
	public void modifyConsonantKeepApoPosition() {
		final Token wsToken = new Token(TokenType.WORD, "templ'ate");
		assertEquals("emplate" + "'" + "t" + Constants.AY, Transformer.transform(wsToken));
	}
	
	@Test
	public void modifyConsonantKeepCaseAndApoPosition() {
		final Token wsToken = new Token(TokenType.WORD, "TeMpLa'Te");
		assertEquals("EmPlAtE" + "t" + "'"  + Constants.AY, Transformer.transform(wsToken));
	}
	
}
