package com.jhhy.cuiweitourism.moudle;

import java.io.Serializable;

/**
 * Created by jiahe008 on 2016/9/27.
 * 签证详情
 */
public class VisaDetail implements Serializable{
//    "id": "1",
//    "title": "新加坡旅游签证[北京领区]",
//    "litpic": "http://cwly.taskbees.cn:8083/uploads/2015/0910/176e08735a00fdb7c0feb376b7630003.jpg",
//    "price": "300",
//    "handleday": "预计5个工作日为使馆受理的工作日", 办理时间
//    "visatype": "旅游签证",
//    "belongconsulate": "新加坡",
//    "partday": "20", 停留时间
//    "validday": "35天", 有效时间
//    "needinterview": "需要",
//    "needletter": "需要",
//    "handlerange": "适用于短期出行旅游的申请人",
//    "content": "1. 具体材料清单和签证服务费请咨询客服；2. 使馆的签证费和申请时间以申请时使领馆的相关规定为准；3. 受理全国护照，外国人护照和港澳台护照；4. 签证需至少提前2周预约(旺季需更早)，如需加急预约请咨询客服。",
//    "material1": "",
//    "material2": "护照有效期超过6个月的因私护照（首页、尾页以及欧美国家签证页清晰的复印件）照片1.2寸白底彩色近照3张. 2.照片尺寸要求35&times;45mm. 3.请在照片背面用铅笔写上自己的姓名.申请表填写完整的个人资料表。车产证明机动车所有权证或行驶证复印件。（可选）房产证明房产证复印件（可选）在职证明1.中文或英文在职证明一份. 2.使用公司正规抬头纸打印,加盖公司红章.3.在职证明必须包括申请人姓名，护照号，具体职务，旅行时间、费用由谁承担，申请人入职时间，并保证按期回国，在职证明部门电话，在职证明部门名称和联系人，出具在职证明具体时间（年月日）.4.如无法提供"在职证明",请提供公司空白抬头纸4张,并加盖公司红章.5.使领馆可能会致电单位电话或本人手机，请提交资料后注意接听，如有未接或不属实情况，可能会造成拒签户口本全家户口本整本的清晰的复印件（包括迁出、注销页），若配偶及子女不在同一户口本，请一同提供配偶及子女户口本整本复印件。身份证身份证正反面的清晰的复印件。​",
//    "material3": "护照有效期超过6个月的因私护照（首页、尾页以及欧美国家签证页清晰的复印件）照片1.2寸白底彩色近照3张. 2.照片尺寸要求35&times;45mm. 3.请在照片背面用铅笔写上自己的姓名.申请表填写完整的个人资料表。车产证明机动车所有权证或行驶证复印件。（可选）房产证明房产证复印件（可选）在职证明1.中文或英文在职证明一份. 2.使用公司正规抬头纸打印,加盖公司红章.3.在职证明必须包括申请人姓名，护照号，具体职务，旅行时间、费用由谁承担，申请人入职时间，并保证按期回国，在职证明部门电话，在职证明部门名称和联系人，出具在职证明具体时间（年月日）.4.如无法提供"在职证明",请提供公司空白抬头纸4张,并加盖公司红章.5.使领馆可能会致电单位电话或本人手机，请提交资料后注意接听，如有未接或不属实情况，可能会造成拒签户口本全家户口本整本的清晰的复印件（包括迁出、注销页），若配偶及子女不在同一户口本，请一同提供配偶及子女户口本整本复印件。身份证身份证正反面的清晰的复印件。​",
//    "material4": "护照有效期超过6个月的因私护照（首页、尾页以及欧美国家签证页清晰的复印件）照片1.2寸白底彩色近照3张. 2.照片尺寸要求35&times;45mm. 3.请在照片背面用铅笔写上自己的姓名.申请表填写完整的个人资料表。车产证明机动车所有权证或行驶证复印件。（可选）房产证明房产证复印件（可选）在职证明1.中文或英文在职证明一份. 2.使用公司正规抬头纸打印,加盖公司红章.3.在职证明必须包括申请人姓名，护照号，具体职务，旅行时间、费用由谁承担，申请人入职时间，并保证按期回国，在职证明部门电话，在职证明部门名称和联系人，出具在职证明具体时间（年月日）.4.如无法提供"在职证明",请提供公司空白抬头纸4张,并加盖公司红章.5.使领馆可能会致电单位电话或本人手机，请提交资料后注意接听，如有未接或不属实情况，可能会造成拒签户口本全家户口本整本的清晰的复印件（包括迁出、注销页），若配偶及子女不在同一户口本，请一同提供配偶及子女户口本整本复印件。身份证身份证正反面的清晰的复印件。​",
//    "material5": "护照有效期超过6个月的因私护照（首页、尾页以及欧美国家签证页清晰的复印件）照片1.2寸白底彩色近照3张. 2.照片尺寸要求35&times;45mm. 3.请在照片背面用铅笔写上自己的姓名.申请表填写完整的个人资料表。车产证明机动车所有权证或行驶证复印件。（可选）房产证明房产证复印件（可选）在职证明1.中文或英文在职证明一份. 2.使用公司正规抬头纸打印,加盖公司红章.3.在职证明必须包括申请人姓名，护照号，具体职务，旅行时间、费用由谁承担，申请人入职时间，并保证按期回国，在职证明部门电话，在职证明部门名称和联系人，出具在职证明具体时间（年月日）.4.如无法提供"在职证明",请提供公司空白抬头纸4张,并加盖公司红章.5.使领馆可能会致电单位电话或本人手机，请提交资料后注意接听，如有未接或不属实情况，可能会造成拒签户口本全家户口本整本的清晰的复印件（包括迁出、注销页），若配偶及子女不在同一户口本，请一同提供配偶及子女户口本整本复印件。身份证身份证正反面的清晰的复印件。​",
//    "bookingtip": "",
//    "friendtip": "​收到护照之后，请务必核实一下您的签证信息（护照号、姓名、签证有效期、出生年月及使馆圆章和签名章等等），若有问题，在收到护照后1个工作日内与销售联系，过期概不负责。"
//    "jifentprice": "0"
    private String id;
    private String title;
    private String litPic;
    private String price;
    private String handleday; //办理时间
    private String visatype;
    private String belongconsulate;
    private String partday; //停留时间
    private String validday; //有效时间
    private String needinterview;
    private String needletter;
    private String handlerange;
    private String content;
    private String material1;
    private String material2;
    private String material3;
    private String material4;
    private String material5;
    private String bookingtip;
    private String friendtip;
    private String jifentprice;

    public VisaDetail() {
    }

    public String getJifentprice() {
        return jifentprice;
    }

    public void setJifentprice(String jifentprice) {
        this.jifentprice = jifentprice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLitPic() {
        return litPic;
    }

    public void setLitPic(String litPic) {
        this.litPic = litPic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHandleday() {
        return handleday;
    }

    public void setHandleday(String handleday) {
        this.handleday = handleday;
    }

    public String getVisatype() {
        return visatype;
    }

    public void setVisatype(String visatype) {
        this.visatype = visatype;
    }

    public String getBelongconsulate() {
        return belongconsulate;
    }

    public void setBelongconsulate(String belongconsulate) {
        this.belongconsulate = belongconsulate;
    }

    public String getPartday() {
        return partday;
    }

    public void setPartday(String partday) {
        this.partday = partday;
    }

    public String getValidday() {
        return validday;
    }

    public void setValidday(String validday) {
        this.validday = validday;
    }

    public String getNeedinterview() {
        return needinterview;
    }

    public void setNeedinterview(String needinterview) {
        this.needinterview = needinterview;
    }

    public String getNeedletter() {
        return needletter;
    }

    public void setNeedletter(String needletter) {
        this.needletter = needletter;
    }

    public String getHandlerange() {
        return handlerange;
    }

    public void setHandlerange(String handlerange) {
        this.handlerange = handlerange;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMaterial1() {
        return material1;
    }

    public void setMaterial1(String material1) {
        this.material1 = material1;
    }

    public String getMaterial2() {
        return material2;
    }

    public void setMaterial2(String material2) {
        this.material2 = material2;
    }

    public String getMaterial3() {
        return material3;
    }

    public void setMaterial3(String material3) {
        this.material3 = material3;
    }

    public String getMaterial4() {
        return material4;
    }

    public void setMaterial4(String material4) {
        this.material4 = material4;
    }

    public String getMaterial5() {
        return material5;
    }

    public void setMaterial5(String material5) {
        this.material5 = material5;
    }

    public String getBookingtip() {
        return bookingtip;
    }

    public void setBookingtip(String bookingtip) {
        this.bookingtip = bookingtip;
    }

    public String getFriendtip() {
        return friendtip;
    }

    public void setFriendtip(String friendtip) {
        this.friendtip = friendtip;
    }
}
