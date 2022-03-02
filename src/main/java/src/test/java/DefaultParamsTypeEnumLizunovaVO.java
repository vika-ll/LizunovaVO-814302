public enum DefaultParamsTypeEnumLizunovaVO {
    MODULES_COUNT,
    PRIORITY,
    DETAILS_REQ,
    PERFORMANCE,
    COMPLEXITY;

    public double getBaseValue() {
        switch (this) {
            case MODULES_COUNT:
                return 0.2;
            case PRIORITY:
                return 0.25;
            case DETAILS_REQ:
                return 0.1;
            case PERFORMANCE:
                return 0.2;
            case COMPLEXITY:
                return 0.25;
        }
        return 0;
    }
}