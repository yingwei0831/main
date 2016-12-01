package cn.jeesoft;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.jeesoft.widget.pickerview.CharacterPickerView;
import cn.jeesoft.widget.pickerview.CharacterPickerWindow;
import cn.jeesoft.widget.pickerview.OnOptionChangedListener;

/**
 * 地址选择器
 *
 * @version 0.1 king 2015-10
 */
public class OptionsWindowHelper {

    private static List<String> options1Items = null;
    private static List<List<String>> options2Items = null;
//    private static List<List<List<String>>> options3Items = null;

//    座位类型SZ:0(商务座),TZ:1(特等座),R1:2(一等座),R2:3(二等座),GW:4(高级软卧),RW:5(软卧),YW:6(硬卧),RZ:7(软座),YZ:8(硬座),WZ:9(无座),OT:10(其他)
//    车次类型:G:"高铁",C:"城际",D:"动车",Z:"直达",T:"特快",K:"快速",P:"普通",O:"其他"

    public interface OnOptionsSelectListener {
        void onOptionsSelect(String province, String city, String area);
    }

    private OptionsWindowHelper() {
    }

    public static CharacterPickerWindow builder(Activity activity, final OnOptionsSelectListener listener) {
        //选项选择器
        CharacterPickerWindow mOptions = new CharacterPickerWindow(activity);
        //初始化选项数据
        setPickerData(mOptions.getPickerView());
        //设置默认选中的三级项目
        mOptions.setSelectOptions(0, 0, 0);
        //监听确定选择按钮
        mOptions.setOnoptionsSelectListener(new OnOptionChangedListener() {
            @Override
            public void onOptionChanged(int option1, int option2, int option3) {
                if (listener != null) {
                    String province = options1Items.get(option1);
                    String city = options2Items.get(option1).get(option2);
//                    String area = options3Items.get(option1).get(option2).get(option3); //因为是二级联动，所以，此处只传回2个数据
                    listener.onOptionsSelect(province, city, null);
                }
            }
        });

        return mOptions;
    }

    /**
     * 初始化选项数据
     */
    public static void setPickerData(CharacterPickerView view) {
        if (options1Items == null) {
            options1Items = new ArrayList<>();
            options2Items = new ArrayList<>();
//            options3Items = new ArrayList<>();

            final Map<String, Map<String, String>> allCitys = ArrayDataDemo.getAll();
            for (Map.Entry<String, Map<String, String>> entry1 : allCitys.entrySet()) {
                String key1 = entry1.getKey(); //车型
                Map<String, String> value1 = entry1.getValue(); //座位类型

                options1Items.add(key1);

                List<String> options2Items01 = new ArrayList<>();
//                List<List<String>> options3Items01 = new ArrayList<>();
                for (Map.Entry<String, String> entry2 : value1.entrySet()) {
                    String key2 = entry2.getKey();
//                    List<String> value2 = entry2.getValue();

                    options2Items01.add(key2);
//                    Collections.sort(value2);
//                    options3Items01.add(new ArrayList<>(value2));
                }
//                Collections.sort(options2Items01);
                options2Items.add(options2Items01);
//                options3Items.add(options3Items01);
            }
//            Collections.sort(options1Items);
        }

        //三级联动效果
        view.setPicker(options1Items, options2Items, null);
    }

//    public static void setPickerData(CharacterPickerView view, List<String> seat) {
//        if (options1Items == null) {
//            options1Items = new ArrayList<>();
//
//            final Map<String, Map<String, String>> allCitys = ArrayDataDemo.getSeatType(seat);
//            for (Map.Entry<String, Map<String, String>> entry1 : allCitys.entrySet()) {
//                String key1 = entry1.getKey(); //座位类型 价格
//                 options1Items.add(key1);
//            }
//        }
//
//        //三级联动效果
//        view.setPicker(options1Items, null, null);
//    }

}
