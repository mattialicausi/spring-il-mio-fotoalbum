package com.java.springilmiofotoalbum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

@Entity
@Table(name = "photos")
public class Photo {

    //variabili
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Il titolo deve essere inserito")
    private String title;

    @Lob
    private String description;

//    @URL(message = "Il path deve essere valido")
//    @NotEmpty(message = "Url deve essere inserito")
//    private String url;

    @NotNull(message = "true o false")
    private boolean visible;

    // relazioni

    @ManyToMany
    @JoinTable(
            name = "category_photo",
            joinColumns = @JoinColumn(name = "photo_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnore
    private Set<Category> categories;

    @OneToOne(mappedBy = "photo")
    private Image url;

    // costruttori


    public Photo() {
        super();
    }

    public Photo(String title, String description, boolean visible) {

        this.title = title;
        this.description = description;
//        this.url = url;
        this.visible = visible;

    }

    // getter e setter


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Image getUrl() {
        return url;
    }

    public void setUrl(Image url) {
        this.url = url;
    }
}
