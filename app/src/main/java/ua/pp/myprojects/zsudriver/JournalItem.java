/*
*    Copyright (C) 2017 Oleksandr Korneiko
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/

package ua.pp.myprojects.zsudriver;

public class JournalItem {

    //private String name;
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
//        this.name = name;
        this.date = date;
        this.number = number;
        this.kmBefore = kmBefore;
        this.kmAfter = kmAfter;
        this.fuelBefore = fuelBefore;
        this.fuelAdd = fuelAdd;
        this.fuelAfter = fuelAfter;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

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
