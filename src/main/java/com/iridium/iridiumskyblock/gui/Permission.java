package com.iridium.iridiumskyblock.gui;

import com.iridium.iridiumskyblock.XMaterial;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Permission {
    String id();
    String name();
    XMaterial icon();
}
