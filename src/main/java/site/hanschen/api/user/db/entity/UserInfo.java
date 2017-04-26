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
    private String nickname;
    private String phone;
    private Date birthday;
    private Short sex;
    private String bio;
    private long userInfoId;
    private long userId;

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

    @Id
    @Column(name = "user_info_id", nullable = false)
    public long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(long userInfoId) {
        this.userInfoId = userInfoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserInfo userInfo = (UserInfo) o;

        if (userInfoId != userInfo.userInfoId)
            return false;
        if (nickname != null ? !nickname.equals(userInfo.nickname) : userInfo.nickname != null)
            return false;
        if (phone != null ? !phone.equals(userInfo.phone) : userInfo.phone != null)
            return false;
        if (birthday != null ? !birthday.equals(userInfo.birthday) : userInfo.birthday != null)
            return false;
        if (sex != null ? !sex.equals(userInfo.sex) : userInfo.sex != null)
            return false;
        if (bio != null ? !bio.equals(userInfo.bio) : userInfo.bio != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nickname != null ? nickname.hashCode() : 0;
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (bio != null ? bio.hashCode() : 0);
        result = 31 * result + (int) (userInfoId ^ (userInfoId >>> 32));
        return result;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
