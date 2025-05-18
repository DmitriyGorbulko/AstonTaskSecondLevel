package org.example.module.first;

public class Main {
    public static void main(String[] args) {
        CustomHashMap<Integer, String> map = new CustomHashMap<>();

        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, "Three");
        map.put(null, "Null Key");

        System.out.println("Key 1: " + map.get(1));
        System.out.println("Key 2: " + map.get(2));
        System.out.println("Key 3: " + map.get(3));
        System.out.println("Key null: " + map.get(null));
        System.out.println("Key 4 not exists: " + map.get(4));

        map.remove(2);
        System.out.println("Key 2 after remove: " + map.get(2));

        map.put(1, "One Updated");
        System.out.println("Key 1 after update: " + map.get(1));

        System.out.println("Size: " + map.size());
    }
}

