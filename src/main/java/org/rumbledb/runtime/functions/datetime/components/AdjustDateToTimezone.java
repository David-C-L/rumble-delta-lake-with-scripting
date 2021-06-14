package org.rumbledb.runtime.functions.datetime.components;

import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.rumbledb.api.Item;
import org.rumbledb.context.DynamicContext;
import org.rumbledb.exceptions.ExceptionMetadata;
import org.rumbledb.exceptions.InvalidTimezoneException;
import org.rumbledb.expressions.ExecutionMode;
import org.rumbledb.items.ItemFactory;
import org.rumbledb.runtime.AtMostOneItemLocalRuntimeIterator;
import org.rumbledb.runtime.RuntimeIterator;

import java.util.List;

public class AdjustDateToTimezone extends AtMostOneItemLocalRuntimeIterator {

    private static final long serialVersionUID = 1L;
    private Item dateItem = null;
    private Item timezone = null;

    public AdjustDateToTimezone(
            List<RuntimeIterator> arguments,
            ExecutionMode executionMode,
            ExceptionMetadata iteratorMetadata
    ) {
        super(arguments, executionMode, iteratorMetadata);
    }

    @Override
    public Item materializeFirstItemOrNull(DynamicContext context) {
        this.dateItem = this.children.get(0).materializeFirstItemOrNull(context);
        if (this.children.size() == 2) {
            this.timezone = this.children.get(1)
                .materializeFirstItemOrNull(context);
        }
        if (this.dateItem == null) {
            return null;
        }
        if (this.timezone == null && this.children.size() == 1) {
            return ItemFactory.getInstance()
                .createDateItem(this.dateItem.getDateTimeValue().withZone(DateTimeZone.UTC), true);
        }
        if (this.timezone == null) {
            if (this.dateItem.hasTimeZone()) {
                return ItemFactory.getInstance()
                    .createDateItem(
                        this.dateItem.getDateTimeValue()
                            .withZoneRetainFields(this.dateItem.getDateTimeValue().getZone()),
                        false
                    );
            }
            return ItemFactory.getInstance()
                .createDateItem(this.dateItem.getDateTimeValue(), this.dateItem.hasTimeZone());
        } else {
            if (this.checkTimeZoneArgument()) {
                throw new InvalidTimezoneException("Invalid timezone", getMetadata());
            }
            if (this.dateItem.hasTimeZone()) {
                return ItemFactory.getInstance()
                    .createDateItem(
                        this.dateItem.getDateTimeValue()
                            .withZone(
                                DateTimeZone.forOffsetHoursMinutes(
                                    this.timezone.getDurationValue().getHours(),
                                    this.timezone.getDurationValue().getMinutes()
                                )
                            ),
                        true
                    );
            }
            return ItemFactory.getInstance()
                .createDateItem(
                    this.dateItem.getDateTimeValue()
                        .withZoneRetainFields(
                            DateTimeZone.forOffsetHoursMinutes(
                                this.timezone.getDurationValue().getHours(),
                                this.timezone.getDurationValue().getMinutes()
                            )
                        ),
                    true
                );
        }
    }

    private boolean checkTimeZoneArgument() {
        return (Math.abs(this.timezone.getDurationValue().toDurationFrom(Instant.now()).getMillis()) > 50400000)
            ||
            (Double.compare(
                this.timezone.getDurationValue().getSeconds()
                    + this.timezone.getDurationValue().getMillis() * 1.0 / 1000,
                0
            ) != 0);
    }
}
