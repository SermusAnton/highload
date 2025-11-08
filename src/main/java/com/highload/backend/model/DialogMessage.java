package com.highload.backend.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public class DialogMessage {
    @JsonProperty("from")
    private String from = null;

    @JsonProperty("to")
    private String to = null;

    @JsonProperty("text")
    private String text = null;

    public DialogMessage from(String from) {
        this.from = from;
        return this;
    }

    @Schema(required = true, description = "Идентификатор пользователя")
    @NotNull
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public DialogMessage to(String to) {
        this.to = to;
        return this;
    }

    @Schema(required = true, description = "Идентификатор пользователя")
    @NotNull
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public DialogMessage text(String text) {
        this.text = text;
        return this;
    }

    @Schema(example = "Привет, как дела?", required = true, description = "Текст сообщения")
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
        DialogMessage dialogMessage = (DialogMessage) o;
        return Objects.equals(this.from, dialogMessage.from) &&
            Objects.equals(this.to, dialogMessage.to) &&
            Objects.equals(this.text, dialogMessage.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, text);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DialogMessage {\n");

        sb.append("    from: ").append(toIndentedString(from)).append("\n");
        sb.append("    to: ").append(toIndentedString(to)).append("\n");
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
