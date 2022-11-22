# Native Ads

[Back to manual mode overview](https://github.com/nextmillenniummedia/next-sdk-android-example/blob/main/docs/manual/Manual.md)

To show native ads you need to add `NextNativeView` component to your UI.

Add it to your layout XML file:

```xml

<io.nextmillennium.nextsdk.ui.nativeads.NextNativeView android:id="@+id/nativeView"
    android:layout_width="match_parent" android:layout_height="wrap_content" />
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

### Ad management

You can control your ad view using various build-it tools

#### Listen to load events

You can use `nativeView.setFetchListener(FetchListener)` to provide your customized behavior for
successful and failure native ad loads.

It is available by implementing `FetchListener` interface. It contains 2 methods for success and
error:

Available event callbacks:

| method | description |
| --- | --- |
| `onSuccess` | Called when an ad is successfully received. |
| `onError(Throwable)` | Called when error happened while fetching an ad data |

Implementation examples:

<details>
<summary>Java</summary>

```java
public class NativeAdsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_ads);
        String unitId = "108";
        NextNativeView nativeView = findViewById(R.id.native_activity);
        nativeView.setUnitId(unitId);
        nativeView.setFetchListener(createListener(unitId));
        nativeView.load();
    }

    public FetchListener createListener(String unitId) {
        return new FetchListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(NativeAdsActivity.this,
                                "Successfully loaded ad " + unitId,
                                Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(NativeAdsActivity.this,
                                "Error ad load",
                                Toast.LENGTH_SHORT)
                        .show();
                if (throwable == null) {
                    return;
                }
                Toast.makeText(NativeAdsActivity.this,
                                throwable.getMessage(),
                                Toast.LENGTH_SHORT)
                        .show();
            }
        };
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
        val unitId = "108"
        val nativeView = findViewById(R.id.native_activity_kt)
        nativeView.unitId = unitId
        nativeView.setFetchListener(createListener(unitId))
        nativeView.load()
    }

    private fun createListener(unitId: String): FetchListener {
        return object : FetchListener {
            override fun onSuccess() {
                Toast.makeText(
                    this@NativeAdsActivityKt,
                    "Successfully loaded ad : $unitId",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(err: Throwable?) {
                Toast.makeText(
                    this@NativeAdsActivityKt,
                    "Error ad load: $err",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
```

</details>

#### Lifecycle control

Always destroy ad views to ensure that it is removed from the layout and cleared from memory. You
can do it by calling `destroy` for `NextNativeView`:

<details>
<summary>Java</summary>

```java
public class NativeAdsActivity extends AppCompatActivity {

    private NextNativeView nativeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_ads);
        nativeView = findViewById(R.id.native_activity);
        nativeView.setUnitId("108");
        nativeView.load();
    }

    @Override
    protected void onDestroy() {
        nativeView.destroy();
        super.onDestroy();
    }
}
```

</details>

<details>
<summary>Kotlin</summary>

```kotlin

class NativeAdsActivityKt : AppCompatActivity() {

    private lateinit var nativeView: NextNativeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_ads_kt)
        nativeView = findViewById(R.id.native_activity_kt)
        nativeView.unitId = "108"
        nativeView.load()
    }

    override fun onDestroy() {
        nativeView.destroy()
        super.onDestroy()
    }
}
```

</details>

#### Customize ad lifecycle events

When you call `load()` by default ad will be shown right after load. Sometimes you need to add
specific events for native ad lifecycle. You can use `NextAdListener` for this purpose.

`NextAdListener` used for managing all lifecycle events of ad.

Available event callbacks:

| method | description |
| --- | --- |
| `onAdLoaded(BaseAdContainer)` | Called when an ad is received. |
| `onAdImpression` | Called when an impression is recorded for an ad. |
| `onAdClicked` | Called when a click is recorded for an ad. |
| `onAdOpened` | Called when an ad opens an overlay that covers the screen. |
| `onAdClosed` | Called when the user wants to return to the application after clicking on an ad. |
| `onAdLoadFail(NextAdError)` | Called when an ad load failed. |

`NextAdError` class contains error code and message:

```kotlin
class NextAdError(val code: Int, val message: String)
```

Example of using listener for native ads.

<details>
<summary>Java</summary>

```java
public class NativeAdsActivity extends AppCompatActivity implements NextAdListener {

    private NextNativeView nativeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_ads);
        String unitId = "108";
        nativeView = findViewById(R.id.native_activity);
        nativeView.setUnitId(unitId);
        nativeView.setFetchListener(createListener(unitId));
        nativeView.setAdListener(this);
        nativeView.load();
    }

    @Override
    public void onAdLoaded(BaseAdContainer container) {
        nativeView = (NextNativeView) container;
    }

    @Override
    public void onAdLoadFail(NextAdError adError) {
        Log.d("NEXTSDK_ERROR", adError.toString());
    }

    public FetchListener createListener(String unitId) {
        return new FetchListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(NativeAdsActivity.this,
                                "Successfully loaded ad " + unitId,
                                Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(NativeAdsActivity.this,
                                "Error ad load",
                                Toast.LENGTH_SHORT)
                        .show();
                if (throwable == null) {
                    return;
                }
                Toast.makeText(NativeAdsActivity.this,
                                throwable.getMessage(),
                                Toast.LENGTH_SHORT)
                        .show();
            }
        };
    }

    @Override
    protected void onDestroy() {
        nativeView.destroy();
        super.onDestroy();
    }
}
```

</details>
<details>
<summary>Kotlin</summary>

```kotlin
class NativeAdsActivityKt : AppCompatActivity(), NextAdListener {

    private lateinit var nativeView: NextNativeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_ads_kt)
        val unitId = "108"
        nativeView = findViewById(R.id.native_activity_kt)
        nativeView.unitId = unitId
        nativeView.setFetchListener(createListener(unitId))
        nativeView.load()
    }

    override fun onAdLoaded(container: BaseAdContainer?) {
        nativeView = container as NextNativeView
    }

    override fun onAdLoadFail(adError: NextAdError?) {
        Log.d("NEXTSDK_ERROR", adError.toString())
    }

    private fun createListener(unitId: String): FetchListener {
        return object : FetchListener {
            override fun onSuccess() {
                Toast.makeText(
                    this@NativeAdsActivityKt,
                    "Successfully loaded ad : $unitId",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(err: Throwable?) {
                Toast.makeText(
                    this@NativeAdsActivityKt,
                    "Error ad load: $err",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroy() {
        nativeView.destroy()
        super.onDestroy()
    }
}
```

</details>