# Rewarded Interstitial ads

[Back to manual mode overview](https://github.com/nextmillenniummedia/next-sdk-android-example/blob/2.x/docs/manual/Manual.md)

To show rewarded interstitial ad in manual mode you need to create an instance
of `RewardedInterstitialAdProvider`
with passed activity context and unit id:

```kotlin
val provider = RewardedInterstitialAdProvider(context, "108")
```

| param | description |
| --- | --- |
| `context` | activity context |
| `unitId` | unit id in our system |

Simple rewarded interstitial ad load will be look like:

<details>
<summary>Java</summary>

```java
public class RewardedInterstitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_interstitial);
        RewardedInterstitialAdProvider provider = new RewardedInterstitialAdProvider(this, "108");
        provider.load();
    }
}
```

</details>
<details>
<summary>Kotlin</summary>

```kotlin
class RewardedInterstitialActivityKt : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewarded_interstitial_kt)
        val provider = RewardedInterstitialAdProvider(this, "108")
        provider.load()
    }
}
```

</details>

You can also pass unit id with `setUnitId (String)`:

<details>
<summary>Java</summary>

```java
public class RewardedInterstitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_interstitial);
        RewardedInterstitialAdProvider provider = new RewardedInterstitialAdProvider(this);
        provider.setUnitId("108");
        provider.load();
    }
}
```

</details>
<details>
<summary>Kotlin</summary>

```kotlin
class RewardedInterstitialActivityKt : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewarded_interstitial_kt)
        val provider = RewardedInterstitialAdProvider(this)
        provider.unitId = "108"
        provider.load()
    }
}
```

</details>

### Customize ad lifecycle events

Use `RewardedInterstitialAdListener` to provide your customized behavior for successful and failure
banner loads. You can pass it by `setListener(RewardedInterstitialAdListener)` method
in `RewardedInterstitialAdProvider`.

| overridable methods | description |
| --- | --- |
| `onAdLoaded(NextRewardedInterstitialAd)` | Called when an ad successfully loads |
| `onAdImpression` | Called when an impression is recorded for an ad. |
| `onAdClicked` | Called when a click is recorded for an ad. |
| `onFullScreenAdDeclined` | Called when the ad dismissed full screen content. |
| `onFullScreenAdShowed` | Called when the ad showed the full screen content. |
| `onFullScreenAdLoadFail(NextAdError)` | Called when the ad failed to show or load full screen content. |
| `onError(Throwable)` | Called when an unexpected error occurred while handling fullscreen ad |

Prefer preload rewarded interstitial ads and show it with UI interacting actions like clicking on
button. Use `onAdLoaded` callback in `RewardedInterstitialAdListener` for this. You will
receive `RewardedInterstitialAdListener` as callback param.

Usage examples of `RewardedInterstitialAdListener` will be given further in the article.

Use the following examples to show rewarded ad.

**Activity**

Let’s create new empty activity and add text view to look at the earned reward amount.

Look at the example below:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent" android:orientation="vertical"
    tools:context=".ui.activities.RewardedInterstitialActivity">

    <Button android:id="@+id/action_rewarded_interstitial_activity"
        android:layout_width="match_parent" android:layout_height="wrap_content"
        android:layout_margin="8dp" android:text="Action" />

    <LinearLayout android:layout_width="match_parent" android:layout_height="match_parent"
        android:layout_margin="8dp">

        <TextView android:id="@+id/title_ri_activity" android:layout_width="wrap_content"
            android:layout_height="wrap_content" android:layout_weight="1"
            android:text="Reward: " />

        <TextView android:id="@+id/reward_ri_activity" android:layout_width="wrap_content"
            android:layout_height="wrap_content" android:layout_weight="1" android:text="" />

    </LinearLayout>

</LinearLayout>
```

Also add string for reward data:

```xml

<string name="reward_data">Amount: %d Type: %s</string>
```

<details>
<summary>Java</summary>

```java
public class RewardedInterstitialActivity extends AppCompatActivity implements RewardedInterstitialAdListener {

    @Nullable
    private NextRewardedInterstitialAd rewardedInterstitialAd;

    private TextView rewardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_interstitial);
        rewardView = findViewById(R.id.title_ri_activity);
        Button action = findViewById(R.id.action_rewarded_interstitial_activity);
        RewardedInterstitialAdProvider provider = new RewardedInterstitialAdProvider(this, "108");
        provider.setListener(this);
        provider.load();
        action.setOnClickListener((v) -> {
            // some action ...
            
            if (rewardedInterstitialAd != null) {
                rewardedInterstitialAd.show(RewardedInterstitialActivity.this);
            }
        });
    }

    @Override
    public void onAdLoaded(NextRewardedInterstitialAd nextRewardedInterstitialAd) {
        rewardedInterstitialAd = nextRewardedInterstitialAd;
    }

    @Override
    public void onUserEarnedRewardListener(InAppReward inAppReward) {
        rewardView.setText(getString(R.string.reward_data, inAppReward.getAmount(), inAppReward.getRewardType()));
    }
}
```

</details>

<details>
<summary>Kotlin</summary>

```kotlin
class RewardedInterstitialActivityKt : AppCompatActivity(), RewardedInterstitialAdListener {

    private var rewardedInterstitialAd: NextRewardedInterstitialAd? = null
    private lateinit var rewardView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewarded_interstitial_kt)
        rewardView = findViewById(R.id.title_ri_activity_kt)
        val action: Button = findViewById(R.id.action_rewarded_activity_kt)
        RewardedInterstitialAdProvider(this, "108").setListener(this).load()
        action.setOnClickListener {
            // some action ...
            rewardedInterstitialAd?.show(this@RewardedInterstitialActivityKt)
        }
    }

    override fun onAdLoaded(nextRewardedInterstitialAd: NextRewardedInterstitialAd?) {
        rewardedInterstitialAd = nextRewardedInterstitialAd

    }

    override fun onUserEarnedRewardListener(reward: InAppReward?) {
        rewardView.text = getString(R.string.reward_data, reward?.amount, reward?.rewardType)
    }
}
```

</details>

Prefer to pass current activity to show method. If it is impossible use `show` method without
params. Next SDK will find current activity.

**Fragments**

For example you have a fragment with next layout.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent" android:orientation="vertical"
    tools:context=".ui.fragments.RewardedInterstitialFragment">

    <Button android:id="@+id/action_rewarded_interstitial_fragment"
        android:layout_width="match_parent" android:layout_height="wrap_content"
        android:layout_margin="8dp" android:text="Action" />

    <LinearLayout android:layout_width="match_parent" android:layout_height="match_parent"
        android:layout_margin="8dp">

        <TextView android:id="@+id/title_ri_fragment" android:layout_width="wrap_content"
            android:layout_height="wrap_content" android:layout_weight="1"
            android:text="Reward: " />

        <TextView android:id="@+id/reward_ri_fragment" android:layout_width="wrap_content"
            android:layout_height="wrap_content" android:layout_weight="1" android:text="" />

    </LinearLayout>


</LinearLayout>
```

And some class `RewardedInterstitialFragment` for this fragment in code.

All that you need is to create new instance of `RewardedAdProvider` after view is created and
call `load`.

<details>
<summary>Java</summary>

```java
public class RewardedInterstitialFragment extends Fragment implements RewardedInterstitialAdListener {

    @Nullable
    private NextRewardedInterstitialAd rewardedInterstitialAd;

    @Nullable
    private FragmentRewardedInterstitialBinding binding;

    private TextView rewardView;

    public RewardedInterstitialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRewardedInterstitialBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new RewardedInterstitialAdProvider(requireActivity(), "108").setListener(this).load();
        if (binding == null) return;
        rewardView = binding.rewardRiFragment;
        Button action = binding.actionRewardedInterstitialFragment;
        action.setOnClickListener((v) -> {
            // some action
            if (rewardedInterstitialAd != null) {
                rewardedInterstitialAd.show(requireActivity());
            }
        });
    }

    @Override
    public void onAdLoaded(NextRewardedInterstitialAd nextRewardedInterstitialAd) {
        rewardedInterstitialAd = nextRewardedInterstitialAd;
    }

    @Override
    public void onUserEarnedRewardListener(InAppReward inAppReward) {
        rewardView.setText(getString(R.string.reward_data, inAppReward.getAmount(), inAppReward.getRewardType()));
    }
}
```

</details>

<details>
<summary>Kotlin</summary>

```kotlin

class RewardedInterstitialFragmentKt : Fragment(), RewardedInterstitialAdListener {

    private var rewardedInterstitialAd: NextRewardedInterstitialAd? = null
    private lateinit var rewardView: TextView
    private var binding: FragmentRewardedInterstitialKtBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRewardedInterstitialKtBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RewardedInterstitialAdProvider(requireActivity(), "108").setListener(this).load()
        val action = binding?.actionRewardedInterstitialFragment
        action?.setOnClickListener {
            // some action ...
            rewardedInterstitialAd?.show(requireActivity())
        }
    }

    override fun onAdLoaded(nextRewardedInterstitialAd: NextRewardedInterstitialAd?) {
        rewardedInterstitialAd = nextRewardedInterstitialAd
    }

    override fun onUserEarnedRewardListener(reward: InAppReward?) {
        rewardView.text = getString(R.string.reward_data, reward?.amount, reward?.rewardType)
    }

}

```
</details>

Usage of all listener events

<details>

```java
public class RewardedInterstitialFragment extends Fragment implements RewardedInterstitialAdListener {

    @Nullable
    private NextRewardedInterstitialAd rewardedInterstitialAd;

    @Nullable
    private FragmentRewardedInterstitialBinding binding;

    private TextView rewardView;

    public RewardedInterstitialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRewardedInterstitialBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new RewardedInterstitialAdProvider(requireActivity(), "108").setListener(this).load();
        if (binding == null) return;
        rewardView = binding.rewardRiFragment;
        Button action = binding.actionRewardedInterstitialFragment;
        action.setOnClickListener((v) -> {
            // some action
            if (rewardedInterstitialAd != null) {
                rewardedInterstitialAd.show(requireActivity());
            }
        });
    }

    @Override
    public void onAdLoaded(NextRewardedInterstitialAd nextRewardedInterstitialAd) {
        rewardedInterstitialAd = nextRewardedInterstitialAd;
    }

    @Override
    public void onUserEarnedRewardListener(InAppReward inAppReward) {
        rewardView.setText(getString(R.string.reward_data, inAppReward.getAmount(), inAppReward.getRewardType()));
    }

    @Override
    public void onAdImpression() {
    }

    @Override
    public void onAdClicked() {
    }

    @Override
    public void onFullScreenAdDeclined() {
    }

    @Override
    public void onFullScreenAdShowed() {
    }

    @Override
    public void onFullScreenAdLoadFail(NextAdError loadError) {
        Toast.makeText(requireActivity(), loadError.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable error) {
        Toast.makeText(requireActivity(), error.toString(), Toast.LENGTH_SHORT).show();
    }
}
```
</details>

<details>
<summary>Kotlin</summary>

```kotlin
class RewardedInterstitialFragmentKt : Fragment(), RewardedInterstitialAdListener {

    private var rewardedInterstitialAd: NextRewardedInterstitialAd? = null
    private lateinit var rewardView: TextView
    private var binding: FragmentRewardedInterstitialKtBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRewardedInterstitialKtBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RewardedInterstitialAdProvider(requireActivity(), "108").setListener(this).load()
        val action = binding?.actionRewardedInterstitialFragment
        action?.setOnClickListener {
            // some action ...
            rewardedInterstitialAd?.show(requireActivity())
        }
    }

    override fun onAdLoaded(nextRewardedInterstitialAd: NextRewardedInterstitialAd?) {
        rewardedInterstitialAd = nextRewardedInterstitialAd
    }

    override fun onUserEarnedRewardListener(reward: InAppReward?) {
        rewardView.text = getString(R.string.reward_data, reward?.amount, reward?.rewardType)
    }

    override fun onAdImpression() {
    }

    override fun onAdClicked() {
    }

    override fun onFullScreenAdDeclined() {
    }

    override fun onFullScreenAdShowed() {
    }

    override fun onFullScreenAdLoadFail(loadError: NextAdError?) {
        Toast.makeText(requireActivity(), loadError.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onError(error: Throwable?) {
        Toast.makeText(requireActivity(), error.toString(), Toast.LENGTH_SHORT).show()
    }
}
```
</details>