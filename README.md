# 📰 Android News App Project

## 📱 Overview
This project is an Android application built using **XML layouts** and **Firebase Authentication** for user management.  
It fetches real-time news using the **[NewsAPI.org](https://newsapi.org/)** service and follows the design style of  
the **[Bose News App Template](https://preview.themeforest.net/item/bose-new-publishing-app-template-in-html-5-framework-7/full_screen_preview/26131742)**.

---

## 👥 Team Members & Responsibilities

| Team Member | Assigned Screen | UI Responsibilities | Firebase/NewsAPI Usage |
|--------------|----------------|---------------------|------------------------|
| **Hossam Ahmed Ali** | **Sign Up Screen& Home Screen** | Design the registration screen using XML — fields for full name, email, and password with form validation. | Uses **Firebase Authentication** to create a new user. |
| **Yousseif Mohamed Ali** | **Sign In Screen** | Create login screen with email & password fields, and “Forgot Password?” link. | Uses **Firebase Auth signInWithEmailAndPassword()**. |
| **Yousseif Alaa** | **Forget Password Screen** | Build a simple UI where users can enter their email to reset the password. | Uses **Firebase sendPasswordResetEmail()** method. |
| **Essam Mohamed** | **Settings Screen** | Create the settings UI for profile updates, theme toggle (dark/light), and account actions like logout. | Uses **Firebase Auth** for profile info and logout. |
| **Kirolos Emad** | **Favorites Screen** | Build the screen showing saved or bookmarked articles using RecyclerView. | Stores favorite articles in **Firebase Firestore**. Fetches news from **NewsAPI.org**. |

---

## 🔥 Technologies Used

- **Android (Java / Kotlin)**
- **XML Layouts**
- **Firebase Authentication**
- **Firebase Firestore**
---
## 🔑 Firebase Setup

1. Go to [Firebase Console](https://console.firebase.google.com/).
2. Create a new Firebase project.
3. Enable **Email/Password Authentication**.
4. Add **Firestore Database** (for favorites).
5. Download `google-services.json` and add it to your app folder.
6. Add the Firebase dependencies in `build.gradle`.

---

## 📰 NewsAPI Setup

1. Go to [https://newsapi.org/](https://newsapi.org/) and create a free account.
2. Get your **API key**.
3. Use the endpoint: https://newsapi.org/v2/top-headlines?country=us&apiKey=YOUR_API_KEY
4. Fetch and display news articles in the Favorites or Main screen using Retrofit or Volley.

---
## 🚀 Contribution Guidelines

- Each member works on their assigned screen in a separate branch.
- Use consistent colors and styles across all screens.
- Commit with clear messages
- Test Firebase and API functions before merging.

---

### 👥 Team
- Hossam Ahmed Ali  
- Yousseif Mohamed Ali  
- Yousseif Alaa  
- Essam Mohamed  
- Kirolos Emad
