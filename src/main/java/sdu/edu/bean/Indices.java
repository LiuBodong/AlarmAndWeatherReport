package sdu.edu.bean;

public class Indices {

    /*
    全部生活指数 	0
    运动指数 	1 	适宜(1)、较适宜(2)、较不宜(3)
    洗车指数 	2 	适宜(1)、较适宜(2)、较不宜(3)、不宜(4)
    穿衣指数 	3 	寒冷(1)、冷(2)、较冷(3)、较舒适(4)、舒适(5)、热(6)、炎热(7)
    钓鱼指数 	4 	适宜(1)、较适宜(2)、不宜(3)
    紫外线指数 	5 	最弱(1)、弱(2)、中等(3)、强(4)、很强(5)
    旅游指数 	6 	适宜(1)、较适宜(2)、一般(3)、较不宜(4)、不适宜(5)
    花粉过敏指数 	7 	极不易发(1)、不易发(2)、较易发(3)、易发(4)、极易发(5)
    舒适度指数 	8 	舒适(1)、较舒适(2)、较不舒适(3)、很不舒适(4)、极不舒适(5)、不舒适(6)、非常不舒适(7)
    感冒指数 	9 	少发(1)、较易发(2)、易发(3)、极易发(4)
    空气污染扩散条件指数 	10 	优(1)、良(2)、中(3)、较差(4)、很差(5)
    空调开启指数 	11 	长时间开启(1)、部分时间开启(2)、较少开启(3)、开启制暖空调(4)
    太阳镜指数 	12 	不需要(1)、需要(2)、必要(3)、很必要(4)、非常必要(5)
    化妆指数 	13 	保湿(1)、保湿防晒(2)、去油防晒(3)、防脱水防晒(4)、去油(5)、防脱水(6)、防晒(7)、滋润保湿(8)
    晾晒指数 	14 	极适宜(1)、适宜(2)、基本适宜(3)、不太适宜(4)、不宜(5)、不适宜(6)
    交通指数 	15 	良好(1)、较好(2)、一般(3)、较差(4)、很差(5)
    防晒指数 	16 	弱(1)、较弱(2)、中等(3)、强(4)、极强(5)
     */
    private int type;
    private String name;
    private int level;
    private String category;
    private String text;

    public Indices() {

    }

    public Indices(int type, String name, int level, String category, String text) {
        this.type = type;
        this.name = name;
        this.level = level;
        this.category = category;
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s", name, category, text);
    }
}
