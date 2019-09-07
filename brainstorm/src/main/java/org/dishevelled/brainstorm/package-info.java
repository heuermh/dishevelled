/*

    dsh-brainstorm  Brain storm, a fit of mental confusion or excitement.
    Copyright (c) 2008-2019 held jointly by the individual authors.

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
 * Brain storm, a fit of mental confusion or excitement.
 *
 * Feature list:
 * <ul>
 *   <li>full screen 2-line scrolling text editor with word-based line wrap</li>
 *   <li>font, font size</li>
 *   <li>foreground/text paint</li>
 *   <li>background paint or image</li>
 *   <li>specify file name as commandline param, or use lower(first word typed after startup)</li>
 *   <li>save to ./fileName.txt every n seconds/minutes, first copying backup to ./fileName-revisions/fileName-rev${timestamp}.txt</li>
 *   <li>backspace/delete/arrow/home/end/page up/page down keys disabled</li>
 *   <li>Ctrl-+, Ctrl-- to increase/decrease font size</li>
 *   <li>Ctrl-S or Ctrl-X Ctrl-S to save</li>
 *   <li>Q or ESC or Ctrl-X Ctrl-C to quit</li>
 *   <li>Some sort of property editor dialog</li>
 *   <li>Blinking old-school cursor</li>
 *   <li>Cursor paint/transparency</li>
 * </ul>
 *
 * Command line options for:
 * <ul>
 *   <li>font size</li>
 *   <li>font familiy</li>
 *   <li>number of lines to show (default 2)</li>
 *   <li>text hinting settings</li>
 *   <li>foreground color</li>
 *   <li>background color</li>
 *   <li>background image (will have to figure out the opacity stuff)</li>
 *   <li>cursor color</li>
 *   <li>cursor style</li>
 *   <li>auto-save on/off</li>
 *   <li>auto-save delay</li>
 *   <li>file extension (default .txt)</li>
 *   <li>revisions folder name</li>
 *   <li>allow backspace, allow arrow keys, etc. as switches</li>
 *   <li>background color gradients</li>
 *   <li>full screen mode could be a switch</li>
 * </ul>
 */
package org.dishevelled.brainstorm;
