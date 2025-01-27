package com.nqlo.ch.mkt.service.utils;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class UpdateUtils {
    private UpdateUtils() {}
    
    //Funcion que antes de actualizar se fija si el nuevo valor es  !null y !actual
    public static <T> void updateIfChanged(T newValue, Supplier<T> getter, Consumer<T> setter) {
        if (newValue != null && !(newValue instanceof String && ((String) newValue).isEmpty()) && !Objects.equals(newValue, getter.get())) {
            setter.accept(newValue);
        }
    }
}