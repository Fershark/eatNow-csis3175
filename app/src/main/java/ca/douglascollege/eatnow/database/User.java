package ca.douglascollege.eatnow.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String email;
    private String phone;
    private String name;
    private String password;
    @ColumnInfo(name = "created_at")
    private Date createdAt;

    public User(int id, String email, String phone, String name, String password, Date createdAt){
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.createdAt = createdAt;
    }

    @Ignore
    public User(String email, String phone, String name, String password) {
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.createdAt = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
