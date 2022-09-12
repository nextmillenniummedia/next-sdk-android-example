# Initialize the SDK

**You must initialize the Next SDK before you start requesting ads.**

Initialize the SDK by calling the `NextSdkBase.initialize()` in `onCreate` method of your
application’s main class or of your launch activity

<details>
<summary style="font-size:14px">Java</summary>

Application (recommended)

```java
import android.app.Application;

import io.nextmillennium.nextsdk.NextSdkBase;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NextSdkBase.initialize(this);
    }
}
```

Launch activity

```java
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NextSdkBase.initialize(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
```

</details>

<details>
<summary style="font-size:14px">Kotlin</summary>

Application (recommended)

```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NextSdkBase.initialize(this)
    }
}
```

Launch activity

```Kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NextSdkBase.initialize(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
```

</details>

`Initialize` method can receive the following parameters:

- `context` — application context;
- `isTestMode` — `boolean` flag enabling test mode;
- `InitializedListener` — callback for SDK initialized event

`InitializedListener` is a functional interface:

```java
public interface InitializedListener {
    void onInitializationComplete(InitializationStatus initializationStatus);
}
```

**ProGuard rules**

Next SDK comes with necessary Proguard rules. Therefore, you have no need in adding any extra rules
to your project.

**SDK Modularization**

Our main module contains modules with all needed ad formats and dynamic mode. If you don't need
injection/dynamic mode and you need only custom ad unit configuration, you can use our ads with all
appropriate modules without main module.

With the modular SDK, you can choose to include specific formats to decrease overall SDK footprint
in your app. To do so, include the line for any combination of components that you want in
your `build.gradle` file as follows:

```groovy
dependencies {
    // ... other project dependencies

    // For banners
    implementation('io.nextmillennium:nextsdk-banner:2.0.0')

    // For interstitials, rewarded and app open ads
    implementation('io.nextmillennium:nextsdk-fullscreen:2.0.0')

    // For native ads (since 2.2.0)
    implementation('io.nextmillennium:nextsdk-native:2.2.0')
}
```

If you will use SDK without main module, you will have access only to `NextSdkBase` class. 


