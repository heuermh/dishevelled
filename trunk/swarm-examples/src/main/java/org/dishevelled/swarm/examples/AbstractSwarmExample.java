/*

    dsh-swarm-examples  Examples for the swarm library.
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
package org.dishevelled.swarm.examples;

import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;
import org.dishevelled.commandline.Switch;

import org.dishevelled.commandline.argument.DoubleArgument;

import org.dishevelled.swarm.ParticleSwarmOptimizationAlgorithm;
import org.dishevelled.swarm.ParticleSwarmOptimizationAlgorithmEvent;
import org.dishevelled.swarm.ParticleSwarmOptimizationAlgorithmListener;

/**
 * Abstract swarm example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public abstract class AbstractSwarmExample
{
    /** Algorithm. */
    private final ParticleSwarmOptimizationAlgorithm algorithm;

    /** Inertia weight argument. */
    private final DoubleArgument inertiaWeight;

    /** Cognitive weight argument. */
    private final DoubleArgument cognitiveWeight;

    /** Social weight argument. */
    private final DoubleArgument socialWeight;

    /** Minimum position argument. */
    private final DoubleArgument minimumPosition;

    /** Maximum position argument. */
    private final DoubleArgument maximumPosition;

    /** Minimum velocity argument. */
    private final DoubleArgument minimumVelocity;

    /** Maximum velocity argument. */
    private final DoubleArgument maximumVelocity;

    /** Verbose switch. */
    private final Switch verbose;

    /** Verbose listener. */
    private ParticleSwarmOptimizationAlgorithmListener verboseListener;

    /** Argument list. */
    private final ArgumentList arguments;


    /**
     * Create a new abstract swarm example.
     */
    protected AbstractSwarmExample()
    {
        algorithm = new ParticleSwarmOptimizationAlgorithm();

        inertiaWeight = new DoubleArgument("i", "inertia-weight", "inertia weight", false);
        cognitiveWeight = new DoubleArgument("c", "cognitive-weight", "cognitive weight", false);
        socialWeight = new DoubleArgument("s", "social-weight", "social weight", false);
        minimumPosition = new DoubleArgument("j", "minimum-position", "minimum position", false);
        maximumPosition = new DoubleArgument("k", "maximum-position", "maximum position", false);
        minimumVelocity = new DoubleArgument("l", "minimum-velocity", "minimum velocity", false);
        maximumVelocity = new DoubleArgument("m", "maximum-velocity", "maximum velocity", false);
        verbose = new Switch("v", "verbose", "verbose");

        arguments = new ArgumentList();
        arguments.add(inertiaWeight);
        arguments.add(cognitiveWeight);
        arguments.add(socialWeight);
        arguments.add(minimumPosition);
        arguments.add(maximumPosition);
        arguments.add(minimumVelocity);
        arguments.add(maximumVelocity);
        arguments.add(verbose);

        verboseListener = new VerboseListener();
    }


    /**
     * Return the particle swarm optimization algorithm for this abstract swarm example.
     *
     * @return the particle swarm optimization algorithm for this abstract swarm example
     */
    protected ParticleSwarmOptimizationAlgorithm getAlgorithm()
    {
        return algorithm;
    }

    /**
     * Return the argument list for this abstract swarm example.
     *
     * @return the argument list for this abstract swarm example
     */
    public ArgumentList getArgumentList()
    {
        return arguments;
    }

    /**
     * Initialize the particle swarm optimization algorithm for this
     * example with the specified command line.
     *
     * @param commandLine command line
     * @throws CommandLineParseException if an error occurs
     */
    public void initialize(final CommandLine commandLine) throws CommandLineParseException
    {
        CommandLineParser.parse(commandLine, arguments);

        if (inertiaWeight.wasFound())
        {
            algorithm.setInertiaWeight(inertiaWeight.getValue());
        }
        if (cognitiveWeight.wasFound())
        {
            algorithm.setCognitiveWeight(cognitiveWeight.getValue());
        }
        if (socialWeight.wasFound())
        {
            algorithm.setSocialWeight(socialWeight.getValue());
        }
        if (minimumPosition.wasFound())
        {
            algorithm.setMinimumPosition(minimumPosition.getValue());
        }
        if (maximumPosition.wasFound())
        {
            algorithm.setMaximumPosition(maximumPosition.getValue());
        }
        if (minimumVelocity.wasFound())
        {
            algorithm.setMinimumVelocity(minimumVelocity.getValue());
        }
        if (maximumVelocity.wasFound())
        {
            algorithm.setMaximumVelocity(maximumVelocity.getValue());
        }
        if (verbose.wasFound())
        {
            algorithm.addParticleSwarmOptimizationAlgorithmListener(verboseListener);
        }
        else
        {
            algorithm.removeParticleSwarmOptimizationAlgorithmListener(verboseListener);
        }
    }

    /**
     * Verbose listener.
     */
    private static class VerboseListener
        implements ParticleSwarmOptimizationAlgorithmListener
    {

        /** {@inheritDoc} */
        public void exitFailed(final ParticleSwarmOptimizationAlgorithmEvent event)
        {
            System.out.println("exitFailed, epoch=" + event.getEpoch());
        }

        /** {@inheritDoc} */
        public void exitSucceeded(final ParticleSwarmOptimizationAlgorithmEvent event)
        {
            System.out.println("exitSucceeded, epoch=" + event.getEpoch());
        }

        /** {@inheritDoc} */
        public void cognitiveMemoryUpdated(final ParticleSwarmOptimizationAlgorithmEvent event)
        {
            System.out.println("cognitiveMemoryUpdated");
        }

        /** {@inheritDoc} */
        public void fitnessCalculated(final ParticleSwarmOptimizationAlgorithmEvent event)
        {
            System.out.println("fitnessCalculated, fitness=" + event.getFitness());
        }

        /** {@inheritDoc} */
        public void positionUpdated(final ParticleSwarmOptimizationAlgorithmEvent event)
        {
            System.out.println("positionUpdated");
        }

        /** {@inheritDoc} */
        public void socialMemoryUpdated(final ParticleSwarmOptimizationAlgorithmEvent event)
        {
            System.out.println("socialMemoryUpdated");
        }

        /** {@inheritDoc} */
        public void velocityCalculated(final ParticleSwarmOptimizationAlgorithmEvent event)
        {
            System.out.println("velocityCalculated");
        }
    }
}
