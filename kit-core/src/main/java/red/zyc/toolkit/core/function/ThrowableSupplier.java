package red.zyc.toolkit.core.function;

import java.util.function.Supplier;

/**
 * Throwable值提供者
 *
 * @param <T> Throwable的类型
 * @author zyc
 */
@FunctionalInterface
public interface ThrowableSupplier<T extends Throwable> extends Supplier<T> {
}
