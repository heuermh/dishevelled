

    /**
     * Map iterator.
     */
    protected static class MapIterator<K1, K2, K3, K4, V>
        extends HashIterator<QuaternaryKey<K1, K2, K3, K4>, V>
        implements QuaternaryKeyMapIterator<K1, K2, K3, K4, V>
    {

        /**
         * Create a new map iterator for the specified parent
         * hashed quaternary key map.
         *
         * @param parent parent hashed quaternary key map
         */
        protected MapIterator(final HashedQuaternaryKeyMap<K1, K2, K3, K4, V> parent)
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
            HashEntry<QuaternaryKey<K1, K2, K3, K4>, V> current = currentEntry();
            if (current == null)
            {
                throw new IllegalStateException(GETKEY_INVALID);
            }
            return current.getKey().getFirstKey();
        }

        /** {@inheritDoc} */
        public K2 getSecondKey()
        {
            HashEntry<QuaternaryKey<K1, K2, K3, K4>, V> current = currentEntry();
            if (current == null)
            {
                throw new IllegalStateException(GETKEY_INVALID);
            }
            return current.getKey().getSecondKey();
        }

        /** {@inheritDoc} */
        public K3 getThirdKey()
        {
            HashEntry<QuaternaryKey<K1, K2, K3, K4>, V> current = currentEntry();
            if (current == null)
            {
                throw new IllegalStateException(GETKEY_INVALID);
            }
            return current.getKey().getThirdKey();
        }

        /** {@inheritDoc} */
        public K4 getFourthKey()
        {
            HashEntry<QuaternaryKey<K1, K2, K3, K4>, V> current = currentEntry();
            if (current == null)
            {
                throw new IllegalStateException(GETKEY_INVALID);
            }
            return current.getKey().getFourthKey();
        }

        /** {@inheritDoc} */
        public V getValue()
        {
            HashEntry<QuaternaryKey<K1, K2, K3, K4>, V> current = currentEntry();
            if (current == null) {
                throw new IllegalStateException(GETVALUE_INVALID);
            }
            return current.getValue();
        }

        /** {@inheritDoc} */
        public V setValue(V value)
        {
            HashEntry<QuaternaryKey<K1, K2, K3, K4>, V> current = currentEntry();
            if (current == null) {
                throw new IllegalStateException(SETVALUE_INVALID);
            }
            return current.setValue(value);
        }
    }