/*

    dsh-piccolo-physics  Piccolo2D particle system physics integration.
    Copyright (c) 2009 held jointly by the individual authors.

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

import edu.umd.cs.piccolo.PNode;

import edu.umd.cs.piccolo.activities.PActivity;

import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PUtil;

//import org.dishevelled.multimap.BinaryKeyMap;

//import org.dishevelled.multimap.impl.BinaryKeyHashMap;

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
    //private final BinaryKeyMap<PNode, PNode, Attraction> attractions;

    /** Map of particles keyed by node. */
    private final Map<PNode, Particle> particles;

    /** Map of springs keyed by source and target nodes. */
    //private final BinaryKeyMap<PNode, PNode, Spring> springs;


    /**
     * Create a new particle system activity with the specified duration in milliseconds.
     *
     * @param duration duration in milliseconds, must be at least <code>1</code> ms
     */
    public ParticleSystemActivity(final long duration)
    {
        this(duration, PUtil.DEFAULT_ACTIVITY_STEP_RATE, System.currentTimeMillis());
    }

    /**
     * Create a new particle system activity with the specified duration, step rate, and start time in milliseconds.
     *
     * @param duration duration in milliseconds, must be at least <code>1</code> ms
     * @param stepRate step rate in milliseconds
     * @param startTime start time in milliseconds
     */
    public ParticleSystemActivity(final long duration, final long stepRate, final long startTime)
    {
        super(duration, stepRate, startTime);
        if (duration < 1L)
        {
            throw new IllegalArgumentException("duration must be at least 1 ms");
        }
        particleSystem = new ParticleSystem();
        //attractions = new BinaryKeyHashMap<PNode, PNode, Particle>();
        particles = new HashMap<PNode, Particle>();
        //springs = new BinaryKeyHashMap<PNode, PNode, Particle>();
    }


    // set integrator?

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
        if (node == null)
        {
            throw new IllegalArgumentException("node must not be null");
        }
        if (velocity == null)
        {
            throw new IllegalArgumentException("velocity must not be null");
        }
        Particle particle = particles.get(node);
        if (particle == null)
        {
            throw new IllegalArgumentException("no particle exists for node " + node);
        }
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
        if (node == null)
        {
            throw new IllegalArgumentException("node must not be null");
        }
        Particle particle = particles.get(node);
        if (particle == null)
        {
            throw new IllegalArgumentException("no particle exists for node " + node);
        }
        particle.velocity().set(x, y, 0.0f);
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
        if (node == null)
        {
            throw new IllegalArgumentException("node must not be null");
        }
        if (velocity == null)
        {
            throw new IllegalArgumentException("velocity must not be null");
        }
        Particle particle = particles.get(node);
        if (particle == null)
        {
            throw new IllegalArgumentException("no particle exists for node " + node);
        }
        particle.velocity().set((float) velocity.getX(), (float) velocity.getY(), 0.0f);
    }

    // todo:  fix/free, clamp/release, affix?

    /**
     * Clamp the velocity for the particle associated with the specified node
     * to <code>[0.0f, 0.0f]</code>.  A particle must have already been created
     * for the specified node.
     *
     * @param node node, must not be null
     */
    public void clamp(final PNode node)
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
        particle.makeFixed();
    }

    /**
     * Release or unclamp the velocity for the particle associated with the specified node.
     * A particle must have already been created for the specified node.
     *
     * @param node node, must not be null
     */
    public void release(final PNode node)
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
        particle.makeFree();
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
        //springs.put(source, target, spring);
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
        if (source == null)
        {
            throw new IllegalArgumentException("source node must not be null");
        }
        if (target == null)
        {
            throw new IllegalArgumentException("target node must not be null");
        }
        /*
        if (!springs.containsKey(source, target))
        {
            throw new IllegalArgumentException("no spring exists between source node "
            + source + " and target node " + target);
        }
        springs.get(source, target).turnOn();
        */
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
        if (source == null)
        {
            throw new IllegalArgumentException("source node must not be null");
        }
        if (target == null)
        {
            throw new IllegalArgumentException("target node must not be null");
        }
        /*
        if (!springs.containsKey(source, target))
        {
            throw new IllegalArgumentException("no spring exists between source node "
            + source + " and target node " + target);
        }
        springs.get(source, target).turnOff();
        */
    }

    /**
     * Create a new attraction (or replusion) force between the specified source and target
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
        //attractions.put(source, target, attraction);
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
        if (source == null)
        {
            throw new IllegalArgumentException("source node must not be null");
        }
        if (target == null)
        {
            throw new IllegalArgumentException("target node must not be null");
        }
        /*
        if (!attractions.containsKey(source, target))
        {
            throw new IllegalArgumentException("no attraction exists between source node "
            + source + " and target node " + target);
        }
        attractions.get(source, target).turnOn();
        */
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
        if (source == null)
        {
            throw new IllegalArgumentException("source node must not be null");
        }
        if (target == null)
        {
            throw new IllegalArgumentException("target node must not be null");
        }
        /*
        if (!attractions.containsKey(source, target))
        {
            throw new IllegalArgumentException("no attraction exists between source node "
            + source + " and target node " + target);
        }
        attractions.get(source, target).turnOff();
        */
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
        // todo:  is the call to super req'd?
        super.activityStep(elapsedTime);
        particleSystem.tick();
        for (Map.Entry<PNode, Particle> entry : particles.entrySet())
        {
            PNode node = entry.getKey();
            Particle particle = entry.getValue();
            node.setOffset(particle.position().x(), particle.position().y());
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
        // todo:  is the call to super req'd?
        super.activityFinished();
        //attractions.clear();
        particles.clear();
        //springs.clear();
        particleSystem.clear();
    }
}