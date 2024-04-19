package co.edu.javeriana.bigtexts.enums;

/**
 * Enumeración con las opciones de entrega del resultado
 * @author Wilson Alzate Calderón
 */
public enum DeliveryMethodEnum {
    HDFS("HDFS"),
    FTP("FTP");
    private final String name;

    /**
     * Constructor de la enumeración
     * @param name Nombre del enum
     */
    private DeliveryMethodEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    
}
