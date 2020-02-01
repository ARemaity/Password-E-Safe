package com.clapsforapps.password_e_safe;

/**
 * Created by User on 8/22/2015.
 */
public class Password {

    private int id;
    private int type;
    private String tag;
    private String email;
    private String password;
    private String username;
    private String notes;

    public Password(){

    }

    public Password(int id, String Tag, int type) {
        this.tag = Tag;
        this.id = id;
        this.type = type;
    }

    public Password(int id, String Tag, String Email, String Password, String Username, String Notes, int type) {
        this.id = id;
        this.tag = Tag;
        this.email = Email;
        this.password = Password;
        this.username = Username;
        this.notes = Notes;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTag(){
        return tag;
    }

    public void setTag(String tag){
        this.tag = tag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SocialMedia{" +
                "tag='" + tag + '\'' +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
