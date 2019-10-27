/*

    dsh-piccolo-identify  Piccolo2D nodes for identifiable beans.
    Copyright (c) 2007-2019 held jointly by the individual authors.

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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.scxml.Context;
import org.apache.commons.scxml.ErrorReporter;
import org.apache.commons.scxml.Evaluator;
import org.apache.commons.scxml.EventDispatcher;
import org.apache.commons.scxml.SCXMLExecutor;
import org.apache.commons.scxml.SCXMLListener;
import org.apache.commons.scxml.TriggerEvent;

import org.apache.commons.scxml.env.SimpleDispatcher;

import org.apache.commons.scxml.env.jexl.JexlContext;
import org.apache.commons.scxml.env.jexl.JexlEvaluator;

import org.apache.commons.scxml.model.ModelException;
import org.apache.commons.scxml.model.SCXML;
import org.apache.commons.scxml.model.Transition;
import org.apache.commons.scxml.model.TransitionTarget;

/**
 * State machine support class, meant to be delegated to.  Provided
 * an instance of a state machine, this class manages a state machine
 * executor, allows delegators to fire state machine events, and responds
 * to transition entry events by invoking methods on the delegating class
 * via reflection.  Most methods on this class fail silently instead of
 * throwing state machine- or reflection-specific exceptions.
 *
 * @author  Michael Heuer
 */
final class StateMachineSupport
{
    /** Object to delegate to. */
    private final Object delegator;

    /** State machine executor for this support class. */
    private final SCXMLExecutor executor;


    /**
     * Create a new state machine support class to be delegated to
     * by the specified delegator with the specified state machine.
     *
     * @param delegator object delegating to this state machine support
     *    class, must not be null
     * @param stateMachine state machine for this support class, must
     *    not be null
     */
    StateMachineSupport(final Object delegator, final SCXML stateMachine)
    {
        if (delegator == null)
        {
            throw new IllegalArgumentException("delegator must not be null");
        }
        if (stateMachine == null)
        {
            throw new IllegalArgumentException("stateMachine must not be null");
        }
        this.delegator = delegator;

        Evaluator evaluator = new JexlEvaluator();
        EventDispatcher dispatcher = new SimpleDispatcher();
        ErrorReporter errorReporter = new NoopErrorReporter();
        Context rootContext = new JexlContext();
        SCXMLListener listener = new SCXMLListener()
            {
                @Override
                public void onEntry(final TransitionTarget entered)
                {
                    invoke(entered.getId());
                }

                @Override
                public void onExit(final TransitionTarget exited)
                {
                    // empty
                }

                @Override
                public void onTransition(final TransitionTarget to,
                                         final TransitionTarget from,
                                         final Transition transition)
                {
                    // empty
                }
            };

        executor = new SCXMLExecutor(evaluator, dispatcher, errorReporter);
        executor.setStateMachine(stateMachine);
        executor.setSuperStep(false);
        executor.setRootContext(rootContext);
        executor.addListener(stateMachine, listener);

        try
        {
            executor.go();
        }
        catch (ModelException e)
        {
            // ignore
        }
    }

    /**
     * Reset the state machine for this state machine support class to its
     * &quot;initial&quot; configuration (fails silently).
     */
    void resetStateMachine()
    {
        try
        {
            executor.reset();
        }
        catch (ModelException e)
        {
            // ignore
        }
    }

    /**
     * Fire an event with the specified event name on the state machine for
     * this state machine support class (fails silently).
     *
     * @param eventName event name, must not be null
     */
    void fireStateMachineEvent(final String eventName)
    {
        if (eventName == null)
        {
            throw new IllegalArgumentException("eventName must not be null");
        }
        try
        {
            TriggerEvent event = new TriggerEvent(eventName, TriggerEvent.SIGNAL_EVENT, null);
            executor.triggerEvents(new TriggerEvent[] { event });
        }
        catch (ModelException e)
        {
            // ignore
        }
    }

    /**
     * Invoke the no-argument method with the following name
     * on the object delegating to this state machine support class
     * (fails silently).
     *
     * @param methodName method name to invoke on the object
     *    delegating to this state machine support class
     */
    private void invoke(final String methodName)
    {
        try
        {
            Class<?> c = delegator.getClass();
            Method method = c.getDeclaredMethod(methodName, new Class[0]);
            method.setAccessible(true);
            method.invoke(delegator, new Object[0]);
        }
        catch (SecurityException se)
        {
            // ignore
        }
        catch (NoSuchMethodException nsme)
        {
            // ignore
        }
        catch (IllegalArgumentException iae)
        {
            // ignore
        }
        catch (IllegalAccessException iae)
        {
            // ignore
        }
        catch (InvocationTargetException ite)
        {
            // ignore
        }
    }

    /**
     * No-op error reporter.
     */
    private class NoopErrorReporter
        implements ErrorReporter
    {

        @Override
        public void onError(final String errorCode,
                            final String errorDetail,
                            final Object errorContext)
        {
            // empty
        }
    }
}
