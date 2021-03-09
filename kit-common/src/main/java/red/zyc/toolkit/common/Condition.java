/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package red.zyc.toolkit.common;

import red.zyc.toolkit.core.function.ThrowableSupplier;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * 提供了if、else if、else的分支逻辑判断函数，
 * 例如以下分支逻辑判断：<br>
 * <pre>
 *     if(condition){
 *        System.out.println("if")
 *     }else if(condition){
 *        System.out.println("elseIf")
 *     }else if(condition){
 *        System.out.println("elseIf")
 *     }else{
 *        System.out.println("else")
 *     }
 * </pre>
 * 可以改写成：<br>
 * <pre>
 *      iF(condition).run(() -> System.out.println("if"))
 *      .elseIf(condition).run(() -> System.out.println("elseIf"))
 *      .elseIf(condition).run(() -> System.out.println("elseIf"))
 *      .orElse(() -> System.out.println("orElse"))
 * </pre>
 *
 * @author zyc
 */
public final class Condition<T> {

    private static final Condition<?> TRUE = new Condition<>(true, null);
    private static final Condition<?> FALSE = new Condition<>(false, null);
    private final Branch<T> valid = new ValidBranch();
    private final Branch<T> invalid = new InvalidBranch();
    private final End end = new End();
    private final boolean value;
    private final T result;

    private Condition(boolean value, T result) {
        this.value = value;
        this.result = result;
    }

    @SuppressWarnings("unchecked")
    public static <T> Branch<T> iF(boolean b) {
        return b ? (Branch<T>) TRUE.valid : (Branch<T>) FALSE.invalid;
    }

    public static <T> Branch<T> iF(BooleanSupplier supplier) {
        return iF(supplier.getAsBoolean());
    }

    public Branch<T> elseIf(boolean b) {
        return value ? invalid : iF(b);
    }

    public Branch<T> elseIf(BooleanSupplier supplier) {
        return value ? invalid : iF(supplier);
    }

    /**
     * 如果当前布尔值为false的话则执行相应的{@link Runnable}
     *
     * @param runnable 需要执行的{@link Runnable}
     * @return 结束
     */
    public End orElse(Runnable runnable) {
        if (!value) {
            runnable.run();
        }
        return end;
    }

    /**
     * 如果当前布尔值为false的话则设置{@link Supplier}提供的值
     *
     * @param supplier 提供值的{@link Supplier}
     * @return 结束
     */
    public End orElse(Supplier<? extends T> supplier) {
        return !value ? new Condition<T>(true, supplier.get()).end : end;
    }

    /**
     * 如果当前布尔值为false的话则抛出{@link Supplier}提供的异常
     *
     * @param supplier 提供异常的{@link Supplier}
     * @param <X>      异常类型
     * @return 结束
     * @throws X 异常
     */
    public <X extends Throwable> End orElse(ThrowableSupplier<? extends X> supplier) throws X {
        if (!value) {
            throw supplier.get();
        }
        return end;
    }

    /**
     * 获取当前结果
     *
     * @return 当前结果
     */
    public T get() {
        return result;
    }

    class End {

        public T get() {
            return Condition.this.get();
        }
    }

    /**
     * 分支
     *
     * @param <R> 分支结果类型
     */
    interface Branch<R> {

        /**
         * 执行{@link Runnable}
         *
         * @param runnable 需要执行的{@link Runnable}
         * @return {@link Condition}以便继续执行其它逻辑分支
         */
        Condition<R> run(Runnable runnable);

        /**
         * 设置{@link Supplier}提供的值
         *
         * @param supplier 提供值的{@link Supplier}
         * @return {@link Condition}以便继续执行其它逻辑分支
         */
        Condition<R> set(Supplier<? extends R> supplier);

        /**
         * 抛出{@link Supplier}提供的异常
         *
         * @param supplier 提供异常的{@link Supplier}
         * @param <X>      异常类型
         * @return {@link Condition}以便继续执行其它逻辑分支
         * @throws X 异常
         */
        <X extends Throwable> Condition<R> throwable(Supplier<? extends X> supplier) throws X;
    }

    /**
     * 当前布尔值为true的分支
     */
    private class ValidBranch implements Branch<T> {

        @Override
        public Condition<T> run(Runnable runnable) {
            runnable.run();
            return Condition.this;
        }

        @Override
        public Condition<T> set(Supplier<? extends T> supplier) {
            return new Condition<>(true, supplier.get());
        }

        @Override
        public <X extends Throwable> Condition<T> throwable(Supplier<? extends X> supplier) throws X {
            throw supplier.get();
        }

    }

    /**
     * 只要有一个分支为true，接下来的分支都是无效的。
     */
    private class InvalidBranch implements Branch<T> {

        @Override
        public Condition<T> run(Runnable runnable) {
            return Condition.this;
        }

        @Override
        public Condition<T> set(Supplier<? extends T> supplier) {
            return Condition.this;
        }

        @Override
        public <X extends Throwable> Condition<T> throwable(Supplier<? extends X> supplier) throws X {
            return Condition.this;
        }
    }

}
