package com.highload.backend.service;

import com.highload.backend.dao.tarantool.DialogRepository;
import com.highload.backend.model.DialogMessage;
import com.highload.backend.model.UserIdSendBody;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DialogService {

    private final DialogRepository dialogRepository;

    public DialogService(DialogRepository dialogRepository) {
        this.dialogRepository = dialogRepository;
    }

    public List<DialogMessage> getListBy(UUID userId, UUID withUserId) {
        var sumHash = calculateSum(hashUUID(userId), hashUUID(withUserId));
        return dialogRepository.getListBy(userId, withUserId, sumHash);
    }

    public void send(UUID fromUserId, UUID toUserId, UserIdSendBody body) {
        var sumHash = calculateSum(hashUUID(fromUserId), hashUUID(toUserId));
        dialogRepository.add(fromUserId, toUserId, sumHash, body.getText());
    }


    private static long hashUUID(UUID uuid) {
        // самое простое и быстрое решение,
        // но с переполнением, что для hash не критично
        return uuid.getMostSignificantBits() + uuid.getLeastSignificantBits();
    }

    private static long calculateSum(long hash1, long hash2) {
        // самое простое и быстрое решение,
        // но с переполнением, что для hash не критично
        return hash1 + hash2;
    }
}
