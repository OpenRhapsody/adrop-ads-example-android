package io.adrop.ads.example.model

import java.util.UUID
import kotlin.random.Random
import kotlin.random.nextInt

data class Post (
    val id: String = UUID.randomUUID().toString(),
    val icon: String = "https://storage.adrop.io/public/ic_openrhapsody.png",
    val title: String = "Adrop",
    val subTitle: String = "OpenRhapsody",
    val image: String = "https://storage.adrop.io/public/openrhapsody_title.png",
    val content: String = "Adrop Post Text",
    val like: Int = Random.nextInt(0..100),
    val comment: Int = Random.nextInt(0..100)
)
