/*

    dsh-piccolo-identify  Piccolo2D nodes for identifiable beans.
    Copyright (c) 2007-2010 held jointly by the individual authors.

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

import java.awt.Component;
import java.awt.Image;
import java.awt.Font;

import java.awt.geom.Dimension2D;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.net.URL;

import edu.umd.cs.piccolo.PNode;

import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PDragSequenceEventHandler;

import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PText;

import org.apache.commons.scxml.env.SimpleErrorHandler;

import org.apache.commons.scxml.model.SCXML;

import org.apache.commons.scxml.io.SCXMLParser;

import org.dishevelled.iconbundle.IconBundle;
import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconState;
import org.dishevelled.iconbundle.IconTextDirection;

import org.dishevelled.identify.IdentifyUtils;

/**
 * Abstract id node.
 *
 * <p>An icon bundle image node and name text node are
 * provided by this class but are not added as child nodes.  That
 * is left up to the subclass.</p>
 *
 * <p>This node is backed by a state machine which is responsible
 * for managing all state transitions.  Mouse events are translated
 * into state transitions by a default mouse event handler.  Clients
 * may fire additional state transitions using methods on this class (see e.g.
 * <a href="#enable"><code>enable()</code>.</a>  Invalid state transitions
 * are ignored.</p>
 *
 * <p>Subclasses may associate visual properties and behavior with states
 * by providing private no-arg state methods which will be called via reflection
 * on entry by the state machine engine.</p>
 *
 * <p>For example:
 * <pre>
 * class MyIdNode extends AbstractIdNode {
 *   // ...
 *
 *   private void normal() {
 *     setBackground(Color.WHITE);
 *     setForeground(Color.BLACK);
 *   }
 *   private void selected() {
 *     setBackground(Color.BLACK);
 *     setForeground(Color.WHITE);
 *   }
 * }
 * </pre></p>
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractIdNode
    extends PNode
{
    /** Value for this id node. */
    private Object value;

    /** Icon size for this id node. */
    private IconSize iconSize;

    /** Icon state for this id node. */
    private IconState iconState;

    /** Icon text direction for this id node. */
    private IconTextDirection iconTextDirection;

    /** Icon bundle image node. */
    private final IconBundleImageNode iconBundleImageNode;

    /** Name text node. */
    private final NameTextNode nameTextNode;

    /** State machine. */
    private static SCXML stateMachine;

    /** State machine support. */
    private final StateMachineSupport stateMachineSupport;

    /** Default icon size, 32x32. */
    public static final IconSize DEFAULT_ICON_SIZE = IconSize.DEFAULT_32X32;

    /** Default icon state, normal. */
    public static final IconState DEFAULT_ICON_STATE = IconState.NORMAL;

    /** Default icon text direction, left to right. */
    public static final IconTextDirection DEFAULT_ICON_TEXT_DIRECTION = IconTextDirection.LEFT_TO_RIGHT;


    /**
     * Create a new abstract id node with the specified value.
     *
     * @param value value for this id node
     */
    protected AbstractIdNode(final Object value)
    {
        this.value = value;
        iconSize = DEFAULT_ICON_SIZE;
        iconState = DEFAULT_ICON_STATE;
        iconTextDirection = DEFAULT_ICON_TEXT_DIRECTION;
        iconBundleImageNode = new IconBundleImageNode();
        nameTextNode = new NameTextNode();

        // TODO:  split into drag, mouseover, selection handlers
        PDragSequenceEventHandler dragSequenceEventHandler = new PDragSequenceEventHandler()
            {
                /** {@inheritDoc} */
                protected void drag(final PInputEvent event)
                {
                    Dimension2D delta = event.getDeltaRelativeTo(AbstractIdNode.this);
                    AbstractIdNode.this.translate(delta.getWidth(), delta.getHeight());
                    event.setHandled(true);
                }

                /** {@inheritDoc} */
                protected void startDrag(final PInputEvent event)
                {
                    AbstractIdNode.this.moveToFront();
                    AbstractIdNode.this.startDrag();
                    super.startDrag(event);
                }

                /** {@inheritDoc} */
                protected void endDrag(final PInputEvent event)
                {
                    AbstractIdNode.this.endDrag();
                    super.endDrag(event);
                }

                /** {@inheritDoc} */
                public void mouseEntered(final PInputEvent event)
                {
                    AbstractIdNode.this.mouseEntered();
                    super.mouseEntered(event);
                }

                /** {@inheritDoc} */
                public void mouseExited(final PInputEvent event)
                {
                    AbstractIdNode.this.mouseExited();
                    super.mouseExited(event);
                }

                /** {@inheritDoc} */
                public void mousePressed(final PInputEvent event)
                {
                    AbstractIdNode.this.mousePressed();
                    super.mousePressed(event);
                }

                /** {@inheritDoc} */
                public void mouseReleased(final PInputEvent event)
                {
                    AbstractIdNode.this.mouseReleased();
                    super.mouseReleased(event);
                }
            };
        dragSequenceEventHandler.setMinDragStartDistance(4.0d);
        addInputEventListener(dragSequenceEventHandler);

        if (stateMachine == null)
        {
            try
            {
                URL stateChart = getClass().getResource("stateChart.xml");
                stateMachine = SCXMLParser.parse(stateChart, new SimpleErrorHandler());
            }
            catch (Exception e)
            {
                // ignore
            }
        }
        stateMachineSupport = new StateMachineSupport(this, stateMachine);
    }


    /**
     * Return the value for this id node.
     *
     * @return the value for this id node
     */
    public final Object getValue()
    {
        return value;
    }

    /**
     * Set the value for this id node to <code>value</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param value value for this id node
     */
    public final void setValue(final Object value)
    {
        Object oldValue = this.value;
        this.value = value;
        firePropertyChange(-1, "value", oldValue, this.value);
    }

    /**
     * Return the icon size for this id node.
     * The icon size will not be null.
     *
     * @return the icon size for this id node
     */
    public final IconSize getIconSize()
    {
        return iconSize;
    }

    /**
     * Set the icon size for this id node to <code>iconSize</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param iconSize icon size for this id node, must not be null
     */
    public final void setIconSize(final IconSize iconSize)
    {
        if (iconSize == null)
        {
            throw new IllegalArgumentException("iconSize must not be null");
        }
        IconSize oldIconSize = this.iconSize;
        this.iconSize = iconSize;
        firePropertyChange(-1, "iconSize", oldIconSize, this.iconSize);
    }

    /**
     * Return the icon state for this id node.
     * The icon state will not be null.
     *
     * @return the icon state for this id node
     */
    public final IconState getIconState()
    {
        return iconState;
    }

    /**
     * Set the icon state for this id node to <code>iconState</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param iconState icon state for this id node, must not be null
     */
    public final void setIconState(final IconState iconState)
    {
        if (iconState == null)
        {
            throw new IllegalArgumentException("iconState must not be null");
        }
        IconState oldIconState = this.iconState;
        this.iconState = iconState;
        firePropertyChange(-1, "iconState", oldIconState, this.iconState);
    }

    /**
     * Return the icon text direction for this id node.
     * The icon text direction will not be null.
     *
     * @return the icon text direction for this id node
     */
    public final IconTextDirection getIconTextDirection()
    {
        return iconTextDirection;
    }

    /**
     * Set the icon text direction for this id node to <code>iconTextDirection</code>.
     *
     * <p>This is a bound property.</p>
     *
     * @param iconTextDirection icon text direction for this id node, must not be null
     */
    public final void setIconTextDirection(final IconTextDirection iconTextDirection)
    {
        if (iconTextDirection == null)
        {
            throw new IllegalArgumentException("iconTextDirection must not be null");
        }
        IconTextDirection oldIconTextDirection = this.iconTextDirection;
        this.iconTextDirection = iconTextDirection;
        firePropertyChange(-1, "iconTextDirection", oldIconTextDirection, this.iconTextDirection);
    }

    /**
     * Set the font for the name text node to <code>font</code>.
     *
     * @param font font for the name text node
     */
    public final void setFont(final Font font)
    {
        // TODO:  manage property changes here?
        nameTextNode.setFont(font);
    }

    /**
     * Return the icon bundle image node for this id node.
     *
     * @return the icon bundle image node for this id node
     */
    protected final IconBundleImageNode getIconBundleImageNode()
    {
        return iconBundleImageNode;
    }

    /**
     * Return the name text node for this id node.
     *
     * @return the name text node for this id node
     */
    protected final NameTextNode getNameTextNode()
    {
        return nameTextNode;
    }

    /**
     * Reset the state machine to its &quot;initial&quot; configuration.
     */
    protected final void resetStateMachine()
    {
        stateMachineSupport.resetStateMachine();
    }

    /**
     * Fire a state machine event with the specified event name.
     *
     * @param eventName event name, must not be null
     */
    private void fireStateMachineEvent(final String eventName)
    {
        stateMachineSupport.fireStateMachineEvent(eventName);
    }

    /**
     * Fire a <code>"reverseVideo"</code> state transition event.
     */
    public final void reverseVideo()
    {
        fireStateMachineEvent("reverseVideo");
    }

    /**
     * Fire an <code>"enable"</code> state transition event.
     */
    public final void enable()
    {
        fireStateMachineEvent("enable");
    }

    /**
     * Fire a <code>"disable"</code> state transition event.
     */
    public final void disable()
    {
        fireStateMachineEvent("disable");
    }

    /**
     * Fire a <code>"mouseEntered"</code> state transition event.
     */
    public final void mouseEntered()
    {
        fireStateMachineEvent("mouseEntered");
    }

    /**
     * Fire a <code>"mouseExited"</code> state transition event.
     */
    public final void mouseExited()
    {
        fireStateMachineEvent("mouseExited");
    }

    /**
     * Fire a <code>"mouseReleased"</code> state transition event.
     */
    public final void mouseReleased()
    {
        fireStateMachineEvent("mouseReleased");
    }

    /**
     * Fire a <code>"mousePressed"</code> state transition event.
     */
    public final void mousePressed()
    {
        fireStateMachineEvent("mousePressed");
    }

    /**
     * Fire a <code>"select"</code> state transition event.
     */
    public final void select()
    {
        fireStateMachineEvent("select");
    }

    /**
     * Fire a <code>"deselect"</code> state transition event.
     */
    public final void deselect()
    {
        fireStateMachineEvent("deselect");
    }

    /**
     * Fire a <code>"startDrag"</code> state transition event.
     */
    public final void startDrag()
    {
        fireStateMachineEvent("startDrag");
    }

    /**
     * Fire a <code>"endDrag"</code> state transition event.
     */
    public final void endDrag()
    {
        fireStateMachineEvent("endDrag");
    }

    /**
     * Icon bundle image node.
     */
    protected final class IconBundleImageNode
        extends PImage
        implements PropertyChangeListener
    {

        /**
         * Create a new icon bundle image node.
         */
        IconBundleImageNode()
        {
            super();
            update();
            AbstractIdNode.this.addPropertyChangeListener("value", this);
            AbstractIdNode.this.addPropertyChangeListener("iconSize", this);
            AbstractIdNode.this.addPropertyChangeListener("iconState", this);
            AbstractIdNode.this.addPropertyChangeListener("iconTextDirection", this);
        }


        /**
         * Update this icon bundle image node.
         */
        private void update()
        {
            IconBundle iconBundle = IdentifyUtils.getIconBundleFor(value);
            if (iconBundle == null)
            {
                setWidth(0.0d);
                setHeight(0.0d);
            }
            else
            {
                Image image = iconBundle.getImage(null, iconTextDirection, iconState, iconSize);
                setImage(image);
                setWidth(iconSize.getWidth());
                setHeight(iconSize.getHeight());
            }
        }

        /** {@inheritDoc} */
        public void propertyChange(final PropertyChangeEvent event)
        {
            update();
        }
    }

    /**
     * Name text node.
     */
    protected final class NameTextNode
        extends PText
        implements PropertyChangeListener
    {

        /**
         * Create a new name text node.
         */
        NameTextNode()
        {
            super();
            setHorizontalAlignment(Component.CENTER_ALIGNMENT);
            update();
            AbstractIdNode.this.addPropertyChangeListener("value", this);
            //AbstractIdNode.this.addPropertyChangeListener("iconTextDirection", this);
        }


        /**
         * Update this name text node.
         */
        private void update()
        {
            String name = IdentifyUtils.getNameFor(value);
            setText(name);
        }

        /** {@inheritDoc} */
        public void propertyChange(final PropertyChangeEvent event)
        {
            update();
        }
    }
}
