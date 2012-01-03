/*

    dsh-piccolo-identify  Piccolo2D nodes for identifiable beans.
    Copyright (c) 2007-2012 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 3 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.fsf.org/licensing/licenses/lgpl.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.piccolo.identify;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.ListSelection;

/**
 * List view.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public interface ListView<E, V>
{
    // model
    EventList<E> getModel();
    void setModel(EventList<E> model);

    // view <--> view mapping
    //ViewToModel<E, V> getViewToModel();
    //void setViewToModel(ViewToModel<E, V> viewToModel);
    //ModelToView<E, V> getModelToView();
    //void setModelToView(ModelToView<E, V> modelToView);

    // indexed model <--> view mapping
    IndexedViewToModel<E, V> getViewToModel();
    void setViewToModel(IndexedViewToModel<E, V> viewToModel);
    IndexedModelToView<E, V> getModelToView();
    void setModelToView(IndexedModelToView<E, V> modelToView);

    // selection model
    ListSelection<E> getSelectionModel();
    void setSelectionModel(ListSelection<E> selectionModel);

    // view decorator
    ViewDecorator<V> getViewDecorator();
    void setViewDecorator(ViewDecorator<V> viewDecorator);


    interface ViewToModel<E, V>
    {
        E get(V view);
    }

    interface ModelToView<E, V>
    {
        V get(E model);
    }

   // find the index of the model element mapped to a view
    interface IndexedViewToModel<E, V>
    {
        int get(V view);
    }

   // find the view mapped to the index of a model element
    interface IndexedModelToView<E, V>
    {
        V get(int index);
        // todo  should probably distinguish between different event types
        //V added(int index);
        //void removed(int index);
        //V reordered(int oldIndex, int newIndex);
    }

   // decorate specified views per the specified state transitions
    interface ViewDecorator<V>
    {
        void enable(V view);
        void disable(V view);

        void mousePressed(V view);
        void mouseReleased(V view);

        void mouseEntered(V view);
        void mouseExited(V view);

        void startDrag(V view);
        void endDrag(V view);

        void select(V view);
        void deselect(V view);

        // void anchor, lead selection

        void gainFocus(V view);
        void loseFocus(V view);

        //void hide/show
    }
}