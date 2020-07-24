package sdu.edu.util;

import java.util.HashMap;

public class Int2Zh {

    public static final String[] nums = new String[]{
            "零", "一", "二", "三", "四", "五", "六", "七", "八", "九"
    };

    public static final HashMap<Integer, String> ratios = new HashMap<>();

    static {
        ratios.put(2, "十");
        ratios.put(3, "百");
        ratios.put(4, "千");
        ratios.put(5, "万");
        ratios.put(6, "万十"); // 反转，因为最后要将字符串反转
        ratios.put(7, "万百"); // 反转
        ratios.put(8, "万千"); // 反转
        ratios.put(9, "亿");
    }

    /**
     * int转为中文 如2020->二千零二十
     *
     * @param num
     * @return
     */
    public static String int2Zh(int num) {
        StringBuilder sb = new StringBuilder();
        // 当前处理的下标，最低是十位，因为个位不需要处理
        int index = 1;
        while (num > 0) {
            int tail = num % 10; // 末位
            if (index > 1 && tail != 0) { // 如果比十位大，并且末位不是0，就写入位数
                sb.append(ratios.get(index));
            }
            if (tail != 0) { // 末位不为0，直接写入
                sb.append(nums[tail]);
            } else {
                // 否则判断前一位是不是也是0，如果也是0就不需要写入。如 2002 读作二千零二，而不是二千零零二
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) != '零') {
                    sb.append(nums[tail]);
                }
            }
            num = num / 10;
            index++;
        }
        return sb.reverse().toString();
    }

}

