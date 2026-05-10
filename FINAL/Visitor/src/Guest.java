public class Guest {
    public String name;
    public String occupation;
    public boolean isVisitor;

    // --- 眼睛特征 ---
    public SignType eyesSign;
    public String eyesTexture;
    public String eyesExcuse;

    // --- 牙齿特征 ---
    public SignType teethSign;
    public String teethTexture;
    public String teethExcuse;

    // --- 手部特征 ---
    public SignType handsSign;
    public String handsTexture;
    public String handsExcuse;

    // 构造函数：初始化基本信息
    public Guest(String name, String occupation, boolean isVisitor) {
        this.name = name;
        this.occupation = occupation;
        this.isVisitor = isVisitor;

        // 默认全设为正常，后续针对伪人单独修改破绽
        this.eyesSign = SignType.NORMAL;
        this.eyesTexture = "eyes_normal.png";
        this.eyesExcuse = "我昨晚睡得很好。";

        this.teethSign = SignType.NORMAL;
        this.teethTexture = "teeth_normal.png";
        this.teethExcuse = "我的牙齿一直很健康。";

        this.handsSign = SignType.NORMAL;
        this.handsTexture = "hands_normal.png";
        this.handsExcuse = "我每天都洗手。";
    }

    // 后台设置破绽的方法（方便我们捏人）
    public void setEyesTrait(SignType sign, String texture, String excuse) {
        this.eyesSign = sign;
        this.eyesTexture = texture;
        this.eyesExcuse = excuse;
    }

    public void setHandsTrait(SignType sign, String texture, String excuse) {
        this.handsSign = sign;
        this.handsTexture = texture;
        this.handsExcuse = excuse;
    }

    public void setTeethTrait(SignType sign, String texture, String excuse) {
        this.teethSign = sign;
        this.teethTexture = texture;
        this.teethExcuse = excuse;
    }


    // ... 其他部位的 set 方法同理
}