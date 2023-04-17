package com.java.springilmiofotoalbum.model;

import com.java.springilmiofotoalbum.validators.ValidImageFile;
import org.springframework.web.multipart.MultipartFile;

public class ImageForm {

   @ValidImageFile
    private MultipartFile multipartFile;

   private Photo photo;

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

}
