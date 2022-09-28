
# Initialize the SDK
[Back to repo](https://github.com/nextmillenniummedia/next-sdk-android-example/tree/2.x)

**You must initialize the Next SDK before you start requesting ads.**

Initialize the SDK by calling the `NextSdk.initialize()` in `onCreate` method of your application’s
main class or of your launch activity

<details>
<summary style="font-size:14px">Java</summary>

Application (recommended)

```java
import android.app.Application;

import io.nextmillennium.nextsdk.NextSdk;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NextSdk.initialize(this);
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
        NextSdk.initialize(this);
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
        NextSdk.initialize(this)
    }
}
```

Launch activity

```Kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NextSdk.initialize(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
```

</details>

`NextSdk.initialize` method can receive the following params:

- `context` — application Context;
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

