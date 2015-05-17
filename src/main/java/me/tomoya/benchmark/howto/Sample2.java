package me.tomoya.benchmark.howto;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Created by tomo on 15/05/17.
 */
@State(Scope.Thread)
public class Sample2 {

    private int testInt = 0;

    /**
     * Trial … ベンチマークの単位（ウォームアップから計測終了まで）
     * Iteration … ベンチマークのイテレーションの単位
     * Invocation … 計測対象の呼び出し単位
     */

    @Setup(Level.Iteration)
    public void prepare() {
        testInt = 0;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void methodB() {
        testInt++;
    }

    @TearDown(Level.Iteration)
    public void check() {
        System.out.println("testInt = " + testInt);
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Sample2.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .timeUnit(TimeUnit.MILLISECONDS)
                .forks(1)
                .jvmArgs("-ea") // assert enable
                .build();
        new Runner(opt).run();
    }
}
