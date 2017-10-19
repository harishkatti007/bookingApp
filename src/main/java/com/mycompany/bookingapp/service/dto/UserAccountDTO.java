package com.mycompany.bookingapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the UserAccount entity.
 */
public class UserAccountDTO implements Serializable {

    private Long id;

    @NotNull
    private String fullName;

    @NotNull
    private String emailId;

    @NotNull
    private String phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserAccountDTO userAccountDTO = (UserAccountDTO) o;
        if(userAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAccountDTO{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", emailId='" + getEmailId() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
