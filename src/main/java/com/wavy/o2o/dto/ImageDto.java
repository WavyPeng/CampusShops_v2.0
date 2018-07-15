package com.wavy.o2o.dto;

import java.io.InputStream;

/**
 * 图片
 * Created by WavyPeng on 2018/6/3.
 */
public class ImageDto {
    private String imageName;
    private InputStream image;

    public ImageDto(String imageName, InputStream image) {
        this.imageName = imageName;
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }
}
