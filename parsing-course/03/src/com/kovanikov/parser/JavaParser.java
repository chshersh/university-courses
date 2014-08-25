package com.kovanikov.parser;// Generated from /home/dmitryk/programming/DiskretMathLabs/3/translation/03/Java.g4 by ANTLR 4.x
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JavaParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__34=1, T__33=2, T__32=3, T__31=4, T__30=5, T__29=6, T__28=7, T__27=8, 
		T__26=9, T__25=10, T__24=11, T__23=12, T__22=13, T__21=14, T__20=15, T__19=16, 
		T__18=17, T__17=18, T__16=19, T__15=20, T__14=21, T__13=22, T__12=23, 
		T__11=24, T__10=25, T__9=26, T__8=27, T__7=28, T__6=29, T__5=30, T__4=31, 
		T__3=32, T__2=33, T__1=34, T__0=35, EPS=36, NUMBER=37, CHAR=38, STRING=39, 
		NAME=40, WS=41, IDENTIFIER=42;
	public static final String[] tokenNames = {
		"<INVALID>", "'long'", "']'", "'&'", "'public'", "','", "'['", "'-'", 
		"'while'", "'*'", "'('", "'if'", "'int'", "'false'", "'private'", "'void'", 
		"'double'", "'{'", "'final'", "'else'", "'boolean'", "'}'", "'true'", 
		"'static'", "'char'", "'%'", "'^'", "')'", "'+'", "'protected'", "'='", 
		"'return'", "';'", "'/'", "'class'", "'|'", "EPS", "NUMBER", "CHAR", "STRING", 
		"NAME", "WS", "IDENTIFIER"
	};
	public static final int
		RULE_javaLang = 0, RULE_classBody = 1, RULE_globalName = 2, RULE_definition = 3, 
		RULE_varDefinition = 4, RULE_valueContinuation = 5, RULE_funDefinition = 6, 
		RULE_funArgs = 7, RULE_funArgsContinuation = 8, RULE_funBody = 9, RULE_statement = 10, 
		RULE_expression = 11, RULE_whileExpression = 12, RULE_ifExpression = 13, 
		RULE_elseExpression = 14, RULE_elseContinuation = 15, RULE_returnCase = 16, 
		RULE_value = 17, RULE_bool = 18, RULE_varType = 19, RULE_primitiveType = 20, 
		RULE_modifier = 21, RULE_whileOperator = 22, RULE_ifOperator = 23, RULE_elseOperator = 24, 
		RULE_retOperator = 25, RULE_binOperation = 26;
	public static final String[] ruleNames = {
		"javaLang", "classBody", "globalName", "definition", "varDefinition", 
		"valueContinuation", "funDefinition", "funArgs", "funArgsContinuation", 
		"funBody", "statement", "expression", "whileExpression", "ifExpression", 
		"elseExpression", "elseContinuation", "returnCase", "value", "bool", "varType", 
		"primitiveType", "modifier", "whileOperator", "ifOperator", "elseOperator", 
		"retOperator", "binOperation"
	};

	@Override
	public String getGrammarFileName() { return "Java.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public JavaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class JavaLangContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(JavaParser.EOF, 0); }
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public TerminalNode NAME() { return getToken(JavaParser.NAME, 0); }
		public JavaLangContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javaLang; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterJavaLang(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitJavaLang(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitJavaLang(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JavaLangContext javaLang() throws RecognitionException {
		JavaLangContext _localctx = new JavaLangContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_javaLang);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54); match(T__31);
			setState(55); match(T__1);
			setState(56); match(NAME);
			setState(57); match(T__18);
			setState(58); classBody();
			setState(59); match(EOF);
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

	public static class ClassBodyContext extends ParserRuleContext {
		public DefinitionContext definition() {
			return getRuleContext(DefinitionContext.class,0);
		}
		public GlobalNameContext globalName() {
			return getRuleContext(GlobalNameContext.class,0);
		}
		public ClassBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterClassBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitClassBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitClassBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBodyContext classBody() throws RecognitionException {
		ClassBodyContext _localctx = new ClassBodyContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_classBody);
		try {
			setState(65);
			switch (_input.LA(1)) {
			case T__34:
			case T__31:
			case T__23:
			case T__21:
			case T__20:
			case T__19:
			case T__17:
			case T__15:
			case T__12:
			case T__11:
			case T__6:
				enterOuterAlt(_localctx, 1);
				{
				setState(61); globalName();
				setState(62); definition();
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(64); match(T__14);
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

	public static class GlobalNameContext extends ParserRuleContext {
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public TerminalNode NAME() { return getToken(JavaParser.NAME, 0); }
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public GlobalNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globalName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterGlobalName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitGlobalName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitGlobalName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalNameContext globalName() throws RecognitionException {
		GlobalNameContext _localctx = new GlobalNameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_globalName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__31) | (1L << T__21) | (1L << T__17) | (1L << T__12) | (1L << T__6))) != 0)) {
				{
				{
				setState(67); modifier();
				}
				}
				setState(72);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(73); varType();
			setState(74); match(NAME);
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

	public static class DefinitionContext extends ParserRuleContext {
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public VarDefinitionContext varDefinition() {
			return getRuleContext(VarDefinitionContext.class,0);
		}
		public FunDefinitionContext funDefinition() {
			return getRuleContext(FunDefinitionContext.class,0);
		}
		public DefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_definition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefinitionContext definition() throws RecognitionException {
		DefinitionContext _localctx = new DefinitionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_definition);
		try {
			setState(82);
			switch (_input.LA(1)) {
			case T__5:
			case T__3:
				enterOuterAlt(_localctx, 1);
				{
				setState(76); varDefinition();
				setState(77); classBody();
				}
				break;
			case T__25:
				enterOuterAlt(_localctx, 2);
				{
				setState(79); funDefinition();
				setState(80); classBody();
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

	public static class VarDefinitionContext extends ParserRuleContext {
		public boolean longRule = true;
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public ValueContinuationContext valueContinuation() {
			return getRuleContext(ValueContinuationContext.class,0);
		}
		public VarDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterVarDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitVarDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitVarDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDefinitionContext varDefinition() throws RecognitionException {
		VarDefinitionContext _localctx = new VarDefinitionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_varDefinition);
		try {
			setState(90);
			switch (_input.LA(1)) {
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				setState(84); match(T__5);
				setState(85); value();
				setState(86); valueContinuation();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(88); match(T__3);
				((VarDefinitionContext)_localctx).longRule =  false;
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

	public static class ValueContinuationContext extends ParserRuleContext {
		public BinOperationContext binOperation() {
			return getRuleContext(BinOperationContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public ValueContinuationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueContinuation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterValueContinuation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitValueContinuation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitValueContinuation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContinuationContext valueContinuation() throws RecognitionException {
		ValueContinuationContext _localctx = new ValueContinuationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_valueContinuation);
		try {
			setState(97);
			switch (_input.LA(1)) {
			case T__3:
				enterOuterAlt(_localctx, 1);
				{
				setState(92); match(T__3);
				}
				break;
			case T__32:
			case T__28:
			case T__26:
			case T__22:
			case T__13:
			case T__10:
			case T__9:
			case T__7:
			case T__2:
			case T__0:
			case NUMBER:
			case CHAR:
			case STRING:
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(93); binOperation();
				setState(94); value();
				setState(95); match(T__3);
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

	public static class FunDefinitionContext extends ParserRuleContext {
		public FunArgsContext funArgs() {
			return getRuleContext(FunArgsContext.class,0);
		}
		public FunBodyContext funBody() {
			return getRuleContext(FunBodyContext.class,0);
		}
		public FunDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterFunDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitFunDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitFunDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunDefinitionContext funDefinition() throws RecognitionException {
		FunDefinitionContext _localctx = new FunDefinitionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_funDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99); match(T__25);
			setState(100); funArgs();
			setState(101); match(T__18);
			setState(102); funBody();
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

	public static class FunArgsContext extends ParserRuleContext {
		public Token NAME;
		public TerminalNode NAME() { return getToken(JavaParser.NAME, 0); }
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public FunArgsContinuationContext funArgsContinuation() {
			return getRuleContext(FunArgsContinuationContext.class,0);
		}
		public FunArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funArgs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterFunArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitFunArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitFunArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunArgsContext funArgs() throws RecognitionException {
		FunArgsContext _localctx = new FunArgsContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_funArgs);
		try {
			setState(109);
			switch (_input.LA(1)) {
			case T__34:
			case T__23:
			case T__20:
			case T__19:
			case T__15:
			case T__11:
				enterOuterAlt(_localctx, 1);
				{
				setState(104); varType();
				setState(105); ((FunArgsContext)_localctx).NAME = match(NAME);
				setState(106); funArgsContinuation((((FunArgsContext)_localctx).NAME!=null?((FunArgsContext)_localctx).NAME.getText():null));
				}
				break;
			case T__30:
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				setState(108); funArgsContinuation("");
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

	public static class FunArgsContinuationContext extends ParserRuleContext {
		public String name;
		public boolean endArgs = false;
		public FunArgsContext funArgs() {
			return getRuleContext(FunArgsContext.class,0);
		}
		public FunArgsContinuationContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public FunArgsContinuationContext(ParserRuleContext parent, int invokingState, String name) {
			super(parent, invokingState);
			this.name = name;
		}
		@Override public int getRuleIndex() { return RULE_funArgsContinuation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterFunArgsContinuation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitFunArgsContinuation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitFunArgsContinuation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunArgsContinuationContext funArgsContinuation(String name) throws RecognitionException {
		FunArgsContinuationContext _localctx = new FunArgsContinuationContext(_ctx, getState(), name);
		enterRule(_localctx, 16, RULE_funArgsContinuation);
		try {
			setState(115);
			switch (_input.LA(1)) {
			case T__30:
				enterOuterAlt(_localctx, 1);
				{
				setState(111); match(T__30);
				setState(112); funArgs();
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				setState(113); match(T__8);
				((FunArgsContinuationContext)_localctx).endArgs =  true;
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

	public static class FunBodyContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ReturnCaseContext returnCase() {
			return getRuleContext(ReturnCaseContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FunBodyContext funBody() {
			return getRuleContext(FunBodyContext.class,0);
		}
		public FunBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterFunBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitFunBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitFunBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunBodyContext funBody() throws RecognitionException {
		FunBodyContext _localctx = new FunBodyContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_funBody);
		int _la;
		try {
			setState(127);
			switch (_input.LA(1)) {
			case T__34:
			case T__23:
			case T__20:
			case T__19:
			case T__15:
			case T__11:
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(117); statement();
				setState(118); funBody();
				}
				break;
			case T__27:
			case T__24:
				enterOuterAlt(_localctx, 2);
				{
				setState(120); expression();
				setState(121); funBody();
				}
				break;
			case T__14:
			case T__4:
				enterOuterAlt(_localctx, 3);
				{
				setState(124);
				_la = _input.LA(1);
				if (_la==T__4) {
					{
					setState(123); returnCase();
					}
				}

				setState(126); match(T__14);
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

	public static class StatementContext extends ParserRuleContext {
		public VarDefinitionContext varDefinition() {
			return getRuleContext(VarDefinitionContext.class,0);
		}
		public TerminalNode NAME() { return getToken(JavaParser.NAME, 0); }
		public VarTypeContext varType() {
			return getRuleContext(VarTypeContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__34) | (1L << T__23) | (1L << T__20) | (1L << T__19) | (1L << T__15) | (1L << T__11))) != 0)) {
				{
				setState(129); varType();
				}
			}

			setState(132); match(NAME);
			setState(133); varDefinition();
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

	public static class ExpressionContext extends ParserRuleContext {
		public IfExpressionContext ifExpression() {
			return getRuleContext(IfExpressionContext.class,0);
		}
		public WhileExpressionContext whileExpression() {
			return getRuleContext(WhileExpressionContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_expression);
		try {
			setState(137);
			switch (_input.LA(1)) {
			case T__27:
				enterOuterAlt(_localctx, 1);
				{
				setState(135); whileExpression();
				}
				break;
			case T__24:
				enterOuterAlt(_localctx, 2);
				{
				setState(136); ifExpression();
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

	public static class WhileExpressionContext extends ParserRuleContext {
		public BoolContext bool() {
			return getRuleContext(BoolContext.class,0);
		}
		public WhileOperatorContext whileOperator() {
			return getRuleContext(WhileOperatorContext.class,0);
		}
		public FunBodyContext funBody() {
			return getRuleContext(FunBodyContext.class,0);
		}
		public WhileExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterWhileExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitWhileExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitWhileExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileExpressionContext whileExpression() throws RecognitionException {
		WhileExpressionContext _localctx = new WhileExpressionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_whileExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139); whileOperator();
			setState(140); match(T__25);
			setState(141); bool();
			setState(142); match(T__8);
			setState(143); match(T__18);
			setState(144); funBody();
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

	public static class IfExpressionContext extends ParserRuleContext {
		public ElseExpressionContext elseExpression() {
			return getRuleContext(ElseExpressionContext.class,0);
		}
		public BoolContext bool() {
			return getRuleContext(BoolContext.class,0);
		}
		public IfOperatorContext ifOperator() {
			return getRuleContext(IfOperatorContext.class,0);
		}
		public FunBodyContext funBody() {
			return getRuleContext(FunBodyContext.class,0);
		}
		public IfExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterIfExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitIfExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitIfExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfExpressionContext ifExpression() throws RecognitionException {
		IfExpressionContext _localctx = new IfExpressionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_ifExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146); ifOperator();
			setState(147); match(T__25);
			setState(148); bool();
			setState(149); match(T__8);
			setState(150); match(T__18);
			setState(151); funBody();
			setState(152); elseExpression();
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

	public static class ElseExpressionContext extends ParserRuleContext {
		public boolean sameLine = false;
		public TerminalNode EPS() { return getToken(JavaParser.EPS, 0); }
		public ElseContinuationContext elseContinuation() {
			return getRuleContext(ElseContinuationContext.class,0);
		}
		public ElseOperatorContext elseOperator() {
			return getRuleContext(ElseOperatorContext.class,0);
		}
		public ElseExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterElseExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitElseExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitElseExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseExpressionContext elseExpression() throws RecognitionException {
		ElseExpressionContext _localctx = new ElseExpressionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_elseExpression);
		try {
			setState(159);
			switch (_input.LA(1)) {
			case T__16:
				enterOuterAlt(_localctx, 1);
				{
				setState(154); elseOperator();
				setState(155); elseContinuation();
				((ElseExpressionContext)_localctx).sameLine =  true;
				}
				break;
			case EPS:
				enterOuterAlt(_localctx, 2);
				{
				setState(158); match(EPS);
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

	public static class ElseContinuationContext extends ParserRuleContext {
		public boolean noIf = false;
		public IfExpressionContext ifExpression() {
			return getRuleContext(IfExpressionContext.class,0);
		}
		public FunBodyContext funBody() {
			return getRuleContext(FunBodyContext.class,0);
		}
		public ElseContinuationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseContinuation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterElseContinuation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitElseContinuation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitElseContinuation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseContinuationContext elseContinuation() throws RecognitionException {
		ElseContinuationContext _localctx = new ElseContinuationContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_elseContinuation);
		try {
			setState(166);
			switch (_input.LA(1)) {
			case T__18:
				enterOuterAlt(_localctx, 1);
				{
				setState(161); match(T__18);
				setState(162); funBody();
				((ElseContinuationContext)_localctx).noIf =  true;
				}
				break;
			case T__24:
				enterOuterAlt(_localctx, 2);
				{
				setState(165); ifExpression();
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

	public static class ReturnCaseContext extends ParserRuleContext {
		public RetOperatorContext retOperator() {
			return getRuleContext(RetOperatorContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public ReturnCaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnCase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterReturnCase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitReturnCase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitReturnCase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnCaseContext returnCase() throws RecognitionException {
		ReturnCaseContext _localctx = new ReturnCaseContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_returnCase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168); retOperator();
			setState(170);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__28) | (1L << T__22) | (1L << T__13) | (1L << NUMBER) | (1L << CHAR) | (1L << STRING) | (1L << NAME))) != 0)) {
				{
				setState(169); value();
				}
			}

			setState(172); match(T__3);
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

	public static class ValueContext extends ParserRuleContext {
		public BoolContext bool() {
			return getRuleContext(BoolContext.class,0);
		}
		public TerminalNode NAME() { return getToken(JavaParser.NAME, 0); }
		public TerminalNode NUMBER() { return getToken(JavaParser.NUMBER, 0); }
		public TerminalNode CHAR() { return getToken(JavaParser.CHAR, 0); }
		public TerminalNode STRING() { return getToken(JavaParser.STRING, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_value);
		int _la;
		try {
			setState(182);
			switch (_input.LA(1)) {
			case T__22:
			case T__13:
				enterOuterAlt(_localctx, 1);
				{
				setState(174); bool();
				}
				break;
			case NUMBER:
				enterOuterAlt(_localctx, 2);
				{
				setState(175); match(NUMBER);
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 3);
				{
				setState(176); match(STRING);
				}
				break;
			case CHAR:
				enterOuterAlt(_localctx, 4);
				{
				setState(177); match(CHAR);
				}
				break;
			case T__28:
			case NAME:
				enterOuterAlt(_localctx, 5);
				{
				setState(179);
				_la = _input.LA(1);
				if (_la==T__28) {
					{
					setState(178); match(T__28);
					}
				}

				setState(181); match(NAME);
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

	public static class BoolContext extends ParserRuleContext {
		public BoolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitBool(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitBool(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolContext bool() throws RecognitionException {
		BoolContext _localctx = new BoolContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_bool);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(184);
			_la = _input.LA(1);
			if ( !(_la==T__22 || _la==T__13) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static class VarTypeContext extends ParserRuleContext {
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public VarTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterVarType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitVarType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitVarType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarTypeContext varType() throws RecognitionException {
		VarTypeContext _localctx = new VarTypeContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_varType);
		int _la;
		try {
			setState(195);
			switch (_input.LA(1)) {
			case T__34:
			case T__23:
			case T__19:
			case T__15:
			case T__11:
				enterOuterAlt(_localctx, 1);
				{
				setState(186); primitiveType();
				setState(191);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__29) {
					{
					{
					setState(187); match(T__29);
					setState(188); match(T__33);
					}
					}
					setState(193);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__20:
				enterOuterAlt(_localctx, 2);
				{
				setState(194); match(T__20);
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

	public static class PrimitiveTypeContext extends ParserRuleContext {
		public PrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitiveType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterPrimitiveType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitPrimitiveType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitPrimitiveType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimitiveTypeContext primitiveType() throws RecognitionException {
		PrimitiveTypeContext _localctx = new PrimitiveTypeContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_primitiveType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__34) | (1L << T__23) | (1L << T__19) | (1L << T__15) | (1L << T__11))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static class ModifierContext extends ParserRuleContext {
		public ModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModifierContext modifier() throws RecognitionException {
		ModifierContext _localctx = new ModifierContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_modifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__31) | (1L << T__21) | (1L << T__17) | (1L << T__12) | (1L << T__6))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static class WhileOperatorContext extends ParserRuleContext {
		public WhileOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterWhileOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitWhileOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitWhileOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileOperatorContext whileOperator() throws RecognitionException {
		WhileOperatorContext _localctx = new WhileOperatorContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_whileOperator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201); match(T__27);
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

	public static class IfOperatorContext extends ParserRuleContext {
		public IfOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterIfOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitIfOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitIfOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfOperatorContext ifOperator() throws RecognitionException {
		IfOperatorContext _localctx = new IfOperatorContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_ifOperator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203); match(T__24);
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

	public static class ElseOperatorContext extends ParserRuleContext {
		public ElseOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterElseOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitElseOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitElseOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseOperatorContext elseOperator() throws RecognitionException {
		ElseOperatorContext _localctx = new ElseOperatorContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_elseOperator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205); match(T__16);
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

	public static class RetOperatorContext extends ParserRuleContext {
		public RetOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_retOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterRetOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitRetOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitRetOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RetOperatorContext retOperator() throws RecognitionException {
		RetOperatorContext _localctx = new RetOperatorContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_retOperator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207); match(T__4);
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

	public static class BinOperationContext extends ParserRuleContext {
		public BinOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).enterBinOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavaListener ) ((JavaListener)listener).exitBinOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaVisitor ) return ((JavaVisitor<? extends T>)visitor).visitBinOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinOperationContext binOperation() throws RecognitionException {
		BinOperationContext _localctx = new BinOperationContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_binOperation);
		try {
			setState(218);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(209); match(T__7);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(210); match(T__28);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(211); match(T__26);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(212); match(T__2);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(213); match(T__10);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(214); match(T__32);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(215); match(T__0);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(216); match(T__9);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				}
				break;
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3,\u00df\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3"+
		"\3\3\5\3D\n\3\3\4\7\4G\n\4\f\4\16\4J\13\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\5\5U\n\5\3\6\3\6\3\6\3\6\3\6\3\6\5\6]\n\6\3\7\3\7\3\7\3\7\3\7"+
		"\5\7d\n\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\5\tp\n\t\3\n\3\n\3\n"+
		"\3\n\5\nv\n\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\177\n\13\3\13\5"+
		"\13\u0082\n\13\3\f\5\f\u0085\n\f\3\f\3\f\3\f\3\r\3\r\5\r\u008c\n\r\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\20\3\20\3\20\3\20\3\20\5\20\u00a2\n\20\3\21\3\21\3\21\3\21\3\21\5\21"+
		"\u00a9\n\21\3\22\3\22\5\22\u00ad\n\22\3\22\3\22\3\23\3\23\3\23\3\23\3"+
		"\23\5\23\u00b6\n\23\3\23\5\23\u00b9\n\23\3\24\3\24\3\25\3\25\3\25\7\25"+
		"\u00c0\n\25\f\25\16\25\u00c3\13\25\3\25\5\25\u00c6\n\25\3\26\3\26\3\27"+
		"\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\5\34\u00dd\n\34\3\34\2\2\35\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\34\36 \"$&(*,.\60\62\64\66\2\5\4\2\17\17\30\30\7\2\3\3\16"+
		"\16\22\22\26\26\32\32\7\2\6\6\20\20\24\24\31\31\37\37\u00e1\28\3\2\2\2"+
		"\4C\3\2\2\2\6H\3\2\2\2\bT\3\2\2\2\n\\\3\2\2\2\fc\3\2\2\2\16e\3\2\2\2\20"+
		"o\3\2\2\2\22u\3\2\2\2\24\u0081\3\2\2\2\26\u0084\3\2\2\2\30\u008b\3\2\2"+
		"\2\32\u008d\3\2\2\2\34\u0094\3\2\2\2\36\u00a1\3\2\2\2 \u00a8\3\2\2\2\""+
		"\u00aa\3\2\2\2$\u00b8\3\2\2\2&\u00ba\3\2\2\2(\u00c5\3\2\2\2*\u00c7\3\2"+
		"\2\2,\u00c9\3\2\2\2.\u00cb\3\2\2\2\60\u00cd\3\2\2\2\62\u00cf\3\2\2\2\64"+
		"\u00d1\3\2\2\2\66\u00dc\3\2\2\289\7\6\2\29:\7$\2\2:;\7*\2\2;<\7\23\2\2"+
		"<=\5\4\3\2=>\7\2\2\3>\3\3\2\2\2?@\5\6\4\2@A\5\b\5\2AD\3\2\2\2BD\7\27\2"+
		"\2C?\3\2\2\2CB\3\2\2\2D\5\3\2\2\2EG\5,\27\2FE\3\2\2\2GJ\3\2\2\2HF\3\2"+
		"\2\2HI\3\2\2\2IK\3\2\2\2JH\3\2\2\2KL\5(\25\2LM\7*\2\2M\7\3\2\2\2NO\5\n"+
		"\6\2OP\5\4\3\2PU\3\2\2\2QR\5\16\b\2RS\5\4\3\2SU\3\2\2\2TN\3\2\2\2TQ\3"+
		"\2\2\2U\t\3\2\2\2VW\7 \2\2WX\5$\23\2XY\5\f\7\2Y]\3\2\2\2Z[\7\"\2\2[]\b"+
		"\6\1\2\\V\3\2\2\2\\Z\3\2\2\2]\13\3\2\2\2^d\7\"\2\2_`\5\66\34\2`a\5$\23"+
		"\2ab\7\"\2\2bd\3\2\2\2c^\3\2\2\2c_\3\2\2\2d\r\3\2\2\2ef\7\f\2\2fg\5\20"+
		"\t\2gh\7\23\2\2hi\5\24\13\2i\17\3\2\2\2jk\5(\25\2kl\7*\2\2lm\5\22\n\2"+
		"mp\3\2\2\2np\5\22\n\2oj\3\2\2\2on\3\2\2\2p\21\3\2\2\2qr\7\7\2\2rv\5\20"+
		"\t\2st\7\35\2\2tv\b\n\1\2uq\3\2\2\2us\3\2\2\2v\23\3\2\2\2wx\5\26\f\2x"+
		"y\5\24\13\2y\u0082\3\2\2\2z{\5\30\r\2{|\5\24\13\2|\u0082\3\2\2\2}\177"+
		"\5\"\22\2~}\3\2\2\2~\177\3\2\2\2\177\u0080\3\2\2\2\u0080\u0082\7\27\2"+
		"\2\u0081w\3\2\2\2\u0081z\3\2\2\2\u0081~\3\2\2\2\u0082\25\3\2\2\2\u0083"+
		"\u0085\5(\25\2\u0084\u0083\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0086\3\2"+
		"\2\2\u0086\u0087\7*\2\2\u0087\u0088\5\n\6\2\u0088\27\3\2\2\2\u0089\u008c"+
		"\5\32\16\2\u008a\u008c\5\34\17\2\u008b\u0089\3\2\2\2\u008b\u008a\3\2\2"+
		"\2\u008c\31\3\2\2\2\u008d\u008e\5.\30\2\u008e\u008f\7\f\2\2\u008f\u0090"+
		"\5&\24\2\u0090\u0091\7\35\2\2\u0091\u0092\7\23\2\2\u0092\u0093\5\24\13"+
		"\2\u0093\33\3\2\2\2\u0094\u0095\5\60\31\2\u0095\u0096\7\f\2\2\u0096\u0097"+
		"\5&\24\2\u0097\u0098\7\35\2\2\u0098\u0099\7\23\2\2\u0099\u009a\5\24\13"+
		"\2\u009a\u009b\5\36\20\2\u009b\35\3\2\2\2\u009c\u009d\5\62\32\2\u009d"+
		"\u009e\5 \21\2\u009e\u009f\b\20\1\2\u009f\u00a2\3\2\2\2\u00a0\u00a2\7"+
		"&\2\2\u00a1\u009c\3\2\2\2\u00a1\u00a0\3\2\2\2\u00a2\37\3\2\2\2\u00a3\u00a4"+
		"\7\23\2\2\u00a4\u00a5\5\24\13\2\u00a5\u00a6\b\21\1\2\u00a6\u00a9\3\2\2"+
		"\2\u00a7\u00a9\5\34\17\2\u00a8\u00a3\3\2\2\2\u00a8\u00a7\3\2\2\2\u00a9"+
		"!\3\2\2\2\u00aa\u00ac\5\64\33\2\u00ab\u00ad\5$\23\2\u00ac\u00ab\3\2\2"+
		"\2\u00ac\u00ad\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00af\7\"\2\2\u00af#"+
		"\3\2\2\2\u00b0\u00b9\5&\24\2\u00b1\u00b9\7\'\2\2\u00b2\u00b9\7)\2\2\u00b3"+
		"\u00b9\7(\2\2\u00b4\u00b6\7\t\2\2\u00b5\u00b4\3\2\2\2\u00b5\u00b6\3\2"+
		"\2\2\u00b6\u00b7\3\2\2\2\u00b7\u00b9\7*\2\2\u00b8\u00b0\3\2\2\2\u00b8"+
		"\u00b1\3\2\2\2\u00b8\u00b2\3\2\2\2\u00b8\u00b3\3\2\2\2\u00b8\u00b5\3\2"+
		"\2\2\u00b9%\3\2\2\2\u00ba\u00bb\t\2\2\2\u00bb\'\3\2\2\2\u00bc\u00c1\5"+
		"*\26\2\u00bd\u00be\7\b\2\2\u00be\u00c0\7\4\2\2\u00bf\u00bd\3\2\2\2\u00c0"+
		"\u00c3\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c6\3\2"+
		"\2\2\u00c3\u00c1\3\2\2\2\u00c4\u00c6\7\21\2\2\u00c5\u00bc\3\2\2\2\u00c5"+
		"\u00c4\3\2\2\2\u00c6)\3\2\2\2\u00c7\u00c8\t\3\2\2\u00c8+\3\2\2\2\u00c9"+
		"\u00ca\t\4\2\2\u00ca-\3\2\2\2\u00cb\u00cc\7\n\2\2\u00cc/\3\2\2\2\u00cd"+
		"\u00ce\7\r\2\2\u00ce\61\3\2\2\2\u00cf\u00d0\7\25\2\2\u00d0\63\3\2\2\2"+
		"\u00d1\u00d2\7!\2\2\u00d2\65\3\2\2\2\u00d3\u00dd\7\36\2\2\u00d4\u00dd"+
		"\7\t\2\2\u00d5\u00dd\7\13\2\2\u00d6\u00dd\7#\2\2\u00d7\u00dd\7\33\2\2"+
		"\u00d8\u00dd\7\5\2\2\u00d9\u00dd\7%\2\2\u00da\u00dd\7\34\2\2\u00db\u00dd"+
		"\3\2\2\2\u00dc\u00d3\3\2\2\2\u00dc\u00d4\3\2\2\2\u00dc\u00d5\3\2\2\2\u00dc"+
		"\u00d6\3\2\2\2\u00dc\u00d7\3\2\2\2\u00dc\u00d8\3\2\2\2\u00dc\u00d9\3\2"+
		"\2\2\u00dc\u00da\3\2\2\2\u00dc\u00db\3\2\2\2\u00dd\67\3\2\2\2\25CHT\\"+
		"cou~\u0081\u0084\u008b\u00a1\u00a8\u00ac\u00b5\u00b8\u00c1\u00c5\u00dc";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}