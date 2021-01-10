package red.zyc.toolkit.common;

import red.zyc.toolkit.core.function.ThrowableSupplier;

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
    public static final Booleans<?> TRUE = new Booleans<>(true, false, null);

    /**
     * 布尔值为false的布尔对象
     */
    public static final Booleans<?> FALSE = new Booleans<>(false, false, null);

    /**
     * 当前的布尔值
     */
    private final boolean value;

    /**
     * 当前布尔对象是否匹配，只要当前布尔对象成功执行过任何一个功能性方法，则认为当前布尔对象已匹配。
     */
    private final boolean match;

    /**
     * 根据当前布尔值设置的结果
     */
    private final T result;

    /**
     * 根据指定的布尔值、匹配值、结果构造布尔对象
     *
     * @param value  布尔值
     * @param match  匹配值
     * @param result 结果
     */
    private Booleans(boolean value, boolean match, T result) {
        this.value = value;
        this.match = match;
        this.result = result;
    }

    /**
     * 根据指定的布尔值返回相应的布尔对象
     *
     * @param b   布尔值
     * @param <T> 结果类型
     * @return 布尔对象
     */
    @SuppressWarnings("unchecked")
    public static <T> Booleans<T> iF(boolean b) {
        return (Booleans<T>) (b ? TRUE : FALSE);
    }

    /**
     * 根据{@link BooleanSupplier}提供的布尔值构造相应的布尔对象
     *
     * @param supplier 布尔值提供者
     * @param <T>      结果类型
     * @return 布尔对象
     */
    public static <T> Booleans<T> iF(BooleanSupplier supplier) {
        return iF(supplier.getAsBoolean());
    }

    /**
     * 如果当前布尔值不匹配的话则返回指定布尔值的布尔对象，否则返回自身。
     *
     * @param b 布尔值
     * @return 如果当前布尔值不匹配的话则返回指定布尔值的布尔对象，否则返回自身。
     */
    public Booleans<T> elseIf(boolean b) {
        return !value ? iF(b) : this;
    }

    /**
     * 如果当前布尔值不匹配的话则根据{@link BooleanSupplier}提供的布尔值构造相应的布尔对象，否则返回自身。
     *
     * @param supplier 布尔值提供者
     * @return 如果当前布尔值不匹配的话则根据 {@link BooleanSupplier}提供的布尔值构造相应的布尔对象，否则返回自身。
     */
    public Booleans<T> elseIf(BooleanSupplier supplier) {
        return !value ? iF(supplier) : this;
    }

    @SuppressWarnings("unchecked")
    private <R> Booleans<R> self() {
        return (Booleans<R>) this;
    }

    /**
     * 如果当前布尔对象未匹配且布尔值为true的话则执行该{@link Runnable}
     *
     * @param runnable 布尔值为true的情况下执行的代码块
     * @return 如果当前布尔对象未匹配且布尔值为true的话则返回一个新的布尔值为true且已匹配的布尔对象，否则返回自身。
     */
    public Booleans<T> run(Runnable runnable) {
        if (!match && value) {
            runnable.run();
            return new Booleans<>(true, true, result);
        }
        return this;
    }

    /**
     * 如果当前布尔对象未匹配且当前布尔值为true的话则返回一个新的布尔值为true且已匹配的布尔对象，并且结果由supplier提供，
     * 否则返回自身。
     *
     * @param supplier 结果提供者
     * @param <R>      结果类型
     * @return 如果当前布尔对象未匹配且当前布尔值为true的话则返回一个新的布尔值为true且已匹配的布尔对象，并且结果由supplier提供，否则返回自身。
     */
    public <R> Booleans<R> set(Supplier<? extends R> supplier) {
        return !match && value ? new Booleans<>(true, true, supplier.get()) : self();
    }

    /**
     * 如果当前布尔对象未匹配且布尔值为true的话则抛出supplier提供的{@link Throwable}
     *
     * @param supplier Throwable提供者
     * @param <X>      Throwable的类型
     * @return 返回当前布尔对象以便链式调用
     * @throws X supplier提供的{@link Throwable}类型
     */
    public <X extends Throwable> Booleans<T> exception(Supplier<? extends X> supplier) throws X {
        if (!match && value) {
            throw supplier.get();
        }
        return this;
    }

    public Booleans<T> orElse(Runnable runnable) {
        if (!match && !value) {
            runnable.run();
            return new Booleans<>(true, true, result);
        }
        return this;
    }

    public <R> Booleans<R> orElse(Supplier<? extends R> supplier) {
        return !match && !value ? new Booleans<>(true, true, supplier.get()) : self();
    }

    public <X extends Throwable> Booleans<T> orElse(ThrowableSupplier<? extends X> supplier) throws X {
        if (!match && !value) {
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
        return iF(value && other.value);
    }

    /**
     * 两个布尔对象执行逻辑或操作
     *
     * @param other 另一个布尔对象
     * @return 描述两个布尔对象执行逻辑或结果的布尔对象
     */
    public Booleans<T> or(Booleans<?> other) {
        return iF(value || other.value);
    }

    /**
     * 两个布尔对象执行逻辑异或操作，两个布尔值不同则为true，否则为false
     *
     * @param other 另一个布尔对象
     * @return 描述两个布尔对象执行逻辑异或结果的布尔对象
     */
    public Booleans<T> xor(Booleans<?> other) {
        return iF(value ^ other.value);
    }

    /**
     * 返回一个与当前布尔对象的布尔值相反的布尔对象
     *
     * @return 一个与当前布尔对象的布尔值相反的布尔对象
     */
    public Booleans<T> negate() {
        return new Booleans<>(!value, match, result);
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
