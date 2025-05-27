package oort.cloud.openmarket.enums;

public enum CommonEnums {
    ACCESS_TOKEN_PAYLOAD("accessTokenPayload")
    ;

    private final String value;
    CommonEnums(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
