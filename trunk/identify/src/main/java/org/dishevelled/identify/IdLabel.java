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
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.ComponentOrientation;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconTextDirection;

/**
 * An extension of JLabel that displays the name property
 * and appropriate icon from an icon bundle for a given bean.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdLabel
    extends JLabel
{
    /** Default icon size. */
    public static final IconSize DEFAULT_ICON_SIZE = IconSize.DEFAULT_32X32;

    /** Default icon state. */
    private static final IconState DEFAULT_ICON_STATE = IconState.NORMAL;

    /** Default icon text direction. */
    private static final IconTextDirection DEFAULT_ICON_TEXT_DIRECTION = IconTextDirection.LEFT_TO_RIGHT;

    /** Bound value property. */
    private Object value;

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

    /** Mouseover mouse listener. */
    private MouseListener mouseoverListener = new MouseAdapter()
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
        };

    /**
     * Cache of the previous state, for use in returning to the
     * proper state following a call of <code>setEnabled(true)</code>.
     */
    private transient IconState previousState;


    /**
     * Create a new label with a null value.
     */
    public IdLabel()
    {
        super();

        iconSize = DEFAULT_ICON_SIZE;
        iconState = DEFAULT_ICON_STATE;
        previousState = DEFAULT_ICON_STATE;
        iconTextDirection = DEFAULT_ICON_TEXT_DIRECTION;

        value = null;

        addMouseListener(mouseoverListener);
    }

    /**
     * Create a new label for the specified value.
     *
     * @param value value
     */
    public IdLabel(final Object value)
    {
        this();

        setValue(value);
    }


    /**
     * Return the value for this label.
     *
     * @return the value for this label
     */
    public Object getValue()
    {
        return value;
    }

    /**
     * Set the value for this label to <code>value</code>.
     * This is a bound property.
     *
     * @param value value for this label
     */
    public void setValue(final Object value)
    {
        Object oldValue = this.value;
        this.value = value;
        firePropertyChange("value", oldValue, this.value);

        setDirty(true);
    }

    /**
     * Return the icon size for this label.
     *
     * @return the icon size for this label
     */
    public IconSize getIconSize()
    {
        return iconSize;
    }

    /**
     * Set the icon size for this label to <code>iconSize</code>.
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
     * Return the icon state for this label.
     *
     * @return the icon state for this label
     */
    public IconState getIconState()
    {
        return iconState;
    }

    /**
     * Set the icon state for this label to <code>iconState</code>.
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


    //
    //  synchronize IconTextDirection and AWT componentOrientation

    /** @see JLabel */
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

    /** @see JLabel */
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


    //
    //  synchronize IconState and Swing isEnabled

    /** @see JLabel */
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


    //
    //  rebuild JLabel text and icon properties

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
     * Rebuild the text and icon properties of this label
     * with the name property and icon bundle image of
     * <code>getValue()</code>, respectively.
     */
    protected void rebuild()
    {
        String name = IdentifyUtils.getNameFor(getValue());
        IconBundle bndl = IdentifyUtils.getIconBundleFor(getValue());

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

        setText(name);
        setIcon(imageIcon);

        dirty = false;
    }


    //
    //  override JLabel methods

    /** @see JLabel */
    public Icon getIcon()
    {
        return imageIcon;
    }

    /** @see JLabel */
    public Icon getDisabledIcon()
    {
        return imageIcon;
    }

    /** @see JLabel */
    public Rectangle getBounds()
    {
        if (isDirty())
        {
            rebuild();
        }
        return super.getBounds();
    }

    /** @see JLabel */
    public Rectangle getBounds(final Rectangle rv)
    {
        if (isDirty())
        {
            rebuild();
        }
        return super.getBounds(rv);
    }

    /** @see JLabel */
    public Dimension getMaximumSize()
    {
        if (isDirty())
        {
            rebuild();
        }
        return super.getMaximumSize();
    }

    /** @see JLabel */
    public Dimension getMinimumSize()
    {
        if (isDirty())
        {
            rebuild();
        }
        return super.getMinimumSize();
    }

    /** @see JLabel */
    public Dimension getPreferredSize()
    {
        if (isDirty())
        {
            rebuild();
        }
        return super.getPreferredSize();
    }

    /** @see JLabel */
    public Dimension getSize()
    {
        if (isDirty())
        {
            rebuild();
        }
        return super.getSize();
    }

    /** @see JLabel */
    public Rectangle getVisibleRect()
    {
        if (isDirty())
        {
            rebuild();
        }
        return super.getVisibleRect();
    }

    /** @see JLabel */
    public int getHeight()
    {
        if (isDirty())
        {
            rebuild();
        }
        return super.getHeight();
    }

    /** @see JLabel */
    public int getWidth()
    {
        if (isDirty())
        {
            rebuild();
        }
        return super.getWidth();
    }

    /** @see JLabel */
    public void paintComponent(final Graphics g)
    {
        if (isDirty())
        {
            rebuild();
        }
        super.paintComponent(g);
    }
}
