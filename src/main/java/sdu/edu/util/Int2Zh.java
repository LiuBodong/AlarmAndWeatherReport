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
        ratios.put(6, "十万");
        ratios.put(7, "百万");
        ratios.put(8, "千万");
        ratios.put(9, "亿");
    }

    /**
     * int转为中文
     *
     * @param num
     * @return
     */
    public static String int2Zh(int num) {
        StringBuilder sb = new StringBuilder();
        int index = 1;
        while (num > 0) {
            int tail = num % 10;
            if (index > 1 && tail != 0) {
                sb.append(ratios.get(index));
            }
            if (tail != 0) {
                sb.append(nums[tail]);
            } else {
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

