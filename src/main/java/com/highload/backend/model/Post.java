package com.highload.backend.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

@Schema(description = "Пост пользователя")
@Validated
public class Post {
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private String id = null;

    @JsonProperty("text")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private String text = null;

    @JsonProperty("author_user_id")
    @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
    @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
    private String authorUserId = null;

    public Post id(String id) {
        this.id = id;
        return this;
    }

    @Schema(example = "1d535fd6-7521-4cb1-aa6d-031be7123c4d", description = "Идентификатор поста")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Post text(String text) {
        this.text = text;
        return this;
    }

    @Schema(example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lectus mauris ultrices eros in cursus turpis massa.", description = "Текст поста")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Post authorUserId(String authorUserId) {
        this.authorUserId = authorUserId;
        return this;
    }

    @Schema(description = "Идентификатор пользователя")
    public String getAuthorUserId() {
        return authorUserId;
    }

    public void setAuthorUserId(String authorUserId) {
        this.authorUserId = authorUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(this.id, post.id) &&
            Objects.equals(this.text, post.text) &&
            Objects.equals(this.authorUserId, post.authorUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, authorUserId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Post {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    text: ").append(toIndentedString(text)).append("\n");
        sb.append("    authorUserId: ").append(toIndentedString(authorUserId)).append("\n");
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
