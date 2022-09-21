# Rewarded Ads

[Back to manual mode overview](https://github.com/nextmillenniummedia/next-sdk-android-example/blob/2.x/docs/Manual.md)

To show rewarded ad in manual mode you need to create an instance of `RewardedAdProvider`
with passed activity context and unit id:

```kotlin
val provider = RewardedAdProvider(context, "107")
```

| param | description |
| --- | --- |
| `context` | activity context |
| `unitId` | unit id in our system |

Simple rewarded ad load will be look like:

<details>
<summary>Java</summary>

```java
public class RewardedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded);
        RewardedAdProvider provider = new RewardedAdProvider(this, "107");
        provider.load();
    }
}
```

</details>
<details>
<summary>Kotlin</summary>

```kotlin
class RewardedActivityKt : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewarded_kt)
        val provider = RewardedAdProvider(this, "107")
        provider.load()
    }
}
```

</details>

You can also pass unit id with `setUnitId (String)`:

<details>
<summary>Java</summary>

```java
public class RewardedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded);
        RewardedAdProvider provider = new RewardedAdProvider(this);
        provider.setUnitId("107");
        provider.load();
    }
}
```

</details>
<details>
<summary>Kotlin</summary>

```kotlin
class RewardedActivityKt : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewarded_kt)
        val provider = RewardedAdProvider(this)
        provider.unitId = "107"
        provider.load()
    }
}
```

</details>

### Customize ad lifecycle events

Use `RewardedAdListener` to provide your customized behavior for successful and failure banner
loads. You can pass it by `setListener(RewardedAdListener)` method in `RewardedAdProvider`.

| overridable methods | description |
| --- | --- |
| `onAdLoaded(NextRewardedAd)` | Called when an ad successfully loads |
| `onAdImpression` | Called when an impression is recorded for an ad. |
| `onAdClicked` | Called when a click is recorded for an ad. |
| `onFullScreenAdDeclined` | Called when the ad dismissed full screen content. |
| `onFullScreenAdShowed` | Called when the ad showed the full screen content. |
| `onFullScreenAdLoadFail(NextAdError)` | Called when the ad failed to show or load full screen content. |
| `onError(Throwable)` | Called when an unexpected error occured while handling fullscreen ad |

Prefer preload rewarded ads and show it with UI interacting actions like clicking on button.
Use `onAdLoaded` callback in `RewardedAdListener` for this. You will receive `NextRewardedAd` as
callback param.

Usage examples of `RewardedAdListener` will be given further in the article.

Use the following examples to show rewarded ad.

**Activity**

Let’s create new empty activity and add text view to look at the earned reward amount.

Look at the example below:

```xml

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent" android:orientation="vertical"
    tools:context=".ui.activities.RewardedActivity">

    <Button android:id="@+id/action_rewarded_activity" android:layout_width="match_parent"
        android:layout_height="wrap_content" android:layout_margin="8dp"
        android:text="@string/action" />

    <LinearLayout android:layout_width="match_parent" android:layout_height="match_parent"
        android:layout_margin="8dp">

        <TextView android:id="@+id/title_activity" android:layout_width="wrap_content"
            android:layout_height="wrap_content" android:layout_weight="1"
            android:text="@string/reward" />

        <TextView android:id="@+id/reward_activity" android:layout_width="wrap_content"
            android:layout_height="wrap_content" android:layout_weight="1" android:text="" />

    </LinearLayout>

</LinearLayout>
```

<details>
<summary>Java</summary>

```Java
public class RewardedActivity extends AppCompatActivity implements RewardedAdListener {

    @Nullable
    private NextRewardedAd rewardedAd;

    private TextView rewardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded);
        rewardView = findViewById(R.id.title_activity);
        Button action = findViewById(R.id.action_rewarded_activity);
        RewardedAdProvider provider = new RewardedAdProvider(this, "107");
        provider.setListener(this);
        provider.load();
        action.setOnClickListener((v) -> {
            if (rewardedAd != null) {
                rewardedAd.show(RewardedActivity.this);
            }
        });
    }

    @Override
    public void onAdLoaded(NextRewardedAd nextRewardedAd) {
        rewardedAd = nextRewardedAd;
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
class RewardedActivityKt : AppCompatActivity(), RewardedAdListener {

    private var rewardedAd: NextRewardedAd? = null
    private lateinit var rewardView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewarded_kt)
        rewardView = findViewById(R.id.reward_activity_kt)
        val provider = RewardedAdProvider(this, "107")
        provider.setListener(this)
        provider.load()
        val action: Button = findViewById(R.id.action_rewarded_activity_kt)
        action.setOnClickListener {
            rewardedAd?.show(this@RewardedActivityKt)
        }
    }

    override fun onAdLoaded(nextRewardedAd: NextRewardedAd?) {
        rewardedAd = nextRewardedAd
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
    tools:context=".ui.fragments.RewardedFragment">

    <Button android:id="@+id/action_rewarded_fragment" android:layout_width="match_parent"
        android:layout_height="wrap_content" android:text="@string/action" />

    <LinearLayout android:layout_width="match_parent" android:layout_height="match_parent"
        android:orientation="horizontal">

        <TextView android:id="@+id/title_fragment" android:layout_width="wrap_content"
            android:layout_height="wrap_content" android:layout_weight="1"
            android:text="@string/reward" />

        <TextView android:id="@+id/reward_fragment" android:layout_width="wrap_content"
            android:layout_height="wrap_content" android:layout_weight="1" android:text="" />

    </LinearLayout>

</LinearLayout>
```

And some class `RewardedFragment` for this fragment in code.

All that you need is to create new instance of `RewardedAdProvider` after view is created and
call `load`.

<details>
<summary>Java</summary>

```java
public class RewardedFragment extends Fragment implements RewardedAdListener {

    @Nullable
    private NextRewardedAd rewardedAd;
    @Nullable
    private FragmentRewardedBinding binding;

    private TextView rewardView;

    public RewardedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRewardedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new RewardedAdProvider(requireActivity(), "107").load();
        if (binding == null) return;
        rewardView = binding.rewardFragment;
        Button action = binding.actionRewardedFragment;
        action.setOnClickListener((v) -> {
            // some action
            if (rewardedAd != null) {
                rewardedAd.show(requireActivity());
            }
        });
    }

    @Override
    public void onAdLoaded(NextRewardedAd nextRewardedAd) {
        rewardedAd = nextRewardedAd;
    }

    @Override
    public void onUserEarnedRewardListener(InAppReward inAppReward) {
        rewardView.setText(getString(R.string.reward_data, inAppReward.getAmount(), inAppReward.getRewardType()));
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
class RewardedFragmentKt : Fragment(), RewardedAdListener {

    private var binding: FragmentRewardedKtBinding? = null
    private var rewardedAd: NextRewardedAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRewardedKtBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val provider = RewardedAdProvider(requireActivity(), "107")
        provider.setListener(this)
        provider.load()
        val action = binding?.actionRewardedFragmentKt
        action?.setOnClickListener {
            rewardedAd?.show(requireActivity())
        }
    }

    override fun onAdLoaded(nextRewardedAd: NextRewardedAd?) {
        rewardedAd = nextRewardedAd
    }

    override fun onUserEarnedRewardListener(reward: InAppReward?) {
        val rewardView = binding?.rewardFragment
        rewardView?.text = getString(R.string.reward_data, reward?.amount, reward?.rewardType)
    }
}
```

</details>

Usage of all listener events

<details>
<summary>Java</summary>

```java
public class RewardedFragment extends Fragment implements RewardedAdListener {

    @Nullable
    private NextRewardedAd rewardedAd;
    @Nullable
    private FragmentRewardedBinding binding;

    private TextView rewardView;

    public RewardedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRewardedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RewardedAdProvider provider = new RewardedAdProvider(requireActivity(), "107");
        provider.setService(Helper.createDataService(requireActivity(), RewardedAdMock.class));
        provider.load();
        if (binding == null) return;
        rewardView = binding.rewardFragment;
        Button action = binding.actionRewardedFragment;
        action.setOnClickListener((v) -> {
            // some action
            if (rewardedAd != null) {
                rewardedAd.show(requireActivity());
            }
        });
    }


    @Override
    public void onAdLoaded(NextRewardedAd nextRewardedAd) {
        rewardedAd = nextRewardedAd;
    }

    @Override
    public void onUserEarnedRewardListener(InAppReward inAppReward) {
        rewardView.setText(getString(R.string.reward_data, inAppReward.getAmount(), inAppReward.getRewardType()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
class RewardedFragmentKt : Fragment(), RewardedAdListener {

    private var binding: FragmentRewardedKtBinding? = null
    private var rewardedAd: NextRewardedAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRewardedKtBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val provider = RewardedAdProvider(requireActivity(), "107")
        provider.setService(
            createDataService(
                requireActivity(),
                RewardedAdMock::class.java
            )
        )
        provider.setListener(this)
        provider.load()
        val action = binding?.actionRewardedFragmentKt
        action?.setOnClickListener {
            rewardedAd?.show(requireActivity())
        }
    }

    override fun onAdLoaded(nextRewardedAd: NextRewardedAd?) {
        rewardedAd = nextRewardedAd
    }

    override fun onUserEarnedRewardListener(reward: InAppReward?) {
        val rewardView = binding?.rewardFragment
        rewardView?.text = getString(R.string.reward_data, reward?.amount, reward?.rewardType)
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