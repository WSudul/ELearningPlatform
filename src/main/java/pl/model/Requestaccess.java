package pl.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "requestacess")
public class Requestaccess {

    private long idaccess;
    private long roleid;
    private long subjectid;
    private long userid;


    public long getIdaccess() {
        return idaccess;
    }

    public void setIdaccess(long idaccess) {
        this.idaccess = idaccess;
    }


    public long getRoleid() {
        return roleid;
    }

    public void setRoleid(long roleid) {
        this.roleid = roleid;
    }


    public long getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(long subjectid) {
        this.subjectid = subjectid;
    }


    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

}
