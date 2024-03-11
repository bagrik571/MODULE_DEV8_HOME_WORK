package org.example;

public class Data {
    private int id;
    private String name;

    public Data(int id, String name) {
        this.id = id;
        this.name = name;
    }
    @Override
    public String toString(){
        return "Data{" +
                "ID=" + id +
                ", NAME='" + name + '\'' +
                '}';
    }
}
