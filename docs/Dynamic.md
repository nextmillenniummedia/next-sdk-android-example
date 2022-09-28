# Integrate Ad Formats Dynamically

In Dynamic mode, our SDK dynamically services and displays ads directly in the content of your app’s
screens, using pre-defined ad units selected from the inApp management dashboard. This means you
don’t need to update code when the ad format changes, which reduces set up time and minimizes
maintenance.

The following subsections describe how to integrate ad formats dynamically:

## Register screen names

To start serving ads using Dynamic mode, call **`NextSdk.sendScreenNames`** method in your main
application file or main activity. For example `App.java`. This will allow NMM to get a list of
activities inside of your app where you want to show ads. This method must be called whenever you
add or remove activities and fragment (In the above example, the sending occurs only on the debug
version of the application, but you may want to put it elsewhere).

Also, it is encouraged to use appropriate and clear names for Activity and Fragment classes.

```Java
import android.app.Application;

import io.nextmillennium.NextSdk.core.NextSdk;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            NextSdk.sendScreenNames(this);
        }
        NextSdk.initialize(this);
    }
}
```

Without sending screen names you won’t be able to use dynamic mode.

It is better to call it only when you added new activities/fragments or changed names of the
existing ones. It should be called once to receive application screen names. There is no need to
send screen names every time application starts.

There are next overloads of this method:

```Java
public class NextSdk {
    // ...
    public static void sendScreenNames(Context context);

    public static void sendScreenNames(Context context, List<String> screens);
}
```

In case you use code obfuscation, the send screen names method has a corresponding overload for
manually sending screen names. In this case, when inserting ads in dynamic mode, use only the names
of the screens that were specified manually. You just need to pass list of screen names.

## Show an Ad

To show ads in dynamic mode you just need to inject it to our SDK. Call injectTo method in activity
or fragment where you want to show ads. When you will decide to change the ad format or ad params
like size or position we can do it without changes in code.

### Using in Activities

Call `NextSdk.injectTo(this)` method in needed `Activity` in `OnCreate()` method:

Java

```Java
public class SomeFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NextSdk.injectTo(this, "SomeFragment", savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle state) {
        View rootView = inflater.inflate(R.layout.my_fragment, container, false);
        return NextSdk.wrapView(rootView);
    }
}
```

Kotlin

```Kotlin
class SomeFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NextSdk.injectTo(this, "SomeFragment", savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = inflater.inflate(R.id.my_fragment, container, false)
        return NextSdk.wrapView(root)
    }
}
```

`wrapView` method is needed to get view where you want to show ads.

```java
public class NextSdk {
    // ...
    public static View wrapView(View userView);
}
```

### InContent banners

#### Setting up banners inside RecyclerView

If you want to show banners inside your RecyclerView (between your items), then you need to:

1. Wrap your `RecyclerView` in the `InContentView` on your screen as below:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent" android:orientation="vertical"
    tools:context=".ui.fragments.NewsFragment">

    <io.nextmillennium.nextsdk.core.ui.InContentView android:id="@+id/inContentView"
        android:layout_width="match_parent" android:layout_height="match_parent">

        <androidx.recyclerview.widget.RecyclerView android:layout_weight="1"
            android:layout_width="match_parent" android:layout_height="0dp" />

    </io.nextmillennium.nextsdk.core.ui.InContentView>

</LinearLayout>
```

Note: In dynamic mode, there can be only one RecyclerView into which ads will be inserted.

2. Set the adapter for this `RecyclerView` using this method:  `inContentView.setContent()`.
3. After the `inContentView.setContent` method has been called, call the inject method.

In content example:

Java 

```Java
class NewsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        // Inflate the layout for this fragment
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        String[] dataset = initDataset();
        NewsAdapter adapter = new NewsAdapter(dataset);
        recyclerView.setAdapter(adapter);
        InContentView inContentView = rootView.findViewById(R.id.inContentView);
        inContentView.setContent(adapter);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NextSdk.injectTo(this, savedInstanceState);
    }

    private String[] initDataset() {
        final int DATASET_COUNT = 100;
        String[] dataset = new String[DATASET_COUNT];
        for (int i = 0; i < DATASET_COUNT; i++) {
            dataset[i] = "This is news #" + i;
        }
        return dataset;
    }

}
```

Kotlin

```Kotlin
class NewsFragment : Fragment() {
    private val DATASET_COUNT = 100

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_news, container, false)
        // Inflate the layout for this fragment
        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        val dataset = initDataset()
        val adapter = NewsAdapter(dataset)
        recyclerView.adapter = adapter
        val inContentView: InContentView = rootView.findViewById(R.id.inContentView)
        inContentView.setContent(adapter)
        return NextSdk.wrapView(rootView)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NextSdk.injectTo(this, "NewsFragment", savedInstanceState)
    }

    private fun initDataset(): Array<String?> {
        val dataset = arrayOfNulls<String>(DATASET_COUNT)
        for (i in 0 until DATASET_COUNT) {
            dataset[i] = "This is news #$i"
        }
        return dataset
    }
}
```

#### Setting up banners inside TextView

If you want to show banners inside your TextView (between paragraphs), then you need to:

1. Wrap your TextView in the InContentView on your screen as below:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent" android:orientation="vertical"
    tools:context=".ui.fragments.NewsFragment">

    <io.nextmillennium.nextsdk.core.ui.InContentView android:id="@+id/inContentView"
        android:layout_width="match_parent" android:layout_height="match_parent">

        <TextView android:layout_weight="1" android:layout_width="match_parent"
            android:layout_height="0dp" />

    </io.nextmillennium.nextsdk.core.ui.InContentView>

</LinearLayout>
```

Note: In dynamic mode, there can be only one `TextView` into which ads will be inserted.

2. Set the text for this `TextView` using this method:  `inContentView.setContent()`.
3. After the `inContentView.setContent` method has been called, call the `inject` method.

Example. How to use in content banner injection inside the TextView.

```Java
class NewsFragment extends Fragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InContentView inContentView = view.findViewById(R.id.inContentView);

        inContentView.setContent("Some text");
        NextSdk.injectTo(this, "NewsFragment", savedInstanceState);
    }

}
```

### Custom Event Listeners

You can add custom event listeners in injection mode for fullscreen banners. Just
use `NextSdk.setFullscreenListener(FullScreenListener fullscreenListener)`;

`FullScreenListener` must be one of those:

- `InterstitialListener`
- `RewardedListener`
- `RewardedInterstitialListener`

| overridable methods | description |
| --- | --- |
| `onAdImpression()` | Called when an impression is recorded for an ad. |
| `onAdClicked()` | Called when a click is recorded for an ad. |
| `onAdDismissedFullScreenContent()` | Called when the ad dismissed full screen content. |
| `onAdShowedFullScreenContent()` | Called when the ad showed the full screen content. |
| `onAdFailedToShowFullScreenContent(AdError adError)` | Called when the ad failed to show full screen content. |

For Interstitial:

| overridable methods | description |
| --- | --- |
| `onAdLoaded(Interstitial interstitialAd)`     | Called when an ad successfully loads |
| `onAdLoaded(AdManagerInterstitialAd adManagerInterstitialAd)` | Called when an ad from Google Ad Manager successfully loads |

For Rewarded:

| overridable methods | description |
| --- | --- |
| `onAdLoaded(RewardedAd rewardedAd)` | Called when an ad successfully loads |
| `onAdMetadataChanged(RewardedAd rewardedAd)` | Called when an ad's metadata is loaded for the first time or when the ad's metadata changes. |
| `onUserEarnedRewardListener(InAppReward reward)` | Called when the user earned a reward |

For Rewarded Interstitial:

| overridable methods | description |
| --- | --- |
| `onAdLoaded(RewardedInterstitialAd rewardedInterstitialAd)` | Called when an ad successfully loads |
| `onAdMetadataChanged(RewardedInterstitialAd rewardedInterstitialAd)` | Called when an ad's metadata is loaded for the first time or when the ad's metadata changes. |
| `onUserEarnedRewardListener(InAppReward reward)` | Called when the user earned a reward |

#### Interstitial

You can implement `FullScreenListener` right in activity where you want to load fullscreen ad.

#### Rewarded

Callbacks for rewarded ad:

`onAdMetadataChanged(RewardedAd rewardedAd)` — invoked when an ad's metadata is loaded for the first
time or when the ad's metadata changes.

`onUserEarnedRewardListener(InAppReward reward)` — invoked when user earned reward.

You can implement `RewardedListener` for sending reward to users.

`InAppReward` contains data about earned reward.

Public methods:

- `getAmount()` — get reward amount as integer
- `getRewardType()` — get data about reward as string

#### Rewarded Interstitial

Rewarded interstitial listeners setup is very similar to Rewarded. You just need to
implement `RewardedInterstitialListener` instead of `RewardedListener`

About callbacks for rewarded interstitial ads:

`onAdMetadataChanged(RewardedInterstitialAd rewardedAd)` — invoked when an ad's metadata is loaded
for the first time or when the ad's metadata changes.

`onUserEarnedRewardListener(InAppReward reward)` — invoked when user earned reward.