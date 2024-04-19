package co.edu.javeriana.bigtexts.util;

import org.apache.log4j.Logger;

/**
 * Clase con utilidades para la gestión de enumeraciones
 *
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
public class EnumUtils {

    /**
     * Gestor de la bitácora
     */
    private static final Logger logger = Logger.getLogger(EnumUtils.class.getName());

    /**
     * Método que dada una enumeracioón y un key retorna el valor de enumeración
     * correspondiente
     *
     * @param <T> La clase de enumeración
     * @param enumType La clase de enumeración
     * @param nombre El key a buscar
     * @return El enum encontrado
     */
    public static <T extends Enum<T>> T obtenerValorEnumByNombre(Class<T> enumType, String nombre) {
        T value = null;
        try {
            for (T enumValue : enumType.getEnumConstants()) {
                logger.info(enumValue.toString());
                if (enumValue.name().equalsIgnoreCase(nombre)) {
                    logger.info(nombre);
                    value = enumValue;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return value;
    }
}
