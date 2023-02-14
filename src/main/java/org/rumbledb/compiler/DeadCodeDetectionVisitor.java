package org.rumbledb.compiler;

import org.rumbledb.context.BuiltinFunctionCatalogue;
import org.rumbledb.context.Name;
import org.rumbledb.expressions.AbstractNodeVisitor;
import org.rumbledb.expressions.Node;
import org.rumbledb.expressions.flowr.*;
import org.rumbledb.expressions.module.FunctionDeclaration;
import org.rumbledb.expressions.module.LibraryModule;
import org.rumbledb.expressions.module.MainModule;
import org.rumbledb.expressions.module.Prolog;
import org.rumbledb.expressions.postfix.FilterExpression;
import org.rumbledb.expressions.postfix.ObjectLookupExpression;
import org.rumbledb.expressions.primary.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class DeadCodeDetectionVisitor extends AbstractNodeVisitor<DeadCodeDetectionVisitor.ReferenceMap> {

    private static final Name LOCK_OBJECT = Name.createVariableInNoNamespace("*");

    @Override
    public ReferenceMap visitForClause(ForClause clause, ReferenceMap argument) {
        argument.drop(clause.getVariableName());
        if (clause.getPositionalVariableName() != null) {
            argument.drop(clause.getPositionalVariableName());
        }
        argument.add(visit(clause.getExpression(), new ReferenceMap()));
        return argument;
    }

    @Override
    protected ReferenceMap defaultAction(Node node, ReferenceMap argument) {
        ReferenceMap result = new ReferenceMap();
        node.getChildren().stream().map(child -> visit(child, argument)).forEach(result::add);
        result.add(argument);
        return result;
    }

    @Override
    public ReferenceMap visitLetClause(LetClause clause, ReferenceMap argument) {
        if (clause.getPreviousClause() != null && !argument.containsKey(clause.getVariableName())) {
            if (!argument.containsKey(LOCK_OBJECT)) {
                clause.setReferenced(false);
                System.out.println("unused variable " + clause.getVariableName());
            }
            return argument;
        }
        ReferenceMap letReferenceMap = argument.getReferenceMap(clause.getVariableName());
        ReferenceMap resultMap = this.visit(clause.getExpression(), letReferenceMap);
        argument.drop(clause.getVariableName());
        argument.add(resultMap);
        return argument;
    }

    @Override
    public ReferenceMap visitGroupByClause(GroupByClause clause, ReferenceMap argument) {
        clause.getGroupVariables()
            .stream()
            .filter(expr -> expr.getExpression() != null)
            .map(expr -> visit(expr.getExpression(), new ReferenceMap()))
            .forEach(argument::add);
        // drop variables that are introduced in the group by clause
        clause.getGroupVariables()
            .stream()
            .filter(expr -> expr.getExpression() != null)
            .map(GroupByVariableDeclaration::getVariableName)
            .forEach(argument::drop);
        // add variables that are referenced in the group by clause
        clause.getGroupVariables()
            .stream()
            .filter(expr -> expr.getExpression() == null)
            .map(GroupByVariableDeclaration::getVariableName)
            .forEach(variable -> argument.add(variable, new ReferenceMap()));
        return argument;
    }

    @Override
    public ReferenceMap visitWhereClause(WhereClause expression, ReferenceMap argument) {
        argument.add(visit(expression.getWhereExpression(), new ReferenceMap()));
        return argument;
    }

    public ReferenceMap visitOrderByClause(OrderByClause clause, ReferenceMap argument) {
        clause.getSortingKeys()
            .stream()
            .map(OrderByClauseSortingKey::getExpression)
            .map(expr -> visit(expr, new ReferenceMap()))
            .forEach(argument::add);
        return argument;
    }

    @Override
    public ReferenceMap visitFlowrExpression(FlworExpression expression, ReferenceMap argument) {
        Clause clause = expression.getReturnClause();
        while (clause != null) {
            argument = this.visit(clause, argument);
            clause = clause.getPreviousClause();
        }
        return argument;
    }

    @Override
    public ReferenceMap visitMainModule(MainModule expression, ReferenceMap argument) {
        visit(expression.getExpression(), new ReferenceMap());
        return null;
    }

    @Override
    public ReferenceMap visitProlog(Prolog prolog, ReferenceMap argument) {
        for (FunctionDeclaration declaration : prolog.getFunctionDeclarations()) {
            visit(declaration, null);
        }
        for (LibraryModule libraryModule : prolog.getImportedModules()) {
            visit(libraryModule, null);
        }
        return null;
    }

    @Override
    public ReferenceMap visitVariableReference(VariableReferenceExpression expression, ReferenceMap argument) {
        ReferenceMap map = new ReferenceMap();
        map.add(expression.getVariableName(), argument);
        return map;
    }

    @Override
    public ReferenceMap visitObjectLookupExpression(ObjectLookupExpression expression, ReferenceMap argument) {
        if (expression.getLookupExpression() instanceof StringLiteralExpression) {
            ReferenceMap map = new ReferenceMap();
            map.add(
                Name.createVariableInNoNamespace(
                    ((StringLiteralExpression) expression.getLookupExpression()).getValue()
                ),
                argument
            );
            return visit(expression.getMainExpression(), map);
        }
        ReferenceMap result = new ReferenceMap();
        result.add(visit(expression.getMainExpression(), argument));
        result.add(visit(expression.getLookupExpression(), argument));
        return result;
    }

    @Override
    public ReferenceMap visitObjectConstructor(ObjectConstructorExpression expression, ReferenceMap argument) {
        if (expression.isMergedConstructor()) {
            return this.defaultAction(expression, argument);
        }
        if (!expression.getKeys().stream().allMatch(exp -> exp instanceof StringLiteralExpression)) {
            return this.defaultAction(expression, argument);
        }
        ReferenceMap result = new ReferenceMap();
        for (int i = 0; i < expression.getKeys().size(); i++) {
            StringLiteralExpression key = (StringLiteralExpression) expression.getKeys().get(i);
            Name name = Name.createVariableInNoNamespace(key.getValue());
            if (!argument.isEmpty() && !argument.containsKey(name)) {
                if (!argument.containsKey(LOCK_OBJECT)) {
                    expression.setReferenced(i, false);
                    System.out.println("key " + key.getValue() + " is never used!");
                }
            } else {
                result.add(visit(expression.getValues().get(i), argument.getReferenceMap(name)));
            }
        }
        return result;
    }

    @Override
    public ReferenceMap visitFilterExpression(FilterExpression expression, ReferenceMap argument) {
        ReferenceMap result = new ReferenceMap();
        if (expression.getPredicateExpression() instanceof IntegerLiteralExpression) {
            expression.getChildren().stream().map(child -> visit(child, argument)).forEach(result::add);
        } else {
            expression.getChildren().stream().map(child -> visit(child, new ReferenceMap())).forEach(result::add);
        }
        return result;
    }

    @Override
    public ReferenceMap visitFunctionCall(FunctionCallExpression expression, ReferenceMap argument) {
        if (BuiltinFunctionCatalogue.getBuiltinFunction(expression.getFunctionIdentifier()) != null) {
            ReferenceMap result = new ReferenceMap();
            argument.add(LOCK_OBJECT, new ReferenceMap());
            expression.getArguments().stream().filter(Objects::nonNull).map(arg -> visit(arg, argument)).forEach(result::add);
            argument.drop(LOCK_OBJECT);
            return result;
        }
        return defaultAction(expression, argument);
    }

    public static class ReferenceMap {

        private Map<Name, ReferenceMap> map;

        public ReferenceMap() {
            this.map = new HashMap<>();
        }

        public void add(ReferenceMap object) {
            object.map.forEach(this::add);
        }

        public void add(Name name, ReferenceMap object) {
            if (!this.map.containsKey(name)) {
                this.map.put(name, object);
            } else {
                ReferenceMap reference = this.map.get(name);
                if (reference.map.size() == 0) {
                    // reference everything
                    return;
                }
                if (object.containsKey(name)) {
                    if (object.getReferenceMap(name).isEmpty()) {
                        reference.map.clear();
                    } else {
                        reference.add(object.getReferenceMap(name));
                    }
                } else {
                    reference.add(object);
                }
            }
        }

        public void drop(Name name) {
            this.map.remove(name);
        }

        public ReferenceMap getReferenceMap(Name name) {
            if (this.map.containsKey(name)) {
                return this.map.get(name);
            }
            return new ReferenceMap();
        }

        public boolean containsKey(Name name) {
            return this.map.containsKey(name);
        }

        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        // FIXME convenience methods for debugging
        public void toString(int offset) {
            this.map.forEach((key, value) -> {
                System.out.println(repeat(offset) + key.getLocalName());
                if (value.map.isEmpty()) {
                    System.out.println(repeat(offset + 2) + "(none)");
                } else {
                    value.toString(offset + 4);
                }
            });
        }

        private String repeat(int num) {
            StringBuilder result = new StringBuilder();
            while (num-- > 0) {
                result.append(" ");
            }
            return result.toString();
        }
    }
}
