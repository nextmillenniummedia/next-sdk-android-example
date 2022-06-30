# Advanced Settings

## Usage with code obfuscation

Starting from 1.0.2 you can use `InAppSdk.injectTo` override with passing screen name if your app
uses obfuscation or code generation. For example, you have activity with name `NewsActivity`. After
first application launch it will send screen names to our InApp Server. But if your activity is
obfuscated after release, you can specify its name explicitly:

Java
<details>

```java

public class Zzz extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        InAppSdk.injectTo(this, "NewsActivity", savedInstanceState);
    }
}

```

</details>

Kotlin
<details>

```Kotlin

class Zzz : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        InAppSdk.injectTo(this, "NewsActivity", savedInstanceState)
    }
}

```

</details>

## Force reload

To force reload banners on a screen call  `InAppSDK.reload` method. Make sure to call this method
after calling the `InAppSdk.injectTo` method.

Java
<details>

```java

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InAppSdk.injectTo(this, savedInstanceState);
        Button reload = findViewById(R.id.btn);
        reload.setOnClickListener(v -> {
            InAppSdk.reload(this, InAppSdk.ReloadFilter.TOP_AND_BOTTOM);
        });
    }
}

```

</details>

Kotlin
<details>

```Kotlin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        InAppSdk.injectTo(this, savedInstanceState)
        val reload = findViewById(R.id.btn)
        reload.setOnClickListener {
            InAppSdk.reload(this@MainActivity, InAppSdk.ReloadFilter.TOP_AND_BOTTOM)
        }
    }
}

```

</details>

## Overriding Absolute vs Relative Sticky Top Banners in Injection Mode

By default top and bottom banner will place on TOP of content. (This is a common design with
scrolling lists and other common scenarios). If you do not want this behavior, and would prefer the
ad to 'push' the content beneath or above it, simply ad a FrameLayout like shown in the snippet
below. This gives the Next Millennium SDK an anchor point to look for and render itself inside of.

**For top banner:**  Add FrameLayout with id:
`in_app_ads_override_top_container_id`

```xml

<FrameLayout android:id="@+id/in_app_ads_override_top_container_id"
    android:layout_width="match_parent" android:layout_height="wrap_content" />
```

**For bottom banner:** Add FrameLayout with the id as:
`in_app_ads_override_bottom_container_id`