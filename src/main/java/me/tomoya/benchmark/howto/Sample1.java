package me.tomoya.benchmark.howto;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Created by tomo on 15/05/17.
 */
public class Sample1 {

    public static void main(String[] args) throws RunnerException {

        // default値は、org.openjdk.jmh.runner.Defaultsを参照
        Options opt = new OptionsBuilder()
                .include(Sample1.class.getSimpleName())     // なくてもいけそう
                .warmupIterations(5)                        // ウォームアップ時の繰り返し回数（計測には含まれない）
                .measurementIterations(5)                   // 計測を行う回数
                .mode(Mode.AverageTime)                     // ベンチマークのモード(Throughput/AverageTime/SampleTime/SingleShotTime)
                .timeUnit(TimeUnit.MILLISECONDS)            // 結果表示の時間単位
                .forks(1)                                   // フォークの数
                .jvmArgs("-Xms2048m", "-Xmx2048m", "-XX:MaxDirectMemorySize=512M")
                // JVMオプションも設定できる
                .result("results.csv")                      // 結果の出力先
                .resultFormat(ResultFormatType.CSV)         // 結果の出力フォーマット
                .build();

        new Runner(opt).run();
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void sampleBench1() {

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Threads(8)
    @Fork(value = 3, jvmArgsAppend = {"-server", "-disablesystemassertions"})
    public void sampleBench2() {

    }

}
