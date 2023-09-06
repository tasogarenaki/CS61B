package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Terry
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    private int loadFactor() {
        return size / buckets.length;
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }

        return buckets[hash(key)].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            return;
        }
        size = buckets[hash(key)].containsKey(key) ? size : size + 1;
        buckets[hash(key)].put(key, value);

        if (loadFactor() > MAX_LF) {
            resize(buckets.length * 2);
        }
    }

    /**
     * Resizes the array of ArrayMap.
     * @param capacity  new size
     */
    private void resize(int capacity) {
        ArrayMap<K, V>[] old_buckets = buckets;
        Set<K> keys = keySet();

        buckets = new ArrayMap[capacity];
        clear();

        for (K key : keys) {
            int old_hash = Math.floorMod(key.hashCode(), old_buckets.length);
            V value = old_buckets[old_hash].get(key);
            buckets[hash(key)].put(key, value);
            size++;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> key_set = new HashSet<>();
        for (ArrayMap<K, V> bucket : buckets) {
            key_set.addAll(bucket.keySet());
        }
        return key_set;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        V value = buckets[hash(key)].remove(key);

        if (size < MAX_LF / 4) {
            resize(buckets.length / 2);
        }
        return value;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (get(key).equals(value)) {
            return remove(key);
        } else {
            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        Set<K> key_set = keySet();
        return key_set.iterator();
    }
}
