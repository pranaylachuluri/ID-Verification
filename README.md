# College ID Verification (Android)

An Android app that checks whether a student ID card belongs to my college. You photograph the card or pick an image of it, and the app reads the text on it with on-device OCR. If it finds the college's name it shows **VALID** with a green tick, otherwise **INVALID** with a red cross.

**Try it:** download [`app-release.apk`](app-release.apk) and install it on an Android phone. You may need to allow installs from unknown sources.

## Screenshots

<p>
<img src="https://github.com/pranaylachuluri/ID-Verification/assets/103355762/6fa8bf03-fbfb-4da4-bfab-4ebdae518ee1" width="180">
<img src="https://github.com/pranaylachuluri/ID-Verification/assets/103355762/eedc64a1-9277-44d6-9d82-4a537e42d033" width="180">
<img src="https://github.com/pranaylachuluri/ID-Verification/assets/103355762/990b519d-01d8-4de3-83f6-78db14b1983a" width="180">
<img src="https://github.com/pranaylachuluri/ID-Verification/assets/103355762/263cfc4c-a3ea-4f39-a55b-188c8ccd5d84" width="180">
</p>
<p>
<img src="https://github.com/pranaylachuluri/ID-Verification/assets/103355762/dfc2dd05-a834-47ef-a94c-613e3106c58b" width="180">
<img src="https://github.com/pranaylachuluri/ID-Verification/assets/103355762/c6bc2993-22fe-473f-a256-c0e900395ab1" width="180">
<img src="https://github.com/pranaylachuluri/ID-Verification/assets/103355762/e41721dc-d60f-4888-b7ad-413db6f20c25" width="180">
</p>

## How it works

1. **Capture:** the user takes a photo with the camera or picks one from the gallery. The app asks for camera and storage permissions at runtime.
2. **OCR:** Google Mobile Vision's `TextRecognizer` reads every text block on the image, entirely on the device.
3. **Verify:** the recognised text is checked for the college's name. A match shows VALID, anything else shows INVALID.

## Built with

- Java and the Android SDK
- Google Play Services Mobile Vision (`play-services-vision`) for OCR
- Android Studio

## Files

| File | Purpose |
| --- | --- |
| `MainActivity.java` | Camera and gallery input, OCR and the validity check |
| `activity_main.xml` | Layout |
| `app-release.apk`, `app-debug.apk` | Ready-to-install builds |
