

    /**
     * Map iterator.
     */
    protected static class MapIterator<K1, K2, K3, V>
        extends HashIterator<TertiaryKey<K1, K2, K3>, V>
        implements TertiaryKeyMapIterator<K1, K2, K3, V>
    {

        /**
         * Create a new map iterator for the specified parent
         * hashed tertiary key map.
         *
         * @param parent parent hashed tertiary key map
         */
        protected MapIterator(final HashedTertiaryKeyMap<K1, K2, K3, V> parent)
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
            HashEntry<TertiaryKey<K1, K2, K3>, V> current = currentEntry();
            if (current == null)
            {
                throw new IllegalStateException(GETKEY_INVALID);
            }
            return current.getKey().getFirstKey();
        }

        /** {@inheritDoc} */
        public K2 getSecondKey()
        {
            HashEntry<TertiaryKey<K1, K2, K3>, V> current = currentEntry();
            if (current == null)
            {
                throw new IllegalStateException(GETKEY_INVALID);
            }
            return current.getKey().getSecondKey();
        }

        /** {@inheritDoc} */
        public K3 getThirdKey()
        {
            HashEntry<TertiaryKey<K1, K2, K3>, V> current = currentEntry();
            if (current == null)
            {
                throw new IllegalStateException(GETKEY_INVALID);
            }
            return current.getKey().getThirdKey();
        }

        /** {@inheritDoc} */
        public V getValue()
        {
            HashEntry<TertiaryKey<K1, K2, K3>, V> current = currentEntry();
            if (current == null) {
                throw new IllegalStateException(GETVALUE_INVALID);
            }
            return current.getValue();
        }

        /** {@inheritDoc} */
        public V setValue(V value)
        {
            HashEntry<TertiaryKey<K1, K2, K3>, V> current = currentEntry();
            if (current == null) {
                throw new IllegalStateException(SETVALUE_INVALID);
            }
            return current.setValue(value);
        }
    }