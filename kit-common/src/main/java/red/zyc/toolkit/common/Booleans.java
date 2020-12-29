package red.zyc.toolkit.common;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
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

    @SuppressWarnings("unchecked")
    private <R> Booleans<R> self() {
        return (Booleans<R>) this;
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
     * 根据当前布尔值设置的结果
     *
     * @return 根据当前布尔值设置的结果
     */
    public T get() {
        return result;
    }

}
