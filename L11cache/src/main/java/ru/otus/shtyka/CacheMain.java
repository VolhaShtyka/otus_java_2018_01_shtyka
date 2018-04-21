package ru.otus.shtyka;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class CacheMain {

    public static void main(String[] args) {
        System.out.println("Add to cache weak references");
        new CacheMain().referenceCacheExample(new WeakReference<>(new byte[1024 * 1024]));
        System.out.println("=========================================");
        System.out.println("Add to cache soft references");
        new CacheMain().referenceCacheExample(new SoftReference(new byte[1024 * 1024]));
    }

    private void referenceCacheExample(Reference reference) {
        int size = 5;
        CacheEngine<Integer, Reference> cache = new CacheEngineImpl<>(size, 0, 0, true);

        for (int i = 0; i < 10; i++) {
            cache.put(new MyElement<>(i, reference));
        }

        System.gc();

        for (int i = 0; i < 10; i++) {
            MyElement<Integer, Reference> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue().get() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }
}
