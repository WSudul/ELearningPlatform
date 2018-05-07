package pl.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "access")
public class Access {

  private long idaccess;
  private long roleid;
  private long courseId;
  private long userId;


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


  public long getCourseId() {
    return courseId;
  }

  public void setCourseId(long courseId) {
    this.courseId = courseId;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

}
