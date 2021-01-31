/*
 * Copyright (C) 2018 The Android Open Source Project
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
 */

package my.farhan.movie.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {
    @BindingAdapter(value = ["app:hideIfEmpty"])
    @JvmStatic
    fun hideIfEmpty(view: View, isEmpty: Boolean) {
        view.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    @BindingAdapter("app:showIfEmpty")
    @JvmStatic
    fun showIfEmpty(view: View, isEmpty: Boolean) {
        view.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String) {
        Glide.with(view.context).load(url).into(view)
    }
}