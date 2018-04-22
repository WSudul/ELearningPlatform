package pl.model;

public class Access {
    private Long idaccess;
    private Long roleid;
    private Long subjectid;
    private Long userid;

    public Long getIdaccess() {
        return idaccess;
    }

    public void setIdaccess(Long idaccess) {
        this.idaccess = idaccess;
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public Long getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Long subjectid) {
        this.subjectid = subjectid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
