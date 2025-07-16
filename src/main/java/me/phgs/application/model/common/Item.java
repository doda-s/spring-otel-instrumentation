package me.phgs.application.model.common;

public abstract class Item {
    String name;
    int count;
    long id;

    public Item(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getItemCount() {
        return count;
    }

    public void addItemCount(int count) {
        this.count+=count;
    }

    public void decreaseItemCount(int count) {
        this.count-=count;
    }

    public long getId() {
        return id;
    }
}
