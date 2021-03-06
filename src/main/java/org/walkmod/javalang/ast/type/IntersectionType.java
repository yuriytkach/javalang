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
package org.walkmod.javalang.ast.type;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public class IntersectionType extends Type {

    private List<ReferenceType> bounds;

    public IntersectionType() {

    }

    public IntersectionType(List<ReferenceType> bounds) {
        setBounds(bounds);
    }

    public IntersectionType(int beginLine, int beginColumn, int endLine, int endColumn, List<ReferenceType> bounds) {
        super(beginLine, beginColumn, endLine, endColumn, null);
        setBounds(bounds);
    }

    @Override
    public boolean removeChild(Node child) {
        boolean result = false;
        if (child != null) {
            if (bounds != null) {
                if (child instanceof ReferenceType) {
                    List<ReferenceType> boundsAux = new LinkedList<ReferenceType>(bounds);
                    result = boundsAux.remove(child);
                    bounds = boundsAux;
                }
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
        if (bounds != null) {
            children.addAll(bounds);
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

    public List<ReferenceType> getBounds() {
        return bounds;
    }

    public void setBounds(List<ReferenceType> bounds) {
        this.bounds = bounds;
        setAsParentNodeOf(bounds);
    }

    @Override
    public boolean replaceChildNode(Node oldChild, Node newChild) {
        return super.replaceChildNode(oldChild, newChild);
    }

    @Override
    public IntersectionType clone() throws CloneNotSupportedException {
        return new IntersectionType(clone(getBounds()));
    }
}
