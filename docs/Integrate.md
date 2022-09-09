# Integrate the SDK

This document describes how to integrate the NMM in-app Android SDK into your project.

## Add NMM’s maven repository to your gradle build script:

1. Add the following line with our maven repo to your project’s root level `build.gradle` file
   inside the repositories section:

   In project level `build.gradle` add the entry in `allprojects`:

   ```groovy
    allprojects {
        repositories {
            //your repos
            maven { url 'https://sdk.brainlyads.com/android/repository' }
        }
    }
   ```
   If your project's gradle version is 7.0+ add it to your `settings.gradle` instead
   of `build.gradle` in `dependencyResolutionManagement`
   section:

    ```groovy
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            google()
            jcenter()
            mavenCentral()
            //  ...
            //  your repos
            maven { url 'https://sdk.brainlyads.com/android/repository' }
        }
    }
    ```

2. Add the following dependency to your project’s module level `build.gradle` file:

    ```groovy
    dependencies {
        //Add NMM inApp SDK dependency
        implementation 'io.nextmillennium:nextsdk:2.0.0'
    }
    ```

## Modify your Manifest.xml:

Add the following `<meta-data>` tags to your AndroidManifest.xml inside `<application>`:

```xml

<application>
    <meta-data android:name="io.nextmillennium.nextsdk.API_KEY"
        android:value="[PUT_NMM_API_KEY_HERE]" />
    <meta-data android:name="com.google.android.gms.ads.APPLICATION_ID"
        android:value="[PUT_GOOGLE_APPLICATION_ID_HERE]" />
</application>
```

You can get **NMM_API_KEY** by contacting Next Millennium Media. Visit
our [website](https://nextmillennium.io/contact/) for more information.

> Note: The Next Millennium SDK wraps Google's APIs as a fallback for bids not placed with other preferred buyers to ensure maxim revenue. Next Millennium will create and manage the Google account for your company.
>

A new **GOOGLE_APPLICATION_ID** will be generated for you by Next Millennium Media.

It is required to completely remove Google Mobile Ads SDK from your project to prevent conflicts and
potential fail builds.