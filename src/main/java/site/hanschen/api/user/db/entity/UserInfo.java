package site.hanschen.api.user.db.entity;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author HansChen
 */
@Entity
public class UserInfo {
    private long id;
    private Long userId;
    private String email;
    private String nickname;
    private String phone;
    private Date birthday;
    private Short age;
    private Short sex;
    private String bio;
    private String location;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 128)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "nickname", nullable = true, length = 50)
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 20)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "birthday", nullable = true)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "age", nullable = true)
    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    @Basic
    @Column(name = "sex", nullable = true)
    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "bio", nullable = true, length = 100)
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Basic
    @Column(name = "location", nullable = true, length = 100)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserInfo userInfo = (UserInfo) o;

        if (id != userInfo.id)
            return false;
        if (userId != null ? !userId.equals(userInfo.userId) : userInfo.userId != null)
            return false;
        if (email != null ? !email.equals(userInfo.email) : userInfo.email != null)
            return false;
        if (nickname != null ? !nickname.equals(userInfo.nickname) : userInfo.nickname != null)
            return false;
        if (phone != null ? !phone.equals(userInfo.phone) : userInfo.phone != null)
            return false;
        if (birthday != null ? !birthday.equals(userInfo.birthday) : userInfo.birthday != null)
            return false;
        if (age != null ? !age.equals(userInfo.age) : userInfo.age != null)
            return false;
        if (sex != null ? !sex.equals(userInfo.sex) : userInfo.sex != null)
            return false;
        if (bio != null ? !bio.equals(userInfo.bio) : userInfo.bio != null)
            return false;
        if (location != null ? !location.equals(userInfo.location) : userInfo.location != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (bio != null ? bio.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
