# 💬 Quick Chat

A real-time multi-room chat application built with **Spring Boot**, **WebSockets**, **SockJS**, and **STOMP**.  
Multiple users can join named rooms and exchange messages instantly — no page refresh needed.

---

## ✨ Features

- Real-time messaging via WebSockets (STOMP protocol)
- **Multi-room support** — join any room by entering a Room ID
- SockJS fallback for browsers without native WebSocket support
- Clean, responsive UI with connection status indicator
- XSS-safe message rendering
- Runs on any machine (no hardcoded IPs)

---

## 🛠️ Tech Stack

| Layer     | Technology                          |
|-----------|-------------------------------------|
| Backend   | Spring Boot 3.4.0, Spring WebSocket |
| Protocol  | STOMP over SockJS                   |
| Frontend  | Vanilla JS, SockJS client, STOMP.js |
| Build     | Maven (Java 21)                     |

---

## 🚀 How to Run

### Option 1 — Run the pre-built JAR (fastest)

> Requires: **Java 21+**

```bash
java -jar target/chat-0.0.1-SNAPSHOT.jar
```

Open your browser at: **http://localhost:3000**

---

### Option 2 — Build and run from source

> Requires: **Java 21+** and **Maven 3.8+** (or use the included `mvnw` wrapper)

```bash
# Clone / unzip the project, then:
cd Quick-Chat-fixed

# Build
./mvnw package -DskipTests

# Run
java -jar target/chat-0.0.1-SNAPSHOT.jar
```

Open your browser at: **http://localhost:3000**

---

### Option 3 — Docker

> Requires: **Docker**

```bash
# Build the image
docker build -t quick-chat .

# Run the container
docker run -p 3000:3000 quick-chat
```

Open your browser at: **http://localhost:3000**

---

### Option 4 — Change the port

To run on a different port, pass it as an argument:

```bash
java -jar target/chat-0.0.1-SNAPSHOT.jar --server.port=8080
```

---

## 📖 How to Use

1. Open **http://localhost:3000** in two or more browser tabs/windows.
2. Type a **Room ID** (e.g. `general`, `team-a`) and click **Join Room**.
3. Enter your **name** and start chatting.
4. All users in the same room see each other's messages in real time.
5. Users in different rooms do **not** see each other's messages.

---

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/sts/
│   │   ├── ChatApplication.java          # Spring Boot entry point
│   │   ├── config/
│   │   │   └── WebSocketConfig.java      # WebSocket + STOMP broker config
│   │   ├── controller/
│   │   │   └── ChatController.java       # Handles incoming messages & broadcasts
│   │   └── model/
│   │       └── ChatMessage.java          # Message model (sender, content, recipient)
│   └── resources/
│       ├── application.properties        # Server config (port, logging)
│       └── static/
│           └── index.html                # Frontend chat UI
```

---

## 🔌 WebSocket API

| Direction        | Destination                       | Description                          |
|------------------|-----------------------------------|--------------------------------------|
| Client → Server  | `/app/{roomID}/sendMessage`       | Send a message to a room             |
| Server → Client  | `/topic/{roomID}/messages`        | Subscribe to receive room messages   |

**Message payload (JSON):**
```json
{
  "sender": "Alice",
  "content": "Hello everyone!",
  "recipient": "general"
}
```

---

## ⚙️ Configuration

Edit `src/main/resources/application.properties`:

```properties
server.port=3000                          # Change the port
logging.level.com.sts=DEBUG              # Enable debug logs
```

---

## 🐛 Bugs Fixed (from original)

| Bug | Fix |
|-----|-----|
| Hardcoded IP `192.168.1.35:3000` in frontend | Replaced with `window.location.origin` — works on any machine |
| Frontend subscribed to `/topic/messages` but server broadcast to `/topic/{roomID}/messages` | Aligned paths to use room-scoped topics |
| Frontend sent to `/app/sendMessage` but controller mapped `/{roomID}/sendMessage` | Fixed frontend to send to `/app/{roomID}/sendMessage` |
| Missing `.withSockJS()` on WebSocket endpoint | Added SockJS fallback to `WebSocketConfig` |
| Debug logging level left on for all WebSocket layers | Set to `INFO` in `application.properties` |
| Thymeleaf template warning on every startup | Added `spring.thymeleaf.check-template-location=false` |
| Single-stage Dockerfile (no build caching) | Replaced with optimized multi-stage Docker build |

---

## 📄 License

MIT — free to use and modify.
