import java.util.Comparator;

public class MergeSort {

    static Comparator c;

    private static void merge(Object[] a, Object[] aux, int lo, int mid, int hi)
    {
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++)
        {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    private static boolean less(Object v, Object w) { return c.compare(v, w) < 0; }

    private static void sort(Object[] a, Object[] aux, int lo, int hi)
    {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }

    public static void sort(Object[] a, Comparator comparator)
    {
        c = comparator;
        Object[] aux = new Object[a.length];
        sort(a, aux, 0, a.length - 1);
    }

}
