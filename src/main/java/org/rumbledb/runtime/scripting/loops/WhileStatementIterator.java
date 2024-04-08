package org.rumbledb.runtime.scripting.loops;

import org.rumbledb.api.Item;
import org.rumbledb.context.DynamicContext;
import org.rumbledb.context.RuntimeStaticContext;
import org.rumbledb.runtime.AtMostOneItemLocalRuntimeIterator;
import org.rumbledb.runtime.RuntimeIterator;

import java.util.Arrays;

public class WhileStatementIterator extends AtMostOneItemLocalRuntimeIterator {
    private final RuntimeIterator testConditionIterator;
    private final RuntimeIterator bodyIterator;

    public WhileStatementIterator(
            RuntimeIterator testConditionIterator,
            RuntimeIterator bodyIterator,
            boolean isSequential,
            RuntimeStaticContext staticContext
    ) {
        super(Arrays.asList(testConditionIterator, bodyIterator), staticContext);
        this.testConditionIterator = testConditionIterator;
        this.bodyIterator = bodyIterator;
        this.isSequential = isSequential;
    }

    @Override
    public Item materializeFirstItemOrNull(DynamicContext context) {
        while (this.testConditionIterator.getEffectiveBooleanValue(context)) {
            // TODO: for now, materialize just one item
            this.bodyIterator.materializeFirstItemOrNull(context);
        }
        return null;
    }
}
