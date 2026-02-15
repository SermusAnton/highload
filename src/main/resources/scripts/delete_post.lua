local key = KEYS[1]
local postId = ARGV[1]
local userId = ARGV[2]

local elements = redis.call('LRANGE', key, 0, -1)

for _, val in ipairs(elements) do
    local post = cjson.decode(val)

    if post.id == postId and post.author_user_id == userId then
        redis.call('LREM', key, 1, val)
        return true
    end
end

return false