# Interstitial Ads

[Back to manual mode overview](https://github.com/nextmillenniummedia/inapp-android-example/blob/main/docs/manual/Manual.md)

To show interstitial ad in manual mode you need to create an instance of `InAppInterstitialAd` class
and then call `load` method.

The constructor takes 2 arguments:

| param | description |
| --- | --- |
| `context` | activity context |
| `inAppUnitId` | unit id in our system |

Constructor of InAppInterstitialAd

```java
public class InAppInterstitialAd {
    public InAppInterstitialAd(Context context, String inAppUnitId) {
        super(context, inAppUnitId);
    }
}
```

To load and display you just need to create new instance of `InAppInterstitialAd` class.

Use the following examples to show interstitial.

**Activity**

For example you have an activity with next layout.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent" android:orientation="vertical"
    tools:context=".classic.ClassicInterstitialActivity">
    <Button android:id="@+id/load_interstitial" android:layout_width="match_parent"
        android:layout_height="wrap_content" android:minHeight="48dp" android:text="@string/load" />
</LinearLayout>
```

Everything you need is to create a new instance of `InAppInterstitialAd` and call load.

```Java
public class ClassicInterstitialActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_interstitial);

        Button load = findViewById(R.id.loadInterstitial);
        load.setOnClickListener((view) -> {
            new InAppInterstitialAd(this, "In App Unit Id").load();
        });
    }
}
```

**Fragments**

Usage in fragments is almost similar to activities.

For example you have a fragment with next layout.

```xml

<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".fragments.classic.ClassicInterstitialFragment">
    <!--  layout  -->
</FrameLayout>
```

And some class `ClassicInterstitialFragment` for this fragment in code.

All that you need is to create new instance of `InAppInterstitialAd` after view is created. But you
need to wrap view before the load with `NextSdk.wrapView` method. We recommend to do this
in `onCreateView` method of fragment.

```Java
public class ClassicInterstitialFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_interstitial, container, false);
        return NextSdk.wrapView(root);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button load = findViewById(R.id.load);
        load.setOnClickListener((view) -> {
            new InAppInterstitialAd(requireContext(), "Unit id").load();
        });
    }
}
```

If you need to send your custom event listeners to application, you can
use `setListener(InterstitialListener listener)` method. It takes `InterstitialListener` interface
as parameter

| overridable methods | description |
| --- | --- |
| `onAdImpression()` | Called when an impression is recorded for an ad. |
| `onAdClicked()` | Called when a click is recorded for an ad. |
| `onAdDismissedFullScreenContent()` | Called when the ad dismissed full screen content. |
| `onAdShowedFullScreenContent()` | Called when the ad showed the full screen content. |
| `onAdFailedToShowFullScreenContent(AdError adError)` | Called when the ad failed to show full screen content. |
| `onLoadError(Throwable error)` | Called when an ad fails to load |
| `onAdLoaded(Interstitial interstitialAd)` | Called when an ad successfully loads |
| `onAdLoaded(AdManagerInterstitialAd adManagerInterstitialAd)` | Called when an ad from Google Ad Manager successfully loads |

```Java
public class ClassicInterstitialActivity extends AppCompatActivity implements InterstitialListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_interstitial);
        new InAppInterstitialAd(this, "In App Unit Id").setListener(this).load();
    }

    @Override
    public void onAdLoaded(InterstitialAd interstitialAd) {

    }

    @Override
    public void onAdLoaded(AdManagerInterstitialAd adManagerInterstitialAd) {

    }

    @Override
    public void onAdImpression() {

    }

    @Override
    public void onAdClicked() {

    }

    @Override
    public void onAdDismissedFullScreenContent() {

    }

    @Override
    public void onAdShowedFullScreenContent() {

    }

    @Override
    public void onAdFailedToShowFullScreenContent(AdError adError) {

    }

    @Override
    public void onLoadError(Throwable error) {

    }
}
```

It also works for fragments.

```Java
public class ClassicInterstitialFragment extends Fragment implements InterstitialListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_interstitial, container, false);
        return NextSdk.wrapView(root);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button loadInterstitial = findViewById(R.id.load);
        loadInterstitial.setOnClickListener((view) -> {
            new InAppInterstitialAd(getContext(), "Unit Id").setListener(this).load();
        });
    }

    @Override
    public void onAdLoaded(InterstitialAd interstitialAd) {
        interstitialAd.show(requireActivity());
    }

    @Override
    public void onAdLoaded(AdManagerInterstitialAd adManagerInterstitialAd) {
        adManagerInterstitialAd.show(requireActivity());
    }
}
```
