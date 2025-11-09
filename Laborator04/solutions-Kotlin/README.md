# ContactsManager

## Overview

`ContactsManager` is an Android app designed to simplify managing contact information. Built with Jetpack Compose and following the MVVM architecture pattern, this app allows users to input, edit, and save contact details such as name, phone, email, address, job title, company, website, and instant messaging (IM) addresses.

## Features

- Dynamically show or hide additional fields to input job, company, website, and IM information.
- Input fields for essential contact details: name, phone, email, and address.
- Save contacts to the device's contact list.
- Clear all fields with a single action.

## How It Works

### UI Components

- **Buttons**: Toggle showing additional fields and save or clear inputted contact information.
- **CustomOutlinedTextField**: Custom text fields for entering contact details.
- **ViewModel**: Manages UI-related data in a lifecycle-conscious way, holding the state of the app's UI and handling business logic.

### Saving Contacts

Contacts are saved using the Android Contacts API, with fields for both basic and additional information. An `ActivityResultLauncher` is used to handle the contact saving process securely.

## Getting Started

To build and run the app, ensure you have the latest version of Android Studio and the Android SDK installed. Clone the repository, open the project in Android Studio, and run it on an emulator or a physical device.

## Learn More

To learn more about the technologies and patterns used in this app, consider the following resources:

- [Jetpack Compose Tutorial](https://developer.android.com/jetpack/compose/tutorial)
- [ViewModel Overview](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [LiveData Overview](https://developer.android.com/topic/libraries/architecture/livedata)
- [Android Contacts API](https://developer.android.com/reference/android/provider/ContactsContract)
- [ActivityResultLauncher Overview](https://developer.android.com/training/basics/intents/result)
## Contribution

Contributions are welcome! If you'd like to improve the `ContactsManager` app, please feel free to fork the repository, make your changes, and submit a pull request.
