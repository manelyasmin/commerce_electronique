package sample;


import javafx.beans.property.SimpleDoubleProperty;

import javafx.beans.property.SimpleStringProperty;



public class Result {
  private SimpleDoubleProperty averageGain;
    private SimpleDoubleProperty averageTime;
    private SimpleStringProperty instance;

    public String getInstance() {
        return instance.get();
    //return  this.instance.getName();
    }
    public String getInstanceName(){return this.instance.getName();}


    public double getAverageGain() {
        return averageGain.get();
    }



    public double getAverageTime() {
        return averageTime.get();
    }
    public Result(double averageGain, double averageTime ) {//nombre clause sat et rate et time

        this.averageGain = new SimpleDoubleProperty(averageGain);

        this.averageTime = new SimpleDoubleProperty(averageTime);

    }
    public Result(double averageGain, double averageTime,String instance) {//nombre clause sat et rate et time

        this.averageGain = new SimpleDoubleProperty(averageGain);
        this.averageTime = new SimpleDoubleProperty(averageTime);
        this.instance=new SimpleStringProperty(instance);
    }

}