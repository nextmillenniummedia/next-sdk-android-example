# Banner ads

[Back to manual mode overview](https://github.com/nextmillenniummedia/next-sdk-android-example/blob/2.x/docs/manual/Manual.md)

Banner ad is a rectangle with some advertising content including images, GIFS or videos.

To show banner ads you need to add `NextBannerView` component to your UI.

Add it to your layout XML file:

```xml
<io.nextmillennium.nextsdk.ui.NextBannerView 
    android:id="@+id/bannerView"
    android:layout_width="match_parent" 
    android:layout_height="wrap_content" />
```

Get it in your activity or fragment

```java
import io.nextmillennium.nextsdk.ui.banner.NextBannerView;

public class BannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        NextBannerView bannerView = findViewById(R.id.banner_activity);
    }
}

```

Set ad unit id that we will provide for you

```java 
bannerView.setUnitId("your_unit_id");
```

You will get next error message trying to load banner without unit id:
`UndefinedUnitIdException: Ad unit id did not specified`

And finally just load it by calling `load()` method. By default banner will be shown right after
load.

<details>
<summary>Java</summary>

```java
import io.nextmillennium.nextsdk.NextBannerView;

public class BannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        NextBannerView bannerView = findViewById(R.id.banner_activity);
        bannerView.setUnitId("103");
        bannerView.load();
    }
}
```

</details>

<details>
<summary>Kotlin</summary>

```kotlin
class BannerActivityKt : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_kt)
        val bannerView: NextBannerView = findViewById(R.id.banner_activity_kt)
        bannerView.unitId = "103"
        bannerView.load()
    }
}

```

</details>

### Listen to load events

You can use `bannerView.setFetchListener(FetchListener)` to provide your customized
behavior for successful and failure banner loads.

It is available by implementing `FetchListener` interface. It contains 2 methods for success and
error:

Available events:

| method | description |
| --- | --- |
| `onSuccess` | Called when an ad is successfully received. |
| `onError(Throwable)` | Called when error happened while fetching an ad data |

Implementation examples:

<details>
<summary>Java</summary>

```java

public class SomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some);
        NextBannerView bannerView = findViewById(R.id.banner);
        bannerView.setFetchListener(new FetchListener() {
            @Override
            public void onSuccess() {
                // banner loaded successfully
                Toast.makeText(this, "Successfully loaded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable throwable) {
                // some error occurred
                Toast.makeText(this, "Ad load error", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        bannerView.setUnitId("103");
        bannerView.load();
    }

}
```

</details>

<details>
<summary>Kotlin</summary>

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bannerView: NextBannerView = findViewById(R.id.banner_view)
        bannerView.setFetchListener(object : FetchListener {
            override fun onSuccess() {
                Toast.makeText(
                    this@MainActivity,
                    "Successfully loaded banner : ${bannerView.unitId}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(err: Throwable?) {
                Toast.makeText(
                    this@MainActivity,
                    "Error banner load: $err",
                    Toast.LENGTH_SHORT
                ).show()
                Toast.makeText(
                    this@MainActivity,
                    err?.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        bannerView.unitId = "103"
        bannerView.load()
    }

}
```

</details>

## Examples

Let's look how to add banner to your application!

### Activity

<details>
<summary>Java</summary>

```java
import io.nextmillennium.nextsdk.ui.banner.NextBannerView;

public class BannerActivity extends AppCompatActivity {

    private NextBannerView bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        String unitId = "103"; // your unit id
        bannerView = findViewById(R.id.banner_activity);
        bannerView.setFetchListener(createListener(unitId));
        bannerView.setUnitId(unitId); 
        bannerView.load();
    }

    public FetchListener createListener(String unitId) {
        return new FetchListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(BannerActivity.this,
                                "Successfully loaded banner " + unitId,
                                Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(BannerActivity.this,
                                "Error ad load",
                                Toast.LENGTH_SHORT)
                        .show();
                if (throwable == null) return;
                Toast.makeText(BannerActivity.this,
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
class BannerActivityKt : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_kt)
        val unitId = "103" // your unit id
        val bannerView: NextBannerView = findViewById(R.id.banner_activity_kt)
        bannerView.setFetchListener(createListener(unitId))
        bannerView.unitId = unitId
        bannerView.load()
    }

    private fun createListener(unitId: String): FetchListener {
        return object : FetchListener {
            override fun onSuccess() {
                Toast.makeText(
                    this@BannerActivityKt,
                    "Successfully loaded banner : $unitId",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(err: Throwable?) {
                Toast.makeText(this@BannerActivityKt, "Error banner load: $err", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}
```

</details>

### Fragment

<details>
<summary>Java</summary>

```java
public class NewsFragment extends Fragment {

    private NextBannerView bannerView;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bannerView = view.findViewById(R.id.banner);
        bannerView.setUnitId("your_unit_id");
        bannerView.load();
    }
}
```

</details>
<details>
<summary>Kotlin</summary>

```kotlin
class NewsFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bannerView = findViewById<NextBannerView>(R.id.banner_view)
        bannerView.unitId = "103" // your unit id
        bannerView.setFetchListener(object : FetchListener {
            override fun onSuccess() {
                // banner loaded successfully
            }

            override fun onError(err: Throwable?) {
                // some error occured
            }
        })
        bannerView.load()
    }
}
```

</details>

### Fragments with view-binding enabled

<details>
<summary>Java</summary>

```java

public class BannerFragment extends Fragment {

    @Nullable
    private FragmentBannerBinding binding;

    public BannerFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBannerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (binding == null) return;
        NextBannerView bannerView = binding.bannerFragment;
        bannerView.setUnitId("103"); // your unit id
        bannerView.load();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
```

</details>

<details>
<summary>Kotlin</summary>

```kotlin
class BannerFragmentKt : Fragment() {

    private var binding: FragmentBannerKtBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBannerKtBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bannerView = binding?.bannerFragmentKt
        bannerView?.unitId = "103" // your unit id
        bannerView?.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
```

</details>

### Customize ad lifecycle events

When you call `load()` by default banner will be shown right after load. Sometimes you need to add
specific events for banner lifecycle. You can use `NextAdListener` for this purpose.

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

Example of using listener for banner.

```java
public class BannerActivity extends AppCompatActivity implements NextAdListener {

    private NextBannerView bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        String unitId = "103";
        bannerView = findViewById(R.id.banner_activity);
        bannerView.setAdListener(this);
        bannerView.setUnitId(unitId); // your unit id
        bannerView.load();
    }

    @Override
    public void onAdLoaded(BaseAdContainer container) {
        bannerView = (NextBannerView) container;
        //further work with banner
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bannerView == null) return;
        bannerView.resume();
    }

    @Override
    protected void onPause() {
        if (bannerView != null) {
            bannerView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (bannerView != null) {
            bannerView.destroy();
        }
        super.onDestroy();
    }
}

```

```kotlin

class BannerActivityKt : AppCompatActivity(), NextAdListener {

    private lateinit var bannerView: NextBannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_kt)
        bannerView = findViewById(R.id.banner_activity_kt)
        bannerView.setAdListener(this)
        bannerView.unitId = "103"
        bannerView.load()
    }

    override fun onAdLoaded(container: BaseAdContainer?) {
        bannerView = container as NextBannerView
    }
}

```

### Managing ad view lifecycle

We have methods for managing lifecycle: `resume`,`pause` and `destroy`. More info about activity
lifecycle
callbacks: [resume](https://developer.android.com/guide/components/activities/activity-lifecycle#onresume)
, [pause](https://developer.android.com/guide/components/activities/activity-lifecycle#onpause)
and [destroy](https://developer.android.com/guide/components/activities/activity-lifecycle#ondestroy)
.

Always destroy ad views to ensure that it is removed from the layout and cleared from memory.

You can simply call them for instance of `NextBannerView` in your own overrides of `onResume`
, `onPause` and `onDestroy`:

<details>
<summary>Java</summary>

```java

import io.nextmillennium.nextsdk.core.ui.NextBannerView;

public class BannerActivity extends AppCompatActivity {

    private NextBannerView bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        bannerView = findViewById(R.id.banner_activity);
        bannerView.setUnitId("418"); // your unit id
        bannerView.load();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bannerView == null) return;
        bannerView.resume();
    }

    @Override
    protected void onPause() {
        if (bannerView != null) {
            bannerView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (bannerView != null) {
            bannerView.destroy();
        }
        super.onDestroy();
    }
}
```

</details>
<details>
<summary>Kotlin</summary>

```kotlin
class BannerActivityKt : AppCompatActivity() {

    private lateinit var bannerView: NextBannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_kt)
        bannerView = findViewById(R.id.banner_activity_kt)
        bannerView.unitId = "418" // your unit id
        bannerView.load()
    }

    override fun onPause() {
        bannerView.pause()
        super.onPause()
    }

    override fun onDestroy() {
        bannerView.destroy()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        bannerView.resume()
    }

}
```

</details>

Example with banner load by clicking on button:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent" android:layout_height="match_parent"
    android:orientation="vertical">

    <Button android:id="@+id/action_button" android:layout_width="match_parent"
        android:layout_height="wrap_content" android:text="Action" />

    <io.nextmillennium.nextsdk.ui.banner.NextBannerView android:id="@+id/banner_load"
        android:layout_width="match_parent" android:layout_height="wrap_content" />

</LinearLayout>
```

<details>
<summary>Java</summary>

```java
public class BannerLoadActivity extends AppCompatActivity {

    private ActivityBannerLoadBinding binding;
    @Nullable
    private NextBannerView bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBannerLoadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bannerView = binding.bannerLoad;
        String unitId = "103"; // your unit id
        bannerView.setUnitId(unitId);
        bannerView.setFetchListener(createListener(unitId));
        Button action = binding.actionButton;
        action.setOnClickListener((v) -> {
            bannerView.load();
            //your action
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bannerView == null) return;
        bannerView.resume();
    }

    @Override
    protected void onPause() {
        if (bannerView != null) bannerView.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (bannerView != null) bannerView.destroy();
        super.onDestroy();
    }

    public FetchListener createListener(String unitId) {
        return new FetchListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(BannerLoadActivity.this,
                                "Successfully loaded banner " + unitId,
                                Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(BannerLoadActivity.this,
                                "Error ad load",
                                Toast.LENGTH_SHORT)
                        .show();
                if (throwable == null) return;
                Toast.makeText(BannerLoadActivity.this,
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

```Kotlin

class BannerLoadActivityKt : AppCompatActivity() {

    private lateinit var binding: ActivityBannerLoadKtBinding
    private var bannerView: NextBannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBannerLoadKtBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val unitId = "103" // your unit id
        bannerView?.unitId = unitId
        bannerView?.setFetchListener(createListener(unitId))
        val action = binding.actionButtonKt
        action.setOnClickListener {
            bannerView?.load()
        }
    }

    private fun createListener(unitId: String): FetchListener {
        return object : FetchListener {
            override fun onSuccess() {
                Toast.makeText(
                    this@BannerLoadActivityKt,
                    "Successfully loaded banner : $unitId",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(err: Throwable?) {
                Toast.makeText(
                    this@BannerLoadActivityKt,
                    "Error banner load: $err",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        bannerView?.resume()
    }

    override fun onPause() {
        bannerView?.pause()
        super.onPause()
    }

    override fun onDestroy() {
        bannerView?.destroy()
        super.onDestroy()
    }
}
```

</details>

### Adaptive banners. Banner container width management

Adaptive banners let developers specify the ad-width and use this to determine the optimal ad size.
By default adaptive banners' width is same as device screen width in density pixels. But sometimes
you insert ad in `CardView` or other ViewGroup with custom layout. It must looks ok. If banner still
show full width you can pass container width:

```java 
bannerView.setContainerWidth(int containerWidth)
```

For example

```kotlin
inlineBanner.setContainerWidth(binding.inlineBannerContainer.width)
```

Banner in container will look like on image below
<p align="center">
<img src="https://github.com/nextmillenniummedia/next-sdk-android-example/blob/2.x/docs/assets/container_width.png" height="720">
</p>