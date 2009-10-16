
    public void testEmptyBinaryMapIterator()
    {
        BinaryKeyMap<Integer, String, Double> map = createBinaryKeyMap();
        BinaryKeyMapIterator<Integer, String, Double> iterator = map.mapIterator();
        assertNotNull(iterator);
        assertFalse(iterator.hasNext());
        try
        {
            iterator.next();
            fail("next() expected NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // expected
        }
        try
        {
            iterator.getFirstKey();
            fail("getFirstKey() expected IllegalStateException");
        }
        catch (IllegalStateException e)
        {
            // expected
        }
        try
        {
            iterator.getSecondKey();
            fail("getSecondKey() expected IllegalStateException");
        }
        catch (IllegalStateException e)
        {
            // expected
        }
        try
        {
            iterator.getValue();
            fail("getValue() expected IllegalStateException");
        }
        catch (IllegalStateException e)
        {
            // expected
        }
        try
        {
            iterator.setValue(Double.valueOf(-1.0d));
            fail("setValue() expected UnsupportedOperationException or IllegalStateException");
        }
        catch (UnsupportedOperationException e)
        {
            // expected
        }
        catch (IllegalStateException e)
        {
            // expected
        }
    }

    public void testFullBinaryKeyIterator()
    {
        BinaryKeyMap<Integer, String, Double> map = createBinaryKeyMap();
        fillMap(map);
        BinaryKeyMapIterator<Integer, String, Double> iterator = map.mapIterator();
        assertTrue(iterator.hasNext());

        Set<BinaryKey<Integer, String>> set = new HashSet<BinaryKey<Integer, String>>();
        while(iterator.hasNext())
        {
            // Note:
            //    here is where the BinaryKeyMapIterator contract differs from MapIterator
            Double value = iterator.next();
            assertSame(value, iterator.getValue());

            Integer firstKey = iterator.getFirstKey();
            String secondKey = iterator.getSecondKey();
            BinaryKey<Integer, String> key = new BinaryKey<Integer, String>(firstKey, secondKey);
            assertTrue(map.containsKey(key));
            assertTrue("key must be unique", set.add(key));
            assertSame(value, map.get(key));
            assertSame(value, map.get(firstKey, secondKey));
            assertTrue(map.containsValue(value));
        }
    }

    public void testBinaryKeyIteratorGetsWithoutNext()
    {
        BinaryKeyMap<Integer, String, Double> map = createBinaryKeyMap();
        fillMap(map);
        BinaryKeyMapIterator<Integer, String, Double> iterator = map.mapIterator();
        assertTrue(iterator.hasNext());

        Set<BinaryKey<Integer, String>> set = new HashSet<BinaryKey<Integer, String>>();
        if (iterator.hasNext())
        {
            // Note:
            //    a common mistake with BinaryKeyMapIterator might be to forget to call next()
            try
            {
                iterator.getFirstKey();
                fail("getFirstKey() without next() expected IllegalStateException");
            }
            catch (IllegalStateException e)
            {
                // expected
            }
            try
            {
                iterator.getSecondKey();
                fail("getSecondKey() without next() expected IllegalStateException");
            }
            catch (IllegalStateException e)
            {
                // expected
            }
            try
            {
                iterator.getValue();
                fail("getValue() without next() expected IllegalStateException");
            }
            catch (IllegalStateException e)
            {
                // expected
            }
            try
            {
                iterator.setValue(Double.valueOf(-1.0d));
                fail("setValue() without next() expected UnsupportedOperationException or IllegalStateException");
            }
            catch (UnsupportedOperationException e)
            {
                // expected
            }
            catch (IllegalStateException e)
            {
                // expected
            }
        }
    }
