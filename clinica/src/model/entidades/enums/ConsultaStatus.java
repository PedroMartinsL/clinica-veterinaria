package model.entidades.enums;

public enum ConsultaStatus {

    AGENDADO(1),
    ANDAMENTO(2),
    DIVIDA(3),   
    CANCELADA(4),
    CONCLUIDA(5);

    private int code; //enum 

    private ConsultaStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ConsultaStatus valueOf(int code) {
        for (ConsultaStatus value : ConsultaStatus.values()) {
            if (value.getCode() == code)
                return value;
        }
        throw new IllegalArgumentException("Invalid OrderStatus code");
    }
}


