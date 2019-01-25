package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.repository.datasource.LifeServiceLDataSource;
import com.homepaas.sls.domain.entity.LifeService;
import com.homepaas.sls.domain.entity.LifeServiceItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by fmisser on 2016/7/29.
 *
 */

@Singleton
public class LifeServiceLDataSourceImpl implements LifeServiceLDataSource {

    private static final String ID_HOUSEKEEPING = "170";
    private static final String ID_FITMENT = "172";
    private static final String ID_INSTALL = "168";
    private static final String ID_MAINTAIN = "171";
    private static final String ID_CONVENIENCE = "169";

    @Inject
    public LifeServiceLDataSourceImpl() {

    }

    @Override
    public List<LifeService> getLifeServiceList() {
        List<LifeService> lifeServices = new ArrayList<>();

        //家政
        LifeService service = new LifeService();
        service.setId(ID_HOUSEKEEPING);
        service.setName("家政");
        List<LifeServiceItem> items = new ArrayList<>();
        items.add(new LifeServiceItem("5", "小时工") );
        items.add(new LifeServiceItem("7", "老人看护") );
        items.add(new LifeServiceItem("8", "病患看护") );
        items.add(new LifeServiceItem("9", "月嫂") );
        items.add(new LifeServiceItem("27", "上门做饭") );
        items.add(new LifeServiceItem("118", "不住家保姆") );
        items.add(new LifeServiceItem("165", "住家保姆") );
        items.add(new LifeServiceItem("160", "新房开荒") );
        items.add(new LifeServiceItem("163", "油烟机清洗") );
        items.add(new LifeServiceItem("125", "地板保养") );
        items.add(new LifeServiceItem("148", "石材保养") );
        items.add(new LifeServiceItem("136", "空调清洗") );
        service.setItems(items);
        lifeServices.add(service);

        //装修
        service = new LifeService();
        service.setId(ID_FITMENT);
        service.setName("装修");
        items = new ArrayList<>();
        items.add(new LifeServiceItem("35", "木工") );
        items.add(new LifeServiceItem("63", "泥工") );
        items.add(new LifeServiceItem("173", "硬装") );
        items.add(new LifeServiceItem("37", "油漆工") );
        items.add(new LifeServiceItem("38", "水电工") );
        items.add(new LifeServiceItem("50", "家装设计") );
        items.add(new LifeServiceItem("58", "软装设计") );
        items.add(new LifeServiceItem("61", "瓷砖美缝") );
        items.add(new LifeServiceItem("115", "垃圾清运") );
        service.setItems(items);
        lifeServices.add(service);

        //安装
        service = new LifeService();
        service.setId(ID_INSTALL);
        service.setName("安装");
        items = new ArrayList<>();
        items.add(new LifeServiceItem("135", "空调安装") );
        items.add(new LifeServiceItem("13", "灯饰安装") );
        items.add(new LifeServiceItem("14", "卫浴安装") );
        items.add(new LifeServiceItem("41", "网络安装") );
        items.add(new LifeServiceItem("123", "橱柜安装") );
        items.add(new LifeServiceItem("161", "衣柜安装") );
        items.add(new LifeServiceItem("149", "实木地板安装") );
        items.add(new LifeServiceItem("129", "复合地板安装") );
        items.add(new LifeServiceItem("142", "强化地板安装") );
        items.add(new LifeServiceItem("57", "墙纸铺贴") );
        items.add(new LifeServiceItem("60", "防盗窗安装") );
        items.add(new LifeServiceItem("62", "电动门安装") );
        items.add(new LifeServiceItem("151", "实木门安装") );
        items.add(new LifeServiceItem("150", "实木复合门安装") );
        items.add(new LifeServiceItem("166", "桌、椅、床安装") );
        service.setItems(items);
        lifeServices.add(service);

        //维修
        service = new LifeService();
        service.setId(ID_MAINTAIN);
        service.setName("维修");
        items = new ArrayList<>();
        items.add(new LifeServiceItem("18", "家具维修") );
        items.add(new LifeServiceItem("19", "管道疏通") );
        items.add(new LifeServiceItem("40", "水电维修") );
        items.add(new LifeServiceItem("121", "充氟利昂") );
        items.add(new LifeServiceItem("43", "电脑维修") );
        items.add(new LifeServiceItem("174", "墙面维修") );
        items.add(new LifeServiceItem("124", "大家电维修") );
        items.add(new LifeServiceItem("159", "小家电维修") );
        items.add(new LifeServiceItem("127", "电瓶车维修") );
        service.setItems(items);
        lifeServices.add(service);

        //便民
        service = new LifeService();
        service.setId(ID_CONVENIENCE);
        service.setName("便民");
        items = new ArrayList<>();
        items.add(new LifeServiceItem("22", "开锁换锁") );
        items.add(new LifeServiceItem("24", "搬家公司") );
        items.add(new LifeServiceItem("158", "鲜花配送") );
        items.add(new LifeServiceItem("26", "快递") );
        items.add(new LifeServiceItem("29", "送水") );
        items.add(new LifeServiceItem("45", "厨师") );
        items.add(new LifeServiceItem("137", "跑腿") );
        items.add(new LifeServiceItem("25", "洗衣店") );
        items.add(new LifeServiceItem("122", "宠物店") );
        items.add(new LifeServiceItem("31", "废品回收") );
        items.add(new LifeServiceItem("4", "其他") );
        service.setItems(items);
        lifeServices.add(service);

        return lifeServices;
    }
}
