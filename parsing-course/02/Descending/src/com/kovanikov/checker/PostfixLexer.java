package com.kovanikov.checker;// Generated from /home/dmitryk/programming/DiskretMathLabs/3/translation/02/Descending/Postfix.g4 by ANTLR 4.x
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PostfixLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		NUM=1, SIGN=2, WS=3, EPS=4;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'"
	};
	public static final String[] ruleNames = {
		"NUM", "SIGN", "WS", "EPS"
	};


	public PostfixLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Postfix.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\6\35\b\1\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\3\2\5\2\r\n\2\3\2\6\2\20\n\2\r\2\16\2\21\3\3"+
		"\3\3\3\4\6\4\27\n\4\r\4\16\4\30\3\4\3\4\3\5\2\2\6\3\3\5\4\7\5\t\6\3\2"+
		"\5\3\2\62;\4\2,-//\5\2\13\f\17\17\"\"\36\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3"+
		"\2\2\2\2\t\3\2\2\2\3\f\3\2\2\2\5\23\3\2\2\2\7\26\3\2\2\2\t\34\3\2\2\2"+
		"\13\r\7/\2\2\f\13\3\2\2\2\f\r\3\2\2\2\r\17\3\2\2\2\16\20\t\2\2\2\17\16"+
		"\3\2\2\2\20\21\3\2\2\2\21\17\3\2\2\2\21\22\3\2\2\2\22\4\3\2\2\2\23\24"+
		"\t\3\2\2\24\6\3\2\2\2\25\27\t\4\2\2\26\25\3\2\2\2\27\30\3\2\2\2\30\26"+
		"\3\2\2\2\30\31\3\2\2\2\31\32\3\2\2\2\32\33\b\4\2\2\33\b\3\2\2\2\6\2\f"+
		"\21\30\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}