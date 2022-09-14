# Banner ads

[Back to manual mode overview](https://github.com/nextmillenniummedia/next-sdk-android-example/blob/main/docs/manual/Manual.md)

To show banner ads you need to add `NextBannerView` component to your UI.

Add it to your layout XML file:

```xml

<io.nextmillennium.nextsdk.ui.NextBannerView android:id="@+id/bannerView"
    android:layout_width="match_parent" android:layout_height="wrap_content" />
```

Get it in your activity or fragment

```java
import io.nextmillennium.nextsdk.ui.banner.NextBannerView;

public class SomeActivity extends AppCompatActivity {

    NextBannerView bannerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some);
        bannerView = findViewById(R.id.bannerView);
    }
}
```

Set ad unit id that we will provide for you

```java 
bannerView.setUnitId("your_unit_id");
```

And finally just load it.

<details>
<summary style="font-size:14px">Java</summary>

```java
import io.nextmillennium.nextsdk.NextBannerView;

public class SomeActivity extends AppCompatActivity {

    NextBannerView bannerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some);
        bannerView = view.findViewById(R.id.bannerView);
        bannerView.setUnitId("Your_unit_id");
        bannerView.load();
    }
}
```

</details>

<details>
<summary style="font-size:14px">Kotlin</summary>

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val banner = findViewById<NextBannerView>(R.id.banner_view)
        banner.unitId = "517"
        banner.load()
    }
}
```

</details>

### Listen to load events

You can use `bannerView.setFetchListener` to provide your customized behavior for successful and
failure banner loads.

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
        setContentView(R.layout.activity_classic_custom_banner);
        bannerView = findViewById(R.id.classicBanner);
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
    }
}
```

</details>

<details>
<summary>Kotlin</summary>

```kotlin
class ClassicCustomBannerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classic_custom_banner)
        bannerView = findViewById(R.id.banner)
        bannerView?.setFetchListener(object : FetchListener {
            override fun onSuccess() {
                Toast.make(
                    this@ClassicCustomBannerActivity,
                    "Successfully loaded banner : $unitId",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(err: Throwable?) {
                Toast.make(
                    this@ClassicCustomBannerActivity,
                    "Error banner load: $err",
                    Toast.LENGTH_SHORT
                ).show()
                Toast.makeText(
                    this@ClassicCustomBannerActivity,
                    throwable.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}

```

</details>

## Examples

Let's look how to add banner to your application!

### Activity

<details>
<summary style="font-size:14px">Java</summary>

```java
import io.nextmillennium.nextsdk.ui.banner.NextBannerView;

public class ClassicInAppBannerActivity extends AppCompatActivity {

    NextBannerView bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_custom_banner);
        String unitId = "419";
        bannerView = findViewById(R.id.classicInAppBanner);
        bannerView.setUnitId(unitId);
        // If you need listener for Next Millennium response
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
        bannerView.load();
    }
}
```

</details>
<details>
<summary style="font-size:14px">Kotlin</summary>

```kotlin
class ClassicCustomBannerActivity : AppCompatActivity() {
    private var bannerView: NextBannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classic_custom_banner)
        val unitId = "419"
        bannerView = findViewById(R.id.banner)
        bannerView?.setUnitId(unitId)
        bannerView?.setFetchListener(object : FetchListener {
            override fun onSuccess() {
                Toast.make(
                    this@ClassicCustomBannerActivity,
                    "Successfully loaded banner : $unitId",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(err: Throwable?) {
                Toast.make(
                    this@ClassicCustomBannerActivity,
                    "Error banner load: $err",
                    Toast.LENGTH_SHORT
                ).show()
                Toast.makeText(
                    this@ClassicCustomBannerActivity,
                    throwable.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        bannerView?.load()
    }
}
```

</details>

### Fragment

<details>
<summary style="font-size:14px">Java</summary>

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
<summary style="font-size:14px">Kotlin</summary>

```kotlin
class NewsFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bannerView = findViewById<NextBannerView>(R.id.banner_view)
        bannerView.unitId = "your_unit_id"
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

### Separate load and show ads

<!-- TODO: add load->show example -->

### Managing banner lifecycle

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

public class ClassicCustomBannerActivity extends AppCompatActivity {
    private NextBannerView banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_custom_banner);
        banner = findViewById(R.id.banner);
        banner.setUnitId("417");
        banner.load();
    }

    @Override
    protected void onPause() {
        if (banner != null) {
            banner.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (banner != null) {
            banner.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (banner == null) return;
        banner.resume();
    }
}
```

</details>


<details>
<summary>Kotlin</summary>

```kotlin
class ClassicCustomBannerActivity : AppCompatActivity() {
    var classicBanner: NextBannerView?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classic_custom_banner)
        banner = findViewById(R.id.classicInAppBanner)
    }

    override fun onPause() {
        classicBanner?.pause()
        super.onPause()
    }

    override fun onDestroy() {
        classicBanner?.destroy()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        classicBanner?.resume()
    }
}
```

</details>


More meaningful example:

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

    private var classicBanner: NextBannerView? = null
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
        classicBanner?.unitId = unitId
        classicBanner?.setFetchListener(object : FetchListener {
            override fun onSuccess() {
                showLoaded(unitId)
            }

            override fun onError(err: Throwable?) {
                showError(it)
            }
        })
        classicBanner?.load()
    }

    override fun onPause() {
        classicBanner?.pause()
        super.onPause()
    }

    override fun onDestroy() {
        classicBanner?.destroy()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        classicBanner?.resume()
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

