# Migration Guide from InApp SDK

Since v.2.0.0 we renamed our product from `InApp` to `Next`. You can see full list of breaking
changes related to this below:

1. Replaced all `InApp` prefixes with `Next`:
    * Renamed `InAppSdk` class to `NextSdk`;
    * `InAppBannerView` -> `NextBannerView`;
    * `setInAppUnitId` -> `setUnitId`

2. Changed main package from `com.nextmillennium.inappsdk` to `io.nextmillennium.nextsdk`. Also
   changed artifact name from `com.nextmillennium:inappsdk` to `io.nextmillennium:nextsdk`
3. Moved all public methods for dynamic mode to `NextSdkExtra`.
4. New listener for banners and native ads `NextAdListener`
5. New class for errors `NextAdError`.
6. New listener for fullscreen ads `FullScreenListener`.
7. New public classes for fullscreen ads and classes containing loaded ads:
   *`InterstitialProvider` and `NextInterstitialAd` for interstitial ads,
    * `RewardedProvider` and `NextRewardedAd` for rewarded ads,
    * `RewardedInterstitialProvider` and `NextRewardedInterstitialAd` for rewarded interstitial ads.

8. Added App Open ads support. Created `AppOpenAdProvider` for app open initial load and control
   and `NextAppOpenAd` containing ad.
9. Added new method `setContainerWidth` and `collapsible` parameter to `NextBannerView`
10. Separated native ads to `NextNativeView`
