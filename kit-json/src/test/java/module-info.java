/**
 * @author zyc
 */
module kit.json.test {
    opens red.zyc.toolkit.json.test;
    opens red.zyc.toolkit.json.test.model;
    requires kit.core;
    requires org.junit.jupiter.api;
    requires kit.json;
}