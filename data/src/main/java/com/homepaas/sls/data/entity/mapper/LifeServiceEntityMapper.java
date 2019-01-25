package com.homepaas.sls.data.entity.mapper;

import android.support.v4.util.ArrayMap;

import com.homepaas.sls.data.entity.LifeServiceEntity;
import com.homepaas.sls.domain.entity.LifeService;
import com.homepaas.sls.domain.entity.LifeServiceItem;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/3/2 0002
 *
 * @author zhudongjie .
 */
@Singleton
public class LifeServiceEntityMapper {

    @Inject
    public LifeServiceEntityMapper() {
        //need
    }

    public List<LifeService> transform(List<LifeServiceEntity> entityList) {

        ArrayMap<String, LifeService> lifeServices = new ArrayMap<>();
        for (LifeServiceEntity entity : entityList) {
            if (!lifeServices.containsKey(entity.getTypeId())) {
                LifeService service = new LifeService();
                service.setId(entity.getTypeId());
                service.setName(entity.getTypeName());
                service.setLogo(entity.getTypeLogo());
                List<LifeServiceItem> items = new ArrayList<>();
                LifeServiceItem item = new LifeServiceItem();
                item.setId(entity.getServiceId());
                item.setName(entity.getServiceName());
                items.add(item);
                service.setItems(items);
                lifeServices.put(entity.getTypeId(), service);
            } else {
                List<LifeServiceItem> items = lifeServices.get(entity.getTypeId()).getItems();
                LifeServiceItem item = new LifeServiceItem();
                item.setId(entity.getServiceId());
                item.setName(entity.getServiceName());
                items.add(item);
            }
        }

        return new ArrayList<>(lifeServices.values());
    }

}
