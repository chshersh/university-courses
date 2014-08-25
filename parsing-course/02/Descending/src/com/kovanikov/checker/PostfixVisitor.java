package com.kovanikov.checker;// Generated from /home/dmitryk/programming/DiskretMathLabs/3/translation/02/Descending/Postfix.g4 by ANTLR 4.x
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PostfixParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PostfixVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PostfixParser#sign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSign(@NotNull PostfixParser.SignContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostfixParser#postfix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfix(@NotNull PostfixParser.PostfixContext ctx);
	/**
	 * Visit a parse tree produced by {@link PostfixParser#aux}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAux(@NotNull PostfixParser.AuxContext ctx);
}