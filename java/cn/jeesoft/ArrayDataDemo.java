package cn.jeesoft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @version 0.1 king 2015-11
 */
public class ArrayDataDemo {

    private static final Map<String, Map<String, String>> DATAs = new LinkedHashMap<>();

    public static final Map<String, String> TRAIN = new LinkedHashMap<>();

    public static final Map<String, String> SEAT1 = new LinkedHashMap<>();
    public static final Map<String, String> SEAT2 = new LinkedHashMap<>();
    public static final Map<String, String> SEAT3 = new LinkedHashMap<>();
//    private static final Map<String, List<String>> DATA_TRAIN = new LinkedHashMap<>();
//    座位类型SZ:0(商务座),TZ:1(特等座),R1:2(一等座),R2:3(二等座),GW:4(高级软卧),RW:5(软卧),YW:6(硬卧),RZ:7(软座),YZ:8(硬座),WZ:9(无座),OT:10(其他)
//    座位类型：不限，商务座，特等座，一等座，二等座，高级软卧，软卧，硬卧，软座，硬座，无座，其他

//    车次类型: G:"高铁",C:"城际",  D:"动车",  Z:"直达", T:"特快", K:"快速", P:"普通", O:"其他"
//    车次类型：不限，高铁/动车，普通车

    //不限：不限，商务座，特等座，一等座，二等座，高级软卧，软卧，硬卧，软座，硬座，无座，其他
    //高铁/动车：不限，商务座，特等座，一等座，二等座，软卧
    //普通车：不限，软卧，硬卧，软座，硬座，无座，

    private static void init() {
        if (!DATAs.isEmpty()) {
            return;
        }
        TRAIN.put("不限", "");
        TRAIN.put("高铁/动车", "");
        TRAIN.put("普通车", "");

        SEAT1.put("不限", "");
        SEAT1.put("商务座", "SZ");
        SEAT1.put("特等座", "TZ");
        SEAT1.put("一等座", "R1");
        SEAT1.put("二等座", "R2");
        SEAT1.put("高级软卧", "GW");
        SEAT1.put("软卧", "RW");
        SEAT1.put("硬卧", "YW");
        SEAT1.put("软座", "RZ");
        SEAT1.put("硬座", "YZ");
        SEAT1.put("无座", "WZ");
        SEAT1.put("其他", "OT");

        SEAT2.put("不限", "");
        SEAT2.put("商务座", "SZ");
        SEAT2.put("特等座", "TZ");
        SEAT2.put("一等座", "R1");
        SEAT2.put("二等座", "R2");
        SEAT2.put("软卧", "RW");

        SEAT3.put("不限", "");
        SEAT3.put("高级软卧", "GW");
        SEAT3.put("软卧", "RW");
        SEAT3.put("硬卧", "YW");
        SEAT3.put("软座", "RZ");
        SEAT3.put("硬座", "YZ");
        SEAT3.put("无座", "WZ");

        DATAs.put("不限", SEAT1);
        DATAs.put("高铁/动车", SEAT2);
        DATAs.put("普通车", SEAT3);

//        DATAs.put("高铁", );
//        DATAs.put("城际", );
//        DATAs.put("动车", );
//        DATAs.put("直达", );
//        DATAs.put("特快", );
//        DATAs.put("快速", );
//        DATAs.put("普通", );
//        DATAs.put("其他", );
    }

    private static Random random = new Random();

    private static String getRandomText() {
        int num = random.nextInt(20);
        String str = "五";
        for (int i = 0; i < num; i++) {
            str += "五";
        }
        return str;
    }

    public static Map<String, Map<String, String>> getAll() {
        init();
        return new HashMap<>(DATAs);
    }

//    public static List<String> getTrain() {
//        init();
//        return DATA_SEAT;
//    }
//    public static List<String> getSeat() {
//        init();
//        return SEAT;
//    }

}
