package sdu.edu.util;

import java.util.HashMap;

public class Int2Zh {

    public static final HashMap<Integer, String> nums = new HashMap<>();

    static {
        nums.put(0, "零");
        nums.put(1, "一");
        nums.put(2, "二");
        nums.put(3, "三");
        nums.put(4, "四");
        nums.put(5, "五");
        nums.put(6, "六");
        nums.put(7, "七");
        nums.put(8, "八");
        nums.put(9, "九");
    }

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
                sb.append(nums.get(tail));
            } else {
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) != '零') {
                    sb.append(nums.get(tail));
                }
            }
            num = num / 10;
            index++;
        }
        return sb.reverse().toString();
    }

}

