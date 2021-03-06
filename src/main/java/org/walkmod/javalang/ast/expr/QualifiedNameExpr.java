/*
 * Copyright (C) 2013 Raquel Pau and Albert Coroleu.
 * 
 * Walkmod is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * Walkmod is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Walkmod. If
 * not, see <http://www.gnu.org/licenses/>.
 */
package org.walkmod.javalang.ast.expr;

import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

/**
 * @author Julio Vilmar Gesser
 */
public final class QualifiedNameExpr extends NameExpr {

    private NameExpr qualifier;

    public QualifiedNameExpr() {}

    public QualifiedNameExpr(NameExpr scope, String name) {
        super(name);
        setQualifier(scope);
    }

    public QualifiedNameExpr(int beginLine, int beginColumn, int endLine, int endColumn, NameExpr scope, String name) {
        super(beginLine, beginColumn, endLine, endColumn, name);
        setQualifier(scope);
    }

    @Override
    public boolean removeChild(Node child) {
        boolean result = false;
        if (child != null) {
            if (qualifier == child) {
                qualifier = null;
                result = true;
            }
        }
        if (result) {
            updateReferences(child);
        }
        return result;
    }

    @Override
    public List<Node> getChildren() {
        List<Node> children = super.getChildren();
        if (qualifier != null) {
            children.add(qualifier);
        }
        return children;
    }

    @Override
    public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
        if (!check()) {
            return null;
        }
        return v.visit(this, arg);
    }

    @Override
    public <A> void accept(VoidVisitor<A> v, A arg) {
        if (check()) {
            v.visit(this, arg);
        }
    }

    public NameExpr getQualifier() {
        return qualifier;
    }

    public void setQualifier(NameExpr qualifier) {
        this.qualifier = qualifier;
        setAsParentNodeOf(qualifier);
    }

    @Override
    public QualifiedNameExpr clone() throws CloneNotSupportedException {
        return new QualifiedNameExpr(clone(getQualifier()), getName());
    }

}
