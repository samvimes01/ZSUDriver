/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
 */

package ua.pp.myprojects.zsudriver;

public class JournalItem {

    private String date;
    private Integer number;
    private Integer kmBefore;
    private Integer kmAfter;
    private Integer fuelBefore;
    private Integer fuelAdd;
    private Integer fuelAfter;


    public JournalItem() {
    }

    public JournalItem(String date, Integer number, Integer kmBefore, Integer kmAfter, Integer fuelBefore, Integer fuelAdd, Integer fuelAfter) {
        this.date = date;
        this.number = number;
        this.kmBefore = kmBefore;
        this.kmAfter = kmAfter;
        this.fuelBefore = fuelBefore;
        this.fuelAdd = fuelAdd;
        this.fuelAfter = fuelAfter;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getKmBefore() {
        return kmBefore;
    }

    public void setKmBefore(Integer kmBefore) {
        this.kmBefore = kmBefore;
    }

    public Integer getKmAfter() {
        return kmAfter;
    }

    public void setKmAfter(Integer kmAfter) {
        this.kmAfter = kmAfter;
    }

    public Integer getFuelBefore() {
        return fuelBefore;
    }

    public void setFuelBefore(Integer fuelBefore) {
        this.fuelBefore = fuelBefore;
    }

    public Integer getFuelAdd() {
        return fuelAdd;
    }

    public void setFuelAdd(Integer fuelAdd) {
        this.fuelAdd = fuelAdd;
    }

    public Integer getFuelAfter() {
        return fuelAfter;
    }

    public void setFuelAfter(Integer fuelAfter) {
        this.fuelAfter = fuelAfter;
    }
}
