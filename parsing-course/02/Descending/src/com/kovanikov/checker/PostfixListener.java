package com.kovanikov.checker;// Generated from /home/dmitryk/programming/DiskretMathLabs/3/translation/02/Descending/Postfix.g4 by ANTLR 4.x
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PostfixParser}.
 */
public interface PostfixListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PostfixParser#sign}.
	 * @param ctx the parse tree
	 */
	void enterSign(@NotNull PostfixParser.SignContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostfixParser#sign}.
	 * @param ctx the parse tree
	 */
	void exitSign(@NotNull PostfixParser.SignContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostfixParser#postfix}.
	 * @param ctx the parse tree
	 */
	void enterPostfix(@NotNull PostfixParser.PostfixContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostfixParser#postfix}.
	 * @param ctx the parse tree
	 */
	void exitPostfix(@NotNull PostfixParser.PostfixContext ctx);
	/**
	 * Enter a parse tree produced by {@link PostfixParser#aux}.
	 * @param ctx the parse tree
	 */
	void enterAux(@NotNull PostfixParser.AuxContext ctx);
	/**
	 * Exit a parse tree produced by {@link PostfixParser#aux}.
	 * @param ctx the parse tree
	 */
	void exitAux(@NotNull PostfixParser.AuxContext ctx);
}