package org.example.elements;

import java.sql.Date;

public class YoungestEldestCountWorkers {
    private String type;
    private String name;
    private Date birthday;

    public YoungestEldestCountWorkers(String type, String name, Date birthday) {
        this.type = type;
        this.name = name;
        this.birthday = birthday;
    }
    @Override
    public String toString(){
        return "Youngest Eldest Count Workers {" +
                "TYPE=" + type +
                ", NAME='" + name + '\'' +
                ", BIRTHDAY='" + birthday + '\''  +
                '}';
    }
}
