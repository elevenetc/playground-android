package su.levenetc.androidplayground.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import su.levenetc.androidplayground.exceptions.ObjectDeserializationException;
import su.levenetc.androidplayground.exceptions.ObjectSerialisationException;
import su.levenetc.androidplayground.utils.Utils;

/**
 * Created by eugene.levenetc on 24/12/2016.
 */
public class WorkRunnerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ExecutorService executor = Executors.newFixedThreadPool(1);

        Work work = new Work(10);
        Callable callable;

        try {
            byte[] bytes = Utils.toByteArray(work);
            callable = Utils.toObject(bytes);

            final Future<Result> future = executor.submit(callable);
            Result result = future.get();
            int value = result.getValue();
            if (value == 0) {

            }

        } catch (ObjectSerialisationException | ObjectDeserializationException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    private static class Work implements Serializable, Callable<Result> {

        private final int input;

        public Work(int input) {
            this.input = input;
        }

        @Override
        public Result call() {
            int result = input;
            for (int i = 0; i < 10; i++) {
                result++;
            }
            return new Result(result);
        }
    }

    public static class Result implements Serializable {

        private int value;

        public Result(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
