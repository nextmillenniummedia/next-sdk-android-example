# Interstitial Ads

[Back to manual mode overview](https://github.com/nextmillenniummedia/next-sdk-android-example/blob/main/docs/Manual.md)

To show interstitial ad in manual mode you need to create an instance of `InterstitialAdProvider`
with passed activity context and unit id:

```kotlin
val provider = InterstitialAdProvider(context, "106")
```

| param | description
| --- | --- | 
| `context` | activity context | 
| `unitId` | unit id in our system | 

Simple interstitial ad load will be look like:

<details>
<summary>Java</summary>

```java
public class InterstitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        InterstitialAdProvider provider = new InterstitialAdProvider(this, "106");
        provider.load();
    }
}
```

</details>
<details>
<summary>Kotlin</summary>

```kotlin
class InterstitialActivityKt : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitial_kt)
        val provider = InterstitialAdProvider(this@InterstitialActivityKt, "106")
        provider.load()
    }
}
```

</details>

You can also pass unit id with `setUnitId (String)`:

<details>
<summary>Java</summary>

```java
public class InterstitialActivity extends AppCompatActivity implements InterstitialAdListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        InterstitialAdProvider provider = new InterstitialAdProvider(this);
        provider.setUnitId("106"); // your unit id
        provider.load();
    }
}
```

</details>

<details>
<summary>Kotlin</summary>

```kotlin
class InterstitialActivityKt : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitial_kt)
        val provider = InterstitialAdProvider(this@InterstitialActivityKt)
        provider.unitId = "106" // your unit id
        provider.load()
    }
}
```

</details>

### Customize ad lifecycle events

Use `InterstitialAdListener` to provide your customized behavior for successful and failure banner
loads. You can pass it by `setListener(InterstitialAdListener)` method in `InterstitialAdProvider`.

| overridable methods | description |
| --- | --- |
| `onAdLoaded(NextInterstitialAd)` | Called when an ad successfully loads |
| `onAdImpression` | Called when an impression is recorded for an ad. |
| `onAdClicked` | Called when a click is recorded for an ad. |
| `onFullScreenAdDeclined` | Called when the ad dismissed full screen content. |
| `onFullScreenAdShowed` | Called when the ad showed the full screen content. |
| `onFullScreenAdLoadFail(NextAdError)` | Called when the ad failed to show or load full screen content. |
| `onError(Throwable)` | Called when an unexpected error occurred while handling fullscreen ad |

Prefer preload interstitial ads and show it with UI interacting actions like clicking on button.
Use `onAdLoaded` callback in `InterstitialAdListener` for this. You will
receive `NextInterstitialAd` as callback param.

Use the following examples to show interstitial. Usage examples of `InterstitialAdListener` also
will be given further in the article.

**Activity**

Let’s create new empty activity with next layout:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent" android:orientation="vertical"
    tools:context=".ui.activities.InterstitialActivity">

    <Button android:id="@+id/action_interstitial_activity" android:layout_width="match_parent"
        android:layout_height="wrap_content" android:text="Action" />

</LinearLayout>
```

<details>
<summary>Java</summary>

```java
public class InterstitialActivity extends AppCompatActivity implements InterstitialAdListener {

    @Nullable
    private NextInterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        InterstitialAdProvider provider = new InterstitialAdProvider(this, "106");
        provider.setListener(this);
        provider.load();
        Button actionButton = findViewById(R.id.action_button_interstitial);
        actionButton.setOnClickListener((v) -> {
            // some action
            if (interstitialAd != null) {
                interstitialAd.show(InterstitialActivity.this);
            }
        });
    }

    @Override
    public void onAdLoaded(NextInterstitialAd nextInterstitialAd) {
        interstitialAd = nextInterstitialAd;
    }
}
```

</details>

<details>
<summary>Kotlin</summary>

```kotlin
class InterstitialActivityKt : AppCompatActivity(), InterstitialAdListener {

    private var interstitialAd: NextInterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitial_kt)
        val action: Button = findViewById(R.id.action_button_interstitial)
        val provider = InterstitialAdProvider(this@InterstitialActivityKt)
        provider.setListener(this)
        provider.unitId = "106" // your unit id
        provider.load()
        action.setOnClickListener {
            interstitialAd?.show(this@InterstitialActivityKt)
        }
    }

    override fun onAdLoaded(nextInterstitialAd: NextInterstitialAd?) {
        interstitialAd = nextInterstitialAd
    }
}
```
</details>

Prefer to pass current activity to show method. If it is impossible use `show` method without
params. Next SDK will find current activity.

**Fragments**

For example you have a fragment with next layout.

```xml

<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent" tools:context=".ui.fragments.InterstitialFragment">
    <Button android:id="@+id/action_interstitial_fragment" android:layout_width="match_parent"
        android:layout_height="wrap_content" android:text="Action" />
</FrameLayout>
```

And some class `InterstitialFragment` for this fragment in code.

All that you need is to create new instance of `InterstitialAdProvider` after view is created and
call `load`.

<details>
<summary>Java</summary>

```Java
public class InterstitialFragment extends Fragment implements InterstitialAdListener {

    @Nullable
    private NextInterstitialAd interstitialAd;
    @Nullable
    private FragmentInterstitialBinding binding;

    public InterstitialFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInterstitialBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InterstitialAdProvider provider = new InterstitialAdProvider(requireActivity(), "106");
        provider.load();
        if (binding == null) return;
        Button action = binding.actionInterstitialFragment;
        action.setOnClickListener((v) -> {
            // some action
            if (interstitialAd != null) {
                interstitialAd.show(requireActivity());
            }
        });
    }

    @Override
    public void onAdLoaded(NextInterstitialAd nextInterstitialAd) {
        interstitialAd = nextInterstitialAd;
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
class InterstitialFragmentkt : Fragment(), InterstitialAdListener {

    private var binding: FragmentInterstitialKtBinding? = null
    private var interstitialAd: NextInterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInterstitialKtBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val provider = InterstitialAdProvider(requireActivity(), "106")
        provider.setListener(this)
        provider.load()
        val action = binding?.actionInterstitialFragmentKt
        action?.setOnClickListener {
            // some action
            interstitialAd?.show(requireActivity())
        }
    }

    override fun onAdLoaded(nextInterstitialAd: NextInterstitialAd?) {
        interstitialAd = nextInterstitialAd
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
```

</details>

Usage of all listener events

<details>
<summary>Java</summary>

```Java
public class InterstitialFragment extends Fragment implements InterstitialAdListener {

    @Nullable
    private NextInterstitialAd interstitialAd;
    @Nullable
    private FragmentInterstitialBinding binding;

    public InterstitialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInterstitialBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new InterstitialAdProvider(requireActivity(), "106").setListener(this).load();
        if (binding == null) return;
        Button action = binding.actionInterstitialFragment;
        action.setOnClickListener((v) -> {
            // some action
            if (interstitialAd != null) {
                interstitialAd.show(requireActivity());
            }
        });
    }

    @Override
    public void onAdLoaded(NextInterstitialAd nextInterstitialAd) {
        interstitialAd = nextInterstitialAd;
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
    }

    @Override
    public void onError(Throwable error) {
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
class InterstitialFragmentkt : Fragment(), InterstitialAdListener {

    private var binding: FragmentInterstitialKtBinding? = null
    private var interstitialAd: NextInterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInterstitialKtBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val provider = InterstitialAdProvider(requireActivity(), "106")
        provider.setListener(this)
        provider.load()
        val action = binding?.actionInterstitialFragmentKt
        action?.setOnClickListener {
            // some action
            interstitialAd?.show(requireActivity())
        }
    }

    override fun onAdLoaded(nextInterstitialAd: NextInterstitialAd?) {
        interstitialAd = nextInterstitialAd
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
    }

    override fun onError(error: Throwable?) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
```

</details>
