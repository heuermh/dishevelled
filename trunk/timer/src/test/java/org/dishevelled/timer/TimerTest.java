/*

    dsh-timer  Timer with nanosecond resolution and summary statistics
    on recorded elapsed times.
    Copyright (c) 2004-2007 held jointly by the individual authors.

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
package org.dishevelled.timer;

import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

import junit.framework.TestCase;

/**
 * Unit test for Timer.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public class TimerTest
    extends TestCase
{

    public void testTimer()
    {
        Timer t = new Timer();
        assertNotNull("t not null", t);
        assertEquals("t size == 0", 0, t.size());
        assertEquals("t min == Double.POSITIVE_INFINITY", Double.POSITIVE_INFINITY, t.min());
        assertEquals("t max == Double.NEGATIVE_INFINITY", Double.NEGATIVE_INFINITY, t.max());
        assertEquals("t sum == 0.0d", 0.0d, t.sum());
        assertEquals("t mean == Double.NaN", Double.NaN, t.mean());
        assertEquals("t standardDeviation == Double.NaN", Double.NaN, t.standardDeviation());

        t.reset();
        assertEquals("t size == 0", 0, t.size());
        assertEquals("t min == Double.POSITIVE_INFINITY", Double.POSITIVE_INFINITY, t.min());
        assertEquals("t max == Double.NEGATIVE_INFINITY", Double.NEGATIVE_INFINITY, t.max());
        assertEquals("t sum == 0.0d", 0.0d, t.sum());
        assertEquals("t mean == Double.NaN", Double.NaN, t.mean());
        assertEquals("t standardDeviation == Double.NaN", Double.NaN, t.standardDeviation());

        t.start();

        try
        {
            Thread.sleep(100);
        }
        catch (InterruptedException e)
        {
            // empty
        }

        t.stop();
        assertEquals("t size == 1", 1, t.size());
        assertTrue("t min > 0.0d", t.min() > 0.0d);
        assertTrue("t max > 0.0d", t.max() > 0.0d);
        assertTrue("t sum > 0.0d", t.sum() > 0.0d);
        assertTrue("t mean > 0.0d", t.mean() > 0.0d);
        assertEquals("t standardDeviation == Double.NaN", Double.NaN, t.standardDeviation());

        t.reset();
        assertEquals("t size == 0", 0, t.size());
        assertEquals("t min == Double.POSITIVE_INFINITY", Double.POSITIVE_INFINITY, t.min());
        assertEquals("t max == Double.NEGATIVE_INFINITY", Double.NEGATIVE_INFINITY, t.max());
        assertEquals("t sum == 0.0d", 0.0d, t.sum());
        assertEquals("t mean == Double.NaN", Double.NaN, t.mean());
        assertEquals("t standardDeviation == Double.NaN", Double.NaN, t.standardDeviation());

        t.start();

        try
        {
            Thread.sleep(100);
        }
        catch (InterruptedException e)
        {
            // empty
        }

        t.stop();

        t.start();

        try
        {
            Thread.sleep(100);
        }
        catch (InterruptedException e)
        {
            // empty
        }

        t.stop();

        t.start();

        try
        {
            Thread.sleep(100);
        }
        catch (InterruptedException e)
        {
            // empty
        }

        t.stop();

        t.start();

        try
        {
            Thread.sleep(100);
        }
        catch (InterruptedException e)
        {
            // empty
        }

        t.stop();

        t.start();

        try
        {
            Thread.sleep(100);
        }
        catch (InterruptedException e)
        {
            // empty
        }

        t.stop();

        assertEquals("t size == 5", 5, t.size());
        assertTrue("t min > 0.0d", t.min() > 0.0d);
        assertTrue("t max > 0.0d", t.max() > 0.0d);
        assertTrue("t sum > 0.0d", t.sum() > 0.0d);
        assertTrue("t mean > 0.0d", t.mean() > 0.0d);
        assertTrue("t standardDeviation > 0.0d", t.standardDeviation() > 0.0d);

        t.reset();
        assertEquals("t size == 0", 0, t.size());
        assertEquals("t min == Double.POSITIVE_INFINITY", Double.POSITIVE_INFINITY, t.min());
        assertEquals("t max == Double.NEGATIVE_INFINITY", Double.NEGATIVE_INFINITY, t.max());
        assertEquals("t sum == 0.0d", 0.0d, t.sum());
        assertEquals("t mean == Double.NaN", Double.NaN, t.mean());
        assertEquals("t standardDeviation == Double.NaN", Double.NaN, t.standardDeviation());
    }

    public void testMultipleStop()
    {
        Timer t = new Timer();
        t.start();
        t.stop();
        t.stop();
        t.stop();
        assertEquals("t size == 3", 3, t.size());

        t.start();
        t.stop();
        t.stop();
        assertEquals("t size == 5", 5, t.size());

        t.reset();
        t.start();
        t.stop();
        t.stop();
        t.stop();
        assertEquals("t size == 3", 3, t.size());
    }

    public void testTimerException()
    {
        Timer t0 = new Timer();
        try
        {
            t0.stop();
            fail("t0.stop() without t0.start() expected TimerException");
        }
        catch (TimerException e)
        {
            // expected
        }

        Timer t1 = new Timer();
        t1.start();
        t1.stop();
        t1.reset();
        try
        {
            t1.stop();
            fail("t1.stop() without t1.start() after t1.reset() expected TimerException");
        }
        catch (TimerException e)
        {
            // expected
        }
    }

    Runnable r0 = new Runnable()
        {
            public void run()
            {
                try
                {
                    Thread.sleep(1);
                }
                catch (InterruptedException e)
                {
                    // empty
                }
            }
        };

    Runnable r1 = new Runnable()
        {
            public void run()
            {
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    // empty
                }
            }
        };

    Runnable r2 = new Runnable()
        {
            public void run()
            {
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    // empty
                }
            }
        };

    public void testTime()
    {
        Timer t0 = Timer.time(r0);
        Timer t1 = Timer.time(r1);
        Timer t2 = Timer.time(r2);

        assertNotNull(t0);
        assertNotNull(t1);
        assertNotNull(t2);

        assertEquals("t0 size == 1", 1, t0.size());
        assertTrue("t0 min > 0.0d", t0.min() > 0.0d);
        assertTrue("t0 max > 0.0d", t0.max() > 0.0d);
        assertTrue("t0 sum > 0.0d", t0.sum() > 0.0d);
        assertTrue("t0 mean > 0.0d", t0.mean() > 0.0d);
        assertEquals("t0 standardDeviation == Double.NaN", Double.NaN, t0.standardDeviation());

        assertEquals("t1 size == 1", 1, t1.size());
        assertTrue("t1 min > 0.0d", t1.min() > 0.0d);
        assertTrue("t1 max > 0.0d", t1.max() > 0.0d);
        assertTrue("t1 sum > 0.0d", t1.sum() > 0.0d);
        assertTrue("t1 mean > 0.0d", t1.mean() > 0.0d);
        assertEquals("t1 standardDeviation == Double.NaN", Double.NaN, t1.standardDeviation());

        assertEquals("t2 size == 1", 1, t2.size());
        assertTrue("t2 min > 0.0d", t2.min() > 0.0d);
        assertTrue("t2 max > 0.0d", t2.max() > 0.0d);
        assertTrue("t2 sum > 0.0d", t2.sum() > 0.0d);
        assertTrue("t2 mean > 0.0d", t2.mean() > 0.0d);
        assertEquals("t2 standardDeviation == Double.NaN", Double.NaN, t2.standardDeviation());

        Timer.time(r0, t0);
        Timer.time(r1, t1);
        Timer.time(r2, t2);

        assertEquals("t0 size == 2", 2, t0.size());
        assertTrue("t0 min > 0.0d", t0.min() > 0.0d);
        assertTrue("t0 max > 0.0d", t0.max() > 0.0d);
        assertTrue("t0 sum > 0.0d", t0.sum() > 0.0d);
        assertTrue("t0 mean > 0.0d", t0.mean() > 0.0d);
        assertTrue("t0 standardDeviation > 0.0d", t0.standardDeviation() > 0.0d);

        assertEquals("t1 size == 2", 2, t1.size());
        assertTrue("t1 min > 0.0d", t1.min() > 0.0d);
        assertTrue("t1 max > 0.0d", t1.max() > 0.0d);
        assertTrue("t1 sum > 0.0d", t1.sum() > 0.0d);
        assertTrue("t1 mean > 0.0d", t1.mean() > 0.0d);
        assertTrue("t1 standardDeviation > 0.0d", t1.standardDeviation() > 0.0d);

        assertEquals("t2 size == 2", 2, t2.size());
        assertTrue("t2 min > 0.0d", t2.min() > 0.0d);
        assertTrue("t2 max > 0.0d", t2.max() > 0.0d);
        assertTrue("t2 sum > 0.0d", t2.sum() > 0.0d);
        assertTrue("t2 mean > 0.0d", t2.mean() > 0.0d);
        assertTrue("t2 standardDeviation > 0.0d", t2.standardDeviation() > 0.0d);
    }

    public void testPrime()
    {
        Timer.prime(r0, 1000);
        Timer.prime(r1, 100);
        Timer.prime(r2, 10);

        List<Runnable> codeBlocks = Arrays.asList(new Runnable[] { r0, r1, r2 });

        Timer.prime(codeBlocks, 10);
        Timer.prime(codeBlocks, 100);
    }

    public void testLoop()
    {
        Timer t0 = Timer.loop(r0, 500);
        Timer t1 = Timer.loop(r1, 50);
        Timer t2 = Timer.loop(r2, 5);

        assertNotNull(t0);
        assertNotNull(t1);
        assertNotNull(t2);

        assertEquals("t0 size == 500", 500, t0.size());
        assertTrue("t0 min > 0.0d", t0.min() > 0.0d);
        assertTrue("t0 max > 0.0d", t0.max() > 0.0d);
        assertTrue("t0 sum > 0.0d", t0.sum() > 0.0d);
        assertTrue("t0 mean > 0.0d", t0.mean() > 0.0d);
        assertTrue("t0 standardDeviation > 0.0d", t0.standardDeviation() > 0.0d);

        assertEquals("t1 size == 50", 50, t1.size());
        assertTrue("t1 min > 0.0d", t1.min() > 0.0d);
        assertTrue("t1 max > 0.0d", t1.max() > 0.0d);
        assertTrue("t1 sum > 0.0d", t1.sum() > 0.0d);
        assertTrue("t1 mean > 0.0d", t1.mean() > 0.0d);
        assertTrue("t1 standardDeviation > 0.0d", t1.standardDeviation() > 0.0d);

        assertEquals("t2 size == 5", 5, t2.size());
        assertTrue("t2 min > 0.0d", t2.min() > 0.0d);
        assertTrue("t2 max > 0.0d", t2.max() > 0.0d);
        assertTrue("t2 sum > 0.0d", t2.sum() > 0.0d);
        assertTrue("t2 mean > 0.0d", t2.mean() > 0.0d);
        assertTrue("t2 standardDeviation > 0.0d", t2.standardDeviation() > 0.0d);

        Timer.loop(r0, 500, t0);
        Timer.loop(r1, 50, t1);
        Timer.loop(r2, 5, t2);

        assertEquals("t0 size == 1000", 1000, t0.size());
        assertEquals("t1 size == 100", 100, t1.size());
        assertEquals("t2 size == 10", 10, t2.size());
    }

    public void testLoopOver()
    {
        List<Runnable> codeBlocks = Arrays.asList(new Runnable[] { r0, r1, r2 });
        Map<Runnable,Timer> map0 = Timer.loop(codeBlocks, 10);

        assertNotNull("map0 not null", map0);
        assertEquals("map0 size == 3", 3, map0.size());
        assertTrue("map0 contains key r0", map0.containsKey(r0));
        assertTrue("map0 contains key r1", map0.containsKey(r1));
        assertTrue("map0 contains key r2", map0.containsKey(r2));
        assertNotNull("map0.get(r0) not null", map0.get(r0));
        assertNotNull("map0.get(r1) not null", map0.get(r1));
        assertNotNull("map0.get(r2) not null", map0.get(r2));

        Timer t0 = map0.get(r0);
        Timer t1 = map0.get(r1);
        Timer t2 = map0.get(r2);

        assertEquals("t0 size == 10", 10, t0.size());
        assertTrue("t0 min > 0.0d", t0.min() > 0.0d);
        assertTrue("t0 max > 0.0d", t0.max() > 0.0d);
        assertTrue("t0 sum > 0.0d", t0.sum() > 0.0d);
        assertTrue("t0 mean > 0.0d", t0.mean() > 0.0d);
        assertTrue("t0 standardDeviation > 0.0d", t0.standardDeviation() > 0.0d);

        assertEquals("t1 size == 10", 10, t1.size());
        assertTrue("t1 min > 0.0d", t1.min() > 0.0d);
        assertTrue("t1 max > 0.0d", t1.max() > 0.0d);
        assertTrue("t1 sum > 0.0d", t1.sum() > 0.0d);
        assertTrue("t1 mean > 0.0d", t1.mean() > 0.0d);
        assertTrue("t1 standardDeviation > 0.0d", t1.standardDeviation() > 0.0d);

        assertEquals("t2 size == 10", 10, t2.size());
        assertTrue("t2 min > 0.0d", t2.min() > 0.0d);
        assertTrue("t2 max > 0.0d", t2.max() > 0.0d);
        assertTrue("t2 sum > 0.0d", t2.sum() > 0.0d);
        assertTrue("t2 mean > 0.0d", t2.mean() > 0.0d);
        assertTrue("t2 standardDeviation > 0.0d", t2.standardDeviation() > 0.0d);

        // map0 should be unmodifiable
        try
        {
            map0.put(r0, t1);
            fail("map0 put expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        Map<Runnable,Timer> map1 = Timer.loop(codeBlocks, 2, 5);

        assertNotNull("map1 not null", map1);
        assertEquals("map1 size == 3", 3, map1.size());
        assertTrue("map1 contains key r0", map1.containsKey(r0));
        assertTrue("map1 contains key r1", map1.containsKey(r1));
        assertTrue("map1 contains key r2", map1.containsKey(r2));
        assertNotNull("map1.get(r0) not null", map1.get(r0));
        assertNotNull("map1.get(r1) not null", map1.get(r1));
        assertNotNull("map1.get(r2) not null", map1.get(r2));

        Timer t3 = map1.get(r0);
        Timer t4 = map1.get(r1);
        Timer t5 = map1.get(r2);

        assertEquals("t3 size == 10", 10, t3.size());
        assertTrue("t3 min > 0.0d", t3.min() > 0.0d);
        assertTrue("t3 max > 0.0d", t3.max() > 0.0d);
        assertTrue("t3 sum > 0.0d", t3.sum() > 0.0d);
        assertTrue("t3 mean > 0.0d", t3.mean() > 0.0d);
        assertTrue("t3 standardDeviation > 0.0d", t3.standardDeviation() > 0.0d);

        assertEquals("t4 size == 10", 10, t4.size());
        assertTrue("t4 min > 0.0d", t4.min() > 0.0d);
        assertTrue("t4 max > 0.0d", t4.max() > 0.0d);
        assertTrue("t4 sum > 0.0d", t4.sum() > 0.0d);
        assertTrue("t4 mean > 0.0d", t4.mean() > 0.0d);
        assertTrue("t4 standardDeviation > 0.0d", t4.standardDeviation() > 0.0d);

        assertEquals("t5 size == 10", 10, t5.size());
        assertTrue("t5 min > 0.0d", t5.min() > 0.0d);
        assertTrue("t5 max > 0.0d", t5.max() > 0.0d);
        assertTrue("t5 sum > 0.0d", t5.sum() > 0.0d);
        assertTrue("t5 mean > 0.0d", t5.mean() > 0.0d);
        assertTrue("t5 standardDeviation > 0.0d", t5.standardDeviation() > 0.0d);

        // map1 should be unmodifiable
        try
        {
            map1.put(r0, t5);
            fail("map1 put expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
    }

    public void testShuffle()
    {
        List<Runnable> codeBlocks0 = new ArrayList<Runnable>(Arrays.asList(new Runnable[] { r0, r1, r2 }));
        Map<Runnable,Timer> map0 = Timer.shuffle(codeBlocks0, 10);

        assertNotNull("map0 not null", map0);
        assertEquals("map0 size == 3", 3, map0.size());
        assertTrue("map0 contains key r0", map0.containsKey(r0));
        assertTrue("map0 contains key r1", map0.containsKey(r1));
        assertTrue("map0 contains key r2", map0.containsKey(r2));
        assertNotNull("map0.get(r0) not null", map0.get(r0));
        assertNotNull("map0.get(r1) not null", map0.get(r1));
        assertNotNull("map0.get(r2) not null", map0.get(r2));

        Timer t0 = map0.get(r0);
        Timer t1 = map0.get(r1);
        Timer t2 = map0.get(r2);

        assertEquals("t0 size == 10", 10, t0.size());
        assertTrue("t0 min > 0.0d", t0.min() > 0.0d);
        assertTrue("t0 max > 0.0d", t0.max() > 0.0d);
        assertTrue("t0 sum > 0.0d", t0.sum() > 0.0d);
        assertTrue("t0 mean > 0.0d", t0.mean() > 0.0d);
        assertTrue("t0 standardDeviation > 0.0d", t0.standardDeviation() > 0.0d);

        assertEquals("t1 size == 10", 10, t1.size());
        assertTrue("t1 min > 0.0d", t1.min() > 0.0d);
        assertTrue("t1 max > 0.0d", t1.max() > 0.0d);
        assertTrue("t1 sum > 0.0d", t1.sum() > 0.0d);
        assertTrue("t1 mean > 0.0d", t1.mean() > 0.0d);
        assertTrue("t1 standardDeviation > 0.0d", t1.standardDeviation() > 0.0d);

        assertEquals("t2 size == 10", 10, t2.size());
        assertTrue("t2 min > 0.0d", t2.min() > 0.0d);
        assertTrue("t2 max > 0.0d", t2.max() > 0.0d);
        assertTrue("t2 sum > 0.0d", t2.sum() > 0.0d);
        assertTrue("t2 mean > 0.0d", t2.mean() > 0.0d);
        assertTrue("t2 standardDeviation > 0.0d", t2.standardDeviation() > 0.0d);

        // map0 should be unmodifiable
        try
        {
            map0.put(r0, t1);
            fail("map0 put expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        List<Runnable> codeBlocks1 = new ArrayList<Runnable>(Arrays.asList(new Runnable[] { r0, r1, r2 }));
        Map<Runnable,Timer> map1 = Timer.shuffle(codeBlocks1, 2, 5);

        assertNotNull("map1 not null", map1);
        assertEquals("map1 size == 3", 3, map1.size());
        assertTrue("map1 contains key r0", map1.containsKey(r0));
        assertTrue("map1 contains key r1", map1.containsKey(r1));
        assertTrue("map1 contains key r2", map1.containsKey(r2));
        assertNotNull("map1.get(r0) not null", map1.get(r0));
        assertNotNull("map1.get(r1) not null", map1.get(r1));
        assertNotNull("map1.get(r2) not null", map1.get(r2));

        Timer t3 = map1.get(r0);
        Timer t4 = map1.get(r1);
        Timer t5 = map1.get(r2);

        assertEquals("t3 size == 10", 10, t3.size());
        assertTrue("t3 min > 0.0d", t3.min() > 0.0d);
        assertTrue("t3 max > 0.0d", t3.max() > 0.0d);
        assertTrue("t3 sum > 0.0d", t3.sum() > 0.0d);
        assertTrue("t3 mean > 0.0d", t3.mean() > 0.0d);
        assertTrue("t3 standardDeviation > 0.0d", t3.standardDeviation() > 0.0d);

        assertEquals("t4 size == 10", 10, t4.size());
        assertTrue("t4 min > 0.0d", t4.min() > 0.0d);
        assertTrue("t4 max > 0.0d", t4.max() > 0.0d);
        assertTrue("t4 sum > 0.0d", t4.sum() > 0.0d);
        assertTrue("t4 mean > 0.0d", t4.mean() > 0.0d);
        assertTrue("t4 standardDeviation > 0.0d", t4.standardDeviation() > 0.0d);

        assertEquals("t5 size == 10", 10, t5.size());
        assertTrue("t5 min > 0.0d", t5.min() > 0.0d);
        assertTrue("t5 max > 0.0d", t5.max() > 0.0d);
        assertTrue("t5 sum > 0.0d", t5.sum() > 0.0d);
        assertTrue("t5 mean > 0.0d", t5.mean() > 0.0d);
        assertTrue("t5 standardDeviation > 0.0d", t5.standardDeviation() > 0.0d);

        // map1 should be unmodifiable
        try
        {
            map1.put(r0, t5);
            fail("map1 put expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
    }

    public void testShuffleWithRandom()
    {
        List<Runnable> codeBlocks0 = new ArrayList<Runnable>(Arrays.asList(new Runnable[] { r0, r1, r2 }));
        Map<Runnable,Timer> map0 = Timer.shuffle(codeBlocks0, 10, new Random());

        assertNotNull("map0 not null", map0);
        assertEquals("map0 size == 3", 3, map0.size());
        assertTrue("map0 contains key r0", map0.containsKey(r0));
        assertTrue("map0 contains key r1", map0.containsKey(r1));
        assertTrue("map0 contains key r2", map0.containsKey(r2));
        assertNotNull("map0.get(r0) not null", map0.get(r0));
        assertNotNull("map0.get(r1) not null", map0.get(r1));
        assertNotNull("map0.get(r2) not null", map0.get(r2));

        Timer t0 = map0.get(r0);
        Timer t1 = map0.get(r1);
        Timer t2 = map0.get(r2);

        assertEquals("t0 size == 10", 10, t0.size());
        assertTrue("t0 min > 0.0d", t0.min() > 0.0d);
        assertTrue("t0 max > 0.0d", t0.max() > 0.0d);
        assertTrue("t0 sum > 0.0d", t0.sum() > 0.0d);
        assertTrue("t0 mean > 0.0d", t0.mean() > 0.0d);
        assertTrue("t0 standardDeviation > 0.0d", t0.standardDeviation() > 0.0d);

        assertEquals("t1 size == 10", 10, t1.size());
        assertTrue("t1 min > 0.0d", t1.min() > 0.0d);
        assertTrue("t1 max > 0.0d", t1.max() > 0.0d);
        assertTrue("t1 sum > 0.0d", t1.sum() > 0.0d);
        assertTrue("t1 mean > 0.0d", t1.mean() > 0.0d);
        assertTrue("t1 standardDeviation > 0.0d", t1.standardDeviation() > 0.0d);

        assertEquals("t2 size == 10", 10, t2.size());
        assertTrue("t2 min > 0.0d", t2.min() > 0.0d);
        assertTrue("t2 max > 0.0d", t2.max() > 0.0d);
        assertTrue("t2 sum > 0.0d", t2.sum() > 0.0d);
        assertTrue("t2 mean > 0.0d", t2.mean() > 0.0d);
        assertTrue("t2 standardDeviation > 0.0d", t2.standardDeviation() > 0.0d);

        // map0 should be unmodifiable
        try
        {
            map0.put(r0, t1);
            fail("map0 put expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }

        List<Runnable> codeBlocks1 = new ArrayList<Runnable>(Arrays.asList(new Runnable[] { r0, r1, r2 }));
        Map<Runnable,Timer> map1 = Timer.shuffle(codeBlocks1, 2, 5);

        assertNotNull("map1 not null", map1);
        assertEquals("map1 size == 3", 3, map1.size());
        assertTrue("map1 contains key r0", map1.containsKey(r0));
        assertTrue("map1 contains key r1", map1.containsKey(r1));
        assertTrue("map1 contains key r2", map1.containsKey(r2));
        assertNotNull("map1.get(r0) not null", map1.get(r0));
        assertNotNull("map1.get(r1) not null", map1.get(r1));
        assertNotNull("map1.get(r2) not null", map1.get(r2));

        Timer t3 = map1.get(r0);
        Timer t4 = map1.get(r1);
        Timer t5 = map1.get(r2);

        assertEquals("t3 size == 10", 10, t3.size());
        assertTrue("t3 min > 0.0d", t3.min() > 0.0d);
        assertTrue("t3 max > 0.0d", t3.max() > 0.0d);
        assertTrue("t3 sum > 0.0d", t3.sum() > 0.0d);
        assertTrue("t3 mean > 0.0d", t3.mean() > 0.0d);
        assertTrue("t3 standardDeviation > 0.0d", t3.standardDeviation() > 0.0d);

        assertEquals("t4 size == 10", 10, t4.size());
        assertTrue("t4 min > 0.0d", t4.min() > 0.0d);
        assertTrue("t4 max > 0.0d", t4.max() > 0.0d);
        assertTrue("t4 sum > 0.0d", t4.sum() > 0.0d);
        assertTrue("t4 mean > 0.0d", t4.mean() > 0.0d);
        assertTrue("t4 standardDeviation > 0.0d", t4.standardDeviation() > 0.0d);

        assertEquals("t5 size == 10", 10, t5.size());
        assertTrue("t5 min > 0.0d", t5.min() > 0.0d);
        assertTrue("t5 max > 0.0d", t5.max() > 0.0d);
        assertTrue("t5 sum > 0.0d", t5.sum() > 0.0d);
        assertTrue("t5 mean > 0.0d", t5.mean() > 0.0d);
        assertTrue("t5 standardDeviation > 0.0d", t5.standardDeviation() > 0.0d);

        // map1 should be unmodifiable
        try
        {
            map1.put(r0, t5);
            fail("map1 put expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
    }

    public void testShuffleDoesNotAlterListOrder()
    {
        List<Runnable> codeBlocks0 = new ArrayList<Runnable>(Arrays.asList(new Runnable[] { r0, r1, r2 }));
        Timer.shuffle(codeBlocks0, 2);

        assertEquals(r0, codeBlocks0.get(0));
        assertEquals(r1, codeBlocks0.get(1));
        assertEquals(r2, codeBlocks0.get(2));

        List<Runnable> codeBlocks1 = new ArrayList<Runnable>(Arrays.asList(new Runnable[] { r0, r1, r2 }));
        Timer.shuffle(codeBlocks1, 2, new Random());

        assertEquals(r0, codeBlocks1.get(0));
        assertEquals(r1, codeBlocks1.get(1));
        assertEquals(r2, codeBlocks1.get(2));

        List<Runnable> codeBlocks2 = new ArrayList<Runnable>(Arrays.asList(new Runnable[] { r0, r1, r2 }));
        Timer.shuffle(codeBlocks2, 2);

        assertEquals(r0, codeBlocks2.get(0));
        assertEquals(r1, codeBlocks2.get(1));
        assertEquals(r2, codeBlocks2.get(2));

        List<Runnable> codeBlocks3 = new ArrayList<Runnable>(Arrays.asList(new Runnable[] { r0, r1, r2 }));
        Timer.shuffle(codeBlocks3, 2, 2, new Random());

        assertEquals(r0, codeBlocks3.get(0));
        assertEquals(r1, codeBlocks3.get(1));
        assertEquals(r2, codeBlocks3.get(2));
    }
}
