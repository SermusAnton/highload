package com.highload.backend.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

@Validated
public class InlineResponse200 {
    @JsonProperty("token")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private String token = null;

    public InlineResponse200 token(String token) {
        this.token = token;
        return this;
    }

    @Schema(example = "e4d2e6b0-cde2-42c5-aac3-0b8316f21e58", description = "")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InlineResponse200 inlineResponse200 = (InlineResponse200) o;
        return Objects.equals(this.token, inlineResponse200.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class InlineResponse200 {\n");

        sb.append("    token: ").append(toIndentedString(token)).append("\n");
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
