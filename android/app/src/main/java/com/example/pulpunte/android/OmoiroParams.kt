package com.example.pulpunte.android

import android.graphics.Bitmap
import androidx.navigation.NavArgs
import androidx.palette.graphics.Palette
import java.io.Serializable
import java.time.LocalDate

data class OmoiroParams(
    var date: LocalDate = LocalDate.now(),
    var images: List<Bitmap> = listOf(),
    var text: String = "",
    var kana: String = "",
    var name: String = "",
    val color: Int = 0
): Serializable {

    fun hintWardList(): List<String> {
        return Regex("\\p{InCjkUnifiedIdeographs}").matchEntire(text)?.groupValues ?: listOf()
    }

    fun hintColor(): List<Int> {
        return images.map { Palette.from(it).generate() }.map {
            listOf<Int>() +
                    it.darkMutedSwatch?.rgb +
                    it.darkVibrantSwatch?.rgb +
                    it.dominantSwatch?.rgb +
                    it.lightMutedSwatch?.rgb +
                    it.lightVibrantSwatch?.rgb +
                    it.mutedSwatch?.rgb
        }.flatten().filterNotNull()
    }
}
