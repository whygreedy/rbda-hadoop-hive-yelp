import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class MinMaxCountTuple implements Writable {

    private long min = Long.MAX_VALUE;
    private long max = Long.MIN_VALUE;
    private long count = 0;

    public MinMaxCountTuple() {
    }

    public MinMaxCountTuple(long min, long max, long count) {
        this.min = min;
        this.max = max;
        this.count = count;
    }

    public void write(DataOutput out) throws IOException {
        out.writeLong(min);
        out.writeLong(max);
        out.writeLong(count);
    }

    public void readFields(DataInput in) throws IOException {
        min = in.readLong();
        max = in.readLong();
        count = in.readLong();
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

    public long getCount() {
        return count;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return String.format("%d\t%d\t%d", min, max, count);
    }
}
