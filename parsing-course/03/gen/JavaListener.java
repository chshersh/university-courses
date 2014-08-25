// Generated from /home/dmitryk/programming/DiskretMathLabs/3/translation/03/Java.g4 by ANTLR 4.x
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JavaParser}.
 */
public interface JavaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(@NotNull JavaParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(@NotNull JavaParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#funArgs}.
	 * @param ctx the parse tree
	 */
	void enterFunArgs(@NotNull JavaParser.FunArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#funArgs}.
	 * @param ctx the parse tree
	 */
	void exitFunArgs(@NotNull JavaParser.FunArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#returnCase}.
	 * @param ctx the parse tree
	 */
	void enterReturnCase(@NotNull JavaParser.ReturnCaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#returnCase}.
	 * @param ctx the parse tree
	 */
	void exitReturnCase(@NotNull JavaParser.ReturnCaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#funArgsContinuation}.
	 * @param ctx the parse tree
	 */
	void enterFunArgsContinuation(@NotNull JavaParser.FunArgsContinuationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#funArgsContinuation}.
	 * @param ctx the parse tree
	 */
	void exitFunArgsContinuation(@NotNull JavaParser.FunArgsContinuationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#globalName}.
	 * @param ctx the parse tree
	 */
	void enterGlobalName(@NotNull JavaParser.GlobalNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#globalName}.
	 * @param ctx the parse tree
	 */
	void exitGlobalName(@NotNull JavaParser.GlobalNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#varType}.
	 * @param ctx the parse tree
	 */
	void enterVarType(@NotNull JavaParser.VarTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#varType}.
	 * @param ctx the parse tree
	 */
	void exitVarType(@NotNull JavaParser.VarTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#varDefinition}.
	 * @param ctx the parse tree
	 */
	void enterVarDefinition(@NotNull JavaParser.VarDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#varDefinition}.
	 * @param ctx the parse tree
	 */
	void exitVarDefinition(@NotNull JavaParser.VarDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#retOperator}.
	 * @param ctx the parse tree
	 */
	void enterRetOperator(@NotNull JavaParser.RetOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#retOperator}.
	 * @param ctx the parse tree
	 */
	void exitRetOperator(@NotNull JavaParser.RetOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#elseOperator}.
	 * @param ctx the parse tree
	 */
	void enterElseOperator(@NotNull JavaParser.ElseOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#elseOperator}.
	 * @param ctx the parse tree
	 */
	void exitElseOperator(@NotNull JavaParser.ElseOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#funDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunDefinition(@NotNull JavaParser.FunDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#funDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunDefinition(@NotNull JavaParser.FunDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#javaLang}.
	 * @param ctx the parse tree
	 */
	void enterJavaLang(@NotNull JavaParser.JavaLangContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#javaLang}.
	 * @param ctx the parse tree
	 */
	void exitJavaLang(@NotNull JavaParser.JavaLangContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(@NotNull JavaParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(@NotNull JavaParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#whileOperator}.
	 * @param ctx the parse tree
	 */
	void enterWhileOperator(@NotNull JavaParser.WhileOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#whileOperator}.
	 * @param ctx the parse tree
	 */
	void exitWhileOperator(@NotNull JavaParser.WhileOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(@NotNull JavaParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(@NotNull JavaParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#whileExpression}.
	 * @param ctx the parse tree
	 */
	void enterWhileExpression(@NotNull JavaParser.WhileExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#whileExpression}.
	 * @param ctx the parse tree
	 */
	void exitWhileExpression(@NotNull JavaParser.WhileExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#elseExpression}.
	 * @param ctx the parse tree
	 */
	void enterElseExpression(@NotNull JavaParser.ElseExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#elseExpression}.
	 * @param ctx the parse tree
	 */
	void exitElseExpression(@NotNull JavaParser.ElseExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#definition}.
	 * @param ctx the parse tree
	 */
	void enterDefinition(@NotNull JavaParser.DefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#definition}.
	 * @param ctx the parse tree
	 */
	void exitDefinition(@NotNull JavaParser.DefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#ifExpression}.
	 * @param ctx the parse tree
	 */
	void enterIfExpression(@NotNull JavaParser.IfExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#ifExpression}.
	 * @param ctx the parse tree
	 */
	void exitIfExpression(@NotNull JavaParser.IfExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#binOperation}.
	 * @param ctx the parse tree
	 */
	void enterBinOperation(@NotNull JavaParser.BinOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#binOperation}.
	 * @param ctx the parse tree
	 */
	void exitBinOperation(@NotNull JavaParser.BinOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#ifOperator}.
	 * @param ctx the parse tree
	 */
	void enterIfOperator(@NotNull JavaParser.IfOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#ifOperator}.
	 * @param ctx the parse tree
	 */
	void exitIfOperator(@NotNull JavaParser.IfOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull JavaParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull JavaParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#modifier}.
	 * @param ctx the parse tree
	 */
	void enterModifier(@NotNull JavaParser.ModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#modifier}.
	 * @param ctx the parse tree
	 */
	void exitModifier(@NotNull JavaParser.ModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(@NotNull JavaParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(@NotNull JavaParser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#funBody}.
	 * @param ctx the parse tree
	 */
	void enterFunBody(@NotNull JavaParser.FunBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#funBody}.
	 * @param ctx the parse tree
	 */
	void exitFunBody(@NotNull JavaParser.FunBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#bool}.
	 * @param ctx the parse tree
	 */
	void enterBool(@NotNull JavaParser.BoolContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#bool}.
	 * @param ctx the parse tree
	 */
	void exitBool(@NotNull JavaParser.BoolContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#valueContinuation}.
	 * @param ctx the parse tree
	 */
	void enterValueContinuation(@NotNull JavaParser.ValueContinuationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#valueContinuation}.
	 * @param ctx the parse tree
	 */
	void exitValueContinuation(@NotNull JavaParser.ValueContinuationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#elseContinuation}.
	 * @param ctx the parse tree
	 */
	void enterElseContinuation(@NotNull JavaParser.ElseContinuationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#elseContinuation}.
	 * @param ctx the parse tree
	 */
	void exitElseContinuation(@NotNull JavaParser.ElseContinuationContext ctx);
}