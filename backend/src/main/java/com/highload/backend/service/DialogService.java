package com.highload.backend.service;

import com.highload.backend.dao.DialogRepository;
import com.highload.backend.model.DialogMessage;
import com.highload.backend.model.UserIdSendBody;
import net.jpountz.xxhash.XXHash64;
import net.jpountz.xxhash.XXHashFactory;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

@Service
public class DialogService {

    private final DialogRepository dialogRepository;

    private static final XXHash64 hasher = XXHashFactory.fastestInstance().hash64();
    private static final long SEED = 0L;

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
        // Конвертируем UUID в 16 байт (стандартное представление)
        byte[] bytes = ByteBuffer.allocate(16)
            .putLong(uuid.getMostSignificantBits())
            .putLong(uuid.getLeastSignificantBits())
            .array();
        // Получаем 64-битный хеш
        return hasher.hash(bytes, 0, bytes.length, SEED);
    }

    private static long calculateSum(long hash1, long hash2) {
        // самое простое и быстрое решение,
        // но с переполнением, что для hash не критично
        return hash1 + hash2;
    }
}
