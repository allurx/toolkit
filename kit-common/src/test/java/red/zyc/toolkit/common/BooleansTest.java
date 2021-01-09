/*
 * Copyright 2019 the original author or authors.
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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author zyc
 */
class BooleansTest {

    @Test
    void testLogicalAnd() {
        assertTrue(Booleans.of(true).and(Booleans.of(true)).value());
    }

    @Test
    void testLogicalOr() {
        assertTrue(Booleans.of(true).or(Booleans.of(true)).value());
    }

    @Test
    void testLogicalXor() {
        assertTrue(Booleans.of(true).xor(Booleans.of(false)).value());
    }

    @Test
    void testNegate() {
        assertTrue(Booleans.of(false).negate().value());
    }

    @Test
    void testRunnable() {
        assertTrue(Booleans.of(true).ifTrue(() -> System.out.println(true)).value());
        assertFalse(Booleans.of(false).ifFalse(() -> System.out.println(false)).value());
    }

    @Test
    void testSupplier() {
        assertTrue(Booleans.of(true).ifTrue(() -> true).result());
        assertFalse(Booleans.of(false).ifFalse(() -> false).result());
    }

    @Test
    void testThrow() {
        assertTrue(Booleans.of(true).ifTrue(() -> true).ifFalseThrow(RuntimeException::new).result());
        assertFalse(Booleans.of(false).ifFalse(() -> false).ifTrueThrow(RuntimeException::new).result());
    }
}
