package com.kovanikov.parser;// Generated from /home/dmitryk/programming/DiskretMathLabs/3/translation/03/Java.g4 by ANTLR 4.x
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link JavaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface JavaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(@NotNull JavaParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#funArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunArgs(@NotNull JavaParser.FunArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#returnCase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnCase(@NotNull JavaParser.ReturnCaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#funArgsContinuation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunArgsContinuation(@NotNull JavaParser.FunArgsContinuationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#globalName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalName(@NotNull JavaParser.GlobalNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#varType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarType(@NotNull JavaParser.VarTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#varDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDefinition(@NotNull JavaParser.VarDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#retOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRetOperator(@NotNull JavaParser.RetOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#elseOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseOperator(@NotNull JavaParser.ElseOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#funDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunDefinition(@NotNull JavaParser.FunDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#javaLang}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJavaLang(@NotNull JavaParser.JavaLangContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(@NotNull JavaParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#whileOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileOperator(@NotNull JavaParser.WhileOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(@NotNull JavaParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#whileExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileExpression(@NotNull JavaParser.WhileExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#elseExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseExpression(@NotNull JavaParser.ElseExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinition(@NotNull JavaParser.DefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#ifExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfExpression(@NotNull JavaParser.IfExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#binOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinOperation(@NotNull JavaParser.BinOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#ifOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfOperator(@NotNull JavaParser.IfOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(@NotNull JavaParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#modifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifier(@NotNull JavaParser.ModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(@NotNull JavaParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#funBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunBody(@NotNull JavaParser.FunBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#bool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool(@NotNull JavaParser.BoolContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#valueContinuation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueContinuation(@NotNull JavaParser.ValueContinuationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaParser#elseContinuation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseContinuation(@NotNull JavaParser.ElseContinuationContext ctx);
}