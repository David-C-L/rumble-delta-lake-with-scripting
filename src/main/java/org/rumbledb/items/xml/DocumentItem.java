package org.rumbledb.items.xml;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.rumbledb.api.Item;
import org.rumbledb.types.BuiltinTypesCatalogue;
import org.rumbledb.types.ItemType;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class DocumentItem implements Item {
    private static final long serialVersionUID = 1L;
    private Node documentNode;
    private List<Item> children;
    // TODO: add base-uri, document-uri, typed-value

    public DocumentItem(Node documentNode, List<Item> children) {
        this.documentNode = documentNode;
        this.children = children;
    }

    @Override
    public void addParentToDescendants() {
        this.children.forEach(child -> child.setParent(this));
    }

    @Override
    public void write(Kryo kryo, Output output) {
        kryo.writeObject(output, this.documentNode);
        kryo.writeObject(output, this.children);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void read(Kryo kryo, Input input) {
        this.documentNode = kryo.readObject(input, Node.class);
        this.children = kryo.readObject(input, ArrayList.class);
    }

    @Override
    public List<Item> children() {
        return this.children;
    }

    @Override
    public boolean nilled() {
        return false;
    }

    @Override
    public String nodeKind() {
        return "document";
    }

    @Override
    public Item parent() {
        return null;
    }

    @Override
    public String stringValue() {
        return this.documentNode.getTextContent();
    }

    @Override
    public ItemType getDynamicType() {
        return BuiltinTypesCatalogue.item;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DocumentItem)) {
            return false;
        }
        DocumentItem otherDocumentItem = (DocumentItem) other;
        return otherDocumentItem.documentNode.isEqualNode(this.documentNode);
    }

    @Override
    public boolean isDocumentNode() {
        return true;
    }

    @Override
    public int hashCode() {
        return this.documentNode.hashCode();
    }
}
