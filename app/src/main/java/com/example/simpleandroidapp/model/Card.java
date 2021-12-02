package com.example.simpleandroidapp.model;

public class Card {
    private int id;
    private int imageId;
    private boolean isTurned;

    public Card(int id, int imageId, boolean isTurned) {
        this.id = id;
        this.imageId = imageId;
        this.isTurned = isTurned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public boolean isTurned() {
        return isTurned;
    }

    public void setTurned(boolean turned) {
        isTurned = turned;
    }
}
