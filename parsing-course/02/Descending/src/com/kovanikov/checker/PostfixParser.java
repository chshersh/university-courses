package com.kovanikov.checker;// Generated from /home/dmitryk/programming/DiskretMathLabs/3/translation/02/Descending/Postfix.g4 by ANTLR 4.x
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PostfixParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		NUM=1, SIGN=2, WS=3, EPS=4;
	public static final String[] tokenNames = {
		"<INVALID>", "NUM", "SIGN", "WS", "''"
	};
	public static final int
		RULE_postfix = 0, RULE_aux = 1, RULE_sign = 2;
	public static final String[] ruleNames = {
		"postfix", "aux", "sign"
	};

	@Override
	public String getGrammarFileName() { return "Postfix.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PostfixParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class PostfixContext extends ParserRuleContext {
		public TerminalNode EPS() { return getToken(PostfixParser.EPS, 0); }
		public AuxContext aux() {
			return getRuleContext(AuxContext.class,0);
		}
		public TerminalNode NUM() { return getToken(PostfixParser.NUM, 0); }
		public PostfixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostfixListener ) ((PostfixListener)listener).enterPostfix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostfixListener ) ((PostfixListener)listener).exitPostfix(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostfixVisitor ) return ((PostfixVisitor<? extends T>)visitor).visitPostfix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PostfixContext postfix() throws RecognitionException {
		PostfixContext _localctx = new PostfixContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_postfix);
		try {
			setState(9);
			switch (_input.LA(1)) {
			case NUM:
				enterOuterAlt(_localctx, 1);
				{
				setState(6); match(NUM);
				setState(7); aux();
				}
				break;
			case EPS:
				enterOuterAlt(_localctx, 2);
				{
				setState(8); match(EPS);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AuxContext extends ParserRuleContext {
		public TerminalNode EPS() { return getToken(PostfixParser.EPS, 0); }
		public List<AuxContext> aux() {
			return getRuleContexts(AuxContext.class);
		}
		public SignContext sign() {
			return getRuleContext(SignContext.class,0);
		}
		public AuxContext aux(int i) {
			return getRuleContext(AuxContext.class,i);
		}
		public TerminalNode NUM() { return getToken(PostfixParser.NUM, 0); }
		public AuxContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aux; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostfixListener ) ((PostfixListener)listener).enterAux(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostfixListener ) ((PostfixListener)listener).exitAux(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostfixVisitor ) return ((PostfixVisitor<? extends T>)visitor).visitAux(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AuxContext aux() throws RecognitionException {
		AuxContext _localctx = new AuxContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_aux);
		try {
			setState(17);
			switch (_input.LA(1)) {
			case NUM:
				enterOuterAlt(_localctx, 1);
				{
				setState(11); match(NUM);
				setState(12); aux();
				setState(13); sign();
				setState(14); aux();
				}
				break;
			case EPS:
				enterOuterAlt(_localctx, 2);
				{
				setState(16); match(EPS);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SignContext extends ParserRuleContext {
		public TerminalNode SIGN() { return getToken(PostfixParser.SIGN, 0); }
		public SignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sign; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostfixListener ) ((PostfixListener)listener).enterSign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostfixListener ) ((PostfixListener)listener).exitSign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostfixVisitor ) return ((PostfixVisitor<? extends T>)visitor).visitSign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SignContext sign() throws RecognitionException {
		SignContext _localctx = new SignContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_sign);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(19); match(SIGN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\6\30\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\3\2\3\2\3\2\5\2\f\n\2\3\3\3\3\3\3\3\3\3\3\3\3\5\3\24\n\3"+
		"\3\4\3\4\3\4\2\2\5\2\4\6\2\2\26\2\13\3\2\2\2\4\23\3\2\2\2\6\25\3\2\2\2"+
		"\b\t\7\3\2\2\t\f\5\4\3\2\n\f\7\6\2\2\13\b\3\2\2\2\13\n\3\2\2\2\f\3\3\2"+
		"\2\2\r\16\7\3\2\2\16\17\5\4\3\2\17\20\5\6\4\2\20\21\5\4\3\2\21\24\3\2"+
		"\2\2\22\24\7\6\2\2\23\r\3\2\2\2\23\22\3\2\2\2\24\5\3\2\2\2\25\26\7\4\2"+
		"\2\26\7\3\2\2\2\4\13\23";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}