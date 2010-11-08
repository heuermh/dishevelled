/*

    dsh-glazedlists-view  Views for event lists.
    Copyright (c) 2010 held jointly by the individual authors.

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

/**
 * Views for event lists.
 */
package org.dishevelled.glazedlists.view;

    /*

Implementation plan:

observable-view, observable

<<int>>ObservableSetView
<<int>>ObservableListView
<<int>>ObservableMapView
ObservableSetViewSupport
ObservableListViewSupport
ObservableMapViewSupport
<<abstract>>AbstractObservableSetView extends JPanel
<<abstract>>AbstractObservableListView extends JPanel
<<abstract>>AbstractObservableMapView extends JPanel

glazedlists-view, glazedlists

<<int>>EventListView
EventListViewSupport
<<abstract>>AbstractEventListView extends JPanel

piccolo-selection, observable-view, piccolo

ObservableSetNode extends PNode
ObservableListNode extends PNode
ObservableMapNode extends PNode
SelectionMarquee
SelectionEventHandler  (set of selected elements)
IndexedSelectionEventHandler  (similar to ListSelectionModel)
KeyedSelectionEventHandler  (set of selected keys)

piccolo-glazedlists-selection, glazedlists-view, piccolo

EventListNode extends PNode
EventListSelectionEventHandler ?

glazedlists-identify-view, identify, glazedlists-view

IdElementsLabel
IdList(View)
IdTable(View)
IdTree(View)
IdGraph(View)
IdMatrix1/2/3D(View)

piccolo-identify-observable-view, piccolo-selection, piccolo-identify

IdObservableSetNode
IdObservableListNode
IdObservableMapNode

piccolo-identify-glazedlists-view, piccolo-glazedlists-selection, piccolo-identify

IdEventListNode

other selection models

GraphSelectionModel?
IndexedSelection1/2/3DModel?
Binary/Ternary/Quaternary/KeyedSelectionModel?
BitMatrix1/2/3DSelectionModel?
     */
