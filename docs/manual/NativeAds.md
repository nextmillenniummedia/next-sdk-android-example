# Native Ads

[Back to manual mode overview](https://github.com/nextmillenniummedia/next-sdk-android-example/blob/main/docs/manual/Manual.md)

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

### Release resources

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
public class NativeAdsActivity extends AppCompatActivity implements NextAdListener {

    private NextNativeView nativeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_ads);
        String unitId = "108";
        nativeView = findViewById(R.id.native_activity);
        nativeView.setUnitId(unitId);
        nativeView.setAdListener(this);
        nativeView.load();
    }

    @Override
    public void onAdLoaded(BaseAdContainer container) {
        nativeView = (NextNativeView) container;
    }

    @Override
    public void onAdLoadFail(NextAdError adError) {
        Log.e("NEXT_SDK", adError.toString());
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
        nativeView.setAdListener(this)
        nativeView.load()
    }

    override fun onAdLoaded(container: BaseAdContainer?) {
        nativeView = container as NextNativeView
    }

    override fun onAdLoadFail(adError: NextAdError?) {
        Log.e("NEXT_SDK", adError.toString())
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
        Log.d("NEXT_SDK", "Successful loaded ad");
        nativeView = (NextNativeView) container;
    }

    @Override
    public void onAdLoadFail(NextAdError adError) {
        Log.e("NEXT_SDK", adError.toString());
        if (isDestroyed()) {
            nativeView.destroy();
        }
    }

    @Override
    public void onAdClicked() {
        Log.d("NEXT_SDK", "Successfully tracked click");
    }

    @Override
    public void onAdImpression() {
        Log.d("NEXT_SDK", "Successfully tracked impression");
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
        nativeView.setAdListener(this)
        nativeView.setFetchListener(createListener(unitId))
        nativeView.load()
    }

    override fun onAdLoaded(container: BaseAdContainer?) {
        nativeView = container as NextNativeView
    }

    override fun onAdClicked() {
        Log.d("NEXT_SDK", "Successfully tracked click")
    }

    override fun onAdImpression() {
        Log.d("NEXT_SDK", "Successfully tracked impression")
    }

    override fun onAdLoadFail(adError: NextAdError?) {
        Log.e("NEXT_SDK", adError.toString())
        if (isDestroyed) {
            nativeView.destroy()
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

### **Fragment**

Full example of implementing native ad in fragment

<details>
<summary>Java</summary>

```java
public class NativeAdFragment extends Fragment implements NextAdListener {

    private FragmentNativeAdBinding binding;
    private NextNativeView nativeView;

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
        nativeView.setAdListener(this);
        nativeView.load();
    }

    public FetchListener createListener(String unitId) {
        return new FetchListener() {
            @Override
            public void onSuccess() {
                if (isAdded()) {
                    Toast.makeText(requireActivity(),
                            "Successfully loaded ad " + unitId,
                            Toast.LENGTH_SHORT).show();
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
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    public void onAdLoaded(BaseAdContainer container) {
        Log.d("NEXT_SDK", "Successful loaded ad");
        nativeView = (NextNativeView) container;
    }

    @Override
    public void onAdLoadFail(NextAdError adError) {
        Log.e("NEXT_SDK", adError.toString());
    }

    @Override
    public void onAdClicked() {
        Log.d("NEXT_SDK", "Successfully tracked click");
    }

    @Override
    public void onAdImpression() {
        Log.d("NEXT_SDK", "Successfully tracked impression");
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
class NativeAdFragmentKt : Fragment(), NextAdListener {

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
        val unitId = "108"
        nativeView?.unitId = unitId
        nativeView?.setAdListener(this)
        nativeView?.setFetchListener(createListener(unitId))
        nativeView?.load()
    }

    override fun onAdLoaded(container: BaseAdContainer?) {
        nativeView = container as NextNativeView
    }

    override fun onAdClicked() {
        Log.d("NEXT_SDK", "Successfully tracked click")
    }

    override fun onAdImpression() {
        Log.d("NEXT_SDK", "Successfully tracked impression")
    }

    override fun onAdLoadFail(adError: NextAdError?) {
        Log.e("NEXT_SDK", adError.toString())
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

## Custom layouts

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