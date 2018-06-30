package ru.otus.shtyka.cache;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {

    private final static Logger logger = Logger.getLogger(CacheEngineImpl.class.getName());
    private static final int TIME_THRESHOLD_MS = 5;

    private int maxElements;
    private long lifeTimeMs;
    private long idleTimeMs;
    private boolean isEternal;

    private final Map<K, MyElement<K, V>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;

    public CacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }
//
//    public CacheEngineImpl() {
//        this(10, 0, 0, true);
//    }

    public void put(K key, V value) {
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        MyElement<K, V> element = new MyElement<>(key, value);
        elements.put(key, element);

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
        logger.log(Level.INFO, "Saved in cache: " + value);
    }

    public V get(K key) {
        MyElement<K, V> element = elements.get(key);
        if (element == null) {
            miss++;
            logger.log(Level.INFO, "Miss by key: " + key);
            return null;
        } else {
            hit++;
            element.setAccessed();
            logger.log(Level.INFO, "Getting from cache: " + element.getValue());
            return element.getValue();
        }
    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }

    public int getCurrentSize() {
        return elements.size();
    }

    public int getMaxSize() {
        return maxElements;
    }

    public String getCacheInfo() {
        return "Hit count: " + getHitCount() + "\n" +
                "Miss count: " + getMissCount() + "\n" +
                "The current size of the cache: " + getCurrentSize() + "\n" +
                "The max size of the cache: " + getMaxSize();
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask getTimerTask(final K key, Function<MyElement<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                MyElement<K, V> element = elements.get(key);
                if (element == null || isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())) {
                    elements.remove(key);
                    this.cancel();
                }
            }
        };
    }

    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }
}
