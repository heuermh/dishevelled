

    /**
     * Map iterator.
     */
    protected static class MapIterator<K1, K2, V>
        extends HashIterator<BinaryKey<K1, K2>, V>
        implements BinaryKeyMapIterator<K1, K2, V>
    {

        /**
         * Create a new map iterator for the specified parent
         * hashed binary key map.
         *
         * @param parent parent hashed binary key map
         */
        protected MapIterator(final HashedBinaryKeyMap<K1, K2, V> parent)
        {
            super(parent);
        }


        /** {@inheritDoc} */
        public V next()
        {
            return super.nextEntry().getValue();
        }

        /** {@inheritDoc} */
        public K1 getFirstKey()
        {
            HashEntry<BinaryKey<K1, K2>, V> current = currentEntry();
            if (current == null)
            {
                throw new IllegalStateException(GETKEY_INVALID);
            }
            return current.getKey().getFirstKey();
        }

        /** {@inheritDoc} */
        public K2 getSecondKey()
        {
            HashEntry<BinaryKey<K1, K2>, V> current = currentEntry();
            if (current == null)
            {
                throw new IllegalStateException(GETKEY_INVALID);
            }
            return current.getKey().getSecondKey();
        }

        /** {@inheritDoc} */
        public V getValue()
        {
            HashEntry<BinaryKey<K1, K2>, V> current = currentEntry();
            if (current == null) {
                throw new IllegalStateException(GETVALUE_INVALID);
            }
            return current.getValue();
        }

        /** {@inheritDoc} */
        public V setValue(V value)
        {
            HashEntry<BinaryKey<K1, K2>, V> current = currentEntry();
            if (current == null) {
                throw new IllegalStateException(SETVALUE_INVALID);
            }
            return current.setValue(value);
        }
    }