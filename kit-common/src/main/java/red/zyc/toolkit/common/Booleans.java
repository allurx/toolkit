package red.zyc.toolkit.common;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * 一个包含布尔值的容器，提供了许多有用的功能性方法。
 *
 * @param <T> 根据布尔值设置的结果类型
 * @author zyc
 */
public final class Booleans<T> {

    /**
     * 布尔值为true的布尔对象
     */
    public static final Booleans<?> TRUE = new Booleans<>(true, null);

    /**
     * 布尔值为false的布尔对象
     */
    public static final Booleans<?> FALSE = new Booleans<>(false, null);

    /**
     * 当前的布尔值
     */
    private final boolean value;

    /**
     * 根据当前布尔值设置的结果
     */
    private final T result;

    /**
     * 根据指定的布尔值构和结果构造布尔对象
     *
     * @param value  布尔值
     * @param result 结果
     */
    private Booleans(boolean value, T result) {
        this.value = value;
        this.result = result;
    }

    /**
     * 根据指定的布尔值返回相应的布尔对象
     *
     * @param value 布尔值
     * @return 布尔对象
     */
    @SuppressWarnings("unchecked")
    public static <T> Booleans<T> of(boolean value) {
        return (Booleans<T>) (value ? TRUE : FALSE);
    }

    /**
     * 根据{@link BooleanSupplier}提供的布尔值构造相应的布尔对象
     *
     * @param supplier 布尔值提供者
     * @return 布尔对象
     */
    public static <T> Booleans<T> of(BooleanSupplier supplier) {
        return of(supplier.getAsBoolean());
    }

    @SuppressWarnings("unchecked")
    private <R> Booleans<R> self() {
        return (Booleans<R>) this;
    }

    /**
     * 如果当前布尔值为true的话则执行该{@link Runnable}
     *
     * @param runnable 布尔值为true的情况下执行的代码块
     * @return 返回当前布尔对象以便链式调用
     */
    public Booleans<T> ifTrue(Runnable runnable) {
        if (value) {
            runnable.run();
        }
        return this;
    }

    /**
     * 如果当前布尔值为true的话则返回一个新的布尔值为true的布尔对象，并且结果由supplier提供，
     * 否则返回自身。
     *
     * @param supplier 结果提供者
     * @param <R>      结果类型
     * @return 如果当前布尔值为true的话则返回一个新的布尔值为true的布尔对象，并且结果由supplier提供，否则的返回自身。
     */
    public <R> Booleans<R> ifTrue(Supplier<R> supplier) {
        return value ? new Booleans<>(true, supplier.get()) : self();
    }

    /**
     * 如果当前布尔值为true的话则抛出supplier提供的{@link Throwable}
     *
     * @param supplier Throwable提供者
     * @param <X>      Throwable的类型
     * @return 返回当前布尔对象以便链式调用
     * @throws X supplier提供的{@link Throwable}
     */
    public <X extends Throwable> Booleans<T> ifTrueThrow(Supplier<X> supplier) throws X {
        if (value) {
            throw supplier.get();
        }
        return this;
    }

    /**
     * 如果当前布尔值为false的话则执行该{@link Runnable}
     *
     * @param runnable 布尔值为false的情况下执行的代码块
     * @return 返回当前布尔对象以便链式调用
     */
    public Booleans<T> ifFalse(Runnable runnable) {
        if (!value) {
            runnable.run();
        }
        return this;
    }

    /**
     * 如果当前布尔值为false的话则返回一个新的布尔值为false的布尔对象，并且结果由supplier提供，
     * 否则返回自身。
     *
     * @param supplier 结果提供者
     * @param <R>      结果类型
     * @return 如果当前布尔值为false的话则返回一个新的布尔值为false的布尔对象，并且结果由supplier提供，否则的返回自身。
     */
    public <R> Booleans<R> ifFalse(Supplier<R> supplier) {
        return !value ? new Booleans<>(false, supplier.get()) : self();
    }

    /**
     * 如果当前布尔值为false的话则抛出supplier提供的{@link Throwable}
     *
     * @param supplier Throwable提供者
     * @param <X>      Throwable的类型
     * @return 返回当前布尔对象以便链式调用
     * @throws X supplier提供的{@link Throwable}
     */
    public <X extends Throwable> Booleans<T> ifFalseThrow(Supplier<X> supplier) throws X {
        if (!value) {
            throw supplier.get();
        }
        return this;
    }

    /**
     * 两个布尔对象执行逻辑与操作
     *
     * @param other 另一个布尔对象
     * @return 描述两个布尔对象执行逻辑与结果的布尔对象
     */
    public Booleans<T> and(Booleans<?> other) {
        return of(value && other.value);
    }

    /**
     * 两个布尔对象执行逻辑或操作
     *
     * @param other 另一个布尔对象
     * @return 描述两个布尔对象执行逻辑或结果的布尔对象
     */
    public Booleans<T> or(Booleans<?> other) {
        return of(value || other.value);
    }

    /**
     * 两个布尔对象执行逻辑异或操作，两个布尔值不同则为true，否则为false
     *
     * @param other 另一个布尔对象
     * @return 描述两个布尔对象执行逻辑异或结果的布尔对象
     */
    public Booleans<T> xor(Booleans<?> other) {
        return of(value ^ other.value);
    }

    /**
     * 返回一个与当前布尔对象的布尔值相反的布尔对象
     *
     * @return 一个与当前布尔对象的布尔值相反的布尔对象
     */
    public Booleans<T> negate() {
        return new Booleans<>(!value, result);
    }

    /**
     * 返回当前布尔对象的布尔值
     *
     * @return 当前布尔对象的布尔值
     */
    public boolean value() {
        return value;
    }

    /**
     * 根据当前布尔值设置的结果
     *
     * @return 根据当前布尔值设置的结果
     */
    public T result() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Booleans<?> booleans = (Booleans<?>) o;
        return value == booleans.value && Objects.equals(result, booleans.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, result);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "Booleans[", "]")
                .add("value=" + value)
                .add("result=" + result)
                .toString();
    }
}
