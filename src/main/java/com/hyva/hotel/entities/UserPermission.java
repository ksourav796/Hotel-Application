package com.hyva.hotel.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "userpermission")
public class UserPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String created_by;
    private String user_id;
    private String page_id;
    private String per_add;
    private String per_delete;
    private String per_edit;
    private String per_view;
    private String per_active;
    private String per_inactive;
    private String per_pay;
    private String per_export;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPage_id() {
        return page_id;
    }

    public void setPage_id(String page_id) {
        this.page_id = page_id;
    }

    public String getPer_add() {
        return per_add;
    }

    public void setPer_add(String per_add) {
        this.per_add = per_add;
    }

    public String getPer_delete() {
        return per_delete;
    }

    public void setPer_delete(String per_delete) {
        this.per_delete = per_delete;
    }

    public String getPer_edit() {
        return per_edit;
    }

    public void setPer_edit(String per_edit) {
        this.per_edit = per_edit;
    }

    public String getPer_view() {
        return per_view;
    }

    public void setPer_view(String per_view) {
        this.per_view = per_view;
    }

    public String getPer_active() {
        return per_active;
    }

    public void setPer_active(String per_active) {
        this.per_active = per_active;
    }

    public String getPer_inactive() {
        return per_inactive;
    }

    public void setPer_inactive(String per_inactive) {
        this.per_inactive = per_inactive;
    }

    public String getPer_pay() {
        return per_pay;
    }

    public void setPer_pay(String per_pay) {
        this.per_pay = per_pay;
    }

    public String getPer_export() {
        return per_export;
    }

    public void setPer_export(String per_export) {
        this.per_export = per_export;
    }
}
