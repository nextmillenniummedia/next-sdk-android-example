# Banner ads

[Back to manual mode overview](https://github.com/nextmillenniummedia/next-sdk-android-example/blob/main/docs/manual/Manual.md)

To show banner ads you need to add `NextBannerView` component to your UI. Add it to your layout XML
file:

```xml

<io.nextmillennium.nextsdk.ui.NextBannerView android:id="@+id/bannerView"
    android:layout_width="match_parent" android:layout_height="wrap_content" />
```

Then you need to find it in your Activity or Fragment by id

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

Then you need to set unit id from our system that we provide for you

```java 
bannerView.setUnitId("your_unit_id");
```

And finally you just need to load an ad by calling load method.

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
        bannerView.setFetchListener(new FetchListener() {
                    @Override
                    public void onSuccess() {
                        // banner loaded successfully
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        // some error occured
                    }
                }
        );
        bannerView.load();
    }
}

```

Example of using banner in activity. While in activity always load banner ads `onCreate` method:

Java

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
        bannerContainer.setFetchListener(new FetchListener() {
            @Override
            public void onSuccess() {
                // success callback
                Toast.makeText(this, "Successfully loaded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable throwable) {
                // error callback
                Toast.makeText(this, "Ad load error", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        bannerView.load();
    }
}
```

Kotlin

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

Example of using banner in fragment. While in fragment always load ads `onViewCreated`

Java

```java
public class NewsFragment extends Fragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NextBannerView bannerView = view.findViewById(R.id.banner);

        bannerView.setUnitId("your_unit_id");
        bannerView.load();
    }
}
```

Kotlin

```kotlin
class NewsFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bannerView: NextBannerView = view.findViewById(R.id.banner)
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

### Managing banner lifecycle

We have 3 methods for managing banner lifecycle: `resume`,`pause` and `destroy`. More info about
activity lifecycle
callbacks: [resume](https://developer.android.com/guide/components/activities/activity-lifecycle#onresume)
, [pause](https://developer.android.com/guide/components/activities/activity-lifecycle#onpause)
and [destroy](https://developer.android.com/guide/components/activities/activity-lifecycle#ondestroy)
.

For now, it's only available with `NextBannerView`. NextAlways destroy ad views to ensure that it is
removed from the layout and cleared from memory.

You can simply call them for instance of `NextBannerView` Next your own overrides of `onResume`
, `onPause` and `onDestroy`:

Java

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

Kotlin

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

More meaningful example:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent" android:orientation="vertical"
    tools:context=".classic.ClassicCustomBannerActivity">

    <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content"
        android:minHeight="48dp" android:orientation="horizontal">

        <TextView android:id="@+id/textView4" android:layout_width="wrap_content"
            android:layout_height="wrap_content" android:text="@string/enter_id" />

        <EditText android:id="@+id/classicBannerUnitId" android:layout_width="match_parent"
            android:layout_height="wrap_content" android:autofillHints="name" android:ems="10"
            android:hint="@string/enter_id" android:inputType="number" android:minHeight="48dp" />
    </LinearLayout>

    <Button android:id="@+id/loadBanner" android:layout_width="match_parent"
        android:layout_height="wrap_content" android:text="@string/load" />

    <io.nextmillennium.nextsdk.ui.NextBannerView android:id="@+id/banner"
        android:layout_width="match_parent" android:layout_height="wrap_content">

    </io.nextmillennium.nextsdk.ui.NextBannerView>

</LinearLayout>
```

Java

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
        Button load = binding.loadBanner;
        classicBanner = binding.banner;
        load.setOnClickListener((view) -> loadBanner(enteredUnitId));
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
        if (classicBanner != null) {
            classicBanner.resume();
        }
    }
}
```

Kotlin

```Kotlin

class ClassicCustomBannerActivity : AppCompatActivity() {

    private var classicBanner: NextBannerView? = null
    private var binding: ActivityClassicCustomBannerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassicCustomBannerBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val unitId = "417"
        val load = binding?.loadBanner
        classicBanner = binding?.classicInAppBanner
        load.setOnClickListener { loadBanner(unitId) }
    }

    private fun loadBanner(unitId: String?) {
        classicBanner?.inAppUnitId = unitId
        classicBanner?.load({ showLoaded(classicBanner?.inAppUnitId ?: "") }, { showError(it) })
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
        binding?.let {
            Snackbar.make(
                it.root,
                "Successfully loaded banner : $message",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun showError(error: Throwable) {
        binding?.let {
            Snackbar.make(it.root, "Error banner load: $error", Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}
```
