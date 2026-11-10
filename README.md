# OAuth2 Client vs JWT Resource Server — учебный проект

Проект содержит два отдельных Spring Boot приложения и Keycloak как учебный Authorization Server:

- **client-app** (`:8080`) — Spring OAuth2 Client. Выполняет Authorization Code login, хранит OAuth сессию пользователя и получает access token.
- **resource-server** (`:8081`) — Spring OAuth2 Resource Server. Не запускает login flow; принимает bearer JWT, проверяет подпись/issuer и ограничивает доступ по ролям.
- **Keycloak** (`:8180`) — выдаёт JWT и содержит локальный импортируемый realm.

Это наглядно показывает: Client инициирует авторизацию и вызывает API от имени пользователя; Resource Server защищает API и проверяет предъявленный access token. Приложения запускаются локально, Keycloak — через Docker Compose.

## Быстрый запуск

См. `start.txt` для полного walkthrough. Коротко:

```bash
docker compose up -d
```

В терминале 1:

```bash
gradle :resource-server:bootRun
```

В терминале 2:

```bash
gradle :client-app:bootRun
```

Затем открой `http://localhost:8080/oauth2/authorization/keycloak`, войди как `alice` (локальный пароль `alice-local-only`) и запроси `/api/call-resource`.

Все встроенные credentials предназначены только для локального обучения; не используй их в реальных системах.
