# Native Ads

[Back to manual mode overview](https://github.com/nextmillenniummedia/next-sdk-android-example/blob/main/docs/manual/Manual.md)

To show native ads you need to add `NextNativeView` component to your UI.

Add it to your layout XML file:

```xml

<io.nextmillennium.nextsdk.ui.nativeads.NextNativeView 
    android:id="@+id/nativeView"
    android:layout_width="match_parent" 
    android:layout_height="wrap_content" />
```

Get it in your activity or fragment

```java
public class NativeAdsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_ads);
        NextNativeView nativeView = findViewById(R.id.native_activity);
    }
}
```

Set ad unit id that we will provide for you

```java 
nativeView.setUnitId("your_unit_id");
```

You will get next error message trying to load banner without unit id:
`UndefinedUnitIdException: Ad unit id did not specified`

And finally just load it by calling `load()` method. By default banner will be shown right after
load.

<details>
<summary>Java</summary>

```java
public class NativeAdsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_ads);
        NextNativeView nativeView = findViewById(R.id.native_activity);
        nativeView.setUnitId("108");
        nativeView.load();
    }
}
```

</details>

<details>
<summary>Kotlin</summary>

```kotlin
class NativeAdsActivityKt : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_ads_kt)
        val nativeView: NextNativeView = findViewById(R.id.native_activity_kt)
        nativeView.unitId = "108"
        nativeView.load()
    }
}
```

</details>
