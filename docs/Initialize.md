# Initialize the SDK

**You must initialize the NMM InApp SDK before you start requesting ads.**

Initialize the SDK by calling the `NextSdk.initialize()` in `onCreate` listener of your
application’s main class or main activity

Java

```java
import android.app.Application;

import io.nextmillennium.nextsdk.core.NextSdk;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NextSdk.initialize(this);
    }
}
```

Kotlin

```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NextSdk.initialize(this)
    }
}
```

This method can receive the following parameters:

- `context` — application context;
- `isTestMode` — `boolean` flag enabling test mode;
- `InitializedListener` — callback for SDK initialized event

`InitializedListener` is a functional interface:

```java
public interface InitializedListener {
    void onInitializationComplete(InitializationStatus initializationStatus);
}
```

There are next overloads of this method:

```java
public class NextSdk {
    // ...
    public static void initialize(Context context);

    public static void initialize(Context context, boolean isTestMode);

    public static void initialize(Context context, InitializedListener listener);

    public static void initialize(Context context, boolean isTestMode, InitializedListener listener);
}
```
