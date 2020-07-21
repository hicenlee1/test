package jksj.concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 利用原子类实现不变性.
 */
public class Immutability {
    final AtomicReference<WMRange> rf = new AtomicReference<>(new WMRange(0, 0));

    void setUpper(int upper) {
        while(true) {
            WMRange or = rf.get();
            if (or.low > upper) {
                throw new IllegalArgumentException();
            }
            WMRange nr = new WMRange(or.low, upper);
            if (rf.compareAndSet(or, nr)) {
                return;
            }
        }
    }
}

class WMRange {
    final int low;
    final int upper;
    public WMRange(int low, int upper) {
        this.low = low;
        this.upper = upper;
    }


}


