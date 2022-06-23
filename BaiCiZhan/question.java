package BaiCiZhan;
/*
* 一个数据表对应一个java类
* 表中的一条记录对应java类的一个对象
* 表中的一个字段对应java类的一个属性
*
* */
public class question {
    private int id;
    private String English;
    private String Chinese1;
    private String Chinese2;
    private String Chinese3;
    private String Chinese4;
    private String Answer;

    public question() {
    }

    public question(int id, String english, String chinese1, String chinese2, String chinese3, String chinese4, String answer) {
        this.id = id;
        this.English = english;
        this.Chinese1 = chinese1;
        this.Chinese2 = chinese2;
        this.Chinese3 = chinese3;
        this.Chinese4 = chinese4;
        this.Answer = answer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEnglish(String english) {
        English = english;
    }

    public void setChinese1(String chinese1) {
        Chinese1 = chinese1;
    }

    public void setChinese2(String chinese2) {
        Chinese2 = chinese2;
    }

    public void setChinese3(String chinese3) {
        Chinese3 = chinese3;
    }

    public void setChinese4(String chinese4) {
        Chinese4 = chinese4;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public int getId() {
        return id;
    }

    public String getEnglish() {
        return English;
    }

    public String getChinese1() {
        return Chinese1;
    }

    public String getChinese2() {
        return Chinese2;
    }

    public String getChinese3() {
        return Chinese3;
    }

    public String getChinese4() {
        return Chinese4;
    }

    public String getAnswer() {
        return Answer;
    }

    @Override
    public String toString() {
        return "question{" +
                "id=" + id +
                ", English='" + English + '\'' +
                ", Chinese1='" + Chinese1 + '\'' +
                ", Chinese2='" + Chinese2 + '\'' +
                ", Chinese3='" + Chinese3 + '\'' +
                ", Chinese4='" + Chinese4 + '\'' +
                ", Chinese4='" + Answer + '\'' +
                '}';
    }
}
