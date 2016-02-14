/* 
  Copyright (C) 2013 Raquel Pau and Albert Coroleu.
 
 Walkmod is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 
 Walkmod is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.
 
 You should have received a copy of the GNU Lesser General Public License
 along with Walkmod.  If not, see <http://www.gnu.org/licenses/>.*/
package org.walkmod.javalang.ast.stmt;

import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

/**
 * @author Julio Vilmar Gesser
 */
public final class LabeledStmt extends Statement {

   private String label;

   private Statement stmt;

   public LabeledStmt() {
   }

   public LabeledStmt(String label, Statement stmt) {
      this.label = label;
      setStmt(stmt);
   }

   public LabeledStmt(int beginLine, int beginColumn, int endLine, int endColumn, String label, Statement stmt) {
      super(beginLine, beginColumn, endLine, endColumn);
      this.label = label;
      setStmt(stmt);
   }

   @Override
   public List<Node> getChildren() {
      List<Node> children = super.getChildren();
      if (stmt != null) {
         children.add(stmt);
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

   public String getLabel() {
      return label;
   }

   public Statement getStmt() {
      return stmt;
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public void setStmt(Statement stmt) {
      if (this.stmt != null) {
         updateReferences(this.stmt);
      }
      this.stmt = stmt;
      setAsParentNodeOf(stmt);
   }

   @Override
   public boolean replaceChildNode(Node oldChild, Node newChild) {
      boolean updated = false;
      if (oldChild == stmt) {
         setStmt((Statement) newChild);
         updated = true;
      }

      return updated;
   }

   @Override
   public LabeledStmt clone() throws CloneNotSupportedException {
      return new LabeledStmt(label, clone(stmt));
   }
}
