package com.geek.huixiaoer.storage.entity.address;

import java.util.List;

public class AddressDetailsEntity {

    public String Province;
    public String City;
    public String Area;
    public String Address;
    public Data ProvinceItems;

    public class Data {
        public List<ProvinceEntity> Province;
    }

}
