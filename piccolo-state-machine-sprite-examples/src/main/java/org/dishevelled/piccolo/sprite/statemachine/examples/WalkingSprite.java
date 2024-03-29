/*

    dsh-piccolo-state-machine-sprite-examples  Piccolo2D state machine sprite node examples.
    Copyright (c) 2009-2013 held jointly by the individual authors.

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
package org.dishevelled.piccolo.sprite.statemachine.examples;

import java.awt.Image;

import java.awt.image.BufferedImage;

import java.util.List;

import org.piccolo2d.event.PBasicInputEventHandler;
import org.piccolo2d.event.PInputEvent;

import org.piccolo2d.activities.PActivity;

import org.apache.commons.scxml.model.SCXML;

import org.dishevelled.piccolo.sprite.Animation;
import org.dishevelled.piccolo.sprite.Animations;

import org.dishevelled.piccolo.sprite.statemachine.AbstractStateMachineSprite;

/**
 * Walking state machine sprite.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class WalkingSprite
    extends AbstractStateMachineSprite
{
    /** State machine. */
    private static final SCXML STATE_MACHINE = loadStateMachine(WalkingSprite.class, "stateMachine.xml");

    /** Sprite sheet. */
    private static final BufferedImage SPRITE_SHEET = loadImage(WalkingSprite.class, "weakGreenSoldierLeft.png");

    /** Normal animation frames. */
    private static final List<Image> NORMAL = Animations.createFrameList(SPRITE_SHEET, 0, 28, 18, 28, 4);

    /** Walking animation frames. */
    private static final List<Image> WALKING = Animations.createFrameList(SPRITE_SHEET, 0, 0, 18, 28, 2);

    /** Walking activity. */
    private final PActivity walkingActivity = new WalkingActivity();


    /**
     * Create a new walking state machine sprite.
     */
    public WalkingSprite()
    {
        super();
        initializeStateMachine(STATE_MACHINE);

        addInputEventListener(new PBasicInputEventHandler()
            {
                @Override
                public void mousePressed(final PInputEvent event)
                {
                    stop();
                }
            });
    }


    @Override
    protected Animation createAnimation(final String id)
    {
        if ("normal".equals(id))
        {
            return Animations.createLoopedAnimation(NORMAL);
        }
        else if ("walking".equals(id))
        {
            return Animations.createLoopedAnimation(WALKING);
        }
        return null;
    }

    /**
     * Walk.
     */
    public void walk()
    {
        fireStateMachineEvent("walk");
    }

    /**
     * Stop.
     */
    public void stop()
    {
        fireStateMachineEvent("stop");
    }

    /**
     * Normal state.
     */
    private void normal()
    {
        if (walkingActivity.isStepping())
        {
            walkingActivity.terminate(PActivity.TERMINATE_WITHOUT_FINISHING);
        }
    }

    /**
     * Walking state.
     */
    private void walking()
    {
        addActivity(walkingActivity);
    }

    /**
     * Walking activity.
     */
    private class WalkingActivity
        extends PActivity
    {

        /**
         * Create a new walking activity.
         */
        WalkingActivity()
        {
            super(-1, (long) (1000/24));
        }


        @Override
        protected void activityStep(final long elapsedTime)
        {
            offset(2.0d, 0.0d);
        }
    }
}
