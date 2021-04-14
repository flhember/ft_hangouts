package com.example.ft_hangouts;

public class ContactData {
    private int IdContact;
    private String First;
    private String Name;
    private String Phone;
    private String Mail;
    private String Postal;

    public ContactData(int idContact, String first, String name, String phone, String mail, String postal) {
        this.setIdContact( idContact );
        this.setFirst( first );
        this.setName( name );
        this.setPhone( phone );
        this.setMail( mail );
        this.setPostal( postal );
    }

    public int getIdContact() {
        return IdContact;
    }

    public void setIdContact(int idContact) {
        IdContact = idContact;
    }

    public String getFirst() {
        return First;
    }

    public void setFirst(String first) {
        First = first;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getPostal() {
        return Postal;
    }

    public void setPostal(String postal) {
        Postal = postal;
    }


}
