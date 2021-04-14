package com.example.ft_hangouts;

public class SmsData {
    private int IdSms;
    private String Phone;
    private String Msg;
    private String Who;

    public SmsData(int idSms, String Phone, String Msg, String Who) {
        this.setIdSms( idSms );
        this.setPhone( Phone );
        this.setMsg( Msg );
        this.setWho( Who );
    }

    public int getIdSms() {
        return IdSms;
    }

    public void setIdSms(int idSms) {
        IdSms = idSms;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getWho() {
        return Who;
    }

    public void setWho(String who) {
        Who = who;
    }
}
