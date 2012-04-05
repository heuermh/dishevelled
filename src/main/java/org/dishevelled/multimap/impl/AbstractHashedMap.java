/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*

  Copied with modifications from
    org.apache.commons.collections.map.AbstractHashedMap
    see http://commons.apache.org/collections

*/
package org.dishevelled.multimap.impl;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Abstract hashed map.
 */
abstract class AbstractHashedMap<K, V>
    implements Map<K, V>
{
    protected static final String NO_NEXT_ENTRY = "No next() entry in the iteration";
    protected static final String NO_PREVIOUS_ENTRY = "No previous() entry in the iteration";
    protected static final String REMOVE_INVALID = "remove() can only be called once after next()";
    protected static final String GETKEY_INVALID = "getKey() can only be called after next() and before remove()";
    protected static final String GETVALUE_INVALID = "getValue() can only be called after next() and before remove()";
    protected static final String SETVALUE_INVALID = "setValue() can only be called after next() and before remove()";

    /**
     * The default capacity to use
     */
    protected static final int DEFAULT_CAPACITY = 16;
    /**
     * The default threshold to use
     */
    protected static final int DEFAULT_THRESHOLD = 12;
    /**
     * The default load factor to use
     */
    protected static final float DEFAULT_LOAD_FACTOR = 0.75f;
    /**
     * The maximum capacity allowed
     */
    protected static final int MAXIMUM_CAPACITY = 1 << 30;
    /**
     * An object for masking null
     */
    protected static final Object NULL = new Object();

    /**
     * Load factor, normally 0.75
     */
    protected transient float loadFactor;
    /**
     * The size of the map
     */
    protected transient int size;
    /**
     * Map entries
     */
    protected transient HashEntry<K, V>[] data;
    /**
     * Size at which to rehash
     */
    protected transient int threshold;
    /**
     * Modification count for iterators
     */
    protected transient int modCount;
    /**
     * Entry set
     */
    protected transient EntrySet<K, V> entrySet;
    /**
     * Key set
     */
    protected transient KeySet<K, V> keySet;
    /**
     * Values
     */
    protected transient Values<K, V> values;

    /**
     * Constructor only used in deserialization, do not use otherwise.
     */
    protected AbstractHashedMap() {
        super();
    }

    /**
     * Constructor which performs no validation on the passed in parameters.
     *
     * @param initialCapacity the initial capacity, must be a power of two
     * @param loadFactor      the load factor, must be &gt; 0.0f and generally &lt; 1.0f
     * @param threshold       the threshold, must be sensible
     */
    protected AbstractHashedMap(int initialCapacity, float loadFactor, int threshold) {
        super();
        this.loadFactor = loadFactor;
        this.data = new HashEntry[initialCapacity];
        this.threshold = threshold;
        init();
    }

    /**
     * Constructs a new, empty map with the specified initial capacity and
     * default load factor.
     *
     * @param initialCapacity the initial capacity
     * @throws IllegalArgumentException if the initial capacity is less than one
     */
    protected AbstractHashedMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructs a new, empty map with the specified initial capacity and
     * load factor.
     *
     * @param initialCapacity the initial capacity
     * @param loadFactor      the load factor
     * @throws IllegalArgumentException if the initial capacity is less than one
     * @throws IllegalArgumentException if the load factor is less than or equal to zero
     */
    protected AbstractHashedMap(int initialCapacity, float loadFactor) {
        super();
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Initial capacity must be greater than 0");
        }
        if (loadFactor <= 0.0f || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Load factor must be greater than 0");
        }
        this.loadFactor = loadFactor;
        this.threshold = calculateThreshold(initialCapacity, loadFactor);
        int newInitialCapacity = calculateNewCapacity(initialCapacity);
        this.data = new HashEntry[newInitialCapacity];
        init();
    }

    /**
     * Constructor copying elements from another map.
     *
     * @param map the map to copy
     * @throws NullPointerException if the map is null
     */
    protected AbstractHashedMap(Map<? extends K, ? extends V> map) {
        this(Math.max(2 * map.size(), DEFAULT_CAPACITY), DEFAULT_LOAD_FACTOR);
        putAll(map);
    }

    /**
     * Initialise subclasses during construction, cloning or deserialization.
     */
    protected void init() {
        // empty
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the value mapped to the key specified.
     *
     * @param key the key
     * @return the mapped value, null if no match
     */
    public V get(Object key) {
        int hashCode = hash((key == null) ? NULL : key);
        HashEntry<K, V> entry = data[hashIndex(hashCode, data.length)]; // no local for hash index
        while (entry != null) {
            if (entry.hashCode == hashCode && isEqualKey(key, entry.key)) {
                return entry.getValue();
            }
            entry = entry.next;
        }
        return null;
    }

    /**
     * Gets the size of the map.
     *
     * @return the size
     */
    public int size() {
        return size;
    }

    /**
     * Checks whether the map is currently empty.
     *
     * @return true if the map is currently size zero
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks whether the map contains the specified key.
     *
     * @param key the key to search for
     * @return true if the map contains the key
     */
    public boolean containsKey(Object key) {
        int hashCode = hash((key == null) ? NULL : key);
        HashEntry entry = data[hashIndex(hashCode, data.length)]; // no local for hash index
        while (entry != null) {
            if (entry.hashCode == hashCode && isEqualKey(key, entry.getKey())) {
                return true;
            }
            entry = entry.next;
        }
        return false;
    }

    /**
     * Checks whether the map contains the specified value.
     *
     * @param value the value to search for
     * @return true if the map contains the value
     */
    public boolean containsValue(Object value) {
        if (value == null) {
            for (int i = 0, isize = data.length; i < isize; i++) {
                HashEntry entry = data[i];
                while (entry != null) {
                    if (entry.getValue() == null) {
                        return true;
                    }
                    entry = entry.next;
                }
            }
        } else {
            for (int i = 0, isize = data.length; i < isize; i++) {
                HashEntry entry = data[i];
                while (entry != null) {
                    if (isEqualValue(value, entry.getValue())) {
                        return true;
                    }
                    entry = entry.next;
                }
            }
        }
        return false;
    }

    //-----------------------------------------------------------------------
    /**
     * Puts a key-value mapping into this map.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return the value previously mapped to this key, null if none
     */
    public V put(K key, V value) {
        int hashCode = hash((key == null) ? NULL : key);
        int index = hashIndex(hashCode, data.length);
        HashEntry<K, V> entry = data[index];
        while (entry != null) {
            if (entry.hashCode == hashCode && isEqualKey(key, entry.getKey())) {
                V oldValue = entry.getValue();
                updateEntry(entry, value);
                return oldValue;
            }
            entry = entry.next;
        }
        addMapping(index, hashCode, key, value);
        return null;
    }

    /**
     * Puts all the values from the specified map into this map.
     * <p/>
     * This implementation iterates around the specified map and
     * uses {@link #put(Object, Object)}.
     *
     * @param map the map to add
     * @throws NullPointerException if the map is null
     */
    public void putAll(Map<? extends K, ? extends V> map) {
        int mapSize = map.size();
        if (mapSize == 0) {
            return;
        }
        int newSize = (int) ((size + mapSize) / loadFactor + 1);
        ensureCapacity(calculateNewCapacity(newSize));
        // Have to cast here because of compiler inference problems.
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry<? extends K, ? extends V> entry = (Map.Entry<? extends K, ? extends V>) it.next();
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Removes the specified mapping from this map.
     *
     * @param key the mapping to remove
     * @return the value mapped to the removed key, null if key not in map
     */
    public V remove(Object key) {
        int hashCode = hash((key == null) ? NULL : key);
        int index = hashIndex(hashCode, data.length);
        HashEntry<K, V> entry = data[index];
        HashEntry<K, V> previous = null;
        while (entry != null) {
            if (entry.hashCode == hashCode && isEqualKey(key, entry.getKey())) {
                V oldValue = entry.getValue();
                removeMapping(entry, index, previous);
                return oldValue;
            }
            previous = entry;
            entry = entry.next;
        }
        return null;
    }

    /**
     * Clears the map, resetting the size to zero and nullifying references
     * to avoid garbage collection issues.
     */
    public void clear() {
        modCount++;
        HashEntry[] data = this.data;
        for (int i = data.length - 1; i >= 0; i--) {
            data[i] = null;
        }
        size = 0;
    }

    /**
     * Gets the hash code for the key specified.
     * This implementation uses the additional hashing routine from JDK1.4.
     * Subclasses can override this to return alternate hash codes.
     *
     * @param key the key to get a hash code for
     * @return the hash code
     */
    protected int hash(Object key) {
        // same as JDK 1.4
        int h = key.hashCode();
        h += ~(h << 9);
        h ^= (h >>> 14);
        h += (h << 4);
        h ^= (h >>> 10);
        return h;
    }

    /**
     * Compares two keys, in internal converted form, to see if they are equal.
     * This implementation uses the equals method.
     * Subclasses can override this to match differently.
     *
     * @param key1 the first key to compare passed in from outside
     * @param key2 the second key extracted from the entry via <code>entry.key</code>
     * @return true if equal
     */
    protected boolean isEqualKey(Object key1, Object key2) {
        return (key1 == key2 || ((key1 != null) && key1.equals(key2)));
    }

    /**
     * Compares two values, in external form, to see if they are equal.
     * This implementation uses the equals method and assumes neither value is null.
     * Subclasses can override this to match differently.
     *
     * @param value1 the first value to compare passed in from outside
     * @param value2 the second value extracted from the entry via <code>getValue()</code>
     * @return true if equal
     */
    protected boolean isEqualValue(Object value1, Object value2) {
        return (value1 == value2 || value1.equals(value2));
    }

    /**
     * Gets the index into the data storage for the hashCode specified.
     * This implementation uses the least significant bits of the hashCode.
     * Subclasses can override this to return alternate bucketing.
     *
     * @param hashCode the hash code to use
     * @param dataSize the size of the data to pick a bucket from
     * @return the bucket index
     */
    protected int hashIndex(int hashCode, int dataSize) {
        return hashCode & (dataSize - 1);
    }
    
    //-----------------------------------------------------------------------
    /**
     * Gets the entry mapped to the key specified.
     * <p/>
     * This method exists for subclasses that may need to perform a multi-step
     * process accessing the entry. The public methods in this class don't use this
     * method to gain a small performance boost.
     *
     * @param key the key
     * @return the entry, null if no match
     */
    protected HashEntry<K, V> getEntry(Object key) {
        int hashCode = hash((key == null) ? NULL : key);
        HashEntry<K, V> entry = data[hashIndex(hashCode, data.length)]; // no local for hash index
        while (entry != null) {
            if (entry.hashCode == hashCode && isEqualKey(key, entry.getKey())) {
                return entry;
            }
            entry = entry.next;
        }
        return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Updates an existing key-value mapping to change the value.
     * <p/>
     * This implementation calls <code>setValue()</code> on the entry.
     * Subclasses could override to handle changes to the map.
     *
     * @param entry    the entry to update
     * @param newValue the new value to store
     */
    protected void updateEntry(HashEntry<K, V> entry, V newValue) {
        entry.setValue(newValue);
    }

    /**
     * Reuses an existing key-value mapping, storing completely new data.
     * <p/>
     * This implementation sets all the data fields on the entry.
     * Subclasses could populate additional entry fields.
     *
     * @param entry     the entry to update, not null
     * @param hashIndex the index in the data array
     * @param hashCode  the hash code of the key to add
     * @param key       the key to add
     * @param value     the value to add
     */
    protected void reuseEntry(HashEntry<K, V> entry, int hashIndex, int hashCode, K key, V value) {
        entry.next = data[hashIndex];
        entry.hashCode = hashCode;
        entry.key = key;
        entry.value = value;
    }
    
    //-----------------------------------------------------------------------
    /**
     * Adds a new key-value mapping into this map.
     * <p/>
     * This implementation calls <code>createEntry()</code>, <code>addEntry()</code>
     * and <code>checkCapacity()</code>.
     * It also handles changes to <code>modCount</code> and <code>size</code>.
     * Subclasses could override to fully control adds to the map.
     *
     * @param hashIndex the index into the data array to store at
     * @param hashCode  the hash code of the key to add
     * @param key       the key to add
     * @param value     the value to add
     */
    protected void addMapping(int hashIndex, int hashCode, K key, V value) {
        modCount++;
        HashEntry<K, V> entry = createEntry(data[hashIndex], hashCode, key, value);
        addEntry(entry, hashIndex);
        size++;
        checkCapacity();
    }

    /**
     * Creates an entry to store the key-value data.
     * <p/>
     * This implementation creates a new HashEntry instance.
     * Subclasses can override this to return a different storage class,
     * or implement caching.
     *
     * @param next     the next entry in sequence
     * @param hashCode the hash code to use
     * @param key      the key to store
     * @param value    the value to store
     * @return the newly created entry
     */
    protected HashEntry<K, V> createEntry(HashEntry<K, V> next, int hashCode, K key, V value) {
        return new HashEntry<K, V>(next, hashCode, key, value);
    }

    /**
     * Adds an entry into this map.
     * <p/>
     * This implementation adds the entry to the data storage table.
     * Subclasses could override to handle changes to the map.
     *
     * @param entry     the entry to add
     * @param hashIndex the index into the data array to store at
     */
    protected void addEntry(HashEntry<K, V> entry, int hashIndex) {
        data[hashIndex] = entry;
    }
    
    //-----------------------------------------------------------------------
    /**
     * Removes a mapping from the map.
     * <p/>
     * This implementation calls <code>removeEntry()</code> and <code>destroyEntry()</code>.
     * It also handles changes to <code>modCount</code> and <code>size</code>.
     * Subclasses could override to fully control removals from the map.
     *
     * @param entry     the entry to remove
     * @param hashIndex the index into the data structure
     * @param previous  the previous entry in the chain
     */
    protected void removeMapping(HashEntry<K, V> entry, int hashIndex, HashEntry<K, V> previous) {
        modCount++;
        removeEntry(entry, hashIndex, previous);
        size--;
        destroyEntry(entry);
    }

    /**
     * Removes an entry from the chain stored in a particular index.
     * <p/>
     * This implementation removes the entry from the data storage table.
     * The size is not updated.
     * Subclasses could override to handle changes to the map.
     *
     * @param entry     the entry to remove
     * @param hashIndex the index into the data structure
     * @param previous  the previous entry in the chain
     */
    protected void removeEntry(HashEntry<K, V> entry, int hashIndex, HashEntry<K, V> previous) {
        if (previous == null) {
            data[hashIndex] = entry.next;
        } else {
            previous.next = entry.next;
        }
    }

    /**
     * Kills an entry ready for the garbage collector.
     * <p/>
     * This implementation prepares the HashEntry for garbage collection.
     * Subclasses can override this to implement caching (override clear as well).
     *
     * @param entry the entry to destroy
     */
    protected void destroyEntry(HashEntry<K, V> entry) {
        entry.next = null;
        entry.key = null;
        entry.value = null;
    }
    
    //-----------------------------------------------------------------------
    /**
     * Checks the capacity of the map and enlarges it if necessary.
     * <p/>
     * This implementation uses the threshold to check if the map needs enlarging
     */
    protected void checkCapacity() {
        if (size >= threshold) {
            int newCapacity = data.length * 2;
            if (newCapacity <= MAXIMUM_CAPACITY) {
                ensureCapacity(newCapacity);
            }
        }
    }

    /**
     * Changes the size of the data structure to the capacity proposed.
     *
     * @param newCapacity the new capacity of the array (a power of two, less or equal to max)
     */
    protected void ensureCapacity(int newCapacity) {
        int oldCapacity = data.length;
        if (newCapacity <= oldCapacity) {
            return;
        }
        if (size == 0) {
            threshold = calculateThreshold(newCapacity, loadFactor);
            data = new HashEntry[newCapacity];
        } else {
            HashEntry<K, V> oldEntries[] = data;
            HashEntry<K, V> newEntries[] = new HashEntry[newCapacity];

            modCount++;
            for (int i = oldCapacity - 1; i >= 0; i--) {
                HashEntry<K, V> entry = oldEntries[i];
                if (entry != null) {
                    oldEntries[i] = null;  // gc
                    do {
                        HashEntry<K, V> next = entry.next;
                        int index = hashIndex(entry.hashCode, newCapacity);
                        entry.next = newEntries[index];
                        newEntries[index] = entry;
                        entry = next;
                    } while (entry != null);
                }
            }
            threshold = calculateThreshold(newCapacity, loadFactor);
            data = newEntries;
        }
    }

    /**
     * Calculates the new capacity of the map.
     * This implementation normalizes the capacity to a power of two.
     *
     * @param proposedCapacity the proposed capacity
     * @return the normalized new capacity
     */
    protected int calculateNewCapacity(int proposedCapacity) {
        int newCapacity = 1;
        if (proposedCapacity > MAXIMUM_CAPACITY) {
            newCapacity = MAXIMUM_CAPACITY;
        } else {
            while (newCapacity < proposedCapacity) {
                newCapacity <<= 1;  // multiply by two
            }
            if (newCapacity > MAXIMUM_CAPACITY) {
                newCapacity = MAXIMUM_CAPACITY;
            }
        }
        return newCapacity;
    }

    /**
     * Calculates the new threshold of the map, where it will be resized.
     * This implementation uses the load factor.
     *
     * @param newCapacity the new capacity
     * @param factor      the load factor
     * @return the new resize threshold
     */
    protected int calculateThreshold(int newCapacity, float factor) {
        return (int) (newCapacity * factor);
    }
    
    //-----------------------------------------------------------------------
    /**
     * Gets the <code>next</code> field from a <code>HashEntry</code>.
     * Used in subclasses that have no visibility of the field.
     *
     * @param entry the entry to query, must not be null
     * @return the <code>next</code> field of the entry
     * @throws NullPointerException if the entry is null
     * @since Commons Collections 3.1
     */
    protected HashEntry<K, V> entryNext(HashEntry<K, V> entry) {
        return entry.next;
    }

    /**
     * Gets the <code>hashCode</code> field from a <code>HashEntry</code>.
     * Used in subclasses that have no visibility of the field.
     *
     * @param entry the entry to query, must not be null
     * @return the <code>hashCode</code> field of the entry
     * @throws NullPointerException if the entry is null
     * @since Commons Collections 3.1
     */
    protected int entryHashCode(HashEntry<K, V> entry) {
        return entry.hashCode;
    }

    /**
     * Gets the <code>key</code> field from a <code>HashEntry</code>.
     * Used in subclasses that have no visibility of the field.
     *
     * @param entry the entry to query, must not be null
     * @return the <code>key</code> field of the entry
     * @throws NullPointerException if the entry is null
     * @since Commons Collections 3.1
     */
    protected K entryKey(HashEntry<K, V> entry) {
        return entry.key;
    }

    /**
     * Gets the <code>value</code> field from a <code>HashEntry</code>.
     * Used in subclasses that have no visibility of the field.
     *
     * @param entry the entry to query, must not be null
     * @return the <code>value</code> field of the entry
     * @throws NullPointerException if the entry is null
     * @since Commons Collections 3.1
     */
    protected V entryValue(HashEntry<K, V> entry) {
        return entry.value;
    }

    //-----------------------------------------------------------------------    
    /**
     * Gets the entrySet view of the map.
     * Changes made to the view affect this map.
     *
     * @return the entrySet view
     */
    public Set<Map.Entry<K, V>> entrySet() {
        if (entrySet == null) {
            entrySet = new EntrySet<K, V>(this);
        }
        return entrySet;
    }

    /**
     * Creates an entry set iterator.
     * Subclasses can override this to return iterators with different properties.
     *
     * @return the entrySet iterator
     */
    protected Iterator<Map.Entry<K, V>> createEntrySetIterator() {
        return new EntrySetIterator<K, V>(this);
    }

    /**
     * EntrySet implementation.
     */
    protected static class EntrySet <K,V> extends AbstractSet<Map.Entry<K, V>> {
        /**
         * The parent map
         */
        protected final AbstractHashedMap<K, V> parent;

        protected EntrySet(AbstractHashedMap<K, V> parent) {
            super();
            this.parent = parent;
        }

        public int size() {
            return parent.size();
        }

        public void clear() {
            parent.clear();
        }

        public boolean contains(Map.Entry<K, V> entry) {
            Map.Entry<K, V> e = entry;
            Entry<K, V> match = parent.getEntry(e.getKey());
            return (match != null && match.equals(e));
        }

        public boolean remove(Object obj) {
            if (obj instanceof Map.Entry == false) {
                return false;
            }
            if (contains(obj) == false) {
                return false;
            }
            Map.Entry<K, V> entry = (Map.Entry<K, V>) obj;
            K key = entry.getKey();
            parent.remove(key);
            return true;
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return parent.createEntrySetIterator();
        }
    }

    /**
     * EntrySet iterator.
     */
    protected static class EntrySetIterator <K,V> extends HashIterator<K, V> implements Iterator<Map.Entry<K, V>> {

        protected EntrySetIterator(AbstractHashedMap<K, V> parent) {
            super(parent);
        }

        public HashEntry<K, V> next() {
            return super.nextEntry();
        }
    }

    //-----------------------------------------------------------------------    
    /**
     * Gets the keySet view of the map.
     * Changes made to the view affect this map.
     *
     * @return the keySet view
     */
    public Set<K> keySet() {
        if (keySet == null) {
            keySet = new KeySet<K, V>(this);
        }
        return keySet;
    }

    /**
     * Creates a key set iterator.
     * Subclasses can override this to return iterators with different properties.
     *
     * @return the keySet iterator
     */
    protected Iterator<K> createKeySetIterator() {
        return new KeySetIterator<K, V>(this);
    }

    /**
     * KeySet implementation.
     */
    protected static class KeySet <K,V> extends AbstractSet<K> {
        /**
         * The parent map
         */
        protected final AbstractHashedMap<K, V> parent;

        protected KeySet(AbstractHashedMap<K, V> parent) {
            super();
            this.parent = parent;
        }

        public int size() {
            return parent.size();
        }

        public void clear() {
            parent.clear();
        }

        public boolean contains(Object key) {
            return parent.containsKey(key);
        }

        public boolean remove(Object key) {
            boolean result = parent.containsKey(key);
            parent.remove(key);
            return result;
        }

        public Iterator<K> iterator() {
            return parent.createKeySetIterator();
        }
    }

    /**
     * KeySet iterator.
     */
    protected static class KeySetIterator <K,V> extends HashIterator<K, V> implements Iterator<K> {

        protected KeySetIterator(AbstractHashedMap<K, V> parent) {
            super(parent);
        }

        public K next() {
            return super.nextEntry().getKey();
        }
    }
    
    //-----------------------------------------------------------------------    
    /**
     * Gets the values view of the map.
     * Changes made to the view affect this map.
     *
     * @return the values view
     */
    public Collection<V> values() {
        if (values == null) {
            values = new Values(this);
        }
        return values;
    }

    /**
     * Creates a values iterator.
     * Subclasses can override this to return iterators with different properties.
     *
     * @return the values iterator
     */
    protected Iterator<V> createValuesIterator() {
        return new ValuesIterator<K, V>(this);
    }

    /**
     * Values implementation.
     */
    protected static class Values <K,V> extends AbstractCollection<V> {
        /**
         * The parent map
         */
        protected final AbstractHashedMap<K, V> parent;

        protected Values(AbstractHashedMap<K, V> parent) {
            super();
            this.parent = parent;
        }

        public int size() {
            return parent.size();
        }

        public void clear() {
            parent.clear();
        }

        public boolean contains(Object value) {
            return parent.containsValue(value);
        }

        public Iterator<V> iterator() {
            return parent.createValuesIterator();
        }
    }

    /**
     * Values iterator.
     */
    protected static class ValuesIterator <K,V> extends HashIterator<K, V> implements Iterator<V> {

        protected ValuesIterator(AbstractHashedMap<K, V> parent) {
            super(parent);
        }

        public V next() {
            return super.nextEntry().getValue();
        }
    }
    
    //-----------------------------------------------------------------------
    /**
     * HashEntry used to store the data.
     * <p/>
     * If you subclass <code>AbstractHashedMap</code> but not <code>HashEntry</code>
     * then you will not be able to access the protected fields.
     * The <code>entryXxx()</code> methods on <code>AbstractHashedMap</code> exist
     * to provide the necessary access.
     */
    protected static class HashEntry <K,V> implements Map.Entry<K, V> {
        /**
         * The next entry in the hash chain
         */
        protected HashEntry<K, V> next;
        /**
         * The hash code of the key
         */
        protected int hashCode;
        /**
         * The key
         */
        private K key;
        /**
         * The value
         */
        private V value;

        protected HashEntry(HashEntry<K, V> next, int hashCode, K key, V value) {
            super();
            this.next = next;
            this.hashCode = hashCode;
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof Map.Entry == false) {
                return false;
            }
            Map.Entry other = (Map.Entry) obj;
            return (getKey() == null ? other.getKey() == null : getKey().equals(other.getKey())) && (getValue() == null ? other.getValue() == null : getValue().equals(other.getValue()));
        }

        public int hashCode() {
            return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
        }

        public String toString() {
            return new StringBuffer().append(getKey()).append('=').append(getValue()).toString();
        }
    }

    /**
     * Base Iterator
     */
    protected static abstract class HashIterator <K,V> {

        /**
         * The parent map
         */
        protected final AbstractHashedMap parent;
        /**
         * The current index into the array of buckets
         */
        protected int hashIndex;
        /**
         * The last returned entry
         */
        protected HashEntry<K, V> last;
        /**
         * The next entry
         */
        protected HashEntry<K, V> next;
        /**
         * The modification count expected
         */
        protected int expectedModCount;

        protected HashIterator(AbstractHashedMap<K, V> parent) {
            super();
            this.parent = parent;
            HashEntry<K, V>[] data = parent.data;
            int i = data.length;
            HashEntry<K, V> next = null;
            while (i > 0 && next == null) {
                next = data[--i];
            }
            this.next = next;
            this.hashIndex = i;
            this.expectedModCount = parent.modCount;
        }

        public boolean hasNext() {
            return (next != null);
        }

        protected HashEntry<K, V> nextEntry() {
            if (parent.modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            HashEntry<K, V> newCurrent = next;
            if (newCurrent == null) {
                throw new NoSuchElementException(AbstractHashedMap.NO_NEXT_ENTRY);
            }
            HashEntry<K, V>[] data = parent.data;
            int i = hashIndex;
            HashEntry<K, V> n = newCurrent.next;
            while (n == null && i > 0) {
                n = data[--i];
            }
            next = n;
            hashIndex = i;
            last = newCurrent;
            return newCurrent;
        }

        protected HashEntry<K, V> currentEntry() {
            return last;
        }

        public void remove() {
            if (last == null) {
                throw new IllegalStateException(AbstractHashedMap.REMOVE_INVALID);
            }
            if (parent.modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            parent.remove(last.getKey());
            last = null;
            expectedModCount = parent.modCount;
        }

        public String toString() {
            if (last != null) {
                return "Iterator[" + last.getKey() + "=" + last.getValue() + "]";
            } else {
                return "Iterator[]";
            }
        }
    }
    
    //-----------------------------------------------------------------------
    /**
     * Writes the map data to the stream. This method must be overridden if a
     * subclass must be setup before <code>put()</code> is used.
     * <p/>
     * Serialization is not one of the JDK's nicest topics. Normal serialization will
     * initialise the superclass before the subclass. Sometimes however, this isn't
     * what you want, as in this case the <code>put()</code> method on read can be
     * affected by subclass state.
     * <p/>
     * The solution adopted here is to serialize the state data of this class in
     * this protected method. This method must be called by the
     * <code>writeObject()</code> of the first serializable subclass.
     * <p/>
     * Subclasses may override if they have a specific field that must be present
     * on read before this implementation will work. Generally, the read determines
     * what must be serialized here, if anything.
     *
     * @param out the output stream
     */
    protected void doWriteObject(ObjectOutputStream out) throws IOException {
        out.writeFloat(loadFactor);
        out.writeInt(data.length);
        out.writeInt(size);
        for (Iterator<Map.Entry<K, V>> it = createEntrySetIterator(); it.hasNext();) {
            Map.Entry entry = it.next();
            out.writeObject(entry.getKey());
            out.writeObject(entry.getValue());
        }
    }

    /**
     * Reads the map data from the stream. This method must be overridden if a
     * subclass must be setup before <code>put()</code> is used.
     * <p/>
     * Serialization is not one of the JDK's nicest topics. Normal serialization will
     * initialise the superclass before the subclass. Sometimes however, this isn't
     * what you want, as in this case the <code>put()</code> method on read can be
     * affected by subclass state.
     * <p/>
     * The solution adopted here is to deserialize the state data of this class in
     * this protected method. This method must be called by the
     * <code>readObject()</code> of the first serializable subclass.
     * <p/>
     * Subclasses may override if the subclass has a specific field that must be present
     * before <code>put()</code> or <code>calculateThreshold()</code> will work correctly.
     *
     * @param in the input stream
     */
    protected void doReadObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        loadFactor = in.readFloat();
        int capacity = in.readInt();
        int size = in.readInt();
        init();
        data = new HashEntry[capacity];
        for (int i = 0; i < size; i++) {
            K key = (K) in.readObject();
            V value = (V) in.readObject();
            put(key, value);
        }
        threshold = calculateThreshold(data.length, loadFactor);
    }
    
    //-----------------------------------------------------------------------
    /**
     * Clones the map without cloning the keys or values.
     * <p/>
     * To implement <code>clone()</code>, a subclass must implement the
     * <code>Cloneable</code> interface and make this method public.
     *
     * @return a shallow clone
     */
    protected Object clone() {
        try {
            AbstractHashedMap cloned = (AbstractHashedMap) super.clone();
            cloned.data = new HashEntry[data.length];
            cloned.entrySet = null;
            cloned.keySet = null;
            cloned.values = null;
            cloned.modCount = 0;
            cloned.size = 0;
            cloned.init();
            cloned.putAll(this);
            return cloned;

        } catch (CloneNotSupportedException ex) {
            return null;  // should never happen
        }
    }

    /**
     * Compares this map with another.
     *
     * @param obj the object to compare to
     * @return true if equal
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Map == false) {
            return false;
        }
        Map map = (Map) obj;
        if (map.size() != size()) {
            return false;
        }
        try {
            for (Iterator<Map.Entry<K, V>> it = createEntrySetIterator(); it.hasNext();) {
                Map.Entry entry = it.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (value == null) {
                    if (map.get(key) != null || map.containsKey(key) == false) {
                        return false;
                    }
                } else {
                    if (value.equals(map.get(key)) == false) {
                        return false;
                    }
                }
            }
        } catch (ClassCastException ignored) {
            return false;
        } catch (NullPointerException ignored) {
            return false;
        }
        return true;
    }

    /**
     * Gets the standard Map hashCode.
     *
     * @return the hash code defined in the Map interface
     */
    public int hashCode() {
        int total = 0;
        Iterator it = createEntrySetIterator();
        while (it.hasNext()) {
            total += it.next().hashCode();
        }
        return total;
    }

    /**
     * Gets the map as a String.
     *
     * @return a string version of the map
     */
    public String toString() {
        if (size() == 0) {
            return "{}";
        }
        StringBuffer buf = new StringBuffer(32 * size());
        buf.append('{');

        for (Iterator<Map.Entry<K, V>> it = createEntrySetIterator(); it.hasNext(); ) {
            Map.Entry entry = it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            buf.append(key == this ? "(this Map)" : key).append('=').append(value == this ? "(this Map)" : value);

            if (it.hasNext()) {
                buf.append(',').append(' ');
            }
        }

        buf.append('}');
        return buf.toString();
    }
}