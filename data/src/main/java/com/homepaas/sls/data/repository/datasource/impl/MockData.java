package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.domain.entity.BusinessInfo;
import com.homepaas.sls.domain.entity.LifeService;
import com.homepaas.sls.domain.entity.LifeServiceItem;
import com.homepaas.sls.domain.entity.Message;
import com.homepaas.sls.domain.entity.WorkerInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * on 2016/1/18 0018
 *
 * @author zhudongjie .
 */
public class MockData {

    private static Random random = new Random();

    public static final String[] ADDRESSES =
            "上城区 下城区 江干区 拱墅区 西湖区 滨江区 萧山区 余杭区 建德市 富阳市 临安市 桐庐县 淳安县".split(" ");

    public static final String[] YEARS = ("1990年 1991年 1992年 1993年 1994年 1995年 1996年 1997年 1998年 1999年 2000年 2001年 2002年 2003年 " +
            "2006年 2008年 2010年 2011年 2014年 2015年").split(" ");

    public static final String[] MONTHS = "1月 2月 3月 4月 5月 6月 7月 8月 9月 10月 11月 12月".split(" ");

    public static final String[] MAIN_BUSINESSES = "宫保鸡丁 夫妻肺片 酸菜鱼 剁椒鱼头 花蛤".split(" ");

    public static final String[] BUSINESS_NAMES = "筷子兄弟 一品鲜 新白鹿 东池便当 老堂客 弄堂里 春暖花开".split(" ");

    public static final String[] PHONE_NUMBERS = ("15865230125 18245127545 15602134587 " +
            "13755516674 18835478214 19245871336 15196628341 13754864135 15867421568").split(" ");

    public static final String[] SERVICE_DAYS = "周一至周五 每天 周末".split(" ");

    public static final String[] SERVICE_TIMES = "08:00-18:00 09:00-17:00 17:00-23:00 全天".split(" ");

    public static final String[] MESSAGE_DATES = "2016-1-15 2016-2-16 2016-3-10 2016-3-11 2016-4-7 2016-1-1 2016-6-15".split(" ");

    public static final String[] MESSAGE_TIMES = "17:07:08 20:10:09 12:21:55 10:10:19 22:07:22".split(" ");

    public static final String[] BLOOD_TYPE = {"A", "B", "AB", "O"};

    public static final String[] CONSTELLATION = "白羊座、金牛座、双子座、巨蟹座、狮子座、处女座、天秤座、天蝎座、射手座、摩羯座、水瓶座、双鱼座".split("、");

    public static final String[] CRAFT = {"小时工", "装修工", "保洁工"};

    public static final String[] EDUCATION = {"小学", "初中", "高中", "大专", "本科", "硕士"};

    public static final String[] HOBBY = {"篮球", "足球", "羽毛球", "看电视", "打游戏"};

    public static final String[] WORKER_NAME = "张三 李四 王五 赵六 朱七 周八".split(" ");

    public static final String[] LATITUDES = ("30.262241756818252,30.252653127479846,30.237905135153994,30.231244709390737," +
            "30.223469705762305,30.220962680243375,30.206645960352695,30.20744542953342," +
            "30.21780155656492,30.230263786180434,30.24399582673592,30.238038250026875," +
            "30.220454002813245,30.209407728670268,30.205955512470418,30.212108937657618," +
            "30.211672887324386,30.226497519723413,30.247755320733926,30.254475061587875,30.26951107005937," +
            "30.293679147659805,30.25051340358336,30.236093459923513," +
            "30.240722710393243,30.255873382759177,30.27149015907779," +
            "30.27149015907779,30.27748200249469,30.25156928756104," +
            "30.243686930775084,30.24811856433462,30.25847036577228," +
            "30.268930014929612,30.26787684038057,30.255873570797977," +
            "30.245339868845132,30.254348070293688,30.232334620093006," +
            "30.227175631001423,30.218237582318952,30.22099901533224," +
            "30.228592559955075,30.242179530574127," +
            "30.247737287937102,30.238946437190474").split(",");

    public static final String[] LONGITUDES = ("120.20851155752985," + "120.20859523087205," + "120.19826169976224," +
            "120.19416175533932," + "120.17960277143811," + "120.17165389878092," +
            "120.17466609962419,120.17412222893093," + "120.16102751097337," + "120.15881019317405," +
            "120.16981310115247," + "120.18976894928412," + "120.20633137509591," + "120.22130872241848," +
            "120.2255341741133,120.23871256376823," + "120.23950745065015,120.23540750622723,120.22524602571188,120.22244299999991," +
            "120.2354958834795,120.2246603177307,120.20212105794383," +
            "120.20687140921885,120.23783609011561,120.24812562353543," +
            "120.24427669584945,120.23155850464215," +
            "120.25858466411029,120.20846972346308,120.21085438465713," +
            "120.19918209816525,120.2008137086002," +
            "120.211356418131,120.22737966523445,120.24009785753832," +
            "120.24118559892484,120.24963650450763," +
            "120.23260918661845,120.21771551208981,120.20608505897934," +
            "120.19483113330449,120.18228028658848," + "120.1862547204498,120.20290551158872,"
            + "120.21445229135698").split(",");

    public static List<LifeService> getFakeServices() {

        List<LifeService> lifeServiceList = new ArrayList<>();

        LifeService lifeService = new LifeService();
        lifeService.setName("家政服务");
        List<LifeServiceItem> items = new ArrayList<>();
        LifeServiceItem item = new LifeServiceItem();
        item.name = "小时工";
        items.add(item);
        item = new LifeServiceItem();
        item.name = "开荒保洁";
        items.add(item);
        item = new LifeServiceItem();
        item.name = "家电清洗";
        items.add(item);
        item = new LifeServiceItem();
        item.name = "地板保养";
        items.add(item);
        item = new LifeServiceItem();
        item.name = "石材清洗";
        items.add(item);
        item = new LifeServiceItem();
        item.name = "地毯清洗";
        items.add(item);
        item = new LifeServiceItem();
        item.name = "充氟利昂";
        items.add(item);
        item = new LifeServiceItem();
        item.name = "空气检测";
        items.add(item);
        item = new LifeServiceItem();
        item.name = "空气治理";
        items.add(item);
        lifeService.setItems(items);
        lifeServiceList.add(lifeService);

        lifeService = new LifeService();
        lifeService.setName("安装服务");
        items = new ArrayList<>();
        item = new LifeServiceItem();
        item.name = "地板安装";
        items.add(item);
        item = new LifeServiceItem();
        item.name = "灯具安装";
        items.add(item);
        item = new LifeServiceItem();
        item.name = "地板安装";
        items.add(item);
        item = new LifeServiceItem();
        item.name = "灶台燃气灶台";
        items.add(item);
        lifeService.setItems(items);
        lifeServiceList.add(lifeService);

        lifeService = new LifeService();
        lifeService.setName("维修服务");
        items = new ArrayList<>();
        item = new LifeServiceItem();
        item.name = "管道维修";
        items.add(item);
        item = new LifeServiceItem();
        item.name = "家电电脑维修";
        items.add(item);
        item = new LifeServiceItem();
        item.name = "家具维修";
        items.add(item);
        item = new LifeServiceItem();
        item.name = "冰箱维修";
        items.add(item);
        lifeService.setItems(items);
        lifeServiceList.add(lifeService);

        return lifeServiceList;
    }

    public static BusinessInfo createBusinessInfo() {
        int index = random.nextInt(100);
        BusinessInfo businessInfo = new BusinessInfo();
        businessInfo.setArea(index + "平方米");
        businessInfo.setAddress("杭州市" + ADDRESSES[random.nextInt(ADDRESSES.length)]);
        businessInfo.setDistance(random.nextInt(1000) + "千米");
        businessInfo.setEstablishedTime(YEARS[random.nextInt(YEARS.length)] + MONTHS[random.nextInt(MONTHS.length)]);
        businessInfo.setFavoriteCount(random.nextInt(100) + "");
        businessInfo.setGrade(random.nextInt(110) + "");
        businessInfo.setGradeRank(random.nextInt(100) + "");
        businessInfo.setId(random.nextInt(500) + "");
        businessInfo.setIntro("这家店很烂，没有任何说明");
        businessInfo.setIsFavorite(random.nextBoolean() ? "0" : "1");
        businessInfo.setIsPraise(random.nextBoolean() ? "0" : "1");
        businessInfo.setLicenseId((10000 + random.nextInt(10000)) + "");
        businessInfo.setMainBusiness(MAIN_BUSINESSES[random.nextInt(MAIN_BUSINESSES.length)]);
        businessInfo.setName(BUSINESS_NAMES[random.nextInt(BUSINESS_NAMES.length)]);
        businessInfo.setOrderCount(random.nextInt(100) + "");
        businessInfo.setOrderRank("" + random.nextInt(100));
        businessInfo.setPhoneRank(random.nextInt(100) + "");
        businessInfo.setPhoneCount(random.nextInt(50) + "");
        businessInfo.setPhoneNumber(PHONE_NUMBERS[random.nextInt(PHONE_NUMBERS.length)]);
        businessInfo.setProperty("个体户");
        businessInfo.setPraiseCount(random.nextInt(100) + "");
        List<BusinessInfo.SystemCertification> certificationList = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            BusinessInfo.SystemCertification certification = new BusinessInfo.SystemCertification();

            certificationList.add(certification);
        }
        businessInfo.setSystemCertifications(certificationList);
        businessInfo.setStaffNumber(random.nextInt(100) + "");
        businessInfo.setScale(random.nextInt(200) + "");
        businessInfo.setServiceScope((500 + random.nextInt(300) + "米内"));
        businessInfo.setServiceTime(SERVICE_DAYS[random.nextInt(SERVICE_DAYS.length)] + SERVICE_TIMES[random.nextInt(SERVICE_TIMES.length)]);
        businessInfo.setTexId("1000" + (10 + random.nextInt(80)));
        businessInfo.setLongitude(getRandomType(LONGITUDES));
        businessInfo.setLatitude(getRandomType(LATITUDES));
        businessInfo.setNumber("askjfnkljasfalskf");
        businessInfo.setSignature("我就是这么牛");
        return businessInfo;
    }

    public static WorkerInfo createWorkerInfo() {
        WorkerInfo workerInfo = new WorkerInfo();
        workerInfo.setAge(random.nextInt(30) + 20 + "");
        workerInfo.setAddress("浙江省杭州市" + ADDRESSES[random.nextInt(ADDRESSES.length)]);
        workerInfo.setBloodType(BLOOD_TYPE[random.nextInt(BLOOD_TYPE.length)]);
        workerInfo.setConstellation(CONSTELLATION[random.nextInt(CONSTELLATION.length)]);
       // workerInfo.setCraft(CRAFT[random.nextInt(CRAFT.length)]);
        workerInfo.setDistance(random.nextInt(500) + 500 + "");
        workerInfo.setEducation(getRandomType(EDUCATION));
        workerInfo.setFavoriteCount(random.nextInt(100) + "");
        workerInfo.setGender(random.nextBoolean() ? "0" : "1");
        workerInfo.setGrade(random.nextInt(110) + "");
        workerInfo.setGradeRank(random.nextInt(100) + "");
        workerInfo.setHobby(getRandomType(HOBBY));
        workerInfo.setIsFavorite(random.nextBoolean() ? "0" : "1");
        workerInfo.setIsPraise(random.nextBoolean() ? "0" : "1 ");
        workerInfo.setIntro("这个人很懒，什么也没留下");
        workerInfo.setId(1000 + random.nextInt(1000) + "");
        workerInfo.setJobNumber(10000 + random.nextInt(20000) + "");
        workerInfo.setName(getRandomType(WORKER_NAME));
        workerInfo.setNativePlace("杭州市" + getRandomType(ADDRESSES));
        workerInfo.setOrderRank(random.nextInt(100) + "");
        workerInfo.setPhoneRank(random.nextInt(100) + "");
        workerInfo.setOrderCount(random.nextInt(1020) + "");
        workerInfo.setPraiseCount(random.nextInt(100) + "");
        workerInfo.setPhoneNumber(getRandomType(PHONE_NUMBERS));
        workerInfo.setPhoneCount(random.nextInt(50) + "");
//        List<WorkerInfo.WorkerPhoto> photos = new ArrayList<>(3);
//        for (int i = 0; i < 3; i++) {
//            WorkerInfo.WorkerPhoto photo = new WorkerInfo.WorkerPhoto();
//            photo.setHqPic("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=642819720,2965626235&fm=111&gp=0.jpg");
//            photo.setSmallPic("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=642819720,2965626235&fm=111&gp=0.jpg");
//            photos.add(photo);
//        }
//        workerInfo.setPhotos(photos);
        workerInfo.setServiceTime(getRandomType(SERVICE_DAYS) + getRandomType(SERVICE_TIMES));
        workerInfo.setServiceScope((500 + random.nextInt(300) + "米内"));
        workerInfo.setStature(150 + random.nextInt(30) + "厘米");
        workerInfo.setSignature("飘柔，就是这么自信");
        List<WorkerInfo.WorkerSystemCertification> certificationList = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            WorkerInfo.WorkerSystemCertification certification = new WorkerInfo.WorkerSystemCertification();

            certificationList.add(certification);
        }
        workerInfo.setSystemCertifications(certificationList);
        workerInfo.setLatitude(getRandomType(LATITUDES));
        workerInfo.setLongitude(getRandomType(LONGITUDES));
        workerInfo.setMandarinAbility("不知道");
        workerInfo.setWorkExperience("这是什么");
        workerInfo.setWorkingYears(random.nextInt(6) + 1 + "年");
        workerInfo.setWage((random.nextInt(3) + 1) * 10 + "");
        return workerInfo;

    }

    public static Message createFakeMessage() {
        Message message = new Message();
        message.setId(random.nextInt(10) + "");
        message.setTitle("标题" + random.nextInt(30));
        message.setDate(getRandomType(MESSAGE_DATES) + " " + getRandomType(MESSAGE_TIMES));
        message.setJumpCode(random.nextInt(10) + "");
        message.setContent("内容-" + random.nextInt(20));
        return message;
    }

    private static String getRandomType(String[] strings) {
        return strings[random.nextInt(strings.length)];
    }
}
