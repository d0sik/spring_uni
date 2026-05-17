# 🛒 Shop Project — Полный гайд

## 📁 СТРУКТУРА ПРОЕКТА
```
shop-project/
├── src/main/java/com/shop/
│   ├── ShopApplication.java          ← Точка входа
│   ├── config/
│   │   ├── SecurityConfig.java       ← Spring Security (сессии)
│   │   └── DataInitializer.java      ← Тестовые данные при старте
│   ├── entity/
│   │   ├── Product.java              ← Товар
│   │   ├── Customer.java             ← Клиент/Пользователь
│   │   └── Order.java                ← Заказ
│   ├── repository/
│   │   ├── ProductRepository.java
│   │   ├── CustomerRepository.java
│   │   └── OrderRepository.java
│   ├── service/
│   │   ├── ProductService.java
│   │   ├── CustomerService.java
│   │   ├── OrderService.java         ← Уникальная логика (склад, расчёт суммы)
│   │   └── CustomerDetailsService.java ← Нужен Spring Security
│   ├── controller/
│   │   ├── AuthController.java       ← /api/auth/**
│   │   ├── ProductController.java    ← /api/products/**
│   │   ├── OrderController.java      ← /api/orders/**
│   │   ├── CustomerController.java   ← /api/customers/**
│   │   └── CurrencyController.java   ← /api/currency/** (Feign)
│   └── feign/
│       ├── CurrencyClient.java       ← Feign Client интерфейс
│       └── CurrencyClientFallback.java ← Fallback если API недоступен
├── src/main/resources/
│   └── application.properties
├── Dockerfile
└── docker-compose.yml
```

---

## ⚙️ ШАГ 1 — Spring Initializr

Зайди на https://start.spring.io и выбери:

| Параметр | Значение |
|----------|----------|
| Project | Maven |
| Language | Java |
| Spring Boot | 3.2.x |
| Group | com.shop |
| Artifact | shop |
| Packaging | Jar |
| Java | 17 |

**Dependencies (добавь все):**
- Spring Web
- Spring Security
- Spring Data JPA
- PostgreSQL Driver
- Spring Session
- Validation
- Lombok
- OpenFeign ← ищи в поиске "OpenFeign"

Нажми **Generate** → распакуй архив.

---

## ⚙️ ШАГ 2 — Перенести файлы

Замени содержимое папки src/ файлами из этого проекта.
Замени pom.xml, добавь Dockerfile и docker-compose.yml.

---

## ⚙️ ШАГ 3 — Запуск через Docker

Убедись что Docker Desktop запущен, затем:

```bash
# В папке проекта:
docker compose up --build
```

Ты увидишь что:
1. Сначала запускается `db` (PostgreSQL)
2. Потом собирается и запускается `app`
3. В логах появится "✅ Sample products added" и "✅ Test user created"

Приложение готово когда видишь: `Started ShopApplication`

---

## 🧪 ШАГ 4 — Тестирование в Postman

### Настройка Postman
В Postman: **Settings → Cookies** — убедись что куки включены.
Это важно! Сессия хранится в cookie `JSESSIONID`.

---

### 1️⃣ Регистрация нового пользователя
```
POST http://localhost:8080/api/auth/register
Body (JSON):
{
  "username": "john",
  "email": "john@example.com",
  "password": "password123"
}
```
Ожидаемый ответ: `200 OK` + `{"message": "Registered successfully"}`

---

### 2️⃣ Вход (создание сессии)
```
POST http://localhost:8080/api/auth/login
Body (JSON):
{
  "username": "john",
  "password": "password123"
}
```
Ожидаемый ответ: `200 OK` + sessionId
После этого Postman автоматически сохраняет cookie `JSESSIONID` — все последующие запросы авторизованы!

---

### 3️⃣ Проверка Spring Security (ОБЯЗАТЕЛЬНО для демо!)

#### Без авторизации — должен вернуть 401:
```
GET http://localhost:8080/api/orders/my
```
Очисти cookies в Postman, потом сделай запрос.
Ожидаемый ответ: `401 Unauthorized`

#### После логина — должен вернуть 200:
Залогинься снова (шаг 2), потом:
```
GET http://localhost:8080/api/orders/my
```
Ожидаемый ответ: `200 OK` + список заказов

---

### 4️⃣ Посмотреть товары (публичный эндпоинт)
```
GET http://localhost:8080/api/products
```
Без авторизации! Вернёт 3 товара из DataInitializer.

---

### 5️⃣ Создать заказ (защищённый)
```
POST http://localhost:8080/api/orders
Body (JSON):
{
  "productIds": [1, 2]
}
```
Ожидаемый ответ: заказ с totalPrice и status: "PENDING"
Уникальная логика: stock автоматически уменьшается!

---

### 6️⃣ Обновить статус заказа
```
PUT http://localhost:8080/api/orders/1/status
Body (JSON):
{
  "status": "CONFIRMED"
}
```

---

### 7️⃣ Feign Client — курсы валют
```
GET http://localhost:8080/api/currency/rates?base=USD
```
Feign делает HTTP запрос к внешнему API open.er-api.com и возвращает курсы.
Если нет интернета — сработает fallback с дефолтными значениями.

---

### 8️⃣ Выход из системы
```
POST http://localhost:8080/api/auth/logout
```
После этого сессия удалена — `/api/orders/my` снова вернёт 401.

---

## 💬 ОТВЕТЫ НА ВОПРОСЫ ГРЕЙДЕРА

### "Почему Feign вместо RestTemplate?"
> Feign использует декларативный подход — я просто описываю интерфейс и аннотации, а Spring генерирует реализацию. RestTemplate требует писать весь код HTTP-запроса вручную. Feign также встроено поддерживает fallback-и, логирование и интеграцию с LoadBalancer.

### "Что делает SecurityFilterChain?"
> Это цепочка фильтров, которая перехватывает каждый HTTP-запрос ДО того, как он достигает контроллера. Я настраиваю: какие URL публичные, какие требуют авторизации, как обрабатывать сессии, что возвращать при 401.

### "Почему сессии, а не JWT?"
> Session-based проще в управлении: Spring автоматически создаёт, хранит и инвалидирует сессии. Logout работает мгновенно — достаточно вызвать session.invalidate(). С JWT нужно реализовывать blacklist токенов для выхода. Для монолитного приложения сессии — правильный выбор.

### "Что делает каждый сервис в docker-compose?"
> `db` — PostgreSQL 16, хранит данные в volume `postgres_data` (данные не теряются при перезапуске), порт 5432.
> `app` — Spring Boot, собирается из Dockerfile, зависит от `db` через `depends_on` с healthcheck — не стартует пока БД не готова. Порт 8080.

---

## 🎤 ПЛАН ПРЕЗЕНТАЦИИ (2-3 минуты)

1. "Это интернет-магазин — клиенты, товары, заказы"
2. Показать структуру проекта кратко
3. `docker compose up --build` — показать что всё запускается
4. Открыть Postman, показать:
   - GET /api/products (публично, 200)
   - GET /api/orders/my БЕЗ логина (401) ← это ключевой момент!
   - POST /api/auth/login (создание сессии)
   - GET /api/orders/my ПОСЛЕ логина (200)
   - POST /api/orders (создание заказа, уменьшение стока)
   - GET /api/currency/rates (Feign Client в действии)
5. Показать код SecurityFilterChain и объяснить
6. Показать @FeignClient интерфейс
