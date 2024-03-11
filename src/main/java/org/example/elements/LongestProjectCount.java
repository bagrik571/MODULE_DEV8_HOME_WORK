package org.example.elements;

public class LongestProjectCount {
    private int id;
    private int monthCount;

    public LongestProjectCount(int id, int monthCount) {
        this.id = id;
        this.monthCount = monthCount;
    }
    @Override
    public String toString(){
        return "Longest Project Count {" +
                "ID=" + id +
                ", MONTH COUNT='" + monthCount + '\'' +
                '}';
    }
}
