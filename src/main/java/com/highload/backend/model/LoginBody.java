package com.highload.backend.model;

import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

@Validated
public class LoginBody {
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private UUID id = null;

    @JsonProperty("password")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private String password = null;

    public LoginBody id(UUID id) {
        this.id = id;
        return this;
    }

    @Schema(description = "Идентификатор пользователя")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LoginBody password(String password) {
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
        LoginBody loginBody = (LoginBody) o;
        return Objects.equals(this.id, loginBody.id) &&
            Objects.equals(this.password, loginBody.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LoginBody {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
