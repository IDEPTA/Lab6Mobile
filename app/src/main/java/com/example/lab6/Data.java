package com.example.lab6;

public class Data {
    int numKv;
    float water;
    float energy;

    public Data(int numKv, float water, float energy) {
        this.numKv = numKv;
        this.water = water;
        this.energy = energy;
    }

    public int getNumKv() {
        return this.numKv;
    }

    public float getEnergy() {
        return energy;
    }
    public float getWater(){
        return water;
    }
}
