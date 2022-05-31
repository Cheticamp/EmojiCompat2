Android EmojiCompat Sample (Kotlin)
===================================

This demo demonstrates usage of EmojiCompat support library. You can  
use this library to prevent your app from showing missing emoji  
characters in the form of tofu (□). This demo
replies on the AppCompat library to manage EmojiCompat initialization.

Based upon the EmojiCompat sample in Google's [user-interface-samples](https://github.com/android/user-interface-samples) 
but migrated to [Emoji2](https://developer.android.com/jetpack/androidx/releases/emoji2).

An additional View has been added that manages it own StaticLayout for
use with `Layout.draw(Canvas)`.

