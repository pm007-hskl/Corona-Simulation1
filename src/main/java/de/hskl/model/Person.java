package de.hskl.model;

public class Person {
    private int id;
    private int color;

    public Person(int id, int color) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", color=" + color +
                '}';
    }
}

