# Integrate Ad Formats Manually

**Manual mode** is a way of serving ads in your apps where the publisher manually adjusts ad placements and does all of the ad management on their own. 

Since **Manual mode** allows for more customization compared to Dynamic method, it's recommended for advanced users.

**Note**: Before continuing, ensure you have configured an InApp ad unit ID from your Next Millennium contact.

**Note:** always work with InApp ads in main thread.

The doc show how to display ads in **Manual mode**.

## Banner Ad

To show banner ads you need to add `InAppBannerView` component to your UI. Add it to your layout XML file:

```xml
<com.nextmillennium.inappsdk.core.ui.InAppBannerView
    android:id="@+id/inAppBannerView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```

Then you need to find it in your Activity or Fragment by id

```java
InAppBannerView bannerView = view.findViewById(R.id.inAppBannerView);
```

Then you need to set unit id from our system that we provide for you

```java
bannerView.setInAppUnitId("Your inAppBanner id")
```

And finally you just need to load an ad by calling load method. 

Example of using banner in activity. While in activity always load banner ads `onCreate` method:

<details>

    ```java
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
</details>
    
<details>
    
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

</details>

Example of using banner in fragment. While in fragment always load ads `onViewCreated`

<details>
    
    ```java
    public class NewsFragment extends Fragment {
    
        public NewsFragment() {
        }
    
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
    
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
</details>

<details>

    ```kotlin
    class NewsFragment : Fragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
        }
    
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
</details>

### Loading banner ads

```java
public final void load(final SuccessListener onSuccess, final ErrorListener onError)
```

Banner load method takes two listeners as arguments: for successful load and callback for error happened. 

It looks like this in implementation:

```java
bannerViewTop.load(new SuccessListener() {
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
```

Or with lamdas:

```java
bannerViewTop.load(() -> {
// banner loaded successfully
}, throwable -> {
// some error occured 
});
```

### Showing banners inside RecyclerView

1. Wrap your `RecyclerView` in the `InContentView` on your layout as below:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
	xmlns:tools="http://schemas.android.com/tools"
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  android:orientation="vertical"
  tools:context=".ui.fragments.NewsFragment">

	<com.nextmillennium.inappsdk.core.ui.InContentView
    android:id="@+id/inContentView"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

		<androidx.recyclerview.widget.RecyclerView
			android:layout_weight="1"
			android:layout_width="match_parent"
			android:layout_height="0dp" />

	</com.nextmillennium.inappsdk.core.ui.InContentView>

</LinearLayout>
```

1. Set the adapter for this `RecyclerView` using this  method:  `inContentView.setContent()`.
2. Set InAppBanner id using `setInAppUnitId` method.
3. After the `inContentView.setContent` method has been called, call the `load` method on `onViewCreated` callback.

```java
class NewsFragment extends Fragment {

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
    NewsAdapter newsAdapter = new NewsAdapter();
		InContentView inContentView = view.findViewById(R.id.inContentView);

		inContentView.setInAppUnitId("Your inAppBanner id")
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
	xmlns:tools="http://schemas.android.com/tools"
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  android:orientation="vertical"
  tools:context=".ui.fragments.NewsFragment">

	<com.nextmillennium.inappsdk.core.ui.InContentView
    android:id="@+id/inContentView"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

		<TextView
			android:layout_weight="1"
			android:layout_width="match_parent"
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

		inContentView.setInAppUnitId("Your inAppBanner id")
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

We have 3 methods for managing banner lifecycle: `resume`,`pause` and `destroy`. More info about activity lifecycle callbacks: [resume](https://developer.android.com/guide/components/activities/activity-lifecycle#onresume), [pause](https://developer.android.com/guide/components/activities/activity-lifecycle#onpause) and [destroy](https://developer.android.com/guide/components/activities/activity-lifecycle#ondestroy).

For now, it's only available with `InAppBannerView`.

Always destroy ad views to ensure that it is removed from the layout and cleared from memory.

You can simply call them for instance of `InAppBannerView` in your own overrides of `onResume`, `onPause` and `onDestroy`:

<details>
    
        ```java
    	private InAppBannerView banner;
    
    	@Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
    				setContentView(R.layout.activity_classic_custom_banner);
    				banner = findViewById(R.id.classicInAppBanner);
    		}
    
    	@Override
        protected void onPause() {
            banner.pause();
            super.onPause();
        }
    
        @Override
        protected void onDestroy() {
            banner.destroy();
            super.onDestroy();
        }
    
        @Override
        protected void onResume() {
            super.onResume();
            banner.resume();
        }
        ```
</details>
    
<details>
    
    ```kotlin
    var classicBanner: InAppBannerView? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classic_custom_banner)
        banner = findViewById(R.id.classicInAppBanner)
    }
    
    override fun onPause() {
        classicBanner!!.pause()
        super.onPause()
    }
    
    override fun onDestroy() {
        classicBanner!!.destroy()
        super.onDestroy()
    }
    
    override fun onResume() {
        super.onResume()
        classicBanner!!.resume()
    }
    ```
</details>

More meaningful example: 

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".classic.ClassicCustomBannerActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:minHeight="48dp"
        android:orientation="horizontal">

        <TextView
            android:id="@+id/textView4"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/enter_id" />

        <EditText
            android:id="@+id/classicBannerUnitId"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:autofillHints="name"
            android:ems="10"
            android:hint="@string/enter_id"
            android:inputType="number"
            android:minHeight="48dp" />
    </LinearLayout>

    <Button
        android:id="@+id/loadBanner"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@string/load" />

    <com.nextmillennium.inappsdk.core.ui.InAppBannerView
        android:id="@+id/classicInAppBanner"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

    </com.nextmillennium.inappsdk.core.ui.InAppBannerView>

</LinearLayout>
```

<details>

    ```java
    public class ClassicCustomBannerActivity extends AppCompatActivity {
    
        InAppBannerView classicBanner;
        ActivityClassicCustomBannerBinding binding;
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityClassicCustomBannerBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
    
            String unitId = "417";
            Button load = binding.loadBanner;
            classicBanner = binding.classicInAppBanner;
    				loadBanner(unitId);
    
            load.setOnClickListener((view) -> {
                loadBanner(enteredUnitId);
            });
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
            classicBanner.pause();
            super.onPause();
        }
    
        @Override
        protected void onDestroy() {
            classicBanner.destroy();
            super.onDestroy();
        }
    
        @Override
        protected void onResume() {
            super.onResume();
            classicBanner.resume();
        }
    }
    ```
</details>
    
<details>

```Kotlin

class ClassicCustomBannerActivity : AppCompatActivity() {

    var classicBanner: InAppBannerView? = null
    var binding: ActivityClassicCustomBannerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassicCustomBannerBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val unitId = "417"
        val load = binding!!.loadBanner
        classicBanner = binding!!.classicInAppBanner
        loadBanner(unitId)
        load.setOnClickListener { loadBanner(unitId) }
    }

    private fun loadBanner(unitId: String?) {
        classicBanner!!.inAppUnitId = unitId
        classicBanner!!.load({
            Snackbar.make(binding!!.root, "Successfully loaded unit $unitId", Snackbar.LENGTH_SHORT)
                .show()
        }) { throwable: Throwable ->
            Snackbar.make(binding!!.root, "Error Ad load", Snackbar.LENGTH_SHORT).show()
            Snackbar.make(binding!!.root, throwable.toString(), Snackbar.LENGTH_LONG).show()
            if (throwable.message != null) {
                Snackbar.make(binding!!.root, throwable.message!!, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onPause() {
        classicBanner!!.pause()
        super.onPause()
    }

    override fun onDestroy() {
        classicBanner!!.destroy()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        classicBanner!!.resume()
    }
}
```
</details>

## Interstitial Ad

To show interstitial ad in manual mode you need to create an instance of `InAppInterstitialAd` class and then call `load` method.

The constructor takes 2 arguments:

| param | description |
| --- | --- |
| `context` | activity context |
| `inAppUnitId` | unit id in our system |

Constructor of InAppInterstitialAd

```
public InAppInterstitialAd(Context context, String inAppUnitId) {
    super(context, inAppUnitId);
}
```

To load and display you just need to create new instance of `InAppInterstitialAd` class.

Use the following examples to show interstitial.

**Activity**

For example you have an activity with next layout.

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".classic.ClassicInterstitialActivity">
</LinearLayout>
```

Everything you need is to create a new instance of `InAppInterstitialAd` and call load.

```
public class ClassicInterstitialActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_interstitial);
        new InAppInterstitialAd(this, "In App Unit Id").load();
    }

}
```

**Fragments**

Usage in fragments is almost similar to activities. 

For example you have a fragment with next layout.

```
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".fragments.classic.ClassicInterstitialFragment">
</FrameLayout>
```

And some class `ClassicInterstitialFragment` for this fragment in code. 

All that you need is to create new instance of `InAppInterstitialAd` after view is created.  But you need to wrap view before the load with `InAppSdk.wrapView` method. We recommend to do this in `onCreateView` method of fragment.

```
public class ClassicInterstitialFragment extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_interstitial, container, false);
        return InAppSdk.wrapView(root);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new InAppInterstitialAd(getContext(), "Unit id").load();
    }
}
```

If you need to send your custom event listeners to application, you can use `setListener(InterstitialListener listener)` method. It takes `InterstitialListener` interface as parameter

You can override all needed events from Google’s [FullScreenContentCallback](https://developers.google.com/android/reference/com/google/android/gms/ads/FullScreenContentCallback)

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

```
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

```
public class ClassicInterstitialFragment extends Fragment implements InterstitialListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_interstitial, container, false);
        return InAppSdk.wrapView(root);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new InAppInterstitialAd(getContext(), "Unit Id").setListener(this).load();
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

## Rewarded Ads

To show rewarded ad in manual mode you need to create an instance of `InAppRewardedAd` class and then call `load` method.

The constructor takes 2 arguments:

| param | description |
| --- | --- |
| `context` | activity context |
| `inAppUnitId` | unit id in our system |

```
public InAppRewardedAd(Context context, String inAppUnitId) {
    super(context, inAppUnitId);
}
```

To load and display you just need to create new instance of `InAppRewarded` class. 

**Activity**

Let’s create new empty activity and add text view to look at the earned reward amount.

Look at the example below:

Creation of a rewarded ad:

```
public class ClassicRewardedActivity extends AppCompatActivity implements RewardedListener {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_rewarded);
        textView = findViewById(R.id.rewardTextViewClassic);
        String unitId = "Your unit id";
        new InAppRewardedAd(this, unitId).load();
    }

    @Override
    public void onUserEarnedRewardListener(InAppReward reward) {
        textView.setText(getString(R.string.reward, reward.getAmount(), reward.getRewardType()));
    }
}
```

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/rewardedLayoutClassic"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".classic.ClassicRewardedActivity">

    <TextView
        android:id="@+id/rewardTextViewClassic"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/reward" />

</LinearLayout>
```

**Fragments**

Usage in fragments is almost similar to activities. 

For example you have a fragment with next layout.

```
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".fragments.classic.ClassicRewardedFragment">
</FrameLayout>
```

And some class `ClassicRewardedFragment` for this fragment in code. 

All that you need is to create new instance of `InAppRewardedAd` after view is created.  But you need to wrap view before the load with `InAppSdk.wrapView` method. We recommend to do this in `onCreateView` method of fragment.

```
public class ClassicRewardedFragment extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_rewarded, container, false);
        return InAppSdk.wrapView(root);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new InAppRewardedAd(getContext(), "Unit id").load();
    }
}
```

You can add custom event listeners for fullscreen banners. Just use `setListener` method for this purpose. It takes `RewardedListener` interface as parameter. You are free to override all needed events from Google’s [FullScreenContentCallback](https://developers.google.com/android/reference/com/google/android/gms/ads/FullScreenContentCallback)

| overridable methods | description |
| --- | --- |
| `onAdImpression()` | Called when an impression is recorded for an ad. |
| `onAdClicked()` | Called when a click is recorded for an ad. |
| `onAdDismissedFullScreenContent()` | Called when the ad dismissed full screen content. |
| `onAdShowedFullScreenContent()` | Called when the ad showed the full screen content. |
| `onAdFailedToShowFullScreenContent(AdError adError)` | Called when the ad failed to show full screen content. |
| `onLoadError(Throwable error)` | Called when an ad fails to load |
| `onAdLoaded(RewardedAd rewardedAd)` | Called when an ad successfully loads |
| `onAdMetadataChanged(RewardedAd rewardedAd)` | Called when an ad's metadata is loaded for the first time or when the ad's metadata changes. |
| `onUserEarnedRewardListener(InAppReward reward)` | Called when the user earned a reward |

Here you can find an example of loading ad with custom listener.

```
public class ClassicRewardedActivity extends AppCompatActivity implements RewardedListener {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_rewarded);
        textView = findViewById(R.id.rewardTextViewClassic);
        String unitId = "Your unit id";
        new InAppRewardedAd(this, unitId).setListener(this).load();
    }

    @Override
    public void onUserEarnedRewardListener(InAppReward reward) {
        textView.setText(getString(R.string.reward, reward.getAmount(), reward.getRewardType()));
    }
}
```

## Rewarded Interstitial Ads


To show rewarded interstitial ad in manual mode you need to create an instance of `InAppRewardedInterstitialAd` class and then call `load` method.

The constructor takes 2 arguments: 

| param | description |
| --- | --- |
| `context` | activity context |
| `inAppUnitId` | unit id in our system |

```java
public InAppRewardedInterstitialAd(Context context, String inAppUnitId) {
    super(context, inAppUnitId);
}
```

To load and display you just need to create new instance of `InAppRewardedInterstitialAd` class. 

**Activity**

Let’s create new empty activity and add text view to look at the earned reward amount.

Look at the example below: 

```java
public class ClassicRewardedInterstitialActivity extends AppCompatActivity implements RewardedInterstitialListener {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_classic_rewarded_interstitial);
        textView = findViewById(R.id.rewardInterstitialTextViewClassic);
        String unitId = "Your unit id";
        new InAppRewardedInterstitialAd(this, unitId).load();
    }

		@Override
    public void onUserEarnedRewardListener(InAppReward reward) {
        textView.setText(getString(R.string.reward, reward.getAmount(), reward.getRewardType()));
    }
}

```

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/rewardedInterstitialLayoutClassic"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".classic.ClassicRewardedInterstitialActivity">

    <TextView
        android:id="@+id/rewardInterstitialTextViewClassic"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/reward" />
</LinearLayout>
```

**Fragments**

Usage in fragments is almost similar to activities. 

For example you have a fragment with next layout.  

And some class `ClassicRewardedInterstitialFragment` for this fragment in code. 

All that you need is to create new instance of `InAppRewardedAd` after view is created.  But you need to wrap view before the load with `InAppSdk.wrapView` method. We recommend to do this in `onCreateView` method of fragment.

```java

public class ClassicRewardedInterstitialFragment extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_rewarded_interstitial, container, false);
        return InAppSdk.wrapView(root);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new InAppRewardedAd(getContext(), "Unit id").load();
    }
}
```

You can add custom event listeners for fullscreen banners. Just use `setListener` method for this purpose. It takes `RewardedInterstitialListener` interface as parameter. You are free to override all needed events from Google’s [FullScreenContentCallback](https://developers.google.com/android/reference/com/google/android/gms/ads/FullScreenContentCallback)

| overridable methods | description |
| --- | --- |
| `onAdImpression()` | Called when an impression is recorded for an ad. |
| `onAdClicked()` | Called when a click is recorded for an ad. |
| `onAdDismissedFullScreenContent()` | Called when the ad dismissed full screen content. |
| `onAdShowedFullScreenContent()` | Called when the ad showed the full screen content. |
| `onAdFailedToShowFullScreenContent(AdError adError)` | Called when the ad failed to show full screen content. |
| `onLoadError(Throwable error)` | Called when an ad fails to load |
| `onAdLoaded(RewardedInterstitialAd rewardedInterstitialAd)` | Called when an ad successfully loads |
| `onAdMetadataChanged(RewardedInterstitialAd rewardedInterstitialAd)` | Called when an ad's metadata is loaded for the first time or when the ad's metadata changes. |
| `onUserEarnedRewardListener(InAppReward reward)` | Called when the user earned a reward |

Here you can find an example of loading ad with custom listener.

```java
public class ClassicRewardedInterstitialActivity extends AppCompatActivity implements RewardedInterstitialListener {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_rewarded_interstitial);
        textView = findViewById(R.id.rewardInterstitialTextViewClassic);
        String unitId = "unit id";
        new InAppRewardedInterstitialAd(this, unitId).setListener(this).load();
    }

    @Override
    public void onUserEarnedRewardListener(InAppReward reward) {
        textView.setText(getString(R.string.reward, reward.getAmount(), reward.getRewardType()));
    }
}
```