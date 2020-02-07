/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authors: Stefan Irimescu, Can Berker Cikis
 *
 */

package org.rumbledb.expressions.control;



import sparksoniq.semantics.visitor.AbstractNodeVisitor;



import java.util.ArrayList;
import java.util.List;

import org.rumbledb.exceptions.ExceptionMetadata;
import org.rumbledb.expressions.Expression;
import org.rumbledb.expressions.Node;

public class IfExpression extends Expression {

    private final Expression _elseBranch;
    private final Expression _condition;
    private final Expression _branch;

    public IfExpression(
            Expression condition,
            Expression branch,
            Expression elseBranch,
            ExceptionMetadata metadataFromContext
    ) {
        super(metadataFromContext);
        this._condition = condition;
        this._branch = branch;
        this._elseBranch = elseBranch;
    }

    public Expression getElseBranch() {
        return _elseBranch;
    }

    public Expression getCondition() {
        return _condition;
    }

    public Expression getBranch() {
        return _branch;
    }

    @Override
    public List<Node> getChildren() {
        List<Node> result = new ArrayList<>();
        result.add(_condition);
        result.add(_branch);
        if (_elseBranch != null)
            result.add(_elseBranch);
        return result;
    }

    @Override
    public <T> T accept(AbstractNodeVisitor<T> visitor, T argument) {
        return visitor.visitIfExpression(this, argument);
    }

    @Override
    public String serializationString(boolean prefix) {
        String result = "(ifExpr if ( ";
        result += _condition.serializationString(prefix);
        result += " ) then ";
        result += _branch.serializationString(true);
        result += ") else ";
        result += _elseBranch.serializationString(true);
        result += "))";
        return result;
    }
}
