package com.kovanikov.formatter;

import com.kovanikov.parser.*;
import org.antlr.v4.runtime.misc.NotNull;


public class JavaFormatListener extends JavaBaseListener {
    private static int tabCnt = 0;

    private String tabs() {
        String ans = "";
        for (int i = 0; i < tabCnt; ++i) {
            ans += "\t";
        }
        return ans;
    }

    @Override
    public void enterJavaLang(@NotNull JavaParser.JavaLangContext ctx) {
        System.out.println("public class " + ctx.NAME() + " {");
        tabCnt = 1;
    }

    @Override
    public void exitJavaLang(@NotNull JavaParser.JavaLangContext ctx) {
        System.out.println("}");
    }

    @Override
    public void enterGlobalName(@NotNull JavaParser.GlobalNameContext ctx) {
        System.out.print(tabs());
    }

    @Override
    public void exitGlobalName(@NotNull JavaParser.GlobalNameContext ctx) {
        System.out.print(ctx.NAME());
    }

    @Override
    public void exitModifier(@NotNull JavaParser.ModifierContext ctx) {
        System.out.print(ctx.getText() + " ");
    }

    @Override public void exitValue(@NotNull JavaParser.ValueContext ctx) {
        System.out.print(ctx.getText());
    }

    @Override
    public void exitVarType(@NotNull JavaParser.VarTypeContext ctx) {
        System.out.print(ctx.getText() + " ");
    }

    @Override
    public void enterVarDefinition(@NotNull JavaParser.VarDefinitionContext ctx) {
        if (ctx.longRule) {
            System.out.print(" = ");
        }
    }

    @Override
    public void exitBinOperation(@NotNull JavaParser.BinOperationContext ctx) {
        System.out.print(" " + ctx.getText() + " ");
    }

    @Override
    public void exitVarDefinition(@NotNull JavaParser.VarDefinitionContext ctx) {
        System.out.println(";");
    }

    @Override
    public void enterFunDefinition(@NotNull JavaParser.FunDefinitionContext ctx) {
        System.out.print("(");
        tabCnt++;
    }

    @Override
    public void exitFunDefinition(@NotNull JavaParser.FunDefinitionContext ctx) {
        tabCnt--;
        System.out.println(tabs() + "}");
        System.out.println();

    }

    @Override
    public void enterFunArgsContinuation(@NotNull JavaParser.FunArgsContinuationContext ctx) {
        System.out.print(ctx.name);
        if (ctx.endArgs) {
            System.out.println(") {");
        } else {
            System.out.print(", ");
        }
    }

    @Override
    public void enterStatement(@NotNull JavaParser.StatementContext ctx) {
        System.out.print(tabs());
        System.out.print(ctx.NAME());
    }

    @Override
    public void enterReturnCase(@NotNull JavaParser.ReturnCaseContext ctx) {
        System.out.print(tabs());
        System.out.print("return");
        if (ctx.value() != null) {
            System.out.print(" ");
        }
    }

    @Override
    public void exitReturnCase(@NotNull JavaParser.ReturnCaseContext ctx) {
        System.out.println(";");
    }

    @Override
    public void enterExpression(@NotNull JavaParser.ExpressionContext ctx) {
        System.out.print(tabs());
        tabCnt++;
    }

    @Override
    public void exitExpression(@NotNull JavaParser.ExpressionContext ctx) {
        tabCnt--;
        System.out.println(tabs() + "}");
    }

    @Override
    public void enterWhileExpression(@NotNull JavaParser.WhileExpressionContext ctx) {
        System.out.println("while (" + ctx.bool().getText() + ") {");
    }

    @Override
    public void enterIfExpression(@NotNull JavaParser.IfExpressionContext ctx) {
        System.out.println("if (" + ctx.bool().getText() + ") {");
    }

    @Override
    public void enterElseExpression(@NotNull JavaParser.ElseExpressionContext ctx) {
        if (ctx.sameLine) {
            tabCnt--;
            System.out.print(tabs() + "} else ");
            tabCnt++;
        }
    }

    @Override
    public void enterElseContinuation(@NotNull JavaParser.ElseContinuationContext ctx) {
        if (ctx.noIf) {
            System.out.println("{");
        }
    }
}