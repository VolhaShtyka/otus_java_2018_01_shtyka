import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class Main {
    private static int THREADS_COUNT = 4;

    public static void main(String[] args) {
        List<Integer> randomIntegers = createRandomIntList();
        final CountDownLatch latch = new CountDownLatch(THREADS_COUNT);
        List<MyThread> threads = assignmentMultiThreadsList(randomIntegers, latch);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Integer> sortedIntegers = mergeThreadsResults(threads);
        Collections.sort(randomIntegers);
        System.out.println(sortedIntegers);
        System.out.println(randomIntegers);

        System.out.println(sortedIntegers.equals(randomIntegers));
    }

    private static List<Integer> mergeThreadsResults(final List<MyThread> threads) {
        if (threads.isEmpty()) {
            return new ArrayList<>();
        }
        int[] arr1 = ((List<Integer>) threads.get(0).getResult()).stream().mapToInt(i -> i).toArray();
        for (int t = 1; t < threads.size(); t++) {
            int[] arr2 = ((List<Integer>) threads.get(t).getResult()).stream().mapToInt(i -> i).toArray();
            int[] arr3 = new int[arr1.length + arr2.length];
            int i = 0, j = 0, k = 0;
            while (i < arr1.length && j < arr2.length) {
                if (arr1[i] < arr2[j]) {
                    arr3[k] = arr1[i];
                    i++;
                } else {
                    arr3[k] = arr2[j];
                    j++;
                }
                k++;
            }

            if (i < arr1.length) {
                System.arraycopy(arr1, i, arr3, k, (arr1.length - i));
            }
            if (j < arr2.length) {
                System.arraycopy(arr2, j, arr3, k, (arr2.length - j));
            }
            arr1 = arr3;
        }
        return Arrays.stream(arr1).boxed().collect(Collectors.toList());
    }

    private static List<MyThread> assignmentMultiThreadsList(final List<Integer> list, final CountDownLatch latch) {
        List<MyThread> threads = new ArrayList<>();
        double explicitStep = list.size() * 1.0 / THREADS_COUNT;
        int step = (int) (explicitStep % 1.0 > 0 ? explicitStep + 1 : explicitStep);

        for (int i = 0; i < list.size(); i = i + step) {
            List<Integer> temp;
            if (list.size() > i + step) {
                temp = list.subList(i, i + step);
            } else {
                temp = list.subList(i, list.size());
            }
            SortHelper sortHelper = new SortHelper();
            threads.add(new MyThread(latch, tem -> sortHelper.sort(temp)));
        }
        threads.forEach(t -> new Thread(t).start());
        return threads;
    }

    private static List<Integer> createRandomIntList() {
        List<Integer> randomIntegers = Collections.synchronizedList(new ArrayList<Integer>());
        for (int i = 0; i < 101; i++) {
            randomIntegers.add(i);
        }

        Collections.shuffle(randomIntegers);
        return randomIntegers;
    }
}
