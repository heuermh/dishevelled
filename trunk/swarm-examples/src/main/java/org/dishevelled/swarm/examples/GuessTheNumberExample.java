/*

    dsh-swarm-examples  Examples for the swarm library.
    Copyright (c) 2007-2008 held jointly by the individual authors.

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

import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;
import org.dishevelled.commandline.Usage;

import org.dishevelled.commandline.argument.DoubleArgument;
import org.dishevelled.commandline.argument.IntegerArgument;

import org.dishevelled.swarm.ExitStrategy;
import org.dishevelled.swarm.Fitness;
import org.dishevelled.swarm.Particle;
import org.dishevelled.swarm.ParticleSwarm;
import org.dishevelled.swarm.ParticleSwarmOptimizationAlgorithm;
import org.dishevelled.swarm.ParticleSwarmOptimizationAlgorithmAdapter;
import org.dishevelled.swarm.ParticleSwarmOptimizationAlgorithmEvent;
import org.dishevelled.swarm.ParticleSwarmOptimizationAlgorithmListener;

import org.dishevelled.swarm.exit.FitnessThresholdExitStrategy;

/**
 * Guess the number example.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class GuessTheNumberExample
    extends AbstractSwarmExample
    implements Runnable
{
    /** Number of particles. */
    private int particles;

    /** Number to guess. */
    private double number;

    /** Default number of particles. */
    private static final int DEFAULT_PARTICLES = 100;

    /** Default number to guess. */
    private static final double DEFAULT_NUMBER = 0.5d;

    /** Particles argument. */
    private final IntegerArgument particlesArgument = new IntegerArgument("p", "particles", "particles", true);

    /** Number argument. */
    private final DoubleArgument numberArgument = new DoubleArgument("n", "number", "number to guess", true);

    /** Fitness. */
    private final Fitness fitness = new Fitness()
        {
            /** {@inheritDoc} */
            public double score(final double[] position)
            {
                double d = Math.abs(position[0] - number);
                return (1 - d);
            }
        };

    /** Exit strategy. */
    private final ExitStrategy exit = new FitnessThresholdExitStrategy(0.99d);

    /** Listener. */
    private final ParticleSwarmOptimizationAlgorithmListener listener = new Listener();


    /**
     * Create a new guess the number example.
     */
    public GuessTheNumberExample()
    {
        super();
        particles = DEFAULT_PARTICLES;
        number = DEFAULT_NUMBER;
        getArgumentList().add(particlesArgument);
        getArgumentList().add(numberArgument);
    }


    /** {@inheritDoc} */
    public void initialize(final CommandLine commandLine) throws CommandLineParseException
    {
        super.initialize(commandLine);
        CommandLineParser.parse(commandLine, getArgumentList());
        particles = particlesArgument.getValue();
        number = numberArgument.getValue();
    }

    /** {@inheritDoc} */
    public void run()
    {
        ParticleSwarmOptimizationAlgorithm algorithm = getAlgorithm();
        algorithm.addParticleSwarmOptimizationAlgorithmListener(listener);
        algorithm.optimize(particles, 1, exit, fitness);
        algorithm.removeParticleSwarmOptimizationAlgorithmListener(listener);
    }

    /**
     * Listener.
     */
    private class Listener
        extends ParticleSwarmOptimizationAlgorithmAdapter
    {

        /** {@inheritDoc} */
        public void exitSucceeded(final ParticleSwarmOptimizationAlgorithmEvent event)
        {
            ParticleSwarm swarm = event.getParticleSwarm();

            double maxFitness = Double.MIN_VALUE;
            double guess = 0.0d;
            for (Particle particle : swarm)
            {
                double f = particle.getFitness();
                if (f > maxFitness)
                {
                    guess = particle.getPosition()[0];
                    maxFitness = f;
                }
            }
            System.out.println("number=" + number + ", best guess=" + guess + ", epoch=" + event.getEpoch());
        }
    }


    /**
     * Main.
     *
     * @param args command-line arguments
     */
    public static void main(final String[] args)
    {
        CommandLine commandLine = new CommandLine(args);
        GuessTheNumberExample example = new GuessTheNumberExample();
        try
        {
            example.initialize(commandLine);
            example.run();
        }
        catch (CommandLineParseException e)
        {
            Usage.usage("Guess the number example", e, commandLine, example.getArgumentList(), System.err);
        }
    }
}
