package org.rumbledb.runtime.update.primitives;

import org.rumbledb.api.Item;
import org.rumbledb.exceptions.ExceptionMetadata;

import java.util.List;

public class UpdatePrimitiveFactory {

    private static UpdatePrimitiveFactory instance;

    public static UpdatePrimitiveFactory getInstance() {
        if (instance == null) {
            instance = new UpdatePrimitiveFactory();
        }
        return instance;
    }

    public UpdatePrimitive createDeleteFromArrayPrimitive(
            Item targetArray,
            Item selectorInt,
            ExceptionMetadata metadata
    ) {
        return new DeleteFromArrayPrimitive(targetArray, selectorInt, metadata);
    }

    public UpdatePrimitive createDeleteFromObjectPrimitive(
            Item targetObject,
            List<Item> selectorStrs,
            ExceptionMetadata metadata
    ) {
        return new DeleteFromObjectPrimitive(targetObject, selectorStrs, metadata);
    }

    public UpdatePrimitive createInsertIntoArrayPrimitive(Item targetArray, Item selectorInt, List<Item> contents) {
        return new InsertIntoArrayPrimitive(targetArray, selectorInt, contents);
    }

    public UpdatePrimitive createInsertIntoObjectPrimitive(Item targetObject, Item contentsObject) {
        return new InsertIntoObjectPrimitive(targetObject, contentsObject);
    }

    public UpdatePrimitive createReplaceInArrayPrimitive(
            Item targetArray,
            Item selectorInt,
            Item content,
            ExceptionMetadata metadata
    ) {
        return new ReplaceInArrayPrimitive(targetArray, selectorInt, content, metadata);
    }

    public UpdatePrimitive createReplaceInObjectPrimitive(
            Item targetObject,
            Item selectorStr,
            Item content,
            ExceptionMetadata metadata
    ) {
        return new ReplaceInObjectPrimitive(targetObject, selectorStr, content, metadata);
    }

    public UpdatePrimitive createRenameInObjectPrimitive(
            Item targetObject,
            Item selectorStr,
            Item content,
            ExceptionMetadata metadata
    ) {
        return new RenameInObjectPrimitive(targetObject, selectorStr, content, metadata);
    }


}
