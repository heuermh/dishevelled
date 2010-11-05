/*

    dsh-glazedlists-view  Views that use GlazedLists' EventList.
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
 * Views that use GlazedLists' EventList.
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
<<abstract>>AbstractObservableSetView
<<abstract>>AbstractObservableListView
<<abstract>>AbstractObservableMapView
<<int>>ModelToView
<<int>>ViewToModel
<<int>>IndexedModelToView
<<int>>IndexedViewToModel
<<int>>KeyedModelToView
<<int>>KeyedViewToModel
<<int>>View, or ViewDecorator
NoopView, NoopViewDecorator, ViewAdapter?

glazedlists-view, glazedlists

<<int>>EventListView
EventListViewSupport
<<abstract>>AbstractEventListView
EventListModelToView?
EventListViewToModel?

piccolo-selection, observable-view, piccolo

ObservableSetNode
ObservableListNode
ObservableMapNode
SelectionMarquee
SelectionEventHandler
IndexedSelectionEventHandler
KeyedSelectionEventHandler

piccolo-glazedlists-selection, glazedlists-view, piccolo

EventListNode
EventListSelectionEventHandler ?

piccolo-identify-observable-view, piccolo-selection, piccolo-identify

IdObservableSetNode
IdObservableListNode
IdObservableMapNode

piccolo-identify-glazedlists-view, piccolo-glazedlists-selection, piccolo-identify

IdEventListNode

     */
