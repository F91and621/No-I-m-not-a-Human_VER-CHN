public class GuestFeatures {

    private boolean teethNeat;
    private boolean handsClean;
    private boolean eyesRed;
    private boolean earsClean;

    private String teethImageKey;
    private String handsImageKey;
    private String eyesImageKey;
    private String earsImageKey;

    public GuestFeatures(
            boolean teethNeat,
            boolean handsClean,
            boolean eyesRed,
            boolean earsClean,
            String teethImageKey,
            String handsImageKey,
            String eyesImageKey,
            String earsImageKey
    ) {
        this.teethNeat = teethNeat;
        this.handsClean = handsClean;
        this.eyesRed = eyesRed;
        this.earsClean = earsClean;

        this.teethImageKey = teethImageKey;
        this.handsImageKey = handsImageKey;
        this.eyesImageKey = eyesImageKey;
        this.earsImageKey = earsImageKey;
    }

    public boolean isTeethNeat() {
        return teethNeat;
    }

    public boolean isHandsClean() {
        return handsClean;
    }

    public boolean isEyesRed() {
        return eyesRed;
    }

    public boolean isEarsClean() {
        return earsClean;
    }

    public String getTeethImageKey() {
        return teethImageKey;
    }

    public String getHandsImageKey() {
        return handsImageKey;
    }

    public String getEyesImageKey() {
        return eyesImageKey;
    }

    public String getEarsImageKey() {
        return earsImageKey;
    }
}
