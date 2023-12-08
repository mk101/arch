open module kolesov.maxim.viewerui {
    requires javafx.controls;
    requires javafx.fxml;
    requires feign.core;
    requires feign.form;
    requires lombok;
    requires org.slf4j;
    requires kafka.clients;
    requires ch.qos.logback.core;
    requires ch.qos.logback.classic;
    requires com.fasterxml.jackson.databind;
    requires feign.slf4j;
    requires feign.jackson;

    exports kolesov.maxim.viewerui;
    exports kolesov.maxim.viewerui.controller;
}