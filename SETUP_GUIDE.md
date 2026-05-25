# 📱 Daily Motivation App — Complete Setup Guide

## What's Included
- **105 motivational quotes** across 10 categories
- **Daily Quote** — one fresh quote every morning
- **Random Quote** — tap "New" to discover more
- **Favorites** — save quotes you love ❤️
- **Share & Copy** — spread inspiration
- **Daily Notifications** — scheduled reminders
- **Home Screen Widget** — quote at a glance
- **Dark/Light/Auto theme** support
- Beautiful dark UI with gold accents, Lora italic + Montserrat typography

---

## ✅ STEP 1 — Prerequisites

Install these tools first:

### 1. Android Studio
Download from: https://developer.android.com/studio
→ Install with default settings (includes Android SDK)

### 2. Java Development Kit (JDK 17)
Android Studio usually bundles this. If not:
Download from: https://adoptium.net/

---

## ✅ STEP 2 — Open the Project

1. Launch **Android Studio**
2. Click **"Open"** (not "New Project")
3. Navigate to and select the `DailyMotivation` folder
4. Wait for **Gradle sync** to complete (2–5 minutes first time)
5. If prompted about missing SDK, click **"Install missing SDK"**

---

## ✅ STEP 3 — Fix local.properties

Android Studio needs to know where your SDK is.

1. In the project root, copy `local.properties.template` → `local.properties`
2. Android Studio usually creates this automatically when you open the project
3. If you see a "SDK not found" error, go to:
   **File → Project Structure → SDK Location** and set your path

---

## ✅ STEP 4 — Download Fonts (Required)

The app uses Google Fonts downloaded at runtime via font provider.
No manual font download needed — they load automatically on device.

**If you see font errors during build:**
Run this script from the project root:
```bash
# Create font directory (already exists)
# The font XML files in res/font/ use Google Fonts API
# They download automatically when app runs on device
```

---

## 📱 RUNNING ON YOUR PHONE (Before Play Store)

### Method A: USB Cable (Easiest)

**On your Android phone:**
1. Go to **Settings → About Phone**
2. Tap **"Build Number"** 7 times rapidly
3. You'll see "You are now a developer!"
4. Go back to **Settings → Developer Options**
5. Enable **"USB Debugging"**
6. Connect phone to computer via USB
7. On phone: tap **"Allow"** when asked about USB debugging

**In Android Studio:**
1. Your phone should appear in the top toolbar device dropdown
2. Click the **▶ Run** button (green play button)
3. App installs and launches on your phone automatically!

### Method B: Wireless (Android 11+)

1. Phone and PC on same WiFi
2. **Settings → Developer Options → Wireless Debugging → Enable**
3. In Android Studio: **Run → Pair Devices Using Wi-Fi**
4. Follow pairing instructions
5. Then click **▶ Run**

### Method C: Build APK manually

1. In Android Studio: **Build → Build Bundle(s) / APK(s) → Build APK(s)**
2. Wait for build (1–3 min)
3. Click **"locate"** in the notification, or find it at:
   `app/build/outputs/apk/debug/app-debug.apk`
4. Transfer APK to your phone (email, Google Drive, USB)
5. On phone: tap the APK file → Install
   *(You may need to enable "Install from unknown sources" in Settings)*

---

## 🚀 PLAY STORE UPLOAD — Step by Step

### Step 1: Create a Signed Release APK / AAB

**Generate a Keystore (do this ONCE, keep it safe forever):**
1. In Android Studio: **Build → Generate Signed Bundle / APK**
2. Choose **Android App Bundle (AAB)** ← Play Store prefers this
3. Click **"Create new..."** for keystore
4. Fill in:
   - Key store path: save to a safe location (e.g., `~/my-release-key.jks`)
   - Password: choose a strong password (SAVE THIS!)
   - Key alias: `daily-motivation-key`
   - Key password: (same or different)
   - Validity: 25 years
   - Your name/organization
5. Click **OK** → **Next** → Select **"release"** → **Finish**
6. AAB file location: `app/build/outputs/bundle/release/app-release.aab`

⚠️ **CRITICAL**: Back up your `.jks` keystore file and passwords.
   If you lose it, you can NEVER update your app on Play Store.

### Step 2: Create Play Store Developer Account

1. Go to: https://play.google.com/console
2. Sign in with a Google account
3. Pay the **one-time $25 registration fee**
4. Complete identity verification (takes 1–2 days)

### Step 3: Create App Listing

1. In Play Console: click **"Create app"**
2. Fill in:
   - **App name**: Daily Motivation
   - **Default language**: English
   - **App or game**: App
   - **Free or paid**: Free
3. Complete the **Dashboard checklist**:

#### Store Listing (required):
- **Short description** (80 chars): "Daily motivational quotes to inspire your day ✨"
- **Full description** (4000 chars): See template below
- **Screenshots**: At least 2 phone screenshots
  → Take them with: *Developer Options → Take bug report* or Android Studio's screenshot tool
- **Feature graphic**: 1024×500px banner image
- **App icon**: 512×512px PNG (high-res version of your launcher icon)

#### Full Description Template:
```
Start every day with the perfect dose of inspiration! Daily Motivation delivers powerful quotes from history's greatest thinkers, leaders, and visionaries.

✨ FEATURES:
• New quote every single day
• 100+ curated motivational quotes
• 10 categories: Success, Happiness, Life, Love, Courage, Dreams & more
• Save your favorites with one tap
• Share quotes with friends & family
• Copy quotes to clipboard instantly
• Daily notification reminders
• Home screen widget
• Beautiful dark theme with elegant design
• Light / Dark / Auto theme modes

📚 QUOTE CATEGORIES:
Success • Happiness • Life • Love • Courage • Dreams • Wisdom • Action • Resilience • Mindset

Perfect for morning motivation, daily journaling, social media captions, or whenever you need a boost!

No ads. No subscriptions. Just pure daily inspiration.
```

#### Content Rating:
- Go to **Policy → App content → Content rating**
- Fill questionnaire → Rating: **Everyone**

#### Target Audience:
- Age group: **18 and over** (or 13+ if appropriate)

#### Data Safety:
- Does app collect data? **No** (this app stores everything locally)
- Fill the form accordingly

### Step 4: Upload the AAB

1. Go to **Production → Create new release**
2. Upload your `app-release.aab` file
3. Add release notes: "Initial release — your daily dose of motivation!"
4. Click **Review release** → **Start rollout to Production**

### Step 5: Wait for Review

- Google reviews take **1–3 days** for first submission
- You'll get an email when approved
- App goes live on Play Store!

---

## 🔧 Customization Tips

### Add More Quotes
Open `QuoteRepository.kt` and add to the `allQuotes` list:
```kotlin
Quote(106, "Your new quote here.", "Author Name", "Category"),
```

### Change App Colors
Edit `res/values/colors.xml`:
- `bg_primary` — main background
- `accent_gold` — gold accent color
- `card_bg` — card background

### Change App Package Name
For Play Store, use a unique package name:
1. In `app/build.gradle`: change `applicationId "com.dailymotivation"` to `"com.yourname.dailymotivation"`
2. Rename the folder: `java/com/dailymotivation/` → `java/com/yourname/dailymotivation/`
3. Update package declaration in all `.kt` files

### Change Notification Time Default
In `QuoteRepository.kt`, change:
```kotlin
val hour = prefs.getInt(KEY_NOTIFICATION_HOUR, 8)   // 8 = 8 AM
val minute = prefs.getInt(KEY_NOTIFICATION_MINUTE, 0) // 0 = :00
```

---

## 📁 Project Structure

```
DailyMotivation/
├── app/
│   ├── src/main/
│   │   ├── java/com/dailymotivation/
│   │   │   ├── data/
│   │   │   │   └── QuoteRepository.kt    ← All quotes + preferences
│   │   │   ├── ui/
│   │   │   │   ├── MainActivity.kt       ← Main quote screen
│   │   │   │   ├── FavoritesActivity.kt  ← Saved quotes
│   │   │   │   ├── SettingsActivity.kt   ← Notifications + theme
│   │   │   │   └── QuoteAdapter.kt       ← RecyclerView adapter
│   │   │   └── widget/
│   │   │       ├── QuoteWidget.kt        ← Home screen widget
│   │   │       └── NotificationScheduler.kt ← Daily notifications
│   │   ├── res/
│   │   │   ├── layout/                   ← XML UI layouts
│   │   │   ├── drawable/                 ← Icons & backgrounds
│   │   │   ├── values/                   ← Colors, strings, themes
│   │   │   ├── anim/                     ← Animations
│   │   │   ├── font/                     ← Font declarations
│   │   │   └── xml/                      ← Widget provider info
│   │   └── AndroidManifest.xml
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle
├── settings.gradle
├── gradlew / gradlew.bat
└── SETUP_GUIDE.md  ← You are here!
```

---

## ❓ Troubleshooting

| Problem | Solution |
|---------|----------|
| Gradle sync fails | File → Invalidate Caches → Restart |
| SDK not found | File → Project Structure → SDK Location |
| Device not detected | Re-enable USB Debugging, try different USB cable |
| Build fails (fonts) | Check that font XML files are in `res/font/` |
| App crashes on launch | Check Logcat in Android Studio for error details |
| Play Store rejected | Read rejection email carefully, fix the issue, resubmit |

---

## 🎉 You're all set!

Questions? The Android Studio built-in **Logcat** (bottom panel) shows all errors in real-time — it's your best debugging tool.

Good luck on the Play Store! 🚀
