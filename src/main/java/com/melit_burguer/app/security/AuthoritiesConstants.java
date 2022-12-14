package com.melit_burguer.app.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String TRABAJADOR_JEFE = "ROLE_TRABAJADOR_JEFE";

    public static final String TRABAJADOR = "ROLE_TRABAJADOR";

    public static final String TRABAJADOR_COCINA = "ROLE_TRABAJADOR_COCINA";

    private AuthoritiesConstants() {
    }
}
