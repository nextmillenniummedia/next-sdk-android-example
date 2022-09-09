# Rewarded Ads

[Back to manual mode overview](https://github.com/nextmillenniummedia/inapp-android-example/blob/main/docs/manual/Manual.md)

To show rewarded ad in manual mode you need to create an instance of `InAppRewardedAd` class and
then call `load` method.

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

```Java
public class ClassicRewardedActivity extends AppCompatActivity implements RewardedListener {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_rewarded);
        textView = findViewById(R.id.rewardTextViewClassic);
        Button button = findViewById(R.id.load_rewarded);
        load.setOnClickListener((view) -> {
            String unitId = "Your unit id";
            new InAppRewardedAd(this, unitId).load();
        });
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
    xmlns:tools="http://schemas.android.com/tools" android:id="@+id/rewardedLayoutClassic"
    android:layout_width="match_parent" android:layout_height="match_parent"
    android:orientation="vertical" tools:context=".classic.ClassicRewardedActivity">
    <Button android:id="@+id/load_rewarded" android:layout_width="match_parent"
        android:layout_height="wrap_content" android:minHeight="48dp"
        android:text="@string/load" />
    <TextView android:id="@+id/rewardTextViewClassic" android:layout_width="wrap_content"
        android:layout_height="wrap_content" android:text="@string/reward" />

</LinearLayout>
```

**Fragments**

Usage in fragments is almost similar to activities.

For example you have a fragment with next layout.

```xml

<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".fragments.classic.ClassicRewardedFragment">
    <Button
        android:id="@+id/load_rewarded"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:minHeight="@dimen/minSize"
        android:text="@string/load" />
</FrameLayout>
```

And some class `ClassicRewardedFragment` for this fragment in code.

All that you need is to create new instance of `InAppRewardedAd` after view is created. But you need
to wrap view before the load with `NextSdk.wrapView` method. We recommend to do this
in `onCreateView` method of fragment.

```Java
public class ClassicRewardedFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_rewarded, container, false);
        return NextSdk.wrapView(root);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = findViewById(R.id.load_rewarded);
        load.setOnClickListener((view) -> {
            String unitId = "Your unit id";
            new InAppRewardedAd(this, unitId).load();
        });
    }
}
```

You can add custom event listeners for fullscreen banners. Just use `setListener` method for this
purpose. It takes `RewardedListener` interface as parameter.

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

```Java
public class ClassicRewardedActivity extends AppCompatActivity implements RewardedListener {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_rewarded);
        textView = findViewById(R.id.rewardTextViewClassic);
        Button button = findViewById(R.id.load_rewarded);
        load.setOnClickListener((view) -> {
            String unitId = "Your unit id";
            new InAppRewardedAd(this, unitId).setListener(this).load();
        });
    }

    @Override
    public void onUserEarnedRewardListener(InAppReward reward) {
        textView.setText(getString(R.string.reward, reward.getAmount(), reward.getRewardType()));
    }
}
```