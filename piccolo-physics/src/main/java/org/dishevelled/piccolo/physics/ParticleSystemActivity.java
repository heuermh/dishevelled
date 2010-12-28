/*

    dsh-piccolo-physics  Piccolo2D particle system physics integration.
    Copyright (c) 2009-2010 held jointly by the individual authors.

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
package org.dishevelled.piccolo.physics;

import java.awt.geom.Point2D;

import java.util.HashMap;
import java.util.Map;

import org.piccolo2d.PNode;

import org.piccolo2d.activities.PActivity;

import org.piccolo2d.util.PBounds;
import org.piccolo2d.util.PUtil;

import org.dishevelled.multimap.BinaryKeyMap;

import org.dishevelled.multimap.impl.HashedBinaryKeyMap;

import traer.physics.Attraction;
import traer.physics.Particle;
import traer.physics.ParticleSystem;
import traer.physics.Spring;

/**
 * Particle system activity.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class ParticleSystemActivity
    extends PActivity
{
    /** Particle system for this particle system activity. */
    private final ParticleSystem particleSystem;

    /** Map of attractions keyed by source and target nodes. */
    private final BinaryKeyMap<PNode, PNode, Attraction> attractions;

    /** Map of particles keyed by node. */
    private final Map<PNode, Particle> particles;

    /** Map of springs keyed by source and target nodes. */
    private final BinaryKeyMap<PNode, PNode, Spring> springs;


    /**
     * Create a new particle system activity with the specified duration in milliseconds.
     *
     * @param duration duration in milliseconds, or <code>-1</code> ms for an infinite duration
     */
    public ParticleSystemActivity(final long duration)
    {
        this(duration, PUtil.DEFAULT_ACTIVITY_STEP_RATE, System.currentTimeMillis());
    }

    /**
     * Create a new particle system activity with the specified duration, step rate, and start time in milliseconds.
     *
     * @param duration duration in milliseconds, or <code>-1</code> ms for an infinite duration
     * @param stepRate step rate in milliseconds
     * @param startTime start time in milliseconds
     */
    public ParticleSystemActivity(final long duration, final long stepRate, final long startTime)
    {
        super(duration, stepRate, startTime);
        particleSystem = new ParticleSystem();
        attractions = new HashedBinaryKeyMap<PNode, PNode, Attraction>();
        particles = new HashMap<PNode, Particle>();
        springs = new HashedBinaryKeyMap<PNode, PNode, Spring>();
    }


    /**
     * Set the drag force for the particle system to <code>drag</code>.
     *
     * @param drag drag force
     */
    public void setDrag(final float drag)
    {
        particleSystem.setDrag(drag);
    }

    /**
     * Set the strength of gravity for the particle system to <code>gravity</code>.
     *
     * @param gravity strength of gravity
     */
    public void setGravity(final float gravity)
    {
        particleSystem.setGravity(gravity);
    }

    /**
     * Create a new particle for the specified node with the specified mass.
     *
     * @param node node, must not be null
     * @param mass particle mass
     */
    public void createParticle(final PNode node, final float mass)
    {
        if (node == null)
        {
            throw new IllegalArgumentException("node must not be null");
        }
        PBounds fullBounds = node.getFullBoundsReference();
        float x = (float) fullBounds.getX();
        float y = (float) fullBounds.getY();
        Particle particle = particleSystem.makeParticle(mass, x, y, 1.0f);
        particles.put(node, particle);
    }

    /**
     * Return the velocity for particle associated with the specified node.
     * A particle must have already been created for the specified node.
     *
     * @param node node, must not be null
     * @return the velocity for particle associated with the specified node
     */
    public Point2D getVelocity(final PNode node)
    {
        return getVelocity(node, new Point2D.Float(0.0f, 0.0f));
    }

    /**
     * Return the specified velocity, set to the velocity for particle associated with the specified node.
     * A particle must have already been created for the specified node.
     *
     * @param node node, must not be null
     * @param velocity velocity, must not be null
     * @return the specified velocity, set to the velocity for particle associated with
     *    the specified node
     */
    public Point2D getVelocity(final PNode node, final Point2D velocity)
    {
        checkParticleArgs(node);
        if (velocity == null)
        {
            throw new IllegalArgumentException("velocity must not be null");
        }
        Particle particle = particles.get(node);
        velocity.setLocation(particle.velocity().x(), particle.velocity().y());
        return velocity;
    }

    /**
     * Set the velocity for the particle associated with the specified node
     * to <code>[x, y]</code>.  A particle must have already been created
     * for the specified node.
     *
     * @param node node, must not be null
     * @param x velocity x
     * @param y velocity y
     */
    public void setVelocity(final PNode node, final float x, final float y)
    {
        checkParticleArgs(node);
        particles.get(node).velocity().set(x, y, 0.0f);
    }

    /**
     * Set the velocity for the particle associated with the specified node
     * to <code>velocity</code>.  A particle must have already been created
     * for the specified node.
     *
     * @param node node, must not be null
     * @param velocity velocity, must not be null
     */
    public void setVelocity(final PNode node, final Point2D velocity)
    {
        checkParticleArgs(node);
        if (velocity == null)
        {
            throw new IllegalArgumentException("velocity must not be null");
        }
        particles.get(node).velocity().set((float) velocity.getX(), (float) velocity.getY(), 0.0f);
    }

    /**
     * Return the mass of the particle associated with the specified node.  A particle
     * must have already been created for the specified node.
     *
     * @param node node, must not be null
     * @return the mass of the particle associated with the specified node
     */
    public float getParticleMass(final PNode node)
    {
        checkParticleArgs(node);
        return particles.get(node).mass();
    }

    /**
     * Set the mass of the particle associated with the specified node to <code>mass</code>.
     * A particle must have already been created for the specified node.
     *
     * @param node node, must not be null
     * @param mass particle mass
     */
    public void setParticleMass(final PNode node, final float mass)
    {
        checkParticleArgs(node);
        particles.get(node).setMass(mass);
    }

    /**
     * Clamp the velocity for the particle associated with the specified node
     * to <code>[0.0f, 0.0f]</code>.  A particle must have already been created
     * for the specified node.  A clamped particle will receive updated position
     * data from the full bounds of the specified node.
     *
     * @param node node, must not be null
     */
    public void clamp(final PNode node)
    {
        checkParticleArgs(node);
        particles.get(node).makeFixed();
    }

    /**
     * Release or unclamp the velocity for the particle associated with the specified node.
     * A particle must have already been created for the specified node.
     *
     * @param node node, must not be null
     */
    public void release(final PNode node)
    {
        checkParticleArgs(node);
        particles.get(node).makeFree();
    }

    /**
     * Create a new spring between the specified source and target nodes with the
     * specified strength, damping factor, and rest length.  A particle must have already
     * been created for both the specified source and target nodes.
     *
     * @param source source node, must not be null
     * @param target target node, must not be null
     * @param strength spring strength
     * @param damping damping factor
     * @param restLength rest length
     */
    public void createSpring(final PNode source,
                             final PNode target,
                             final float strength,
                             final float damping,
                             final float restLength)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source node must not be null");
        }
        if (target == null)
        {
            throw new IllegalArgumentException("target node must not be null");
        }
        if (!particles.containsKey(source))
        {
            throw new IllegalArgumentException("no particle exists for source node " + source);
        }
        if (!particles.containsKey(target))
        {
            throw new IllegalArgumentException("no particle exists for target node " + target);
        }
        Spring spring = particleSystem.makeSpring(particles.get(source),
                                                  particles.get(target),
                                                  strength,
                                                  damping,
                                                  restLength);
        springs.put(source, target, spring);
    }

    /**
     * Enable the spring between the specified source and target nodes.  A spring
     * must have already been created for the specified source and target nodes.
     *
     * @param source source node, must not be null
     * @param target target node, must not be null
     */
    public void enableSpring(final PNode source, final PNode target)
    {
        checkSpringArgs(source, target);
        springs.get(source, target).turnOn();
    }

    /**
     * Disable the spring between the specified source and target nodes.  A spring
     * must have already been created for the specified source and target nodes.
     *
     * @param source source node, must not be null
     * @param target target node, must not be null
     */
    public void disableSpring(final PNode source, final PNode target)
    {
        checkSpringArgs(source, target);
        springs.get(source, target).turnOff();
    }

    /**
     * Return the rest length for the spring between the specified source and target nodes.
     * A spring must have already been created for the specified source and target nodes.
     *
     * @param source source node, must not be null
     * @param target target node, must not be null
     * @return the rest length for the spring between the specified source and target nodes
     */
    public float getSpringRestLength(final PNode source, final PNode target)
    {
        checkSpringArgs(source, target);
        return springs.get(source, target).restLength();
    }

    /**
     * Set the rest length for the spring between the specified source and target nodes to <code>restLength</code>.
     * A spring must have already been created for the specified source and target nodes.
     *
     * @param source source node, must not be null
     * @param target target node, must not be null
     * @param restLength rest length
     */
    public void setSpringRestLength(final PNode source, final PNode target, final float restLength)
    {
        checkSpringArgs(source, target);
        springs.get(source, target).setRestLength(restLength);
    }

    /**
     * Return the strength of the spring between the specified source and target nodes.
     * A spring must have already been created for the specified source and target nodes.
     *
     * @param source source node, must not be null
     * @param target target node, must not be null
     * @return the strength of the spring between the specified source and target nodes
     */
    public float getSpringStrength(final PNode source, final PNode target)
    {
        checkSpringArgs(source, target);
        return springs.get(source, target).strength();
    }

    /**
     * Set the strength of the spring between the specified source and target nodes to <code>strength</code>.
     * A spring must have already been created for the specified source and target nodes.
     *
     * @param source source node, must not be null
     * @param target target node, must not be null
     * @param strength spring strength
     */
    public void setSpringStrength(final PNode source, final PNode target, final float strength)
    {
        checkSpringArgs(source, target);
        springs.get(source, target).setStrength(strength);
    }

    /**
     * Return the damping factor for the spring between the specified source and target nodes.
     * A spring must have already been created for the specified source and target nodes.
     *
     * @param source source node, must not be null
     * @param target target node, must not be null
     * @return the damping factor for the spring between the specified source and target nodes
     */
    public float getSpringDamping(final PNode source, final PNode target)
    {
        checkSpringArgs(source, target);
        return springs.get(source, target).damping();
    }

    /**
     * Set the damping factor for the spring between the specified source and target nodes to
     * <code>dampingFactor</code>.  A spring must have already been created for the specified
     * source and target nodes.
     *
     * @param source source node, must not be null
     * @param target target node, must not be null
     * @param damping damping factor
     */
    public void setSpringDamping(final PNode source, final PNode target, final float damping)
    {
        checkSpringArgs(source, target);
        springs.get(source, target).setDamping(damping);
    }

    /**
     * Create a new attraction (or repulsion) force between the specified source and target
     * nodes with the specified strength and minimum distance.  A particle must have already
     * been created for both the specified source and target nodes.
     *
     * @param source source node, must not be null
     * @param target target node, must not be null
     * @param strength attraction (or repulsion) force strength
     * @param minimumDistance minimum distance
     */
    public void createAttraction(final PNode source,
                                 final PNode target,
                                 final float strength,
                                 final float minimumDistance)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source node must not be null");
        }
        if (target == null)
        {
            throw new IllegalArgumentException("target node must not be null");
        }
        if (!particles.containsKey(source))
        {
            throw new IllegalArgumentException("no particle exists for source node " + source);
        }
        if (!particles.containsKey(target))
        {
            throw new IllegalArgumentException("no particle exists for target node " + target);
        }
        Attraction attraction = particleSystem.makeAttraction(particles.get(source),
                                                              particles.get(target),
                                                              strength,
                                                              minimumDistance);
        attractions.put(source, target, attraction);
    }

    /**
     * Enable the attraction between the specified source and target nodes.  An attraction
     * must have already been created for the specified source and target nodes.
     *
     * @param source source node, must not be null
     * @param target target node, must not be null
     */
    public void enableAttraction(final PNode source, final PNode target)
    {
        checkAttractionArgs(source, target);
        attractions.get(source, target).turnOn();
    }

    /**
     * Disable the attraction between the specified source and target nodes.  An attraction
     * must have already been created for the specified source and target nodes.
     *
     * @param source source node, must not be null
     * @param target target node, must not be null
     */
    public void disableAttraction(final PNode source, final PNode target)
    {
        checkAttractionArgs(source, target);
        attractions.get(source, target).turnOff();
    }

    /**
     * Return the strength of the attraction between the specified source and target nodes.  An attraction
     * must have already been created for the specified source and target nodes.
     *
     * @param source source node, must not be null
     * @param target target node, must not be null
     * @return the strength of the attraction between the specified source and target nodes
     */
    public float getAttractionStrength(final PNode source, final PNode target)
    {
        checkAttractionArgs(source, target);
        return attractions.get(source, target).getStrength();
    }

    /**
     * Set the strength of the attraction between the specified source and target nodes to <code>strength</code>.
     * An attraction must have already been created for the specified source and target nodes.
     *
     * @param source source node, must not be null
     * @param target target node, must not be null
     * @param strength attraction (or repulsion) force strength
     */
    public void setAttractionStrength(final PNode source, final PNode target, final float strength)
    {
        checkAttractionArgs(source, target);
        attractions.get(source, target).setStrength(strength);
    }

    /**
     * Return the minimum distance for the attraction between the specified source and target nodes.  An attraction
     * must have already been created for the specified source and target nodes.
     *
     * @param source source node, must not be null
     * @param target target node, must not be null
     * @return the minimum distance for the attraction between the specified source and target nodes
     */
    public float getAttractionMinimumDistance(final PNode source, final PNode target)
    {
        checkAttractionArgs(source, target);
        return attractions.get(source, target).getMinimumDistance();
    }

    /**
     * Set the minimum distance for the attraction between the specified source and target nodes
     * to <code>minimumDistance</code>.  An attraction must have already been created for the
     * specified source and target nodes.
     *
     * @param source source node, must not be null
     * @param target target node, must not be null
     * @param minimumDistance minimum distance
     */
    public void setAttractionMinimumDistance(final PNode source, final PNode target, final float minimumDistance)
    {
        checkAttractionArgs(source, target);
        attractions.get(source, target).setMinimumDistance(minimumDistance);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * If subclasses override this method, they must call <code>super.activityFinished()</code>.
     * </p>
     */
    protected void activityStep(final long elapsedTime)
    {
        super.activityStep(elapsedTime);
        particleSystem.tick();
        for (Map.Entry<PNode, Particle> entry : particles.entrySet())
        {
            PNode node = entry.getKey();
            Particle particle = entry.getValue();
            // todo:  use local bounds or full bounds?
            if (particle.isFree())
            {
                node.setOffset(particle.position().x(), particle.position().y());
            }
            else
            {
                PBounds fullBounds = node.getFullBoundsReference();
                particle.position().set((float) fullBounds.getX(), (float) fullBounds.getY(), 0.0f);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * If subclasses override this method, they must call <code>super.activityFinished()</code>.
     * </p>
     */
    protected void activityFinished()
    {
        super.activityFinished();
        attractions.clear();
        particles.clear();
        springs.clear();
        particleSystem.clear();
    }


    /**
     * Check the specified particle arguments are valid.  Throws an {@link IllegalArgumentException}
     * if the specified node is null or if no particle exists for the specified node.
     *
     * @param node node, must not be null
     */
    private void checkParticleArgs(final PNode node)
    {
        if (node == null)
        {
            throw new IllegalArgumentException("node must not be null");
        }
        Particle particle = particles.get(node);
        if (particle == null)
        {
            throw new IllegalArgumentException("no particle exists for node " + node);
        }
    }

    /**
     * Check the specified spring arguments are valid.  Throws an {@link IllegalArgumentException}
     * if either the specified nodes are null or if no spring exists for the specified source and target
     * nodes.
     *
     * @param source source node, must not be null
     * @param target target node, must not be null
     */
    private void checkSpringArgs(final PNode source, final PNode target)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source node must not be null");
        }
        if (target == null)
        {
            throw new IllegalArgumentException("target node must not be null");
        }
        if (!springs.containsKey(source, target))
        {
            throw new IllegalArgumentException("no spring exists between source node "
                                               + source + " and target node " + target);
        }
    }

    /**
     * Check the specified attraction arguments are valid.  Throws an {@link IllegalArgumentException}
     * if either the specified nodes are null or if no attraction exists for the specified source and target
     * nodes.
     *
     * @param source source node, must not be null
     * @param target target node, must not be null
     */
    private void checkAttractionArgs(final PNode source, final PNode target)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source node must not be null");
        }
        if (target == null)
        {
            throw new IllegalArgumentException("target node must not be null");
        }
        if (!attractions.containsKey(source, target))
        {
            throw new IllegalArgumentException("no attraction exists between source node "
                                               + source + " and target node " + target);
        }
    }
}