# Initialize the SDK

**You must initialize the NMM inApp SDK before you start requesting ads.**

Add lines below to your application’s main class or to main activity.  
Initialize the SDK by calling the `InAppSdk.initialize()` in `onCreate` callback.

App.java

```java
// imports

import android.app.Application;

import com.nextmillennium.inappsdk.core.InAppSdk;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        InAppSdk.initialize(this);
    }
}
```

There are next overloads of this method:

```java
public static void initialize(Context context);
public static void initialize(Context context,boolean isTestMode);
public static void initialize(Context context,InitializedListener listener);
public static void initialize(Context context,boolean isTestMode,InitializedListener listener);
```

This method can receive the following parameters:

- Application context;
- flag for enabling test mode;
- listener for SDK initialized event

`InitializedListener` is a functional interface:

```java
public interface InitializedListener {
    void onInitializationComplete(InitializationStatus initializationStatus);
}
```