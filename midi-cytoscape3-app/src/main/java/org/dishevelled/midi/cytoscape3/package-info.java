/*

    dsh-midi-cytoscape3-app  Cytoscape3 app for MIDI networks.
    Copyright (c) 2013 held jointly by the individual authors.

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
 * Cytoscape3 app for MIDI networks.
 */
package org.dishevelled.midi.cytoscape3;

/*

  todo:

  refresh midi devices might not work, check rwmidi
  add midi note names to nodes for mapping to label, update vizmapping
  play action context menu item on CyNode
  record action context menu item on CyNetworkView (?)
  visual indicator for playback position (change style, or recenter camera, or both)
  always link noteOn to noteOff note edges for same note
  record and playback other midi message types
  document how to create attributes necessary to play arbitrary graph
  add channel, bpm, humanize, etc. to playback task
  timeline graph layout (left to right or top to bottom based on timestamp)

 */