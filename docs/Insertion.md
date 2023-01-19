# Integrate Ad Formats Dynamically

[Back to repo](https://github.com/nextmillenniummedia/next-sdk-android-example/tree/main)

In Dynamic (or insertion) mode, our SDK dynamically services and displays ads directly in the
content of your app’s
screens, using pre-defined ad units selected from the inApp management dashboard. This means you
don’t need to update code when the ad format changes, which reduces set up time and minimizes
maintenance.

The following subsections describe how to integrate ad formats dynamically.

All you need to do is to call `NextAds.insertAdsTo` method in create callback of your activity or
fragment and pass screen key.
Screen keys you can get from our [support](mailto:support@nextmillennium.io).

## Fragment

### With binding

Java

```java
public class BannersFragment extends Fragment {

    FragmentInjectionBannersBinding binding;

    public BannersFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInjectionBannersBinding.inflate(inflater);
        NextAds.insertAdsTo(binding.getRoot(), "1660904874975-key");
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
```

Kotlin

```kotlin
class BannersFragment : Fragment() {
    private var binding: FragmentInjectionBannersBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInjectionBannersBinding.inflate(inflater)
        NextAds.insertAdsTo(binding?.root, "1660904874975-key")
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
```

### With LayoutInflater

Java

```java
public class AdaptiveFragment extends Fragment {

    public AdaptiveFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_adaptive, container, false);
        NextAds.insertAdsTo((ViewGroup) root, "1673903403654-key");
        return root;
    }
}
```

Kotlin

```kotlin
class AdaptiveFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_adaptive, container, false)
        insertAdsTo(root as ViewGroup, "1673903403654-key")
        return root
    }
}
```

### Reload banner ad

For reloading just pass banner position. We have `BannerPosition` enum containing TOP and BOTTOM
values.

Java

```java
public class BannersFragment extends Fragment {

    FragmentInjectionBannersBinding binding;

    public BannersFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInjectionBannersBinding.inflate(inflater);
        NextAds.insertAdsTo(binding.getRoot(), "1660904874975-key");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button reload = binding.reload;
        reload.setOnClickListener((v) ->
                NextAds.reload(requireActivity(), BannerPosition.TOP)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
```

Kotlin

```kotlin
class BannersFragment : Fragment() {
    private var binding: FragmentInjectionBannersBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInjectionBannersBinding.inflate(inflater)
        NextAds.insertAdsTo(binding?.root, "1660904874975-key")
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val reload = binding?.reload
        reload.setOnClickListener { v: View? ->
            NextAds.reload(requireActivity(), BannerPosition.TOP)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
```

## Activity

### Simple usage with binding

Java

```java
public class BannersActivity extends AppCompatActivity {

    ActivityBannersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBannersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NextAds.insertAdsTo(binding.getRoot(), "1673899895557-key");
    }
}
```

Kotlin

```kotlin
class BannersActivity : AppCompatActivity() {

    lateinit var binding: ActivityInsertionBannersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertionBannersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        NextAds.insertAdsTo(binding.root, "1674041204139-key")
    }
}
```

### Usage with `findViewById`

Java

```java
public class AdaptiveBannersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaptive_banners);
        ViewGroup root = findViewById(android.R.id.content);
        NextAds.insertAdsTo(root, "1673903217765-key");
    }
}
```

Kotlin

```kotlin
class AdaptiveBannersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adaptive_banners)
        val root = findViewById<ViewGroup>(android.R.id.content)
        NextAds.insertAdsTo(root, "1673903217765-key")
    }
}
```

You can just pass activity

```kotlin
class AdaptiveBannersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adaptive_banners)
        NextAds.insertAdsTo(this, "1673903217765-key")
    }
}
```

### Reload

Java

```java
public class BannersActivity extends AppCompatActivity {

    ActivityBannersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBannersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NextAds.insertAdsTo(binding.getRoot(), "1673899895557-key");
        reloadButton.setOnClickListener {
            NextAds.reload(this, BannerPosition.TOP);
        }
    }
}
```

Kotlin

```kotlin
class BannersActivity : AppCompatActivity() {

    lateinit var binding: ActivityInsertionBannersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertionBannersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        NextAds.insertAdsTo(binding.root, "1674041204139-key")
    }
}
```

## Customize ad lifecycle events

Sometimes you need to add specific events for banner lifecycle. You can pass `NextAdListener` for
this purpose.

`NextAdListener` used for managing all lifecycle events of ad.

Available event callbacks:

| method | description |
| --- | --- |
| `onAdLoaded(BaseAdContainer)` | Called when an ad is received. |
| `onAdImpression` | Called when an impression is recorded for an ad. |
| `onAdClicked` | Called when a click is recorded for an ad. |
| `onAdOpened` | Called when an ad opens an overlay that covers the screen. |
| `onAdClosed` | Called when the user wants to return to the application after clicking on an ad. |
| `onAdLoadFail(NextAdError)` | Called when an ad load failed. |

`NextAdError` class contains error code and message:

```kotlin
class NextAdError(val code: Int, val message: String)
```

For passing `NextAdListener` use `NextAds.setListener` method.

### Listener implementing examples

**Activity**

```kotlin
class BannersActivity : AppCompatActivity() {

    lateinit var binding: ActivityInsertionBannersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertionBannersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val screen = Helper.currentScreens["activity"] ?: "1674041204139-key"
        NextAds.insertAdsTo(binding.root, screen)
        val reloadButton = binding.reload
        NextAds.setListener(
            this,
            BannerPosition.TOP,
            createAdListener(BannerPosition.TOP)
        )
        NextAds.setListener(
            this,
            BannerPosition.BOTTOM,
            createAdListener(BannerPosition.BOTTOM)
        )
        reloadButton.setOnClickListener {
            NextAds.reload(this, BannerPosition.TOP)
        }
    }

    private fun showSnackbar(view: View, message: String, length: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(view, message, length).show()
    }

    private fun createAdListener(position: BannerPosition): NextAdListener {
        return object : NextAdListener {
            override fun onAdLoaded(container: BaseAdContainer?) {
                binding.let {
                    Helper.showSnackbar(
                        it.root, "${position.name} banner successfully loaded"
                    )
                }
            }

            override fun onAdClicked() {
                binding.let {
                    Helper.showSnackbar(
                        it.root, "${position.name} banner successfully clicked"
                    )
                }
            }

            override fun onAdLoadFail(adError: NextAdError?) {
                binding.let {
                    Helper.showErrorSnackbar(it.root, adError)
                }
            }

            override fun onAdImpression() {
                binding.let {
                    Helper.showSnackbar(
                        it.root, "${position.name} banner impression"
                    )
                }
            }
        }
    }
}
```

**Fragment**

```kotlin
class BannersFragment : Fragment() {
    private var binding: FragmentInsertionBannersBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInsertionBannersBinding.inflate(layoutInflater)
        NextAds.insertAdsTo(binding?.root, "1674041211926-key")
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NextAds.setListener(
            requireActivity(),
            BannerPosition.TOP,
            createAdListener(BannerPosition.TOP)
        )
        NextAds.setListener(
            requireActivity(),
            BannerPosition.BOTTOM,
            createAdListener(BannerPosition.BOTTOM)
        )
        val reloadButton = binding?.reload

        reloadButton?.setOnClickListener {
            NextAds.reload(requireActivity(), BannerPosition.TOP)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun showSnackbar(view: View, message: String, length: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(view, message, length).show()
    }

    private fun createAdListener(position: BannerPosition): NextAdListener {
        return object : NextAdListener {
            override fun onAdLoaded(container: BaseAdContainer?) {
                if (activity != null) {
                    binding?.let {
                        Helper.showSnackbar(
                            it.root, "${position.name} banner successfully loaded"
                        )
                    }
                }
            }

            override fun onAdClicked() {
                binding?.let {
                    Helper.showSnackbar(
                        it.root, "${position.name} banner successfully clicked"
                    )
                }
            }

            override fun onAdLoadFail(adError: NextAdError?) {
                binding?.let {
                    Helper.showErrorSnackbar(it.root, adError)
                }
            }

            override fun onAdImpression() {
                binding?.let {
                    Helper.showSnackbar(
                        it.root, "${position.name} banner impression"
                    )
                }
            }
        }
    }

}
```