package com.highload.backend.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import jakarta.validation.Valid;

@Validated
public class UserRegisterBody {
    @JsonProperty("first_name")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private String firstName = null;

    @JsonProperty("second_name")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private String secondName = null;

    @JsonProperty("birthdate")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private LocalDate birthdate = null;

    @JsonProperty("biography")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private String biography = null;

    @JsonProperty("city")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private String city = null;

    @JsonProperty("password")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private String password = null;

    public UserRegisterBody firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Schema(example = "Имя", description = "")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public UserRegisterBody secondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    @Schema(example = "Фамилия", description = "")
    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public UserRegisterBody birthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    @Schema(example = "Wed Feb 01 00:00:00 GMT 2017", description = "Дата рождения")

    @Valid
    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public UserRegisterBody biography(String biography) {

        this.biography = biography;
        return this;
    }

    @Schema(example = "Хобби, интересы и т.п.", description = "")
    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public UserRegisterBody city(String city) {
        this.city = city;
        return this;
    }

    @Schema(example = "Москва", description = "")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public UserRegisterBody password(String password) {
        this.password = password;
        return this;
    }

    @Schema(example = "Секретная строка", description = "")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserRegisterBody userRegisterBody = (UserRegisterBody) o;
        return Objects.equals(this.firstName, userRegisterBody.firstName) &&
            Objects.equals(this.secondName, userRegisterBody.secondName) &&
            Objects.equals(this.birthdate, userRegisterBody.birthdate) &&
            Objects.equals(this.biography, userRegisterBody.biography) &&
            Objects.equals(this.city, userRegisterBody.city) &&
            Objects.equals(this.password, userRegisterBody.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, birthdate, biography, city, password);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UserRegisterBody {\n");

        sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
        sb.append("    secondName: ").append(toIndentedString(secondName)).append("\n");
        sb.append("    birthdate: ").append(toIndentedString(birthdate)).append("\n");
        sb.append("    biography: ").append(toIndentedString(biography)).append("\n");
        sb.append("    city: ").append(toIndentedString(city)).append("\n");
        sb.append("    password: ").append(toIndentedString(password)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
