package lab14;

import lab14lib.*;

public class AcceleratingSawToothGenerator implements Generator {
    private int state;
    private int period;
    private double factor;

    public AcceleratingSawToothGenerator (int period, double factor) {
        state = 0;
        this.period = period;
        this.factor = factor;
    }

    @Override
    public double next() {
        state += 1;
        if (state == period) {
            state = 0;
            period *= factor;
        }
        return normalize(state);
    }

    private double normalize(int num) {
        double result = (double) num * 2 / period;
        return result - 1;
    }
}
