package cz.mst.app.piglatin.lex;

import java.io.Serializable;

/**
 * Class represents token data
 * @author Martin Strunc
 */
@SuppressWarnings("serial")
public class Token implements Serializable {
	
	private TokenType type;
	private String value;
	
	public Token(final TokenType type, final String value) {
		this.type = type;
		this.value = value;
	}
	
	public TokenType getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Token [type=");
		builder.append(type);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}
	
}
