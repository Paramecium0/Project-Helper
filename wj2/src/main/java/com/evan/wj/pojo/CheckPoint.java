package com.evan.wj.pojo;

        import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
        import javax.persistence.*;
        import java.sql.Timestamp;

@Entity
public class CheckPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int project;
    String pre_week;
    String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public String getPre_week() {
        return pre_week;
    }

    public void setPre_week(String pre_week) {
        this.pre_week = pre_week;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
