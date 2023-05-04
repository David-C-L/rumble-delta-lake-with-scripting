package org.rumbledb.runtime.functions.durations.components;

import org.rumbledb.api.Item;
import org.rumbledb.context.DynamicContext;
import org.rumbledb.context.RuntimeStaticContext;
import org.rumbledb.items.ItemFactory;
import org.rumbledb.runtime.AtMostOneItemLocalRuntimeIterator;
import org.rumbledb.runtime.RuntimeIterator;

import java.util.List;

public class MinutesFromDurationFunctionIterator extends AtMostOneItemLocalRuntimeIterator {

    private static final long serialVersionUID = 1L;
    private Item durationItem = null;

    public MinutesFromDurationFunctionIterator(
            List<RuntimeIterator> arguments,
            RuntimeStaticContext staticContext
    ) {
        super(arguments, staticContext);
    }

    @Override
    public Item materializeFirstItemOrNull(DynamicContext context) {
        this.durationItem = this.children.get(0)
            .materializeFirstItemOrNull(context);
        if (this.durationItem == null) {
            return null;
        }
        return ItemFactory.getInstance().createIntItem(this.durationItem.getDurationValue().getMinutes());
    }

}
