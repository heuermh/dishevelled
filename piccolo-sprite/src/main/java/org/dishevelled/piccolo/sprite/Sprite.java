/*

    dsh-piccolo-sprite  Piccolo sprite nodes and supporting classes.
    Copyright (c) 2006-2007 held jointly by the individual authors.

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
package org.dishevelled.piccolo.sprite;

import java.awt.Image;
import java.awt.Graphics2D;

import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import edu.umd.cs.piccolo.PNode;

import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PPaintContext;

/**
 * Piccolo sprite node.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class Sprite
    extends PNode
{
    /** The current animation for this piccolo sprite node. */
    private Animation currentAnimation;

    /** The set of animations for this piccolo sprite node. */
    private final Set<Animation> animations;


    /**
     * Create a new piccolo sprite node from the specified required parameters.
     *
     * <p>The specified animation must be contained in the set of animations for this piccolo
     * sprite node.</p>
     *
     * <p>The specified set of animations must contain at least one animation.
     * The animations in <code>animations</code> are copied defensively
     * into this class.</p>
     *
     * @param currentAnimation current animation for this piccolo sprite node, must not be null
     *    and must be contained in the set of animations for this piccolo sprite node
     * @param animations set of animations, must not be null and must
     *    contain at least one animation
     *
     * @throws IllegalArgumentException if <code>animations.size() &lt; 1</code>
     */
    public Sprite(final Animation currentAnimation,
                  final Set<Animation> animations)
    {
        super();

        if (currentAnimation == null)
        {
            throw new IllegalArgumentException("currentAnimation must not be null");
        }
        if (animations == null)
        {
            throw new IllegalArgumentException("animations must not be null");
        }
        if (animations.size() < 1)
        {
            throw new IllegalArgumentException("animations must contain at least one animation");
        }
        this.animations = new HashSet<Animation>(animations);
        setCurrentAnimation(currentAnimation);
    }


    /**
     * Advance this piccolo sprite node one frame.
     */
    public final void advance()
    {
        // advance the current animation
        currentAnimation.advance();
        // and schedule a repaint
        // todo:
        //    -- advance() returns a boolean if the animation requires a repaint
        repaint();
    }

    /**
     * Return the current animation for this piccolo sprite node.
     * The current animation will not be null.
     *
     * @return the current animation for this piccolo sprite node
     */
    public final Animation getCurrentAnimation()
    {
        return currentAnimation;
    }

    /**
     * Set the current animation for this piccolo sprite node to <code>currentAnimation</code>.
     * The specified animation must be contained in the set of animations for this piccolo
     * sprite node.
     *
     * <p>This is a bound property.</p>
     *
     * @see #getAnimations
     * @param currentAnimation current animation for this piccolo sprite node, must not be null
     *    and must be contained in the set of animations for this piccolo sprite node
     */
    public final void setCurrentAnimation(final Animation currentAnimation)
    {
        if (currentAnimation == null)
        {
            throw new IllegalArgumentException("currentAnimation must not be null");
        }
        if (!animations.contains(currentAnimation))
        {
            throw new IllegalArgumentException("currentAnimation must be contained in animations");
        }
        Animation oldCurrentAnimation = this.currentAnimation;
        this.currentAnimation = currentAnimation;
        firePropertyChange(-1, "currentAnimation", oldCurrentAnimation, currentAnimation);
    }

    /**
     * Return an unmodifiable set of animations for this piccolo sprite node.  The returned
     * set will not be null and will contain at least one animation.
     *
     * @return an unmodifiable set of animations for this piccolo sprite node
     */
    public final Set<Animation> getAnimations()
    {
        return Collections.unmodifiableSet(animations);
    }

    /**
     * Add the specified animation to the set of animations for this piccolo sprite node.
     * An exception may be thrown if the underlying set prevents <code>animation</code>
     * from being added.
     *
     * @param animation animation to add
     */
    public final void addAnimation(final Animation animation)
    {
        animations.add(animation);
    }

    /**
     * Remove the specified animation from the set of animations for this piccolo sprite node.
     * An exception may be thrown if the underlying set prevents <code>animation</code>
     * from being removed.
     *
     * @param animation animation to remove, must not be the current animation
     *    and must not be the last animation in the set of animations for this piccolo sprite node
     * @throws IllegalStateException if <code>animation</code> is the current
     *    animation or the last animation in the set of animations for this piccolo sprite node
     */
    public final void removeAnimation(final Animation animation)
    {
        if ((animations.size() == 1) && (animations.contains(animation)))
        {
            throw new IllegalStateException("must not remove the last animation from animations");
        }
        if (currentAnimation.equals(animation))
        {
            throw new IllegalStateException("must not remove the current animation");
        }
        animations.remove(animation);
    }

    /** {@inheritDoc} */
    public final void paint(final PPaintContext paintContext)
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
