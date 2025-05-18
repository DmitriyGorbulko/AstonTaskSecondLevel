package org.example.module.first;

public class CustomHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 4;
    private Entry<K, V>[] buckets;
    private int size;

    public CustomHashMap() {
        this(DEFAULT_CAPACITY);
    }

    public CustomHashMap(int capacity) {
        buckets = new Entry[capacity];
    }

    public void put(K key, V value) {
        int index = getIndex(key);
        Entry<K, V> entry = buckets[index];

        while (entry != null) {
            if (keysEqual(entry.key, key)) {
                entry.value = value;
                return;
            }
            entry = entry.next;
        }

        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;
    }

    public V get(K key) {
        Entry<K, V> entry = findEntry(key);
        return entry != null ? entry.value : null;
    }

    public void remove(K key) {
        int index = getIndex(key);
        Entry<K, V> prev = null;
        Entry<K, V> current = buckets[index];

        while (current != null) {
            if (keysEqual(current.key, key)) {
                if (prev == null) {
                    buckets[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    private int getIndex(K key) {
        if (key == null) return 0;
        return Math.abs(key.hashCode()) % buckets.length;
    }

    private boolean keysEqual(K key1, K key2) {
        if (key1 == null) return key2 == null;
        return key1.equals(key2);
    }

    private Entry<K, V> findEntry(K key) {
        Entry<K, V> entry = buckets[getIndex(key)];
        while (entry != null) {
            if (keysEqual(entry.key, key)) {
                return entry;
            }
            entry = entry.next;
        }
        return null;
    }

    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public int size() {
        return size;
    }
}
