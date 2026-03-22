package com.highload.backend.dao.tarantool;

import com.highload.backend.model.DialogMessage;
import java.util.List;
import java.util.UUID;
import org.springframework.data.tarantool.repository.Query;
import org.springframework.data.tarantool.repository.TarantoolRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  DialogRepository extends TarantoolRepository<DialogMessage, Integer> {

    @Query(function = "get_dialog_by_params")
    List<DialogMessage> getListBy(UUID userId, UUID withUserId, Long sumHash);

    @Query(function = "insert_dialog")
    Boolean add(UUID fromUserId, UUID toUserId, Long sumHash, String text);
}
