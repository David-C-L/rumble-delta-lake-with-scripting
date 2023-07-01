package org.rumbledb.runtime.update.primitives;

import io.delta.tables.DeltaTable;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.rumbledb.api.Item;
import org.rumbledb.exceptions.CannotResolveUpdateSelectorException;
import org.rumbledb.exceptions.ExceptionMetadata;
import org.rumbledb.items.parsing.RowToItemMapper;
import org.rumbledb.types.ItemType;
import org.rumbledb.types.ItemTypeFactory;
import sparksoniq.spark.SparkSessionManager;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.*;

public class DeleteFromObjectPrimitive implements UpdatePrimitive {

    private Item target;
    private List<Item> content;

    public DeleteFromObjectPrimitive(Item targetObject, List<Item> namesToRemove, ExceptionMetadata metadata) {

        for (Item item : namesToRemove) {
            if (targetObject.getItemByKey(item.getStringValue()) == null) {
                throw new CannotResolveUpdateSelectorException(
                        "Cannot delete key that does not exist in target object",
                        metadata
                );
            }
        }

        this.target = targetObject;
        this.content = namesToRemove;
    }

    @Override
    public void apply() {
        if (this.target.getTableLocation() == null || this.target.getTableLocation().equals("null")) {
            this.applyItem();
        } else {
            this.applyDelta();
        }
    }

    @Override
    public void applyItem() {
        for (
                String str : this.content.stream().map(Item::getStringValue).collect(Collectors.toList())
        ) {
            this.target.removeItemByKey(str);
        }
    }

    @Override
    public void applyDelta() {
        String pathIn = this.target.getPathIn().substring(this.target.getPathIn().indexOf(".") + 1);
        String location = this.target.getTableLocation();
        long rowID = this.target.getTopLevelID();
        int startOfArrayIndexing = pathIn.indexOf("[");

        if (startOfArrayIndexing == -1) {
            List<String> setFieldsToNulls = this.content.stream().map(i -> pathIn + i.getStringValue() + " = NULL").collect(Collectors.toList());
            String concatSetNulls = String.join(", ", setFieldsToNulls);

            String query = "UPDATE delta.`" + location + "` SET " + concatSetNulls + " WHERE rowID == " + rowID;

            SparkSessionManager.getInstance().getOrCreateSession().sql(query);
        } else {
            this.arrayIndexingApplyDelta();
        }
    }

    @Override
    public boolean hasSelector() {
        return false;
    }

    @Override
    public Item getTarget() {
        return this.target;
    }

    @Override
    public List<Item> getContentList() {
        return this.content;
    }

    @Override
    public boolean isDeleteObject() {
        return true;
    }

    public static List<Item> mergeSources(List<Item> first, List<Item> second) {
        List<Item> merged = new ArrayList<>(first);
        merged.addAll(second);
        merged = merged.stream().distinct().collect(Collectors.toList());
        return merged;
    }
}
