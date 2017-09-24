/*
 * Copyright (c) 2017. Oleksandr Korneiko
 * This file is subject to the terms and conditions defined in
 * file "LICENSE", which is part of this source code package
 *
 */

package ua.pp.myprojects.zsudriver;

public class CarItem {

    //private String name;
    private String accumDate;
    private String carcasNmb;
    private String engNmb;
    private String name;
    private String type;
    private String tyresDate;
    private String tyresNmbs;
    private String vn;
    private String carId;


    public CarItem() {
    }

    public CarItem(String accumDate, String carcasNmb, String engNmb, String id, String name, String type, String tyresDate, String tyresNmbs, String vn, String carId) {
        this.accumDate = accumDate;
        this.carcasNmb = carcasNmb;
        this.engNmb = engNmb;
        this.name = name;
        this.type = type;
        this.tyresDate = tyresDate;
        this.tyresNmbs = tyresNmbs;
        this.vn = vn;
        this.vn = carId;
    }

    public String getAccumDate() {
        return accumDate;
    }

    public void setAccumDate(String accumDate) {
        this.accumDate = accumDate;
    }

    public String getCarcasNmb() {
        return carcasNmb;
    }

    public void setCarcasNmb(String carcasNmb) {
        this.carcasNmb = carcasNmb;
    }

    public String getEngNmb() {
        return engNmb;
    }

    public void setEngNmb(String engNmb) {
        this.engNmb = engNmb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTyresDate() {
        return tyresDate;
    }

    public void setTyresDate(String tyresDate) {
        this.tyresDate = tyresDate;
    }

    public String getTyresNmbs() {
        return tyresNmbs;
    }

    public void setTyresNmbs(String tyresNmbs) {
        this.tyresNmbs = tyresNmbs;
    }

    public String getVn() {
        return vn;
    }

    public void setVn(String vn) {
        this.vn = vn;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}
