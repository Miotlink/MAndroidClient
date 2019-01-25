package com.homepaas.sls.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import com.homepaas.sls.domain.param.Constant;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public interface IServiceInfo extends Serializable{

    @IntDef({TYPE_WORKER, TYPE_BUSINESS})
    @interface Type {

    }
    int TYPE_WORKER = Constant.SERVICE_TYPE_WORKER_INT;
    int TYPE_BUSINESS = Constant.SERVICE_TYPE_BUSINESS_INT;

    @Type
    int type();

    boolean showPhoto();

    void setShowPhoto(boolean showPhoto);

    String getName();

    String getPhoneNumber();

    String getId();

    int getLikeCount();

    boolean isLike();

    String getOrderCount();

    CharSequence getOrderRank();

    String getPhoneCount();

    int getCollectedCount();

    boolean isCollected();

    double getLatitude();

    double getLongitude();

    boolean isCallable();

    String getGrade();

    List<SystemCertification> getSystemCertifications();

//    List<Photo> getPhotos();

    String getGradeNumber();

    String getDefaultService();

    String getDefaultServiceId();

    String getSplicedServices();

    class SystemCertification implements Parcelable {

        private String name;

        private String description;

        private String image;


        public SystemCertification() {
        }


        protected SystemCertification(Parcel in) {
            name = in.readString();
            description = in.readString();
            image = in.readString();
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getImage() {
            return image;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(description);
            dest.writeString(image);
        }

        public static final Creator<SystemCertification> CREATOR = new Creator<SystemCertification>() {
            @Override
            public SystemCertification createFromParcel(Parcel in) {
                return new SystemCertification(in);
            }

            @Override
            public SystemCertification[] newArray(int size) {
                return new SystemCertification[size];
            }
        };

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("SystemCertification{");
            sb.append("name='").append(name).append('\'');
            sb.append(", description='").append(description).append('\'');
            sb.append(", image='").append(image).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    class Photo implements Parcelable {

        private String smallPic;

        private String hqPic;

        public Photo() {
            //need
        }

        protected Photo(Parcel in) {
            smallPic = in.readString();
            hqPic = in.readString();
        }

        public static final Creator<Photo> CREATOR = new Creator<Photo>() {
            @Override
            public Photo createFromParcel(Parcel in) {
                return new Photo(in);
            }

            @Override
            public Photo[] newArray(int size) {
                return new Photo[size];
            }
        };

        public void setSmallPic(String smallPic) {
            this.smallPic = smallPic;
        }

        public void setHqPic(String hqPic) {
            this.hqPic = hqPic;
        }

        public String getSmallPic() {
            return smallPic;
        }

        public String getHqPic() {
            return hqPic;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(smallPic);
            dest.writeString(hqPic);
        }
    }
}
