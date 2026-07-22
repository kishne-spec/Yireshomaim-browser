# WebView APK Template

A minimal Android WebView shell. Configure three values, build, done.

## Quick Start

### 1. Set your URL, package, and app name

Edit **`app.properties`** in the project root:

```properties
app.url=https://your-domain.com/
app.package=com.yourcompany.yourapp
app.name=Your App Name
```

### 2. Set up signing (for release builds)

Create **`keystore.properties`** in the project root (it is gitignored):

```properties
storeFile=keystore/release-signing.p12
storePassword=your_store_password
keyAlias=your_key_alias
keyPassword=your_key_password
```

Place your `.p12` keystore file at `keystore/release-signing.p12` (or update `storeFile` to match
your path).

### 3. Build

**Option A — locally:**

```bash
./gradlew assembleDebug    # debug APK (no signing required)
./gradlew assembleRelease  # signed release APK
```

**Output paths:**

- Debug: `app/build/outputs/apk/debug/app-debug.apk`
- Release: `app/build/outputs/apk/release/app-release.apk`

**Option B — GitHub Actions (no local Android setup needed):**

Go to **Actions → Build APK → Run workflow**, fill in the three fields, pick `debug` or `release`,
and click **Run workflow**. The APK appears as a downloadable artifact when the job finishes.

For signed release builds, add these repository secrets first (Settings → Secrets and variables →
Actions):

| Secret              | Value                                     |
|---------------------|-------------------------------------------|
| `KEYSTORE_BASE64`   | `base64 -w0 keystore/release-signing.p12` |
| `KEYSTORE_PASSWORD` | store password                            |
| `KEY_ALIAS`         | key alias                                 |
| `KEY_PASSWORD`      | key password                              |

---

## Customization Reference

| What to change               | Where                               |
|------------------------------|-------------------------------------|
| WebView URL                  | `app.properties` → `app.url`        |
| App package / application ID | `app.properties` → `app.package`    |
| App display name             | `app.properties` → `app.name`       |
| Signing keystore             | `keystore/` + `keystore.properties` |

> **Nothing in the Kotlin source needs editing** — all three core values are injected at build time
> via `BuildConfig` and `resValue`.

---

## Technical Details

- **Language:** Kotlin
- **Min API:** 24 (Android 7.0)
- **Target / Compile SDK:** 37
- **Edge-to-edge:** enabled, with inset-aware padding
- **Status bar color:** syncs dynamically with the page background color
- **Back button:** navigates back in WebView history before exiting
- **File upload:** supports gallery picker and camera capture
- **Permissions:** camera and microphone are granted to the WebView on request
- **Signing:** v1 + v2 + v3 schemes enabled, v4 disabled

## Signing Notes

- `keystore.properties` is in `.gitignore` — never commit it
- The `keystore/` directory is tracked by git; committing your keystore file there is optional but
  convenient for private repos
- If `keystore.properties` is absent, release builds are unsigned (debug builds always work)
