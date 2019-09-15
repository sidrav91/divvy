package com.divvy.util;

public interface TrendLine {
    public void setValues(double[] y, double[] x); 
    public double predict(double x); 
}