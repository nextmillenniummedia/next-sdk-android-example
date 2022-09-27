# App Open ads

[Back to manual mode overview](https://github.com/nextmillenniummedia/next-sdk-android-example/blob/2.x/docs/manual/Manual.md)

App open ads are a special ad format intended for publishers wishing to monetize their app load
screens. App open ads can be closed at any time, and are designed to be shown when your users bring
your app to the foreground.

To show app open ad in manual mode you need to create an instance of `AppOpenAdProvider`
with passed activity context and unit id:

```kotlin
val provider = AppOpenAdProvider(context, "106")
```

| param | description
| --- | --- | 
| `context` | activity context | 
| `unitId` | unit id in our system | 

Simple app open ad load will be look like:

<details>
<summary>Java</summary>

```java
public class AppOpenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_open);
        AppOpenAdProvider provider = new AppOpenAdProvider(this, "110");
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppOpenAdObserver(provider));
    }
}
```

</details>

<details>
<summary>Kotlin</summary>

```kotlin
class AppOpenActivityKt : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_open_kt)
        val provider = AppOpenAdProvider(this, "110")
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppOpenAdObserver(provider))
    }
}
```

</details>

You can also pass unit id with `setUnitId (String)`:

<details>
<summary>Java</summary>

```java
public class AppOpenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_open);
        AppOpenAdProvider provider = new AppOpenAdProvider(this);
        provider.setUnitId("110"); // your unit id
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppOpenAdObserver(provider));
    }
}
```

</details>

<details>
<summary>Kotlin</summary>

```kotlin
class AppOpenActivityKt : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_open_kt)
        val provider = AppOpenAdProvider(this)
        provider.unitId = "110" // your unit id
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppOpenAdObserver(provider))
    }
}
```

</details>

### Customize ad lifecycle events

Use `AppOpenAdListener` to provide your customized behavior for successful and failure banner loads.
You can pass it by `setListener(AppOpenAdListener)` method in `AppOpenAdProvider`.

| overridable methods | description |
| --- | --- |
| `onAdLoaded(NextAppOpenAd)` | Called when an ad successfully loads |
| `onAdImpression` | Called when an impression is recorded for an ad. |
| `onAdClicked` | Called when a click is recorded for an ad. |
| `onFullScreenAdDeclined` | Called when the ad dismissed full screen content. |
| `onFullScreenAdShowed` | Called when the ad showed the full screen content. |
| `onFullScreenAdLoadFail(NextAdError)` | Called when the ad failed to show or load full screen content. |
| `onError(Throwable)` | Called when an unexpected error occurred while handling fullscreen ad |

Use the following examples to show app open ads. Usage examples of `AppOpenAdListener` also
will be given further in the article.