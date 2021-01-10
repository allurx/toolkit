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

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zyc
 */
class BooleansTest {

    @Test
    void testLogicalAnd() {
        assertTrue(Booleans.iF(true).and(Booleans.iF(true)).value());
    }

    @Test
    void testLogicalOr() {
        assertTrue(Booleans.iF(true).or(Booleans.iF(true)).value());
    }

    @Test
    void testLogicalXor() {
        assertTrue(Booleans.iF(true).xor(Booleans.iF(false)).value());
    }

    @Test
    void testNegate() {
        assertTrue(Booleans.iF(false).negate().value());
    }

    @Test
    void testRunnable() {
        assertAll(() -> Booleans.iF(true).run(() -> System.out.println(true)));
    }

    @Test
    void testSupplier() {
        assertTrue(Booleans.iF(true).set(() -> true).result());
    }

    @Test
    void testThrow() {
        assertThrows(RuntimeException.class, () -> Booleans.iF(true).exception(RuntimeException::new));
    }

    @Test
    void test() {
        int i = 10;
        String result = Booleans.iF(i >= 1 && i <= 3).set(() -> "低")
                .elseIf(i >= 4 && i <= 6).set(() -> "中")
                .elseIf(i >= 7 && i <= 9).set(() -> "高")
                .orElse(() -> "未知")
                .result();
        System.out.println(result);
    }
}
