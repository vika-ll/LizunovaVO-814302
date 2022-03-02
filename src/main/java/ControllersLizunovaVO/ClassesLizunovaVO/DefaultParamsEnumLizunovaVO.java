package ClassesLizunovaVO;

import java.util.stream.Stream;


public enum DefaultParamsEnumLizunovaVO {
    LOW("Низкая"),
    MEDIUM("Средняя"),
    HIGH("Высокая");

    private final String text;

    DefaultParamsEnumLizunovaVO(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    int getValue() {
        switch (this) {
            case LOW:
                return 1;
            case MEDIUM:
                return 2;
            case HIGH:
                return 3;
        }
        return 0;
    }

    int getValueReversed() {
        switch (this) {
            case LOW:
                return 3;
            case MEDIUM:
                return 2;
            case HIGH:
                return 1;
        }
        return 0;
    }

    public static DefaultParamsEnumLizunovaVO fromInteger(int x) {
        switch(x) {
            case 1:
                return LOW;
            case 2:
                return MEDIUM;
            case 3:
                return HIGH;
        }
        return null;
    }

    public double getCalcValue(DefaultParamsTypeEnumLizunovaVO type) {
        switch (type) {
            case COMPLEXITY:
            case PRIORITY:
                return type.getBaseValue() * this.getValue();
            case DETAILS_REQ:
                return type.getBaseValue() * this.getValueReversed();
            default:
                return 0;
        }
    }

    public static String[] names() {
        return Stream.of(DefaultParamsEnumLizunovaVO.values()).map(dd -> dd.toString()).toArray(String[]::new);
    }
}