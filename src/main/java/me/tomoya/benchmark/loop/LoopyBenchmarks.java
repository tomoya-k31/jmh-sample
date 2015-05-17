package me.tomoya.benchmark.loop;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @see <a href="https://github.com/nitsanw/jmh-samples">copy</a>
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class LoopyBenchmarks {
    @Param({ "32", "1024", "32768" })
    int size;

    byte[] bunn;

    @Setup
    public void prepare() {
        bunn = new byte[size];
    }

    @Benchmark
    public void goodOldLoop(Blackhole fox) {
        for (int y = 0; y < bunn.length; y++) { // good old C style for (the win?)
            fox.consume(bunn[y]);
        }
    }

    @Benchmark
    public void sweetLoop(Blackhole fox) {
        for (byte bunny : bunn) { // syntactic sugar loop goodness
            fox.consume(bunny);
        }
    }

    @Benchmark
    public void goodOldLoopReturns(Blackhole fox) {
        byte[] sunn = bunn; // make a local copy of the field
        for (int y = 0; y < sunn.length; y++) {
            fox.consume(sunn[y]);
        }
    }

    @Benchmark
    public int sumOldLoop() {
        int sum = 0;
        for (int y = 0; y < bunn.length; y++) {
            sum += bunn[y];
        }
        return sum;
    }

    @Benchmark
    public int sumSweetLoop() {
        int sum = 0;
        for (byte bunny : bunn) {
            sum += bunny;
        }
        return sum;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(LoopyBenchmarks.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
