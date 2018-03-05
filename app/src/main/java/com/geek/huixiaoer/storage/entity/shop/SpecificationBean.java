package com.geek.huixiaoer.storage.entity.shop;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 商品规格
 * Created by 刘力 on 2018/3/3.
 */
public class SpecificationBean implements Parcelable{

    /**
     * id : 159
     * marketPrice : 1
     * price : 0.01
     * stock : 1000000
     * image : http://182.254.223.66/shopxx/upload/image/201803/5b916b7f-e339-4366-9ffd-e1b993c7da05-thumbnail.jpg
     * product_sn : 2018030321715_8
     * specifications : ["三包","苦"]
     */

    private String id;
    private String marketPrice;
    private double price;
    private int stock;
    private String image;
    private String product_sn;
    private List<String> specifications;

    protected SpecificationBean(Parcel in) {
        id = in.readString();
        marketPrice = in.readString();
        price = in.readDouble();
        stock = in.readInt();
        image = in.readString();
        product_sn = in.readString();
        specifications = in.createStringArrayList();
    }

    public static final Creator<SpecificationBean> CREATOR = new Creator<SpecificationBean>() {
        @Override
        public SpecificationBean createFromParcel(Parcel in) {
            return new SpecificationBean(in);
        }

        @Override
        public SpecificationBean[] newArray(int size) {
            return new SpecificationBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProduct_sn() {
        return product_sn;
    }

    public void setProduct_sn(String product_sn) {
        this.product_sn = product_sn;
    }

    public List<String> getSpecifications() {
        return specifications;
    }

    public String getSpecification() {
        StringBuilder buffer = new StringBuilder();
        if (specifications == null || specifications.size() == 0){
            return "";
        }else {
            for (int i = 0; i < specifications.size(); i++) {
                if (i == 0) {
                    buffer.append(specifications.get(i));
                } else {
                    buffer.append("/").append(specifications.get(i));
                }
            }
            return buffer.toString();
        }
    }

    public void setSpecifications(List<String> specifications) {
        this.specifications = specifications;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(marketPrice);
        dest.writeDouble(price);
        dest.writeInt(stock);
        dest.writeString(image);
        dest.writeString(product_sn);
        dest.writeStringList(specifications);
    }
}
