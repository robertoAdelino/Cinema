package com.example.acer.cinema;

import java.util.Date;

public class Filmes {

    private int id;
    private String title;
    private double points;
    private int idCategory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return points;
    }

    public void setPrice(double points) {
        this.points = points;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
}
