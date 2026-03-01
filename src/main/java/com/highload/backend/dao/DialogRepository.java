package com.highload.backend.dao;

import com.highload.backend.dao.mapper.DialogMapper;
import com.highload.backend.model.DialogMessage;
import com.highload.backend.model.generated.tables.Dialog;
import jakarta.validation.constraints.NotNull;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class DialogRepository {

    private final DSLContext context;

    public DialogRepository(DSLContext context) {
        this.context = context;
    }

    public List<DialogMessage> getListBy(UUID userId, UUID withUserId, long sumHash) {
        return context.selectFrom(Dialog.DIALOG)
            .where(Dialog.DIALOG.SUM_USER_HASH.eq(sumHash))
            .and(Dialog.DIALOG.FROM_USER_ID.eq(userId).or(Dialog.DIALOG.FROM_USER_ID.eq(withUserId)))
            .and(Dialog.DIALOG.TO_USER_ID.eq(userId).or(Dialog.DIALOG.TO_USER_ID.eq(withUserId)))
            .orderBy(Dialog.DIALOG.CREATE_TIME.desc())
            .fetch(new DialogMapper());
    }

    public void add(@NotNull UUID fromUserId,
        @NotNull UUID toUserId,
        long sumHash,
        @NotNull String text) {
        context.insertInto(Dialog.DIALOG)
            .columns(
                Dialog.DIALOG.FROM_USER_ID,
                Dialog.DIALOG.TO_USER_ID,
                Dialog.DIALOG.SUM_USER_HASH,
                Dialog.DIALOG.TEXT)
            .values(
                fromUserId,
                toUserId,
                sumHash,
                text)
            .execute();
    }
}
