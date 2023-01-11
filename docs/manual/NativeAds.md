# Native Ads

[Back to manual mode overview](https://github.com/nextmillenniummedia/next-sdk-android-example/blob/2.x/docs/manual/Manual.md)

A native ad format is designed to adapt to the environment within which it is displayed. It combines
advertising message with user-centric content. It is similar to banner ads, but they don’t take up a
particular screen space or the full display. Users can still browse the content while viewing the
ad, but because of it’s “native” look and feel, it’s less of an eyesore and non-disruptive. Your ads
will appear contextually appropriate. Thus, making native mobile advertis~~~~ing highly effective.
For instance, an ad for cosmetics placed in the middle of a makeup tutorial article in a blog reader
app.

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

You will get next error message trying to load native ad without unit id:
`UndefinedUnitIdException: Ad unit id did not specified`

And finally just load it by calling `load()` method. By default ad will be shown right after load.

**Java**

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

**Kotlin**

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

## Ad management

You can control your ad view using various build-it tools

### Listen to load events

You can use `nativeView.setFetchListener(FetchListener)` to provide your customized behavior for
successful and failure ad metadata loads.

It is available by implementing `FetchListener` interface. It contains 2 methods for success and
error:

Available event callbacks:

| method | description |
| --- | --- |
| `onSuccess` | Called when an ad metadata is successfully received. |
| `onError(Throwable)` | Called when error happened while fetching an ad metadata |

Implementation examples:

**Java**

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
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(NativeAdsActivity.this,
                        "Error ad load",
                        Toast.LENGTH_SHORT).show();
                if (throwable == null) {
                    return;
                }
                Toast.makeText(NativeAdsActivity.this,
                        throwable.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        };
    }
}
```

**Kotlin**

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

### Release resources

Always destroy ad views to ensure that it is removed from the layout and cleared from memory. You
can do it by calling `destroy` for `NextNativeView`:

**Java**

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

**Kotlin**

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

### Customize ad lifecycle events

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
public class NativeAdsActivity extends AppCompatActivity {

    private NextNativeView nativeView;
    private String LOG_TAG = "NEXT_SDK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_ads);
        String unitId = "108";
        nativeView = findViewById(R.id.native_activity);
        nativeView.setUnitId(unitId);
        nativeView.setAdListener(createAdListener(unitId));
        nativeView.load();
    }

    private NextAdListener createAdListener(String unitId) {
        return new NextAdListener() {
            @Override
            public void onAdLoaded(BaseAdContainer container) {
                logEvent("Successfully loaded ad " + container.getUnitId());
                nativeView = (NextNativeView) container;
            }

            @Override
            public void onAdClicked() {
                logEvent("Successfully tracked click for ad " + unitId);
            }

            @Override
            public void onAdClosed() {
                logEvent("Closed ad " + unitId);
            }

            @Override
            public void onAdLoadFail(NextAdError adError) {
                Log.e("NEXT_SDK", adError.toString());
                Toast
                        .makeText(NativeAdsActivity.this, "Error happened while loading ad: " + adError, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAdImpression() {
                logEvent("Successfully tracked impression for ad " + unitId);
            }

            @Override
            public void onAdOpened() {
                logEvent("Opened ad " + unitId);
            }
        };
    }

    private void logEvent(String message) {
        Log.d(LOG_TAG, message);
        Toast
                .makeText(this, message, Toast.LENGTH_SHORT)
                .show();
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
    private val LOG_TAG = "NEXT_SDK"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_ads_kt)
        val unitId = "108"
        nativeView = findViewById(R.id.native_activity_kt)
        nativeView.unitId = unitId
        nativeView.setAdListener(createAdListener(unitId))
        nativeView.load()
    }

    private fun logEvent(message: String) {
        Log.d(LOG_TAG, message)
        Toast
            .makeText(this, message, Toast.LENGTH_SHORT)
            .show()
    }

    private fun createAdListener(unitId: String): NextAdListener {
        return object : NextAdListener {
            override fun onAdLoaded(container: BaseAdContainer) {
                logEvent("Successfully loaded ad " + container.unitId)
                nativeView = container as NextNativeView
            }

            override fun onAdClicked() {
                logEvent("Successfully tracked click for ad $unitId")
            }

            override fun onAdClosed() {
                logEvent("Closed ad $unitId")
            }

            override fun onAdLoadFail(adError: NextAdError) {
                Log.e("NEXT_SDK", adError.toString())
                Toast
                    .makeText(
                        this@NativeAdsActivityKt,
                        "Error happened while loading ad: $adError",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }

            override fun onAdImpression() {
                logEvent("Successfully tracked impression for ad $unitId")
            }

            override fun onAdOpened() {
                logEvent("Opened ad $unitId")
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

## Examples

### **Activity**

Full example of implementing native ad in activity

<details>
<summary>Java</summary>

```java
public class NativeAdsActivity extends AppCompatActivity {

    private NextNativeView nativeView;
    private String LOG_TAG = "NEXT_SDK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_ads);
        String unitId = "108";
        nativeView = findViewById(R.id.native_activity);
        nativeView.setUnitId(unitId);
        nativeView.setFetchListener(createListener(unitId));
        nativeView.setAdListener(createAdListener(unitId));
        nativeView.load();
    }

    private NextAdListener createAdListener(String unitId) {
        return new NextAdListener() {
            @Override
            public void onAdLoaded(BaseAdContainer container) {
                logEvent("Successfully loaded ad " + container.getUnitId());
                nativeView = (NextNativeView) container;
            }

            @Override
            public void onAdClicked() {
                logEvent("Successfully tracked click for ad " + unitId);
            }

            @Override
            public void onAdClosed() {
                logEvent("Closed ad " + unitId);
            }

            @Override
            public void onAdLoadFail(NextAdError adError) {
                Log.e("NEXT_SDK", adError.toString());
                Toast
                        .makeText(NativeAdsActivity.this, "Error happened while loading ad: " + adError, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAdImpression() {
                logEvent("Successfully tracked impression for ad " + unitId);
            }

            @Override
            public void onAdOpened() {
                logEvent("Opened ad " + unitId);
            }
        };
    }

    private void logEvent(String message) {
        Log.d(LOG_TAG, message);
        Toast
                .makeText(this, message, Toast.LENGTH_SHORT)
                .show();
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
class NativeAdsActivityKt : AppCompatActivity() {

    private lateinit var nativeView: NextNativeView
    private val LOG_TAG = "NEXT_SDK"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_ads_kt)
        val unitId = "108"
        nativeView = findViewById(R.id.native_activity_kt)
        nativeView.unitId = unitId
        nativeView.setAdListener(createAdListener(unitId))
        nativeView.setFetchListener(createListener(unitId))
        nativeView.load()
    }

    private fun logEvent(message: String) {
        Log.d(LOG_TAG, message)
        Toast
            .makeText(this, message, Toast.LENGTH_SHORT)
            .show()
    }

    private fun createAdListener(unitId: String): NextAdListener {
        return object : NextAdListener {
            override fun onAdLoaded(container: BaseAdContainer) {
                logEvent("Successfully loaded ad " + container.unitId)
                nativeView = container as NextNativeView
            }

            override fun onAdClicked() {
                logEvent("Successfully tracked click for ad $unitId")
            }

            override fun onAdClosed() {
                logEvent("Closed ad $unitId")
            }

            override fun onAdLoadFail(adError: NextAdError) {
                Log.e("NEXT_SDK", adError.toString())
                Toast
                    .makeText(
                        this@NativeAdsActivityKt,
                        "Error happened while loading ad: $adError",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }

            override fun onAdImpression() {
                logEvent("Successfully tracked impression for ad $unitId")
            }

            override fun onAdOpened() {
                logEvent("Opened ad $unitId")
            }
        }
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
                )
                    .show()
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

### **Fragment**

Full example of implementing native ad in fragment

<details>
<summary>Java</summary>

```java
public class NativeAdFragment extends Fragment {

    private FragmentNativeAdBinding binding;
    private NextNativeView nativeView;
    private String LOG_TAG = "NEXT_SDK";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNativeAdBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nativeView = binding.nativeFragment;
        String unitId = "108";
        nativeView.setUnitId(unitId);
        nativeView.setFetchListener(createListener(unitId));
        nativeView.setAdListener(createAdListener(unitId));
        nativeView.load();
    }

    private void logEvent(String message) {
        Log.d(LOG_TAG, message);
        Toast
                .makeText(requireActivity(), message, Toast.LENGTH_SHORT)
                .show();
    }

    private NextAdListener createAdListener(String unitId) {
        return new NextAdListener() {
            @Override
            public void onAdLoaded(BaseAdContainer container) {
                logEvent("Successfully loaded ad " + container.getUnitId());
                nativeView = (NextNativeView) container;
            }

            @Override
            public void onAdClicked() {
                logEvent("Successfully tracked click for ad " + unitId);
            }

            @Override
            public void onAdClosed() {
                logEvent("Closed ad " + unitId);
            }

            @Override
            public void onAdLoadFail(NextAdError adError) {
                Log.e("NEXT_SDK", adError.toString());
                Toast
                        .makeText(requireActivity(), "Error happened while loading ad: " + adError, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAdImpression() {
                logEvent("Successfully tracked impression for ad " + unitId);
            }

            @Override
            public void onAdOpened() {
                logEvent("Opened ad " + unitId);
            }
        };
    }

    private FetchListener createListener(String unitId) {
        return new FetchListener() {
            @Override
            public void onSuccess() {
                if (isAdded()) {
                    Toast.makeText(requireActivity(),
                                    "Successfully loaded ad " + unitId,
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (isAdded()) {
                    Toast.makeText(requireActivity(),
                                    "Error ad load",
                                    Toast.LENGTH_SHORT)
                            .show();
                    if (throwable == null) {
                        return;
                    }
                    Toast.makeText(requireActivity(),
                                    throwable.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (nativeView != null) nativeView.destroy();
        binding = null;
    }
}
```

</details>

<details>
<summary>Kotlin</summary>

```kotlin
class NativeAdFragmentKt : Fragment() {

    private var binding: FragmentNativeAdKtBinding? = null
    private var nativeView: NextNativeView? = null
    private val LOG_TAG = "NEXT_SDK"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNativeAdKtBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nativeView = binding?.nativeFragmentKt
        val unitId = "108"
        nativeView?.unitId = unitId
        nativeView?.setAdListener(createAdListener(unitId))
        nativeView?.setFetchListener(createListener(unitId))
        nativeView?.load()
    }

    private fun logEvent(message: String) {
        Log.d(LOG_TAG, message)
        Toast
            .makeText(requireActivity(), message, Toast.LENGTH_SHORT)
            .show()
    }

    private fun createAdListener(unitId: String): NextAdListener {
        return object : NextAdListener {
            override fun onAdLoaded(container: BaseAdContainer) {
                logEvent("Successfully loaded ad " + container.unitId)
                nativeView = container as NextNativeView
            }

            override fun onAdClicked() {
                logEvent("Successfully tracked click for ad $unitId")
            }

            override fun onAdClosed() {
                logEvent("Closed ad $unitId")
            }

            override fun onAdLoadFail(adError: NextAdError) {
                Log.e("NEXT_SDK", adError.toString())
                Toast
                    .makeText(
                        requireActivity(),
                        "Error happened while loading ad: $adError",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }

            override fun onAdImpression() {
                logEvent("Successfully tracked impression for ad $unitId")
            }

            override fun onAdOpened() {
                logEvent("Opened ad $unitId")
            }
        }
    }


    private fun createListener(unitId: String): FetchListener {
        return object : FetchListener {
            override fun onSuccess() {
                if (isAdded) {
                    Toast.makeText(
                        requireActivity(),
                        "Successfully loaded ad : $unitId",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onError(err: Throwable?) {
                if (isAdded) {
                    Toast.makeText(
                        requireActivity(),
                        "Error ad load: $err",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        nativeView?.destroy()
    }
}
```

</details>

## Native ad options

You can customize native ads operating `NextNativeAdOptions`. It includes next fields:

| Field | Type | Description | Default value
| --- | --- | --- | --- |
| `isStartMuted` | `boolean` | Start muted if ad includes video | `true`
| `fullScreenNativeType` | enum `FullScreenNativeType` | Type of full screen native ad. Possible values are `PORTRAIT`, `LANDSCAPE`, `SQUARE` | `PORTRAIT`
| `resourceId` | `int` | Id of native ad. By default equals our template. But you can replace it with your custom template | -
| `videoListener` | `NextVideoListener` | Listener for video ads | -

You can set params by operating `options` object of `NextNativeView`.

**Java**

```java
public class NativeAdFragment extends Fragment {

    private NextNativeView nativeView;
    private FragmentNativeAdBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nativeView = binding.nativeFragment;
        nativeView.setUnitId("108");
        nativeView.getOptions().setStartMuted(false);
        nativeView.getOptions().setResourceId(R.layout.custom_native);
        nativeView.load();
    }
}
```

**Kotlin**

```kotlin
class NativeAdFragmentKt : Fragment() {
    private var binding: FragmentNativeAdKtBinding? = null
    private var nativeView: NextNativeView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nativeView = binding?.nativeFragmentKt
        nativeView?.unitId = "108"
        nativeView?.options?.resourceId = R.layout.custom_native
        nativeView?.options?.isStartMuted = false
        nativeView?.load()
    }
}

```

### Customize video lifecycle events

`NextVideoListener` used for managing lifecycle events of video ad.

Available event callbacks:

| method | description |
| --- | --- |
| `onStart` | Called when video playback first begins. |
| `onPlay` | Called when video playback is playing. |
| `onPause` | Called when video playback is paused. |
| `onEnd` | Called when video playback finishes. |
| `onMute(boolean isMuted)` | Called when the video changes mute state. |

You can define your own listener for video ads by settings corresponding options
field `videoListener`

**Java**

```java
public class NativeAdFragment extends Fragment {

    private FragmentNativeAdBinding binding;
    private NextNativeView nativeView;
    private String LOG_TAG = "NEXT_SDK";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNativeAdBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nativeView = binding.nativeFragment;
        nativeView.setUnitId("108");
        nativeView.getOptions().setVideoListener(createVideoListener(unitId));
        nativeView.load();
    }

    private void logEvent(String message) {
        Log.d(LOG_TAG, message);
        Toast
                .makeText(requireActivity(), message, Toast.LENGTH_SHORT)
                .show();
    }

    private NextVideoListener createVideoListener(String unitId) {
        return new NextVideoListener() {
            @Override
            public void onStart() {
                logEvent("Started video for unit " + unitId);
            }

            @Override
            public void onPlay() {
                logEvent("Play video for unit " + unitId);
            }

            @Override
            public void onPause() {
                logEvent("Paused video ad event for unit " + unitId);
            }

            @Override
            public void onEnd() {
                logEvent("End video event for unit " + unitId);
            }

            @Override
            public void onMute(boolean isMuted) {
                logEvent("Video ad muted: " + isMuted + " for unit " + unitId);
            }
        };
    }

}
```

**Kotlin**

```kotlin
class NativeAdFragmentKt : Fragment() {

    private var nativeView: NextNativeView? = null
    private var binding: FragmentNativeAdKtBinding? = null
    private val LOG_TAG = "NEXT_SDK"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nativeView = binding?.nativeFragmentKt
        nativeView?.unitId = "108"
        nativeView?.options?.videoListener = createVideoListener(unitId)
        nativeView?.load()
    }

    private fun logEvent(message: String) {
        Log.d(LOG_TAG, message)
        Toast
            .makeText(requireActivity(), message, Toast.LENGTH_SHORT)
            .show()
    }

    private fun createVideoListener(unitId: String): NextVideoListener {
        return object : NextVideoListener {
            override fun onStart() {
                logEvent("Started video event for unit $unitId")
            }

            override fun onPlay() {
                logEvent("Play video event for unit $unitId")
            }

            override fun onPause() {
                logEvent("Paused video ad event for unit $unitId")
            }

            override fun onEnd() {
                logEvent("End video event for unit $unitId")
            }

            override fun onMute(isMuted: Boolean) {
                logEvent("Video ad muted: $isMuted for unit $unitId")
            }
        }
    }
}
```

### Custom layouts

You can create your own native ad layout. All you need is create layout xml file
with `NextNativeAdLayout` as root view. Below are our default layouts as examples.

To use custom layout just call `nativeView.getOptions().setResourceId(int)` and provide resource
identifier for your layout.

### Default layouts

<details>
<summary>Small</summary>

```xml

<io.nextmillennium.nextsdk.ui.nativeads.NextNativeAdLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto" android:id="@+id/ad_view"
    android:layout_width="match_parent" android:layout_height="wrap_content">

    <FrameLayout android:layout_width="match_parent" android:layout_height="wrap_content"
        android:layout_gravity="center" android:minHeight="50dp" android:orientation="vertical">

        <androidx.constraintlayout.widget.ConstraintLayout android:id="@+id/ad_container"
            android:layout_width="match_parent" android:layout_height="match_parent">

            <androidx.constraintlayout.widget.ConstraintLayout
                android:layout_width="@dimen/next_no_size"
                android:layout_height="@dimen/next_no_size"
                android:layout_margin="@dimen/next_default_margin" android:orientation="horizontal"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintDimensionRatio="H,180:52"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent">

                <ImageView android:id="@+id/ad_app_icon" android:layout_width="@dimen/next_no_size"
                    android:layout_height="match_parent"
                    android:layout_margin="@dimen/next_no_margin" android:layout_weight="0"
                    app:layout_constraintBottom_toBottomOf="parent"
                    app:layout_constraintDimensionRatio="H,1:1"
                    app:layout_constraintEnd_toStartOf="@+id/content"
                    app:layout_constraintStart_toStartOf="parent"
                    app:layout_constraintTop_toTopOf="parent" />

                <androidx.constraintlayout.widget.ConstraintLayout android:id="@+id/content"
                    android:layout_width="@dimen/next_no_size"
                    android:layout_height="@dimen/next_no_size"
                    android:layout_marginStart="@dimen/next_default_margin"
                    android:layout_marginEnd="@dimen/next_default_margin"
                    android:orientation="vertical" app:layout_constraintBottom_toBottomOf="parent"
                    app:layout_constraintEnd_toEndOf="parent"
                    app:layout_constraintStart_toEndOf="@id/ad_app_icon"
                    app:layout_constraintTop_toTopOf="parent">

                    <LinearLayout android:id="@+id/headline" android:layout_width="match_parent"
                        android:layout_height="wrap_content" android:orientation="horizontal"
                        app:layout_constraintBottom_toTopOf="@+id/row_two"
                        app:layout_constraintEnd_toEndOf="parent"
                        app:layout_constraintStart_toStartOf="parent"
                        app:layout_constraintTop_toTopOf="parent">

                        <TextView android:id="@+id/ad_headline" android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:layout_margin="@dimen/next_no_margin" android:lines="1"
                            android:textColor="@color/next_gray"
                            android:textSize="@dimen/next_text_size_large" android:textStyle="bold"
                            app:layout_constraintBottom_toBottomOf="parent"
                            app:layout_constraintEnd_toEndOf="parent"
                            app:layout_constraintStart_toEndOf="parent"
                            app:layout_constraintTop_toTopOf="parent" />
                    </LinearLayout>

                    <LinearLayout android:id="@+id/row_two" android:layout_width="match_parent"
                        android:layout_height="@dimen/next_no_size" android:orientation="horizontal"
                        app:layout_constraintBottom_toTopOf="@+id/ad_call_to_action"
                        app:layout_constraintEnd_toEndOf="parent"
                        app:layout_constraintStart_toStartOf="parent"
                        app:layout_constraintTop_toBottomOf="@id/headline">

                        <RatingBar android:id="@+id/ad_stars" android:layout_width="match_parent"
                            android:layout_height="match_parent"
                            android:layout_margin="@dimen/next_no_margin" android:lines="1"
                            android:numStars="5" android:textColor="@color/next_gray"
                            android:textSize="@dimen/next_text_size_small" android:visibility="gone"
                            app:layout_constraintBottom_toBottomOf="parent"
                            app:layout_constraintEnd_toEndOf="parent"
                            app:layout_constraintStart_toEndOf="@id/ad_advertiser"
                            app:layout_constraintTop_toTopOf="parent" />

                        <TextView android:id="@+id/ad_body" android:layout_width="match_parent"
                            android:layout_height="match_parent"
                            android:layout_margin="@dimen/next_no_margin" android:gravity="top"
                            android:lines="1" android:textColor="@color/next_gray"
                            android:textSize="@dimen/next_text_size_small"
                            app:layout_constraintBottom_toBottomOf="parent"
                            app:layout_constraintEnd_toEndOf="parent"
                            app:layout_constraintStart_toEndOf="@id/ad_advertiser"
                            app:layout_constraintTop_toTopOf="parent" />

                    </LinearLayout>

                    <Button android:id="@+id/ad_call_to_action" android:layout_width="match_parent"
                        android:layout_height="@dimen/next_no_size"
                        android:background="@color/next_blue" android:lines="1"
                        android:textColor="#fff" app:layout_constraintBottom_toBottomOf="parent"
                        app:layout_constraintEnd_toEndOf="parent"
                        app:layout_constraintStart_toStartOf="parent"
                        app:layout_constraintTop_toBottomOf="@id/row_two" />

                </androidx.constraintlayout.widget.ConstraintLayout>

            </androidx.constraintlayout.widget.ConstraintLayout>

        </androidx.constraintlayout.widget.ConstraintLayout>
    </FrameLayout>

</io.nextmillennium.nextsdk.ui.nativeads.NextNativeAdLayout>

```

</details>

<details>
<summary>Medium</summary>

```xml
<?xml version="1.0" encoding="utf-8"?>
<io.nextmillennium.nextsdk.ui.nativeads.NextNativeAdLayout
    xmlns:android="http://schemas.android.com/apk/res/android" android:id="@+id/ad_view"
    android:layout_width="match_parent" android:layout_height="wrap_content">

    <FrameLayout android:layout_width="match_parent" android:layout_height="350dp"
        android:layout_gravity="center" android:minHeight="50dp" android:orientation="vertical">

        <LinearLayout android:id="@+id/ad_container" android:layout_width="match_parent"
            android:layout_height="match_parent" android:orientation="vertical"
            android:paddingLeft="20dp" android:paddingRight="20dp">

            <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content"
                android:orientation="horizontal">

                <ImageView android:id="@+id/ad_app_icon" android:layout_width="40dp"
                    android:layout_height="40dp" android:adjustViewBounds="true"
                    android:contentDescription="@string/ad_content" android:paddingEnd="5dp"
                    android:paddingBottom="5dp" />

                <LinearLayout android:layout_width="match_parent"
                    android:layout_height="wrap_content" android:orientation="vertical">

                    <TextView android:id="@+id/ad_headline" android:layout_width="match_parent"
                        android:layout_height="wrap_content" android:textColor="#0000FF"
                        android:textSize="16sp" android:textStyle="bold" />

                    <LinearLayout android:layout_width="match_parent"
                        android:layout_height="wrap_content">

                        <TextView android:id="@+id/ad_advertiser"
                            android:layout_width="wrap_content" android:layout_height="match_parent"
                            android:gravity="bottom" android:textSize="14sp"
                            android:textStyle="bold" />

                        <RatingBar android:id="@+id/ad_stars"
                            style="?android:attr/ratingBarStyleSmall"
                            android:layout_width="wrap_content" android:layout_height="wrap_content"
                            android:isIndicator="true" android:numStars="5"
                            android:stepSize="0.5" />
                    </LinearLayout>

                </LinearLayout>
            </LinearLayout>

            <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content"
                android:orientation="vertical">

                <TextView android:id="@+id/ad_body" android:layout_width="wrap_content"
                    android:layout_height="wrap_content" android:layout_marginEnd="20dp"
                    android:textSize="12sp" />

                <FrameLayout android:id="@+id/ad_media" android:layout_width="match_parent"
                    android:layout_height="200dp" android:layout_gravity="center_horizontal"
                    android:layout_marginTop="5dp" />

                <LinearLayout android:layout_width="wrap_content"
                    android:layout_height="wrap_content" android:layout_gravity="end"
                    android:orientation="horizontal" android:paddingTop="10dp"
                    android:paddingBottom="10dp">

                    <TextView android:id="@+id/ad_price" android:layout_width="wrap_content"
                        android:layout_height="wrap_content" android:paddingStart="5dp"
                        android:paddingLeft="5dp" android:paddingEnd="5dp"
                        android:paddingRight="5dp" android:textSize="12sp" />

                    <TextView android:id="@+id/ad_store" android:layout_width="wrap_content"
                        android:layout_height="wrap_content" android:paddingStart="5dp"
                        android:paddingLeft="5dp" android:paddingEnd="5dp"
                        android:paddingRight="5dp" android:textSize="12sp" />

                    <Button android:id="@+id/ad_call_to_action" android:layout_width="wrap_content"
                        android:layout_height="wrap_content" android:gravity="center"
                        android:textSize="12sp" />
                </LinearLayout>
            </LinearLayout>
        </LinearLayout>
    </FrameLayout>
</io.nextmillennium.nextsdk.ui.nativeads.NextNativeAdLayout>
```

</details>

It is important to use pre-defined ids for control elements of native ad view. The table containing
fields, ids and descriptions is below.

### Unified Native ads display fields

| Field | Id | Description | Always included? | Required to be displayed? | May truncate after
| --- | --- | --- | --- | --- | --- |
| Headline | ad_headline | Primary headline text (e.g., app title or article title). | Yes | Required | 25 characters
| Image | ad_media | Large, primary image. | Yes | Recommended | -
| Body | ad_body | Secondary body text (e.g., app description or article description). | Yes | Recommended | 90 characters
| Icon | ad_app_icon | Small icon image (e.g., app store image or advertiser logo). | Not | Recommended | -
| Call to action | ad_call_to_action | Button or text field that encourages user to take action (e.g., Install or Visit Site). | Yes | Recommended | 15 characters
| Star rating | ad_stars | Rating from 0-5 that represents the average rating of the app in a store. | Not | Recommended | -
| Store | ad_store | The app store where the user downloads the app. | Not | Recommended | 15 characters
| Price | ad_price | Cost of the app. | Not | Recommended | 15 characters
| Advertiser | ad_advertiser | Text that identifies the advertiser (e.g., advertiser or brand name, visible URL, etc.). | Not | Recommended | 25 characters

### Example

<details>
<summary>Custom template custom_native.xml</summary>

```xml

<io.nextmillennium.nextsdk.ui.nativeads.NextNativeAdLayout
    xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content"
        android:layout_gravity="center" android:minHeight="50dp" android:orientation="vertical"
        android:paddingLeft="20dp" android:paddingTop="3dp" android:paddingRight="20dp">

        <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content"
            android:orientation="horizontal">

            <ImageView android:id="@+id/ad_app_icon" android:layout_width="40dp"
                android:layout_height="40dp" android:adjustViewBounds="true"
                android:paddingEnd="5dp" android:paddingRight="5dp" android:paddingBottom="5dp" />

            <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content"
                android:orientation="vertical">

                <TextView android:id="@+id/ad_headline" android:layout_width="match_parent"
                    android:layout_height="wrap_content" android:textColor="#0000FF"
                    android:textSize="16sp" android:textStyle="bold" />

                <LinearLayout android:layout_width="match_parent"
                    android:layout_height="wrap_content">

                    <TextView android:id="@+id/ad_advertiser" android:layout_width="wrap_content"
                        android:layout_height="match_parent" android:gravity="bottom"
                        android:textSize="14sp" android:textStyle="bold" />

                    <RatingBar android:id="@+id/ad_stars" style="?android:attr/ratingBarStyleSmall"
                        android:layout_width="wrap_content" android:layout_height="wrap_content"
                        android:isIndicator="true" android:numStars="5" android:stepSize="0.5" />
                </LinearLayout>

            </LinearLayout>
        </LinearLayout>

        <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content"
            android:orientation="vertical">


            <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content">

                <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
                    android:layout_marginEnd="20dp" android:text="AD BODY:"
                    android:textSize="12sp" />

                <TextView android:id="@+id/ad_body" android:layout_width="wrap_content"
                    android:layout_height="wrap_content" android:layout_marginEnd="20dp"
                    android:textSize="12sp" />

            </LinearLayout>


            <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content"
                android:orientation="vertical">

                <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
                    android:text="MEDIA CONTENT BELOW:" />

                <com.google.android.gms.ads.nativead.MediaView android:id="@+id/ad_media"
                    android:layout_width="match_parent" android:layout_height="200dp"
                    android:layout_gravity="center_horizontal" android:layout_marginTop="5dp" />

            </LinearLayout>

            <LinearLayout android:layout_width="wrap_content" android:layout_height="wrap_content"
                android:layout_gravity="end" android:orientation="horizontal"
                android:paddingTop="10dp" android:paddingBottom="10dp">

                <TextView android:id="@+id/ad_price" android:layout_width="wrap_content"
                    android:layout_height="wrap_content" android:paddingStart="5dp"
                    android:paddingLeft="5dp" android:paddingEnd="5dp" android:paddingRight="5dp"
                    android:textSize="12sp" />

                <TextView android:id="@+id/ad_store" android:layout_width="wrap_content"
                    android:layout_height="wrap_content" android:paddingStart="5dp"
                    android:paddingLeft="5dp" android:paddingEnd="5dp" android:paddingRight="5dp"
                    android:textSize="12sp" />

                <Button android:id="@+id/ad_call_to_action" android:layout_width="wrap_content"
                    android:layout_height="wrap_content" android:gravity="center"
                    android:textSize="12sp" />
            </LinearLayout>
        </LinearLayout>
    </LinearLayout>
</io.nextmillennium.nextsdk.ui.nativeads.NextNativeAdLayout>

```

</details>

**Java**

```java
public class NativeAdFragment extends Fragment {

    private FragmentNativeAdBinding binding;
    private NextNativeView nativeView;
    private String LOG_TAG = "NEXT_SDK";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNativeAdBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nativeView = binding.nativeFragment;
        nativeView.setUnitId("108");
        nativeView.getOptions().setResourceId(R.layout.custom_native);
        nativeView.load();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (nativeView != null) nativeView.destroy();
        binding = null;
    }
}
```

**Kotlin**

```kotlin
class NativeAdFragmentKt : Fragment() {

    private var binding: FragmentNativeAdKtBinding? = null
    private var nativeView: NextNativeView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNativeAdKtBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nativeView = binding?.nativeFragmentKt
        nativeView?.unitId = "108"
        nativeView?.options?.resourceId = R.layout.custom_native
        nativeView?.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        nativeView?.destroy()
    }
}
```

### Example of full native ad customization

<details>
<summary>Java</summary>

```java
public class NativeAdFragment extends Fragment {

    private FragmentNativeAdBinding binding;
    private NextNativeView nativeView;
    private String LOG_TAG = "NEXT_SDK";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNativeAdBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nativeView = binding.nativeFragment;
        String unitId = "108";
        nativeView.setUnitId(unitId);
        nativeView.setFetchListener(createListener(unitId));
        nativeView.setAdListener(createAdListener(unitId));
        nativeView.getOptions().setStartMuted(false);
        nativeView.getOptions().setResourceId(R.layout.custom_native);
        nativeView.getOptions().setVideoListener(createVideoListener(unitId));
        nativeView.load();
    }

    private void logEvent(String message) {
        Log.d(LOG_TAG, message);
        Toast
                .makeText(requireActivity(), message, Toast.LENGTH_SHORT)
                .show();
    }

    private NextVideoListener createVideoListener(String unitId) {
        return new NextVideoListener() {
            @Override
            public void onStart() {
                logEvent("Started video for unit " + unitId);
            }

            @Override
            public void onPlay() {
                logEvent("Play video for unit " + unitId);
            }

            @Override
            public void onPause() {
                logEvent("Paused video ad event for unit " + unitId);
            }

            @Override
            public void onEnd() {
                logEvent("End video event for unit " + unitId);
            }

            @Override
            public void onMute(boolean isMuted) {
                logEvent("Video ad muted: " + isMuted + " for unit " + unitId);
            }
        };
    }

    private NextAdListener createAdListener(String unitId) {
        return new NextAdListener() {
            @Override
            public void onAdLoaded(BaseAdContainer container) {
                logEvent("Successfully loaded ad " + container.getUnitId());
                nativeView = (NextNativeView) container;
            }

            @Override
            public void onAdClicked() {
                logEvent("Successfully tracked click for ad " + unitId);
            }

            @Override
            public void onAdClosed() {
                logEvent("Closed ad " + unitId);
            }

            @Override
            public void onAdLoadFail(NextAdError adError) {
                Log.e("NEXT_SDK", adError.toString());
                Toast
                        .makeText(requireActivity(), "Error happened while loading ad: " + adError, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAdImpression() {
                logEvent("Successfully tracked impression for ad " + unitId);
            }

            @Override
            public void onAdOpened() {
                logEvent("Opened ad " + unitId);
            }
        };
    }

    private FetchListener createListener(String unitId) {
        return new FetchListener() {
            @Override
            public void onSuccess() {
                if (isAdded()) {
                    Toast.makeText(requireActivity(),
                                    "Successfully loaded ad " + unitId,
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (isAdded()) {
                    Toast.makeText(requireActivity(),
                                    "Error ad load",
                                    Toast.LENGTH_SHORT)
                            .show();
                    if (throwable == null) {
                        return;
                    }
                    Toast.makeText(requireActivity(),
                                    throwable.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (nativeView != null) nativeView.destroy();
        binding = null;
    }
}
```

</details>

<details>
<summary>Kotlin</summary>

```kotlin
class NativeAdFragmentKt : Fragment() {

    private var binding: FragmentNativeAdKtBinding? = null
    private var nativeView: NextNativeView? = null
    private val LOG_TAG = "NEXT_SDK"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNativeAdKtBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nativeView = binding?.nativeFragmentKt
        val unitId = "108"
        nativeView?.unitId = unitId
        nativeView?.setAdListener(createAdListener(unitId))
        nativeView?.setFetchListener(createListener(unitId))
        nativeView?.options?.resourceId = R.layout.custom_native
        nativeView?.options?.videoListener = createVideoListener(unitId)
        nativeView?.options?.isStartMuted = false
        nativeView?.load()
    }

    private fun logEvent(message: String) {
        Log.d(LOG_TAG, message)
        Toast
            .makeText(requireActivity(), message, Toast.LENGTH_SHORT)
            .show()
    }

    private fun createVideoListener(unitId: String): NextVideoListener {
        return object : NextVideoListener {
            override fun onStart() {
                logEvent("Started video event for unit $unitId")
            }

            override fun onPlay() {
                logEvent("Play video event for unit $unitId")
            }

            override fun onPause() {
                logEvent("Paused video ad event for unit $unitId")
            }

            override fun onEnd() {
                logEvent("End video event for unit $unitId")
            }

            override fun onMute(isMuted: Boolean) {
                logEvent("Video ad muted: $isMuted for unit $unitId")
            }
        }
    }

    private fun createAdListener(unitId: String): NextAdListener {
        return object : NextAdListener {
            override fun onAdLoaded(container: BaseAdContainer) {
                logEvent("Successfully loaded ad " + container.unitId)
                nativeView = container as NextNativeView
            }

            override fun onAdClicked() {
                logEvent("Successfully tracked click for ad $unitId")
            }

            override fun onAdClosed() {
                logEvent("Closed ad $unitId")
            }

            override fun onAdLoadFail(adError: NextAdError) {
                Log.e("NEXT_SDK", adError.toString())
                Toast
                    .makeText(
                        requireActivity(),
                        "Error happened while loading ad: $adError",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }

            override fun onAdImpression() {
                logEvent("Successfully tracked impression for ad $unitId")
            }

            override fun onAdOpened() {
                logEvent("Opened ad $unitId")
            }
        }
    }


    private fun createListener(unitId: String): FetchListener {
        return object : FetchListener {
            override fun onSuccess() {
                if (isAdded) {
                    Toast.makeText(
                        requireActivity(),
                        "Successfully loaded ad : $unitId",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onError(err: Throwable?) {
                if (isAdded) {
                    Toast.makeText(
                        requireActivity(),
                        "Error ad load: $err",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        nativeView?.destroy()
    }
}
```

</details>