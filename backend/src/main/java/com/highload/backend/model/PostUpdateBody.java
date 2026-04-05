package com.highload.backend.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public class PostUpdateBody {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("text")
    private String text = null;

    public PostUpdateBody id(String id) {
        this.id = id;
        return this;
    }

    @Schema(example = "1d535fd6-7521-4cb1-aa6d-031be7123c4d", required = true, description = "Идентификатор поста")
    @NotNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PostUpdateBody text(String text) {
        this.text = text;
        return this;
    }

    @Schema(example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lectus mauris ultrices eros in cursus turpis massa.", required = true, description = "Текст поста")
    @NotNull
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PostUpdateBody postUpdateBody = (PostUpdateBody) o;
        return Objects.equals(this.id, postUpdateBody.id) &&
            Objects.equals(this.text, postUpdateBody.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PostUpdateBody {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    text: ").append(toIndentedString(text)).append("\n");
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
