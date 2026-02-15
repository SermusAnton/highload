local user_id = ARGV[1]
local offset = tonumber(ARGV[2]) or 0
local limit = tonumber(ARGV[3]) or 10

local friends_raw = redis.call('HGET', 'Friends', user_id)
if not friends_raw then return {} end
local friends_ids = cjson.decode(friends_raw)

local result = {}
local current_count = 0
local added_count = 0

for _, f_id in ipairs(friends_ids) do
    if added_count >= limit then break end

    local posts = redis.call('LRANGE', 'Posts' .. f_id, 0, offset + limit)

    for _, post_json in ipairs(posts) do
        current_count = current_count + 1
        if current_count > offset then
            table.insert(result, post_json)
            added_count = added_count + 1
            if added_count >= limit then break end
        end
    end
end

return result