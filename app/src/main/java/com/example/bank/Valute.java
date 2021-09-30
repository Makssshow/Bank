package com.example.bank;

public class Valute {
    private String charCode,
            value,
            name,
            nominal,
            NumCode;


    public String getCharCode() {
        return charCode;
    }

    public String getValue() {
        return value;
    }

    public String getNumCode() {
        return NumCode;
    }

    public String getName() {
        return name;
    }

    public String getNominal() {
        return nominal;
    }


    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setNumCode(String NumCode) {
        this.NumCode = NumCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }
//    public String toString(){
//        return  "CharCode: " + charCode + " Value: " + value;
//    }
}