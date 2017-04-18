package site.hanschen.api.user.db.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author HansChen
 */
@Entity
@Table(name = "User", schema = "UserCenter", catalog = "")
public class UserEntity {
    private long userId;
    private String email;
    private String password;
    private String passwordMd5;

    @Id
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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
    @Column(name = "password", nullable = false, length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "password_md5", nullable = false, length = 256)
    public String getPasswordMd5() {
        return passwordMd5;
    }

    public void setPasswordMd5(String passwordMd5) {
        this.passwordMd5 = passwordMd5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserEntity that = (UserEntity) o;

        if (userId != that.userId)
            return false;
        if (email != null ? !email.equals(that.email) : that.email != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null)
            return false;
        if (passwordMd5 != null ? !passwordMd5.equals(that.passwordMd5) : that.passwordMd5 != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (passwordMd5 != null ? passwordMd5.hashCode() : 0);
        return result;
    }
}
