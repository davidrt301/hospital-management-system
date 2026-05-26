package com.davidrt301.medicare;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utilidad para generar claves secretas seguras para JWT.
 */
public class KeyGeneratorUtil {

    public static void main(String[] args) {
        // 1. Crear un generador aleatorio seguro
        SecureRandom secureRandom = new SecureRandom();
        
        // 2. Crear un array de 32 bytes (256 bits)
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);
        
        // 3. Codificar en Base64 para usarlo en application.yml
        String base64Key = Base64.getEncoder().encodeToString(key);
        
        System.out.println("Nueva clave secreta (Base64): " + base64Key);
    }
}
