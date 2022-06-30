# Banner ads
[Back to manual mode overview](https://github.com/nextmillenniummedia/inapp-android-example/blob/main/docs/manual/Manual.md)

To show banner ads you need to add `InAppBannerView` component to your UI. Add it to your layout XML
file:

```xml

<com.nextmillennium.inappsdk.core.ui.InAppBannerView android:id="@+id/inAppBannerView"
    android:layout_width="match_parent" android:layout_height="wrap_content" />
```

Then you need to find it in your Activity or Fragment by id

```java

public class SomeActivity extends AppCompatActivity {

    InAppBannerView bannerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some);
        InAppBannerView bannerView = findViewById(R.id.inAppBannerView);
    }
}
```

Then you need to set unit id from our system that we provide for you

```java 
bannerView.setInAppUnitId("Your inAppBanner id");
```

And finally you just need to load an ad by calling load method.

```java

public class SomeActivity extends AppCompatActivity {

    InAppBannerView bannerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some);
        InAppBannerView bannerView = view.findViewById(R.id.inAppBannerView);
        bannerView.setInAppUnitId("Your inAppBanner id");
        bannerView.load(new SuccessListener() {
            @Override
            public void onSuccess() {
                // banner loaded successfully
            }
        }, new ErrorListener() {
            @Override
            public void onError(Throwable throwable) {
                // some error occured
            }
        });
    }
}

```

Example of using banner in activity. While in activity always load banner ads `onCreate` method:

Java

```Java
public class ClassicInAppBannerActivity extends AppCompatActivity {

    InAppBannerView bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_custom_banner);
        String unitId = "419";
        bannerView = findViewById(R.id.classicInAppBanner);
        bannerView.setInAppUnitId(unitId);
        bannerView.load(() -> Toast.makeText(this, "Successfully loaded!", Toast.LENGTH_SHORT).show(), (throwable) -> {
            Toast.makeText(this, "Ad load error", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
        });
    }
}
```

Kotlin

```kotlin
class ClassicCustomBannerActivity : AppCompatActivity() {
    var bannerView: InAppBannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classic_custom_banner)
        val unitId = "419"
        bannerView = findViewById(R.id.classicInAppBanner)
        bannerView?.setInAppUnitId(unitId)
        bannerView?.load(
            { Toast.makeText(this, "Successfully loaded!", Toast.LENGTH_SHORT).show() },
            { throwable: Throwable ->
                Toast.makeText(this, "Ad load error", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
            }
        )
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
        InAppBannerView bannerView = view.findViewById(R.id.classicInAppBanner);

        bannerView.setInAppUnitId("Your inAppBanner id");
        bannerView.load(() -> {
            // banner loaded successfully
        }, throwable -> {
            // some error occured
        });
    }
}
```

Kotlin

```kotlin
class NewsFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bannerView: InAppBannerView = view.findViewById(R.id.classicInAppBanner)
        bannerView.inAppUnitId = "Your inAppBanner id"
        bannerView.load({
            // banner loaded successfully
        }) { throwable: Throwable? ->
            // some error occured
        }
    }
}
```

### Loading banner ads

```java
public class InAppBannerView {
    public void load(final SuccessListener onSuccess, final ErrorListener onError);
}
```

Banner load method takes two listeners as arguments: for successful load and callback for error
happened.

It looks like this in implementation:

```java
public class SomeActivity extends AppCompatActivity {
    public void loadBanner() {
        bannerView.load(new SuccessListener() {
            @Override
            public void onSuccess() {
                // banner loaded successfully
            }
        }, new ErrorListener() {
            @Override
            public void onError(Throwable throwable) {
                // some error occured 
            }
        });
    }
}
```

Or with lambdas:

```java
public class SomeActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some);
        InAppBannerView bannerView = view.findViewById(R.id.inAppBannerView);
        bannerView.setInAppUnitId("Your inAppBanner id");
        Button load = findViewById(R.id.load_button);
        load.setOnClickListener((view) -> loadBanner(bannerView));
    }

    public void loadBanner(InAppBannerView bannerView) {
        if (bannerView != null) {
            bannerView.load(() -> {
                // banner loaded successfully
            }, throwable -> {
                // some error occured 
            });
        }
    }
}

```

### Showing banners inside RecyclerView

1. Wrap your `RecyclerView` in the `InContentView` on your layout as below:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent" android:orientation="vertical"
    tools:context=".ui.fragments.NewsFragment">

    <com.nextmillennium.inappsdk.core.ui.InContentView android:id="@+id/inContentView"
        android:layout_width="match_parent" android:layout_height="match_parent">

        <androidx.recyclerview.widget.RecyclerView android:layout_weight="1"
            android:layout_width="match_parent" android:layout_height="0dp" />

    </com.nextmillennium.inappsdk.core.ui.InContentView>

</LinearLayout>
```

1. Set the adapter for this `RecyclerView` using this method:  `inContentView.setContent()`.
2. Set InAppBanner id using `setInAppUnitId` method.
3. After the `inContentView.setContent` method has been called, call the `load` method
   on `onViewCreated` callback.

```java
class NewsFragment extends Fragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NewsAdapter newsAdapter = new NewsAdapter();
        InContentView inContentView = view.findViewById(R.id.inContentView);

        inContentView.setInAppUnitId("Your inAppBanner id");
        inContentView.setContent(newsAdapter);

        inContentView.load(() -> {
            // banner loaded successfully
        }, throwable -> {
            // some error occured 
        });
    }

}
```

### Showing banners inside TextView

1. Wrap your `TextView` in the `InContentView` on your layout as below:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent" android:orientation="vertical"
    tools:context=".ui.fragments.NewsFragment">

    <com.nextmillennium.inappsdk.core.ui.InContentView android:id="@+id/inContentView"
        android:layout_width="match_parent" android:layout_height="match_parent">

        <TextView android:layout_weight="1" android:layout_width="match_parent"
            android:layout_height="0dp" />

    </com.nextmillennium.inappsdk.core.ui.InContentView>

</LinearLayout>
```

1. Set the text for this TextView using this method:  `inContentView.setContent()`.
2. Set InAppBanner id using `setInAppUnitId` method.
3. After the `inContentView.setContent` method has been called, call the `load` method.

```java
class NewsFragment extends Fragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InContentView inContentView = view.findViewById(R.id.inContentView);

        inContentView.setInAppUnitId("Your inAppBanner id");
        inContentView.setContent("Some text");

        inContentView.load(() -> {
            // banner loaded successfully
        }, throwable -> {
            // some error occured 
        });
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

For now, it's only available with `InAppBannerView`.

Always destroy ad views to ensure that it is removed from the layout and cleared from memory.

You can simply call them for instance of `InAppBannerView` in your own overrides of `onResume`
, `onPause` and `onDestroy`:

Java

```java

import com.nextmillennium.inappsdk.core.ui.InAppBannerView;

public class ClassicCustomBannerActivity extends AppCompatActivity {
    private InAppBannerView banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_custom_banner);
        banner = findViewById(R.id.classicInAppBanner);
        banner.setInAppUnitId("417");
        banner.load(() -> {
        }, (throwable) -> {
        });
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
        if (banner != null) {
            banner.resume();
        }
    }
}
```

Kotlin

```kotlin
class ClassicCustomBannerActivity : AppCompatActivity() {
    var classicBanner: InAppBannerView? = null

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

    <com.nextmillennium.inappsdk.core.ui.InAppBannerView android:id="@+id/classicInAppBanner"
        android:layout_width="match_parent" android:layout_height="wrap_content">

    </com.nextmillennium.inappsdk.core.ui.InAppBannerView>

</LinearLayout>
```

Java

```java
public class ClassicCustomBannerActivity extends AppCompatActivity {

    private InAppBannerView classicBanner;
    private ActivityClassicCustomBannerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClassicCustomBannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String unitId = "417";
        Button load = binding.loadBanner;
        classicBanner = binding.classicInAppBanner;
        load.setOnClickListener((view) -> loadBanner(enteredUnitId));
    }

    private void loadBanner(String unitId) {
        classicBanner.setInAppUnitId(unitId);
        classicBanner.load(() -> Snackbar.make(binding.getRoot(), "Successfully loaded unit " + unitId, Snackbar.LENGTH_SHORT).show(), (throwable) -> {
            Snackbar.make(binding.getRoot(), "Error Ad load", Snackbar.LENGTH_SHORT).show();
            Snackbar.make(binding.getRoot(), throwable.toString(), Snackbar.LENGTH_LONG).show();
            if (throwable.getMessage() != null) {
                Snackbar.make(binding.getRoot(), throwable.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
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

    private var classicBanner: InAppBannerView? = null
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
