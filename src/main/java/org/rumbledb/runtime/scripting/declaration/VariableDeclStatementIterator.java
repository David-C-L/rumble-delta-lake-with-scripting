package org.rumbledb.runtime.scripting.declaration;

import org.rumbledb.api.Item;
import org.rumbledb.context.DynamicContext;
import org.rumbledb.context.Name;
import org.rumbledb.context.RuntimeStaticContext;
import org.rumbledb.items.NullItem;
import org.rumbledb.runtime.AtMostOneItemLocalRuntimeIterator;
import org.rumbledb.runtime.RuntimeIterator;

import java.util.Collections;
import java.util.List;

public class VariableDeclStatementIterator extends AtMostOneItemLocalRuntimeIterator {
    private final Name variableName;

    public VariableDeclStatementIterator(
            Name variableName,
            List<RuntimeIterator> children,
            RuntimeStaticContext staticContext
    ) {
        super(children, staticContext);
        this.variableName = variableName;
    }

    @Override
    public Item materializeFirstItemOrNull(DynamicContext dynamicContext) {
        RuntimeIterator exprIterator = this.children.get(0);
        if (exprIterator != null) {
            exprIterator.bindToVariableInDynamicContext(dynamicContext, this.variableName, dynamicContext, true);
        } else {
            dynamicContext.getVariableValues()
                .addVariableValueFromDeclaration(this.variableName, Collections.singletonList(new NullItem()));
        }
        return null;
    }
}
