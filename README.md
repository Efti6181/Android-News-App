# 📰 News App — Modern Android News Application

A modern Android News Application built using **Kotlin**, **Jetpack Compose**, and **MVVM Architecture**.
The app provides real-time news updates, article search, bookmarking for offline reading, and native sharing functionality using modern Android development practices.

---

## 📱 Features

* 🗞️ Real-time top headlines from NewsAPI
* 🔍 Search news articles by keywords
* 📌 Bookmark articles for offline reading
* 📤 Share news articles using Android Share Intent
* 🌐 Open full articles in browser
* 💾 Offline access using Room Database
* ⚡ Reactive UI with StateFlow & Coroutines
* 🎨 Material Design 3 UI with Jetpack Compose
* 🧭 Multi-screen navigation using Navigation Compose

---

## 🏗️ Tech Stack

### Language

* Kotlin

### Architecture

* MVVM (Model-View-ViewModel)

### UI

* Jetpack Compose
* Material Design 3

### Libraries & Frameworks

* Retrofit
* OkHttp
* Room Database
* Dagger Hilt
* Navigation Compose
* Coil
* Kotlin Coroutines & Flow
* Gson

---

## 📂 Project Structure

```bash
com.example.newsapp
│
├── data
│   ├── api
│   ├── local
│   ├── model
│   └── repository
│
├── di
│
├── presentation
│   ├── screens
│   ├── components
│   ├── navigation
│   └── viewmodel
│
├── utils
│
└── ui.theme
```

---

## 📸 Screens

* Home Screen
* Details Screen
* Search Screen
* Bookmark Screen
<img width="72" height="160" alt="WhatsApp Image 2026-05-23 at 2 20 20 AM" src="https://github.com/user-attachments/assets/ac70ee35-0d57-4868-ae2c-2c5ce52e1597" /> <img width="72" height="160" alt="WhatsApp Image 2026-05-23 at 2 20 20 AM (2)" src="https://github.com/user-attachments/assets/3027fd9a-075e-4b63-a557-38ccb01eddff" /> <img width="72" height="160" alt="WhatsApp Image 2026-05-23 at 2 20 19 AM" src="https://github.com/user-attachments/assets/50dcd32b-3771-45e5-99c5-b7cec2eba4de" /> <img width="72" height="160" alt="WhatsApp Image 2026-05-23 at 2 20 20 AM (1)" src="https://github.com/user-attachments/assets/25424d41-e137-4c13-ae7a-442dcc560e68" />


---

## ⚙️ Architecture Overview

The application follows a clean **MVVM Architecture**:

```text
UI (Jetpack Compose)
        ↓
ViewModel (StateFlow)
        ↓
Repository
   ↙         ↘
Room DB    NewsAPI
```

---

## 🚀 Getting Started

### Prerequisites

* Android Studio Meerkat or newer
* Android SDK 26+
* Kotlin 2.0+
* NewsAPI API Key

---

## 🔑 API Setup

1. Get your API key from:

   [https://newsapi.org/](https://newsapi.org/)

2. Add your API key inside:

```properties
local.properties
```

```properties
NEWS_API_KEY=YOUR_API_KEY
```

---

## ▶️ Installation

```bash
git clone https://github.com/your-username/news-app.git
```

Open the project in Android Studio and run the app on an emulator or physical device.

---

## 🧪 Key Functionalities

### ✅ News Fetching

Uses Retrofit and OkHttp to fetch live news data from NewsAPI.

### ✅ Offline Bookmarking

Room Database stores bookmarked articles locally for offline reading.

### ✅ State Management

StateFlow and Coroutines handle reactive UI updates and asynchronous operations.

### ✅ Dependency Injection

Dagger Hilt manages app dependencies efficiently.

---

## 📊 Performance

| Metric             | Observation  |
| ------------------ | ------------ |
| Initial Load Time  | ~1–2 seconds |
| Search Response    | ~1–3 seconds |
| Bookmark Save Time | Near-instant |
| Minimum SDK        | API 26       |
| Target SDK         | API 35       |

---

## ✨ Implemented Concepts

* MVVM Architecture
* Dependency Injection
* REST API Integration
* Local Database (CRUD)
* State Management
* Reactive Programming
* Navigation Component
* Material Design 3
* Offline Support

---

## 🔮 Future Improvements

* Pagination using Paging 3
* Category filtering
* Search history
* Improved image caching
* Unit & UI testing
* Push notifications
* User authentication
* Cloud bookmark sync
* Personalized recommendations

---

## 📚 Learning Outcomes

This project helped in understanding:

* Modern Android app development
* Clean architecture principles
* API integration
* Local storage handling
* Jetpack Compose UI development
* State management using StateFlow
* Dependency Injection with Hilt

---

## 👨‍💻 Author

Developed as a Mobile Application Development course project.

---

## 📄 License

This project is for educational purposes only.
