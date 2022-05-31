/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * The code in this project has been extracted from the EmojiCompat app in
 *      https://github.com/android/user-interface-samples
 * and modified to display a "Face in Clouds" emoji in all fields including an added field that
 * is just a View that manages a StaticLayout.
 *
 * The build.gradle file dependencies have been modified to include
 *     implementation 'androidx.emoji:emoji-bundled:1.2.0-alpha03'
 * to pick up the latest emojis.
 */

package com.example.android.emojicompat

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.emoji2.text.EmojiCompat
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    companion object {
        val FACE_IN_CLOUDS by lazy {
            val sb = StringBuilder()
            val faceInClouds = "\\u1F636\\u200D\\u1F32B\\uFE0F"
            faceInClouds.split("\\u").forEach {
                if (it.isNotEmpty()) {
                    sb.append(getUtf16FromInt(it.toInt(16)))
                }
            }
            sb.toString()
        }

        private fun getUtf16FromInt(unicode: Int) = String(Character.toChars(unicode))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val doAssignment = false

        // For all of the following views, text assignment can be made immediately like this:
        //     regularTextView.text =  getString(R.string.regular_text_view, FACE_IN_CLOUDS)
        // but there may be flash as the two emojis are shown followed by the combined emoji
        // when EmojiCompat completes initialization and the string is reinterpreted.
        // Using the EmojiCompat.get().registerInitCallback() avoids this problem, but the display
        // will be delayed until EmojiCompat completes initialization.

        val regularTextView: TextView = findViewById(R.id.regular_text_view)
        if (doAssignment) {
            regularTextView.text = getString(R.string.regular_text_view, FACE_IN_CLOUDS)
        } else {
            EmojiCompat.get().registerInitCallback(
                InitCallback(
                    regularTextView,
                    getString(R.string.regular_text_view, FACE_IN_CLOUDS)
                )
            )
        }

        val regularEditText: TextView = findViewById(R.id.emoji_edit_text)
        if (doAssignment) {
            regularEditText.text = getString(R.string.regular_text_view, FACE_IN_CLOUDS)
        } else {
            EmojiCompat.get().registerInitCallback(
                InitCallback(regularEditText, getString(R.string.regular_edit_text, FACE_IN_CLOUDS))
            )
        }

        val regularButton: TextView = findViewById(R.id.emoji_button)
        if (doAssignment) {
            regularButton.text = getString(R.string.regular_button, FACE_IN_CLOUDS)
        } else {
            EmojiCompat.get().registerInitCallback(
                InitCallback(regularButton, getString(R.string.regular_button, FACE_IN_CLOUDS))
            )
        }

        // Custom TextView
        val customTextView: TextView = findViewById(R.id.emoji_custom_text_view)
        if (doAssignment) {
            customTextView.text = getString(R.string.custom_text_view, FACE_IN_CLOUDS)
        } else {
            EmojiCompat.get().registerInitCallback(
                InitCallback(customTextView, getString(R.string.custom_text_view, FACE_IN_CLOUDS))
            )
        }

        // A view that creates and manages its own StaticLayout. Straight assignment is not
        // possible here.
        val layoutView = findViewById<LayoutView>(R.id.layoutView)
        EmojiCompat.get().registerInitCallback(
            InitCallback(layoutView, getString(R.string.layout_view, FACE_IN_CLOUDS))
        )
    }

    private class InitCallback(view: View, val s: String) :
        EmojiCompat.InitCallback() {

        val viewRef = WeakReference(view)

        override fun onInitialized() {
            val view = viewRef.get()
            if (view != null) {
                val text = EmojiCompat.get().process(s)
                if (view is TextView) {
                    view.text = text
                } else {
                    (view as LayoutView).text = text
                }
            }
        }
    }
}