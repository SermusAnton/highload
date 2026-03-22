-- Создаем таблицу (space)
local messages = box.schema.space.create('messages', { if_not_exists = true })

-- Описываем формат (аналог Java-модели)
messages:format({
    {name = 'id',   type = 'unsigned'}, -- Первичный ключ (ID сообщения)
    {name = 'from', type = 'string'},   -- @JsonProperty("from")
    {name = 'to',   type = 'string'},   -- @JsonProperty("to")
    {name = 'text', type = 'string'},   -- @JsonProperty("text")
    {name = 'sumHash', type = 'integer'},
})

-- Создаем первичный индекс (обязательно)
messages:create_index('primary', {
    parts = {'id'},
    if_not_exists = true
})

-- Создаем вторичный индекс для поиска по отправителю (опционально)
messages:create_index('from_idx', {
    parts = {'from'},
    unique = false,
    if_not_exists = true
})

-- Индекс для поиска по sumHash
messages:create_index('sum_hash_idx', {
    parts = {'sumHash'},
    unique = false,
    if_not_exists = true
})

-- 4. Определение ФУНКЦИИ (доступна для Spring через @Query)
-- Глобальная функция, чтобы Tarantool её видел
_G.get_dialog_by_params = function(user_id, with_user_id, sum_hash)
    -- Ищем по индексу sumHash (это быстро)
    local result = box.space.messages.index.sum_hash_idx:select({sum_hash})

    local filtered = {}
    for _, tuple in ipairs(result) do
        -- Фильтруем по участникам диалога
        if (tuple.from == user_id and tuple.to == with_user_id) or
                (tuple.from == with_user_id and tuple.to == user_id) then

            local dto_row = {
                ["from"] = tuple.from,
                ["to"]   = tuple.to,
                ["text"] = tuple.text
            }

            table.insert(filtered, dto_row)
        end
    end

    return filtered
end

-- 1. Создаем последовательность для ID, если её нет
box.schema.sequence.create('msg_id_seq', { if_not_exists = true })

-- 2. Регистрируем функцию в глобальной области видимости _G
_G.insert_dialog = function(from_id, to_id, sum_hash, text)
    -- Генерируем следующий ID из последовательности
    local next_id = box.sequence.msg_id_seq:next()

    -- Вставляем данные в спейс 'messages'
    -- Порядок полей должен строго соответствовать вашему :format()
    -- {id, from, to, text, sumHash}
    box.space.messages:insert({
        next_id,            -- id
        tostring(from_id),  -- from (превращаем UUID в строку для индекса)
        tostring(to_id),    -- to
        text,               -- text
        sum_hash            -- sumHash
    })

    -- Процедуры @Query в Spring Data Tarantool для void методов
    -- обычно не требуют возврата значения, но можно вернуть true
    return true
end

