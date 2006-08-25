/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2006 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.identify;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.ComponentOrientation;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconTextDirection;

/**
 * An extension of JButton that displays the name property value
 * and appropriate icon from an icon bundle for an identifiable
 * action.
 *
 * @see IdentifiableAction
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdButton
    extends JButton
    implements PropertyChangeListener
{
    /** Default icon size. */
    public static final IconSize DEFAULT_ICON_SIZE = IconSize.DEFAULT_32X32;

    /** Default icon state. */
    private static final IconState DEFAULT_ICON_STATE = IconState.NORMAL;

    /** Default icon text direction. */
    private static final IconTextDirection DEFAULT_ICON_TEXT_DIRECTION = IconTextDirection.LEFT_TO_RIGHT;

    /** Identifiable action. */
    private IdentifiableAction action;

    /** Icon size. */
    private IconSize iconSize;

    /** Icon state. */
    private IconState iconState;

    /** Icon text direction. */
    private IconTextDirection iconTextDirection;

    /** ImageIcon wrapper for image from icon bundle. */
    private transient ImageIcon imageIcon;

    /** Dirty flag. */
    private transient boolean dirty = false;

    /** Mouse listener. */
    private MouseListener mouseListener = new MouseAdapter()
        {
            /** @see MouseListener */
            public final void mouseEntered(final MouseEvent e)
            {
                if (getIconState().equals(IconState.NORMAL))
                {
                    setIconState(IconState.MOUSEOVER);
                    repaint();
                }
            }

            /** @see MouseListener */
            public final void mouseExited(final MouseEvent e)
            {
                if (getIconState().equals(IconState.MOUSEOVER))
                {
                    setIconState(IconState.NORMAL);
                    repaint();
                }
            }

            /** @see MouseListener */
            public final void mousePressed(final MouseEvent e)
            {
                if (getIconState().equals(IconState.MOUSEOVER))
                {
                    setIconState(IconState.ACTIVE);
                    repaint();
                }
            }

            /** @see MouseListener */
            public final void mouseReleased(final MouseEvent e)
            {
                if (getIconState().equals(IconState.ACTIVE))
                {
                    setIconState(IconState.MOUSEOVER);
                    repaint();
                }
            }
        };

    /**
     * Cache of the previous state, for use in returning to the
     * proper state following a call of <code>setEnabled(true)</code>.
     */
    private transient IconState previousState;


    /**
     * Create a new button with the specified identifiable action.
     *
     * @param action identifiable action for this button, must not be null
     */
    public IdButton(final IdentifiableAction action)
    {
        super();

        iconSize = DEFAULT_ICON_SIZE;
        iconState = DEFAULT_ICON_STATE;
        previousState = DEFAULT_ICON_STATE;
        iconTextDirection = DEFAULT_ICON_TEXT_DIRECTION;

        addMouseListener(mouseListener);

        setAction(action);
    }


    /**
     * Return the icon size for this button.
     *
     * @return the icon size for this button
     */
    public IconSize getIconSize()
    {
        return iconSize;
    }

    /**
     * Set the icon size for this button to <code>iconSize</code>.
     *
     * @param iconSize icon size, must not be null
     */
    public void setIconSize(final IconSize iconSize)
    {
        if (iconSize == null)
        {
            throw new IllegalArgumentException("iconSize must not be null");
        }
        this.iconSize = iconSize;
        setDirty(true);
    }

    /**
     * Return the icon state for this button.
     *
     * @return the icon state for this button
     */
    public IconState getIconState()
    {
        return iconState;
    }

    /**
     * Set the icon state for this button to <code>iconState</code>.
     *
     * @param iconState icon state, must not be null
     */
    public void setIconState(final IconState iconState)
    {
        if (iconState == null)
        {
            throw new IllegalArgumentException("iconState must not be null");
        }
        this.iconState = iconState;
        setDirty(true);
    }

    /**
     * Set the identifiable action for this button to <code>action</code>.
     *
     * @param action identifiable action for this button, must not be null
     */
    private void setAction(final IdentifiableAction action)
    {
        if (action == null)
        {
            throw new IllegalArgumentException("action must not be null");
        }
        if (this.action != null)
        {
            this.action.removePropertyChangeListener(this);
        }
        this.action = action;
        this.action.addPropertyChangeListener(this);
        setDirty(true);
    }

    /** @see JButton */
    public void setComponentOrientation(final ComponentOrientation orientation)
    {
        if (orientation == null)
        {
            return;
        }

        iconTextDirection = orientation.isLeftToRight() ?
            IconTextDirection.LEFT_TO_RIGHT : IconTextDirection.RIGHT_TO_LEFT;

        super.setComponentOrientation(orientation);

        setDirty(true);
    }

    /** @see JButton */
    public void applyComponentOrientation(final ComponentOrientation orientation)
    {
        if (orientation == null)
        {
            return;
        }

        iconTextDirection = orientation.isLeftToRight() ?
            IconTextDirection.LEFT_TO_RIGHT : IconTextDirection.RIGHT_TO_LEFT;

        super.applyComponentOrientation(orientation);

        setDirty(true);
    }

    /** @see JButton */
    public void setEnabled(final boolean enabled)
    {
        boolean previousEnabled = isEnabled();
        boolean disabledState = getIconState().equals(IconState.DISABLED);

        if (enabled && !previousEnabled)
        {
            setIconState(previousState);
        }
        else if (!enabled && disabledState)
        {
            previousState = getIconState();
            setIconState(IconState.DISABLED);
        }

        super.setEnabled(enabled);

        setDirty(true);
    }

    /** @see PropertyChangeListener */
    public void propertyChange(final PropertyChangeEvent e)
    {
        if (action.equals(e.getSource()))
        {
            setDirty(true);
        }
    }

    /**
     * Set the dirty flag to the logical OR of <code>dirty</code>
     * and the previous dirty state.
     *
     * @param dirty dirty flag
     */
    protected void setDirty(final boolean dirty)
    {
        this.dirty = (this.dirty || dirty);
    }

    /**
     * Return true if a rebuild of the text and icon properties
     * is necessary.
     *
     * @return true if a rebuild of the text and icon properties
     *    is necessary
     */
    protected boolean isDirty()
    {
        return dirty;
    }

    /**
     * Rebuild the text and icon properties of this button
     * with the name property and icon bundle image of
     * the identifiable action, respectively.
     */
    protected void rebuild()
    {
        setText(action.getName());
        setEnabled(action.isEnabled());
        setToolTipText((String) action.getValue(Action.SHORT_DESCRIPTION));
        // TODO:
        // install/update keyboard event and mnemonic &c.

        IconBundle bndl = action.getIconBundle();

        Image image = bndl.getImage(this,
                                    iconTextDirection,
                                    iconState,
                                    iconSize);

        if (imageIcon == null)
        {
            imageIcon = new ImageIcon(image);
        }
        else
        {
            imageIcon.setImage(image);
        }

        setIcon(imageIcon);
        dirty = false;
    }


    // TODO:
    // override other JButton methods

    /** @see JButton */
    public void paintComponent(final Graphics g)
    {
        if (isDirty())
        {
            rebuild();
        }
        super.paintComponent(g);
    }
}
