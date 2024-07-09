package org.rumbledb.runtime.functions.random;

import org.rumbledb.api.Item;
import org.rumbledb.context.DynamicContext;
import org.rumbledb.context.RuntimeStaticContext;
import org.rumbledb.runtime.LocalRuntimeIterator;
import org.rumbledb.runtime.RuntimeIterator;

import java.util.List;
import java.util.Random;

public class RandomSequenceWithBoundsIterator extends LocalRuntimeIterator {
    private Item low;
    private Item high;
    private int size;
    private Item type;
    private Random random;
    private GeneratedRandomsIterator generatedRandomsIterator;

    public RandomSequenceWithBoundsIterator(List<RuntimeIterator> children, RuntimeStaticContext staticContext) {
        super(children, staticContext);
    }

    @Override
    public void open(DynamicContext context) {
        this.low = this.children.get(0).materializeFirstItemOrNull(context);
        this.high = this.children.get(1).materializeFirstItemOrNull(context);
        this.type = this.children.get(3).materializeFirstItemOrNull(context);
        this.size = this.children.get(2).materializeFirstItemOrNull(context).castToIntValue();
        this.random = new Random();
        this.generatedRandomsIterator = createRandomNumberStream();
    }

    private GeneratedRandomsIterator createRandomNumberStream() {
        if (type.getStringValue().equals("integer")) {
            return new GeneratedRandomIntegersIterator(
                    random.ints(size, low.castToIntValue(), high.castToIntValue()).iterator()
            );
        } else {
            // Generate doubles otherwise
            return new GeneratedRandomDoublesIterator(
                    random.doubles(size, low.castToDoubleValue(), high.castToDoubleValue()).iterator()
            );
        }
    }

    @Override
    public Item next() {
        return this.generatedRandomsIterator.getNextRandom();
    }

    @Override
    public boolean hasNext() {
        return this.generatedRandomsIterator.hasNext();
    }
}
