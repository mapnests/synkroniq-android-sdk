# SynkroniqSDK — Android Sample App


Android SDK for integrating Synkroniq ticketing and service-category APIs into your app. All network calls run on a background thread; results are dispatched to the main thread via completion callbacks

---

## Release Notes

### v1.0.0

- Initial beta release
- Service Categories: list, get by ID, search
- Tickets: list, create
- Ticket Articles: list, add
- Ticket States: list
- TLS 1.2 enforced via libcurl (native layer)
- JNI bridge from Kotlin → C++ via NDK

---

## Requirements

| Requirement | Minimum                      |
|---|------------------------------|
| Android | API 23                       |
| Java | 11                           |

---

## Installation

SynkroniqSDK ships as a pre-built `.aar` library. There is no Maven or Gradle plugin support at this time.

### Step 1 — Add the AAR

Place the `synkroniq-sdk.aar` file into your app's `libs` directory:

```
your-project/
  app/
    libs/
      synkroniq-sdk.aar
```

### Step 2 — Declare the dependency

In your `app/build.gradle.kts`, add:

```kotlin
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar", "*.jar"))))
}
```

### Step 3 — Add Internet permission

In your `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### Step 4 — Sync and import

Sync your project with Gradle, then import the SDK wherever needed:

```kotlin
import com.technonext.synkroniq.SynkroniqSDK
import com.technonext.synkroniq.data.ApiError
```



---

## Getting Started

**For API key, please contact the Synkroniq Developer Team.**

Initialize the SDK once before making any API calls — typically in your `Application` class or `MainActivity.onCreate()`:

```kotlin
val sdk = SynkroniqSDK.getInstance(
    apiKey    = "YOUR_API_KEY", // API key will be provided upon request
    platform  = "android",
    timeoutMs = 30000L      // optional, default 30,000 ms
)
```

| Parameter | Type | Required | Description |
|---|---|---|---|
| `apiKey` | `String` | Yes | API key issued by Synkroniq |
| `platform` | `String` | Yes | Client platform identifier (e.g. `"android"`) |
| `timeoutMs` | `Long` | No | Request timeout in milliseconds (default `30000`) |

`SynkroniqSDK.getInstance()` returns a singleton — calling it multiple times with the same key is safe.

---

## Common Request Body Fields

All API methods require the following user context fields in the JSON request body:

| Field | Type | Required | Description |
|---|---|---|---|
| `uid` | `Int` | Yes | User ID |
| `email` | `String` | Yes | User email address |
| `firstname` | `String` | Yes | User first name |
| `lastname` | `String` | Yes | User last name |
| `mobile` | `String` | Yes | User mobile number |
| `designation` | `String` | Yes | User designation / role |

---

## API Coverage

All methods are called on the `SynkroniqSDK` instance and deliver results through `SynkroniqSDK.ResponseCallback<String>`. On success, the `String` is a raw JSON response from the server.

---

### Service Categories

#### `getServiceCategoryList(jsonBody, callback)`

Fetches the full list of service categories.

**Endpoint:** `GET /api/sdk/v1/service_categories/`

**Signature:**
```kotlin
fun getServiceCategoryList(
    jsonBody  : JSONObject,
    callback  : ResponseCallback<String>
)
```

**Example:**
```kotlin
val body = JSONObject().apply {
    put("uid", 20)
    put("email", "user@example.com")
    put("firstname", "John")
    put("lastname", "Doe")
    put("mobile", "01616139245")
    put("designation", "Supervisor")
}

Thread {
    sdk.getServiceCategoryList(body, object : SynkroniqSDK.ResponseCallback<String> {
        override fun onSuccess(response: String) {
            // response is a raw JSON string
            Log.d("SDK", response)
        }
        override fun onError(error: ApiError) {
            Log.e("SDK", error.message ?: "Unknown error")
        }
    })
}.start()
```

---

#### `getServiceCategory(jsonBody, callback, id)`

Fetches a single service category by its ID.

**Endpoint:** `GET /api/sdk/v1/service_categories/{id}`

**Signature:**
```kotlin
fun getServiceCategory(
    id        : String,
    jsonBody  : JSONObject,
    callback  : ResponseCallback<String>
)
```

**Request Body Fields:**

Same as common fields above — no extra fields required.

**Example:**
```kotlin
val body = JSONObject().apply {
    put("uid", 20)
    put("email", "user@example.com")
    put("firstname", "John")
    put("lastname", "Doe")
    put("mobile", "01616139245")
    put("designation", "Supervisor")
}

Thread {
    sdk.getServiceCategory("1", body, object : SynkroniqSDK.ResponseCallback<String> {
        override fun onSuccess(response: String) {
            Log.d("SDK", response)
        }
        override fun onError(error: ApiError) {
            Log.e("SDK", error.message ?: "Unknown error")
        }
    })   // ← category ID passed here
}.start()
```

---

#### `getServiceCategorySearch(jsonBody, callback)`

Full-text search across service categories.

**Endpoint:** `GET /api/sdk/v1/service_categories/search`

**Signature:**
```kotlin
fun getServiceCategorySearch(
    jsonBody  : JSONObject,
    callback  : ResponseCallback<String>
)
```

**Additional Request Body Fields:**

| Field | Type | Required | Description |
|---|---|---|---|
| `search` | `String` | Yes | Search query string |

**Example:**
```kotlin
val body = JSONObject().apply {
    put("uid", 20)
    put("email", "user@example.com")
    put("firstname", "John")
    put("lastname", "Doe")
    put("mobile", "01616139245")
    put("designation", "Supervisor")
    put("leaf_parents", true)
    put("query",      "p")
}

Thread {
    sdk.getServiceCategorySearch(body, object : SynkroniqSDK.ResponseCallback<String> {
        override fun onSuccess(response: String) {
            Log.d("SDK", response)
        }
        override fun onError(error: ApiError) {
            Log.e("SDK", error.message ?: "Unknown error")
        }
    })
}.start()
```

---

### Tickets

#### `getTicketsList(jsonBody, callback)`

Retrieves the list of tickets.

**Endpoint:** `GET /api/sdk/v1/tickets/`

**Signature:**
```kotlin
fun getTicketsList(
    jsonBody  : JSONObject,
    callback  : ResponseCallback<String>
)
```

**Example:**
```kotlin
val body = JSONObject().apply {
    put("uid", 20)
    put("email", "user@example.com")
    put("firstname", "John")
    put("lastname", "Doe")
    put("mobile", "01616139245")
    put("designation", "Supervisor")
}

Thread {
    sdk.getTicketsList(body, object : SynkroniqSDK.ResponseCallback<String> {
        override fun onSuccess(response: String) {
            Log.d("SDK", response)
        }
        override fun onError(error: ApiError) {
            Log.e("SDK", error.message ?: "Unknown error")
        }
    })
}.start()
```
---

## Note:getTicketsList also supports a custom type parameter in the request body.


#### `createTicket(jsonBody, callback)`

Creates a new ticket.

**Endpoint:** `POST /api/sdk/v1/tickets/`

**Signature:**
```kotlin
fun createTicket(
    jsonBody  : JSONObject,
    callback  : ResponseCallback<String>
)
```

**Additional Request Body Fields:**

| Field | Type | Required | Description |
|---|---|---|---|
| `title` | `String` | Yes | Ticket title |
| `body` | `String` | Yes | Ticket description |
| `service_category_id` | `Int` | Yes | ID of the associated service category |
| `attachments` | `JSONArray` | Yes | List of file attachments (can be empty) |

## Note: Create createTicket suppport custome field type like

**Example:**
```kotlin
val body = JSONObject().apply {
    put("uid", 20)
    put("email", "user@example.com")
    put("firstname", "John")
    put("lastname", "Doe")
    put("mobile", "01616139245")
    put("designation", "Supervisor")
    put("body",                "issue details")
    put("service_category_id", 1)
    put("title",               "VPN Connectivity Issue - Urgent")
    put("attachments",         JSONArray())
}

Thread {
    sdk.createTicket(body, object : SynkroniqSDK.ResponseCallback<String> {
        override fun onSuccess(response: String) {
            Log.d("SDK", "Ticket created: $response")
        }
        override fun onError(error: ApiError) {
            Log.e("SDK", error.message ?: "Unknown error")
        }
    })
}.start()
```

---

### Ticket Articles

#### `getTicketsArticleList(jsonBody, callback)`

Retrieves articles associated with a ticket.

**Endpoint:** `GET /api/sdk/v1/ticket_articles`

**Signature:**
```kotlin
fun getTicketsArticleList(
    jsonBody  : JSONObject,
    callback  : ResponseCallback<String>
)
```

**Additional Request Body Fields:**

| Field | Type | Required | Description |
|---|---|---|---|
| `ticket_id` | `Int` | Yes | ID of the ticket to fetch articles for |
| `page` | `Int` | Yes | Page number for pagination |
| `limit` | `Int` | Yes | Number of results per page |

**Example:**
```kotlin
val body = JSONObject().apply {
    put("uid", 20)
    put("email", "user@example.com")
    put("firstname", "John")
    put("lastname", "Doe")
    put("mobile", "01616139245")
    put("designation", "Supervisor")
    put("ticket_id",   108)
    put("page",        1)
    put("limit",       50)
}

Thread {
    sdk.getTicketsArticleList(body, object : SynkroniqSDK.ResponseCallback<String> {
        override fun onSuccess(response: String) {
            Log.d("SDK", response)
        }
        override fun onError(error: ApiError) {
            Log.e("SDK", error.message ?: "Unknown error")
        }
    })
}.start()
```

---

#### `createTicketArticle(jsonBody, callback)`

Adds an article (comment / note) to a ticket.

**Endpoint:** `POST /api/sdk/v1/ticket_articles/`

**Signature:**
```kotlin
fun createTicketArticle(
    jsonBody  : JSONObject,
    callback  : ResponseCallback<String>
)
```

**Additional Request Body Fields:**

| Field | Type | Required | Description |
|---|---|---|---|
| `ticket_id` | `Int` | Yes | ID of the ticket to add the article to |
| `body` | `String` | Yes | Content of the article / comment |
| `attachments` | `JSONArray` | Yes | List of file attachments (can be empty) |

**Example:**
```kotlin
val body = JSONObject().apply {
    put("uid", 20)
    put("email", "user@example.com")
    put("firstname", "John")
    put("lastname", "Doe")
    put("mobile", "01616139245")
    put("designation", "Supervisor")
    put("ticket_id",   108)
    put("body",        "sample body")
    put("attachments", JSONArray())
}

Thread {
    sdk.createTicketArticle(body, object : SynkroniqSDK.ResponseCallback<String> {
        override fun onSuccess(response: String) {
            Log.d("SDK", "Article added: $response")
        }
        override fun onError(error: ApiError) {
            Log.e("SDK", error.message ?: "Unknown error")
        }
    })
}.start()
```

---

### Ticket States

#### `getTicketsStatesList(jsonBody, callback)`

Retrieves all available ticket states.

**Endpoint:** `GET /api/sdk/v1/ticket_states/`

**Signature:**
```kotlin
fun getTicketsStatesList(
    jsonBody  : JSONObject,
    callback  : ResponseCallback<String>
)
```

**Example:**
```kotlin
val body = JSONObject().apply {
    put("uid", 20)
    put("email", "user@example.com")
    put("firstname", "John")
    put("lastname", "Doe")
    put("mobile", "01616139245")
    put("designation", "Supervisor")
}

Thread {
    sdk.getTicketsStatesList(body, object : SynkroniqSDK.ResponseCallback<String> {
        override fun onSuccess(response: String) {
            Log.d("SDK", response)
        }
        override fun onError(error: ApiError) {
            Log.e("SDK", error.message ?: "Unknown error")
        }
    })
}.start()
```

---

## Error Handling

All failures are delivered via `ApiError` in the `onError` callback:

| Property | Type | Description |
|---|---|---|
| `message` | `String?` | Human-readable error description |
| `errorType` | `String?` | Error category (e.g. `"network"`) |

```kotlin
sdk.getTicketsList(body, object : SynkroniqSDK.ResponseCallback<String> {
    override fun onSuccess(response: String) {
        // handle success
    }
    override fun onError(error: ApiError) {
        Log.e("SDK", "Type: ${error.errorType}")       // e.g. "network"
        Log.e("SDK", "Message: ${error.message}")      // e.g. "Unable to complete the request after retries."
    }
})
```

---

## Threading Model

- All SDK calls **must be made from a background thread** — never from the main (UI) thread.
- Callbacks are delivered on the **same background thread** the call was made from.
- To update the UI inside a callback, dispatch back to the main thread:


---

## Sample App Structure

The sample app included in this repository demonstrates all 8 API calls across three screens:

```
com.synkroniq.sampleapp/
├── SdkConfig.kt                  ← API key, platform, timeout (single place)
├── MainActivity.kt               ← Entry point
├── navigation/
│   └── AppNavigation.kt          ← Bottom navigation + NavHost
│   └── Screen.kt                 ← All Screen Routes
└── ui/
    ├── theme/
    │   ├── Color.kt
    │   └── Theme.kt
    ├── components/
    │   └── ApiSectionCard.kt     ← Reusable card: label + Call button + response box
    │   └── ApiState.kt           ← All possible states
    └── screens/
        ├── CategoriesScreen.kt   ← getServiceCategoryList, getServiceCategoryById, searchServiceCategories
        ├── TicketsScreen.kt      ← getTicketsList, createTicket, getTicketsStatesList
        └── ArticlesScreen.kt     ← getTicketsArticleList, createTicketArticle
```

| Screen | APIs Demonstrated |
|---|---|
| **Categories** | `getServiceCategoryList`, `getServiceCategoryById`, `getServiceCategorySearch` |
| **Tickets** | `getTicketsList`, `createTicket`, `getTicketsStatesList` |
| **Articles** | `getTicketsArticleList`, `createTicketArticle` |

---

> **Troubleshooting:** If imports are unresolved after syncing, go to **File → Invalidate Caches → Invalidate and Restart**, then sync again.