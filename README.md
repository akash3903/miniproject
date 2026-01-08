# Multi-Threading Chat Server 💬

A robust, real-time chat application built in Java using **Socket Programming** and **Multi-Threading**. This project demonstrates the Client-Server architecture where multiple clients can communicate simultaneously via a central server without blocking.

## 🚀 Features

* **Real-Time Messaging:** Instant delivery of messages to all connected users.
* **Multi-User Support:** Handles multiple concurrent client connections using Java Threads.
* **Broadcasting:** Messages sent by one user are automatically broadcast to everyone else in the chat.
* **Dynamic Usernames:** Supports custom usernames or assigns default ones (e.g., User1, User2).
* **Graceful Exit:** Clients can disconnect cleanly using the `/quit` command.
* **Console Interface:** Simple, lightweight command-line interface (CLI).

## 🛠️ Technologies Used

* **Language:** Java (JDK 8+)
* **Networking:** `java.net.Socket`, `java.net.ServerSocket`
* **Concurrency:** `java.lang.Thread`, `java.util.concurrent.ConcurrentHashMap`
* **I/O:** `java.io.PrintWriter`, `java.io.BufferedReader`

## 📂 Project Structure

```text
📦 Multi-Threading-Chat-Server
 ┣ 📜 ChatServer.java      # The central server that handles connections and broadcasting
 ┣ 📜 ChatClient.java      # The client application for users to join the chat
 ┗ 📜 README.md            # Project documentation
