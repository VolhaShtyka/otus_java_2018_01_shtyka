package ru.otus.shtyka;

public class CacheMain {

    public static void main(String[] args) {
        System.out.println("Add to cache soft references");
        new CacheMain().referenceCacheExample(new byte[1024 * 1024]);
    }

    private void referenceCacheExample(Object object) {
        int size = 5;
        CacheEngine<Integer, Object> cache = new CacheEngineImpl<>(size, 0, 0, true);

        for (int i = 0; i < 10; i++) {
            cache.put(i, object);
        }

        for (int i = 0; i < 10; i++) {
            Object element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }
}
