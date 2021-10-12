package dao.entities.embeddables;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import java.util.Objects;

@Embeddable
public class ContactDetails {
    @Column(name = "phone_number", length = 128)
    private String phoneNumber;

    @Column(name = "email", length = 384)
    @Email
    private String email;

    @Column(name = "extra_info", length = 512)
    private String extraInfo;

    public ContactDetails() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDetails that = (ContactDetails) o;
        return Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(email, that.email) &&
                Objects.equals(extraInfo, that.extraInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber, email, extraInfo);
    }

    @Override
    public String toString() {
        return "ContactDetails{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", extraInfo='" + extraInfo + '\'' +
                '}';
    }
}
