package com.souldev.security.enums;

public enum EstadoMensaje {
    SUCCESS("SUCCESS"),
    ERROR("ERROR");

    String descripcion;

    private EstadoMensaje(String descripcion) {
        this.descripcion = descripcion;
    }

    public static EstadoMensaje toEnum(String tipo) {
        switch (tipo) {
            case "SUCCESS":
                return EstadoMensaje.SUCCESS;
            case "ERROR":
                return EstadoMensaje.ERROR;
            default:
                return EstadoMensaje.valueOf(tipo);
        }
    }
}
