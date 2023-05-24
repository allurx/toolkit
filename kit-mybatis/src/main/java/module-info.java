/**
 * @author zyc
 */
module kit.mybatis {
    exports red.zyc.toolkit.mybatis.handler;
    requires kit.json;
    requires org.mybatis;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
}