package com.highload.backend.dao.mapper;

import com.highload.backend.model.DialogMessage;
import com.highload.backend.model.generated.tables.Dialog;
import org.jooq.Record;
import org.jooq.RecordMapper;

public class DialogMapper  implements RecordMapper<Record, DialogMessage> {

    @Override
    public DialogMessage map(Record record) {
        var message = new DialogMessage();
        message.from(record.get(Dialog.DIALOG.FROM_USER_ID).toString());
        message.to(record.get(Dialog.DIALOG.TO_USER_ID).toString());
        message.text(record.get(Dialog.DIALOG.TEXT));
        return message;
    }
}
