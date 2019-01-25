package com.homepaas.sls.mvp.model.mapper;

import com.homepaas.sls.domain.entity.LifeService;
import com.homepaas.sls.domain.entity.LifeServiceItem;
import com.homepaas.sls.mvp.model.LifeServiceModel;
import com.homepaas.sls.mvp.model.ServiceItemModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/1/21.
 */

@Singleton
public class LifeServiceModelMapper {

    @Inject
    LifeServiceModelMapper() {
    }

    public LifeServiceModel transformToModel(LifeService lifeService) {
        LifeServiceModel model = null;
        if (lifeService != null) {
            model = new LifeServiceModel();
            model.setId(lifeService.getId());
            model.setName(lifeService.getName());
            model.setLogo(lifeService.getLogo());
            model.setItems(transformToModels(lifeService.getItems()));
        }
        return model;
    }

    public List<LifeServiceModel> transformToModelList(List<LifeService> lifeServices) {
        List<LifeServiceModel> models = new ArrayList<>();
        for (LifeService service : lifeServices) {
            LifeServiceModel model = transformToModel(service);
            models.add(model);
        }
        return models;
    }

    public List<String> transformToStringList(List<LifeService> lifeServices) {
        List<String> list = new ArrayList<>(lifeServices.size());
        for (LifeService service : lifeServices) {
            list.add(service.getName());
        }
        return list;
    }

    public List<String> transformToItemStringList(List<LifeService> lifeServices) {
        Set<String> set = new HashSet<>();
        for (LifeService service : lifeServices) {
            for (LifeServiceItem item : service.getItems()) {
                set.add(item.getName());
            }
        }
        return new ArrayList<>(set);
    }


    private List<ServiceItemModel> transformToModels(List<LifeServiceItem> items) {
        List<ServiceItemModel> models = new ArrayList<>();
        for (LifeServiceItem item : items) {
            ServiceItemModel model = transformToModel(item);
            models.add(model);
        }
        return models;
    }

    private ServiceItemModel transformToModel(LifeServiceItem item) {
        ServiceItemModel model = null;
        if (item != null) {
            model = new ServiceItemModel();
            model.setId(item.getId());
            model.setName(item.getName());
        }
        return model;
    }

}
