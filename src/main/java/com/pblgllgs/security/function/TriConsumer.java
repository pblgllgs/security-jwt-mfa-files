package com.pblgllgs.security.function;
/*
 *
 * @author pblgl
 * Created on 04-04-2024
 *
 */

@FunctionalInterface
public interface TriConsumer<T, U, W> {
    void accept(T t, U u, W w);
}
