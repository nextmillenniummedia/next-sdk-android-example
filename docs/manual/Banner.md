# Banner ads

[Back to manual mode overview](https://github.com/nextmillenniummedia/next-sdk-android-example/blob/2.x/docs/manual/Manual.md)

To show banner ads you need to add `NextBannerView` component to your UI.

Add it to your layout XML file:

```xml

<io.nextmillennium.nextsdk.ui.NextBannerView android:id="@+id/bannerView"
    android:layout_width="match_parent" android:layout_height="wrap_content" />
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

You can use `bannerView.setFetchListener(FetchListener listener)` to provide your customized
behavior for successful and failure banner loads.

It is available by implementing `FetchListener` interface. It contains 2 methods for success and
error:

```java
public interface FetchListener {
    void onSuccess();

    void onError(Throwable var1);
}

```

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
                // some error occured
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
        String unitId = "103";
        bannerView = findViewById(R.id.banner_activity);
        bannerView.setFetchListener(createListener(unitId));
        bannerView.setUnitId(unitId); // your unit id
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
                if (throwable == null) {
                    return;
                }
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
        val unitId = "103"
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
        bannerView.unitId = "418" // your unit id
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

### Customize ad lifecycle events

When you call `load()` by default banner will be shown right after load. Sometimes you need to add
specific events for banner lifecycle. You can use `NextAdListener` for this purpose.

`NextAdListener` used for managing all lifecycle events of ad.

```java
public interface NextAdListener {

    void onAdLoaded(BaseAdContainer container);

    void onAdClicked();

    void onAdImpression();

    void onAdOpened();

    void onAdClosed();

    void onAdLoadFail(NextAdError adError);

}
```

Events:

| method | description |
| --- | --- |
| `onAdLoaded(BaseAdContainer container)` | Called when an ad is received. |
| `onAdImpression()` | Called when an impression is recorded for an ad. |
| `onAdClicked()` | Called when a click is recorded for an ad. |
| `onAdOpened()` | Called when an ad opens an overlay that covers the screen. |
| `onAdClosed()` | Called when the user wants to return to the application after clicking on an ad. |
| `onAdLoadFail(NextAdError adError)` | Called when an ad load failed. |

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
        bannerView.unitId = "418"
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

For now, it's only available with `NextBannerView`.

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
        bannerView.resume()
        super.onResume()
    }

}
```

</details>

Example with banner load by clicking on button:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent" android:orientation="vertical"
    tools:context=".classic.ClassicCustomBannerActivity">

    <Button android:id="@+id/loadButton" android:layout_width="match_parent"
        android:layout_height="wrap_content" android:text="Load" />

    <io.nextmillennium.nextsdk.ui.NextBannerView android:id="@+id/banner"
        android:layout_width="match_parent" android:layout_height="wrap_content">

    </io.nextmillennium.nextsdk.ui.NextBannerView>

</LinearLayout>
```

<details>
<summary>Java</summary>

```java
public class ClassicCustomBannerActivity extends AppCompatActivity {

    private NextBannerView classicBanner;
    private ActivityClassicCustomBannerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClassicCustomBannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String unitId = "417";
        Button load = binding.loadButton;
        classicBanner = binding.banner;
        load.setOnClickListener((view) -> loadBanner(unitId));
    }

    private void loadBanner(String unitId) {
        classicBanner.setUnitId(unitId);
        classicBanner.setFetchListener(new FetchListener() {
            @Override
            public void onSuccess() {
                if (getActivity() == null || binding == null) return;
                Snackbar.make(binding.getRoot(),
                        "Successfully loaded unit",
                        Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable throwable) {
                if (binding == null) return;
                Snackbar.make(binding.getRoot(), "Error ad load", Snackbar.LENGTH_SHORT)
                        .show();
                String message = throwable.getMessage() != null ? throwable.getMessage() : "Unexpected error";
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
        classicBanner.load();
    }

    @Override
    protected void onPause() {
        if (classicBanner != null) {
            classicBanner.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (classicBanner != null) {
            classicBanner.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (classicBanner == null) return;
        classicBanner.resume();
    }
}
```

</details>

<details>
<summary>Kotlin</summary>

```Kotlin

class ClassicCustomBannerActivity : AppCompatActivity() {

    private lateinit var classicBanner: NextBannerView
    private lateinit var binding: ActivityClassicCustomBannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassicCustomBannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val unitId = "417"
        val load: Button = binding.loadButton
        classicBanner = binding.banner
        load.setOnClickListener { loadBanner(unitId) }
    }

    private fun loadBanner(unitId: String) {
        classicBanner.unitId = unitId
        classicBanner.setFetchListener(object : FetchListener {
            override fun onSuccess() {
                showLoaded(unitId)
            }

            override fun onError(err: Throwable?) {
                showError(it)
            }
        })
        classicBanner.load()
    }

    override fun onPause() {
        classicBanner.pause()
        super.onPause()
    }

    override fun onDestroy() {
        classicBanner.destroy()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        classicBanner.resume()
    }

    private fun showLoaded(message: String = "") {
        Snackbar.make(
            binding.root,
            "Successfully loaded banner : $message",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun showError(error: Throwable) {
        Snackbar.make(binding.root, "Error banner load: $error", Snackbar.LENGTH_SHORT)
            .show()
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