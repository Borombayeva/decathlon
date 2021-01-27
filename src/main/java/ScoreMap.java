public enum ScoreMap {

    RUN_100M("run100m", 25.4347, 18.0, 1.81, "s", 1, false),
    LONG_JUMP("longJump", 0.14354, 220.0, 1.4, "m", 2, true),
    SHOT_PUT("shotPut", 51.39, 1.5, 1.05, "m", 3,false),
    HIGH_JUMP("highJump", 0.8465, 75.0, 1.42, "m", 4, true),
    RUN_400M("run400m", 1.53775, 82.0, 1.81, "s", 5, false),
    RUN_110M("run110m", 5.74352, 28.5, 1.92, "s", 6, false),
    DISCUS_THROW("discusThrow", 12.91, 4.0, 1.1, "m", 7, false),
    POLE_VAULT("poleVault", 0.2797, 100.0, 1.35, "m", 8, true),
    JAVELIN_THROW("javelinThrow", 10.14, 7.0, 1.08, "m", 9, false),
    RUN_1500M("run1500m", 0.03768, 480.0, 1.85, "m:s", 10, false),
    ;

    public final String event;
    public final Double a;
    public final Double b;
    public final Double c;
    public final String unit;
    public final Integer index;
    public final Boolean needConvertToCm;

    ScoreMap(String event, Double a, Double b, Double c, String unit, Integer index, Boolean needConvertToCm) {
        this.event = event;
        this.a = a;
        this.b = b;
        this.c = c;
        this.unit = unit;
        this.index = index;
        this.needConvertToCm = needConvertToCm;
    }

    public static ScoreMap valueFrom(Integer index) {
        for (ScoreMap value : values()) {
            if (value.index.equals(index)) {
                return value;
            }
        }
        throw new RuntimeException("Score mapping for " + index + " not found");
    }

}
