# AndroVisionOCV
 Android Application for Offline OCR Using Android NDK, OpenCV and Tesserect API

## Installation

1. Clone the Repo
2. Import into Android Studio
3. Import OpenCV-for-Android as a Module named `opencv`
4. change the head of OpenCV Module from `apply plugin: 'com.android.application'` to be `apply plugin: 'com.android.library'`
5. remove `applicationID` from `OpenCV` Module `build.gradle`.
6. Sync the full Project
7. Build and Deploy



## Notes

The Application is Based in basic OCR Functionality on another project BUT I modified the code updated the OpenCV Version, Removed Camera Functionality and Replaced it with something up-to-date, optimized OCR Files to suite specific fonts, changed the structure of the app, added network layer and Persistence functionality and Modified the UI for better UI and UX Based upon Google Material Design Guide Lines. Basically the Whole App.



## Another Note :)

The Application is written using JAVA, so i expect you to have some knowledge about the language. 