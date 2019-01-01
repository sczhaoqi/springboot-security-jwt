package com.sc.zhaoqi.ssj.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class Goods
        implements Serializable, Comparable<Goods>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String sid;
    String name;
    double price;
    int catalog;
    String details;
    int create_user;
    int update_user;
    Timestamp create_time;
    Timestamp update_time;

    public Goods()
    {
    }

    public String getSid()
    {
        return sid;
    }

    public void setSid(String sid)
    {
        this.sid = sid;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public int getCatalog()
    {
        return catalog;
    }

    public void setCatalog(int catalog)
    {
        this.catalog = catalog;
    }

    public String getDetails()
    {
        return details;
    }

    public void setDetails(String details)
    {
        this.details = details;
    }

    public int getCreate_user()
    {
        return create_user;
    }

    public void setCreate_user(int create_user)
    {
        this.create_user = create_user;
    }

    public int getUpdate_user()
    {
        return update_user;
    }

    public void setUpdate_user(int update_user)
    {
        this.update_user = update_user;
    }

    public Timestamp getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time)
    {
        this.create_time = create_time;
    }

    public Timestamp getUpdate_time()
    {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time)
    {
        this.update_time = update_time;
    }

    @Override
    public int compareTo(Goods o)
    {
        if(this.id - o.getId() > 0){
            return 1;
        }else if(this.id - o.getId() < 0){
            return -1;
        }else{
            return 0;
        }
    }
}
