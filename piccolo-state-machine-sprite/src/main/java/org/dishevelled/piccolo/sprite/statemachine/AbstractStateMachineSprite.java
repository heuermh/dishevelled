/*

    dsh-piccolo-state-machine-sprite  Piccolo2D state machine sprite and supporting classes.
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
package org.dishevelled.piccolo.sprite.statemachine;

import java.awt.image.BufferedImage;

import java.awt.Image;
import java.awt.Graphics2D;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;

import edu.umd.cs.piccolo.PNode;

import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PPaintContext;

import org.apache.commons.scxml.env.AbstractSCXMLListener;
import org.apache.commons.scxml.env.SimpleErrorHandler;

import org.apache.commons.scxml.io.SCXMLParser;

import org.apache.commons.scxml.model.ModelException;
import org.apache.commons.scxml.model.SCXML;
import org.apache.commons.scxml.model.State;
import org.apache.commons.scxml.model.TransitionTarget;

import org.dishevelled.piccolo.sprite.Animation;

import org.xml.sax.SAXException;

/**
 * Abstract Piccolo2D state machine sprite node.
 *
 * <p>
 * This abstract sprite node utilizes a state machine to manage all its state transitions.  Consider the
 * following simple state machine in <a href="http://www.w3.org/TR/scxml/">State Chart XML (SCXML)</a>
 * format:
 * <pre>
 * &lt;scxml xmlns="http://www.w3.org/2005/07/scxml" version="1.0" initialstate="normal"&gt;
 *   &lt;state id="normal"&gt;
 *     &lt;transition event="walk" target="walking"/&gt;
 *   &lt;/state&gt;
 *   &lt;state id="walking"&gt;
 *     &lt;transition event="stop" target="normal"/&gt;
 *   &lt;/state&gt;
 * &lt;/scxml&gt;
 * </pre>
 * </p>
 * <p>
 * Subclasses may provide state transition methods that fire an event
 * to the underlying state machine.
 * <pre>
 * public void walk() {
 *   fireStateMachineEvent("walk");
 * }
 * public void stop() {
 *   fireStateMachineEvent("stop");
 * }
 * </pre>
 * </p>
 * <p>
 * Subclasses may associate visual properties and behavior with states
 * by providing private no-arg state methods which will be called via reflection
 * on entry by the state machine engine.
 * <pre>
 * private void normal() {
 *   walkingActivity.stop();
 * }
 * private void walking() {
 *   walkingActivity.start();
 * }
 * </pre>
 * <p>
 * Animations can be associated with states by implementing the
 * protected <code>createAnimation</code> method.  Create and
 * return an animation for the specified state id, or return
 * <code>null</code> if no such animation exists.
 * <pre>
 *   protected Animation createAnimation(final String id) {
 *     Image image = loadImage(getClass(), id + ".png");
 *     return Animations.createAnimation(image);
 *   }
 * </pre>
 * </p>
 * <p>
 * Altogether, the typical implementation pattern for a subclass of this
 * abstract sprite node looks like
 * <pre>
 * class MySprite extends AbstractStateMachineSprite {
 *   // walking activity
 *   private final WalkingActivity walkingActivity = ...;
 *   // load the state machine backing all instances of this MySprite
 *   private static final SCXML stateMachine = loadStateMachine(MySprite.class, "stateMachine.xml");
 *
 *   MySprite() {
 *     super();
 *     // initialize the state machine
 *     initializeStateMachine(stateMachine);
 *   }
 *
 *   protected Animation createAnimation(final String id) {
 *     // load a single PNG image for each state id
 *     Image image = loadImage(getClass(), id + ".png");
 *     return Animations.createAnimation(image);
 *   }
 *
 *   // methods to fire state transition events
 *   public void walk() {
 *     fireStateMachineEvent("walk");
 *   }
 *   public void stop() {
 *     fireStateMachineEvent("stop");
 *   }
 *
 *   // methods that receive notification of state transitions
 *   private void normal() {
 *     walkingActivity.stop();
 *   }
 *   private void walking() {
 *     walkingActivity.start();
 *   }
 * }
 * </pre>
 * </p>
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractStateMachineSprite
    extends PNode
{
    /** State machine support. */
    private StateMachineSupport stateMachineSupport;

    /** Number of frames skipped. */
    private int skipped;

    /** Number of frames to skip, default <code>0</code>. */
    private int frameSkip;

    /** Current animation. */
    private Animation currentAnimation;

    /** Map of animations keyed by state id. */
    private final Map<String, Animation> animations;


    /**
     * Create a new abstract state machine sprite node.
     */
    protected AbstractStateMachineSprite()
    {
        animations = new HashMap<String, Animation>();
    }


    /**
     * Create and return an animation for the specified state id, if any.
     *
     * @param id state id
     * @return an animation for the specified state id, or <code>null</code> if
     *    no such animation exists
     */
    protected abstract Animation createAnimation(final String id);

    /**
     * Initialize the specified state machine.  Animations are loaded for all
     * the state ids and the current animation is set to the initial target, if any.
     *
     * <p>
     * <b>Note:</b> this method should be called from the constructor
     * of a subclass after its state machine has been instantiated.
     * </p>
     *
     * @param stateMachine state machine to initialize, must not be null
     */
    protected final void initializeStateMachine(final SCXML stateMachine)
    {
        if (stateMachine == null)
        {
            throw new IllegalArgumentException("stateMachine must not be null");
        }
        // load animations for state ids
        for (Iterator<?> entries = stateMachine.getTargets().entrySet().iterator(); entries.hasNext(); )
        {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) entries.next();
            String id = (String) entry.getKey();
            Object target = entry.getValue();
            if (target instanceof State)
            {
                Animation animation = createAnimation(id);
                if (animation != null)
                {
                    animations.put(id, animation);
                }
            }
        }
        // set the current animation to the initial target, if any
        String initialTargetId = (stateMachine.getInitialTarget() == null) ? null : stateMachine.getInitialTarget().getId();
        if (animations.containsKey(initialTargetId))
        {
            currentAnimation = animations.get(initialTargetId);
        }
        // create a state machine support class that delegates to this
        stateMachineSupport = new StateMachineSupport(this, stateMachine);
        // update current animation on entry to a new state
        stateMachineSupport.getExecutor().addListener(stateMachine, new AbstractSCXMLListener()
            {
                /** {@inheritDoc} */
                public void onEntry(final TransitionTarget state)
                {
                    Animation animation = animations.get(state.getId());
                    if (animation != null)
                    {
                        currentAnimation = animation;
                    }
                }
            });
    }

    /**
     * Reset the state machine to its &quot;initial&quot; configuration.
     */
    protected final void resetStateMachine()
    {
        if (stateMachineSupport != null)
        {
            stateMachineSupport.resetStateMachine();
        }
    }

    /**
     * Fire a state machine event with the specified event name.
     *
     * @param eventName event name, must not be null
     */
    protected final void fireStateMachineEvent(final String eventName)
    {
        if (stateMachineSupport != null)
        {
            stateMachineSupport.fireStateMachineEvent(eventName);
        }
    }

    /**
     * Set the number of frames to skip to <code>frameSkip</code>.
     *
     * @param frameSkip number of frames to skip, must be <code>&gt;= 0</code>
     */
    protected void setFrameSkip(final int frameSkip)
    {
        if (frameSkip < 0)
        {
            throw new IllegalArgumentException("frameSkip must be at least zero");
        }
        this.frameSkip = frameSkip;
    }

    //protected final State currentState() {} ?

    /**
     * Advance this state machine sprite node one frame.
     */
    public final void advance()
    {
        if (skipped < frameSkip)
        {
            skipped++;
        }
        else
        {
            // advance the current animation
            if (currentAnimation.advance())
            {
                // and schedule a repaint
                repaint();
            }
            skipped = 0;
        }
    }

    /** {@inheritDoc} */
    public final void paint(final PPaintContext paintContext)
    {
        if (currentAnimation != null)
        {
            Graphics2D g = paintContext.getGraphics();
            Image currentFrame = currentAnimation.getCurrentFrame();
            PBounds bounds = getBoundsReference();

            double w = currentFrame.getWidth(null);
            double h = currentFrame.getHeight(null);

            g.translate(bounds.getX(), bounds.getY());
            g.scale(bounds.getWidth() / w, bounds.getHeight() / h);
            g.drawImage(currentFrame, 0, 0, null);
            g.scale(w / bounds.getWidth(), h / bounds.getHeight());
            g.translate(-1 * bounds.getX(), -1 * bounds.getY());
        }
    }

    /**
     * Load the state machine resource with the specified name, if any.  Any exceptions thrown
     * will be ignored.
     *
     * @param cls class
     * @param name name
     * @return the state machine resource with the specified name, or <code>null</code>
     *    if no such resource exists
     */
    protected static final <T> SCXML loadStateMachine(final Class<T> cls, final String name)
    {
        SCXML stateMachine = null;
        try
        {
            stateMachine = SCXMLParser.parse(cls.getResource(name), new SimpleErrorHandler());
        }
        catch (IOException e)
        {
            // ignore
        }
        catch (SAXException e)
        {
            // ignore
        }
        catch (ModelException e)
        {
            // ignore
        }
        return stateMachine;
    }

    /**
     * Load the image resource with the specified name, if any.  Any exceptions thrown will be
     * ignored.
     *
     * @param cls class
     * @param name name
     * @return the image resource with the specified name, or <code>null</code> if no such
     *    resource exists
     */
    protected static final <T> BufferedImage loadImage(final Class<T> cls, final String name)
    {
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(cls.getResource(name));
        }
        catch (IOException e)
        {
            // ignore
        }
        return image;
    }
}