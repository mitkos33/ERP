package com.wg.erp.crm.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public class ClientAddDTO{

    @NotNull(message = "{add.client.firstName.length}")
    @Size(min = 2, max = 50 , message = "{add.client.firstName.length}") String firstName;

    @NotNull(message = "{add.client.lastName.length}")
    @Size(min = 2, max = 50 , message = "{add.client.lastName.length}") String lastName;

    String companyName;
    String companyAddress;

    @NotNull(message = "{add.client.eik.invalid}")
    @Length(min = 9, max = 11, message = "{add.client.eik.invalid}")
    String eik;

    String mol;
    boolean isVatRegistered;
    String vatNumber;

    @NotNull(message = "{add.client.phone.length}")
    @Size(min = 6, max = 10, message = "{add.client.phone.invalid}")
    String phone;

    @Email(message = "{add.client.email.invalid}")
    String email;

    @Size(max = 100, message = "{add.client.description.length}")
    String description;

    public static ClientAddDTO empty() {
        return new ClientAddDTO();
    }

    public @NotNull(message = "{add.client.firstName.length}") @Size(min = 2, max = 50, message = "{add.client.firstName.length}") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull(message = "{add.client.firstName.length}") @Size(min = 2, max = 50, message = "{add.client.firstName.length}") String firstName) {
        this.firstName = firstName;
    }

    public @NotNull(message = "{add.client.lastName.length}") @Size(min = 2, max = 50, message = "{add.client.lastName.length}") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull(message = "{add.client.lastName.length}") @Size(min = 2, max = 50, message = "{add.client.lastName.length}") String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public @NotNull(message = "{add.client.eik.invalid}") @Length(min = 9, max = 11, message = "{add.client.eik.invalid}") String getEik() {
        return eik;
    }

    public void setEik(@NotNull(message = "{add.client.eik.invalid}") @Length(min = 9, max = 11, message = "{add.client.eik.invalid}") String eik) {
        this.eik = eik;
    }

    public String getMol() {
        return mol;
    }

    public void setMol(String mol) {
        this.mol = mol;
    }

    public boolean isVatRegistered() {
        return isVatRegistered;
    }

    public void setVatRegistered(boolean vatRegistered) {
        isVatRegistered = vatRegistered;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public @NotNull(message = "{add.client.phone.length}") @Size(min = 6, max = 10, message = "{add.client.phone.invalid}") String getPhone() {
        return phone;
    }

    public void setPhone(@NotNull(message = "{add.client.phone.length}") @Size(min = 6, max = 10, message = "{add.client.phone.invalid}") String phone) {
        this.phone = phone;
    }

    public @Email(message = "{add.client.email.invalid}") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "{add.client.email.invalid}") String email) {
        this.email = email;
    }

    public @Size(max = 100, message = "{add.client.description.length}") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 100, message = "{add.client.description.length}") String description) {
        this.description = description;
    }

    public boolean getIsVatRegistered() {
        return isVatRegistered;
    }


}
