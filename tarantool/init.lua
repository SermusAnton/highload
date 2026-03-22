box.cfg{
    listen = 3301,
    auth_type = 'chap-sha1'
}

-- 1. Устанавливаем пароль (вы уже это сделали)
box.schema.user.passwd('admin', 'admin')

-- 2. Даем пользователю admin права суперпользователя (если их нет)
box.schema.user.grant('admin', 'super', nil, nil, {if_not_exists = true})

-- 3. ВАЖНО: Разрешаем вход по сети (universe access)
box.schema.user.grant('admin', 'read,write,execute', 'universe', nil, {if_not_exists = true})