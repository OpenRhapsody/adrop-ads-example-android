package io.adrop.ads.example.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.adrop.ads.example.R
import io.adrop.ads.example.model.Post
import io.adrop.ads.nativeAd.AdropNativeAd
import io.adrop.ads.nativeAd.AdropNativeAdView

enum class ViewType {
    ADROP_NATIVE,
    POST_ITEM
}

class PostAdapter(private val nativeAds: ArrayList<AdropNativeAd>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private val posts = arrayListOf(Post(), Post(), Post(), Post(), Post(), Post())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        when (viewType) {
            ViewType.ADROP_NATIVE.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_native_ad, parent, false)

                return NativeAdViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_view, parent, false)

                return PostViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position < nativeAds.size) return ViewType.ADROP_NATIVE.ordinal
        return ViewType.POST_ITEM.ordinal
    }

    override fun getItemCount(): Int {
        return posts.size + nativeAds.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        if (holder is NativeAdViewHolder) {
            holder.onBindView(nativeAds[position], position)
            return
        }

        val index = position - nativeAds.size
        holder.onBindView(posts[index], index)
    }

    open class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun onBindView(post: Post, position: Int) {
            itemView.findViewById<TextView>(R.id.title).text = post.title
            itemView.findViewById<TextView>(R.id.sub_title).text = post.subTitle

            Glide.with(itemView)
                .load(post.image)
                .centerCrop()
                .into(itemView.findViewById(R.id.content))

            Glide.with(itemView)
                .load(post.icon)
                .centerCrop()
                .into(itemView.findViewById(R.id.profile))

            itemView.findViewById<TextView>(R.id.like_count).text = post.like.toString()
            itemView.findViewById<TextView>(R.id.comment_count).text = post.comment.toString()
            itemView.findViewById<TextView>(R.id.content_text).text = "$position, ${post.content}"
        }
    }

    class NativeAdViewHolder(itemView: View) : PostViewHolder(itemView) {
        private val adropNativeAdView: AdropNativeAdView

        init {
            adropNativeAdView = itemView.findViewById(R.id.adrop_native_ad_view)
        }

        fun onBindView(nativeAd: AdropNativeAd, position: Int) {
            adropNativeAdView.setIconView(itemView.findViewById(R.id.profile))
            adropNativeAdView.setHeadLineView(itemView.findViewById<TextView>(R.id.title))
            adropNativeAdView.setMediaView(itemView.findViewById(R.id.content))
            adropNativeAdView.setBodyView(itemView.findViewById(R.id.content_text))
            adropNativeAdView.setCallToActionView(itemView.findViewById(R.id.call_to_action_view))
            adropNativeAdView.setAdvertiserView(itemView.findViewById(R.id.advertiser_view))
            adropNativeAdView.setNativeAd(nativeAd)
        }
    }
}


