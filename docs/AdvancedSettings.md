# Advanced Settings

## Force reload

To force reload banners on a screen call  `NextSdk.reload` method. Make sure to call this method
after calling the `NextSdk.injectTo` method.

Java

```java

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NextSdk.injectTo(this, savedInstanceState);
        Button reload = findViewById(R.id.btn);
        reload.setOnClickListener(v -> {
            NextSdk.reload(this, NextSdk.ReloadFilter.TOP_AND_BOTTOM);
        });
    }
}

```

Kotlin

```Kotlin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NextSdk.injectTo(this, savedInstanceState)
        val reload = findViewById(R.id.btn)
        reload.setOnClickListener {
            NextSdk.reload(this@MainActivity, NextSdk.ReloadFilter.TOP_AND_BOTTOM)
        }
    }
}

```