package com.homepaas.sls.mvp.model.mapper;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.BusinessTagsInfo;
import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.domain.entity.WorkerTagsInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Sherily on 2017/2/8.
 */
@Singleton
public class TagsInfoMapper {
    @Inject
    public TagsInfoMapper() {
    }

    public static class TagsInfo{
        private int Id;
        private String tagName;
        private String remark;

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    public List<TagsInfo> transform(GetWorkerTagsInfo getWorkerTagsInfo){
        List<WorkerTagsInfo> workerTagsInfos = getWorkerTagsInfo.getWorkerTagsInfos();
        List<TagsInfo> tagsInfos = new ArrayList<>();
        if (workerTagsInfos != null){
            for (WorkerTagsInfo workerTagsInfo : workerTagsInfos){
                TagsInfo tagsInfo = new TagsInfo();
                tagsInfo.setId(workerTagsInfo.getId());
                tagsInfo.setRemark(workerTagsInfo.getRemark());
                tagsInfo.setTagName(workerTagsInfo.getTagName());
                tagsInfos.add(tagsInfo);
            }
        }
        return tagsInfos;
    }
    public List<TagsInfo> transform(GetBusinessTagsInfo getBusinessTagsInfo){
        List<BusinessTagsInfo> businessTagsInfos = getBusinessTagsInfo.getBusinessTagsInfos();
        List<TagsInfo> tagsInfos = new ArrayList<>();
        if (businessTagsInfos != null){
            for (BusinessTagsInfo businessTagsInfo : businessTagsInfos){
                TagsInfo tagsInfo = new TagsInfo();
                tagsInfo.setId(businessTagsInfo.getId());
                tagsInfo.setRemark(businessTagsInfo.getRemark());
                tagsInfo.setTagName(businessTagsInfo.getTagName());
                tagsInfos.add(tagsInfo);
            }
        }
        return tagsInfos;
    }

}
