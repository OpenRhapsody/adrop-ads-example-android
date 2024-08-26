package io.adrop.ads.example.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.adrop.ads.example.R
import io.adrop.ads.nativeAd.AdropNativeAd
import io.adrop.ads.nativeAd.AdropNativeAdView

class PostAdapter(private val nativeAd: AdropNativeAd) : RecyclerView.Adapter<PostAdapter.NativeAdViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NativeAdViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_native_ad, parent, false)
        return NativeAdViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: NativeAdViewHolder, position: Int) {
        holder.onBindView(nativeAd)
    }

    class NativeAdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val adropNativeAdView: AdropNativeAdView

        init {
            adropNativeAdView = itemView.findViewById(R.id.adrop_native_ad_view)
        }

        fun onBindView(nativeAd: AdropNativeAd) {
            val iconView = itemView.findViewById<ImageView>(R.id.profile)
            Glide.with(itemView.context).load(nativeAd.profile.displayLogo).into(iconView)
            adropNativeAdView.setProfileLogoView(iconView)

            val nameView = itemView.findViewById<TextView>(R.id.title)
            nameView.text = nativeAd.profile.displayName
            adropNativeAdView.setProfileNameView(nameView)

            val headlineView = itemView.findViewById<TextView>(R.id.headline)
            headlineView.text = nativeAd.headline
            adropNativeAdView.setHeadLineView(headlineView)

            val bodyView = itemView.findViewById<TextView>(R.id.content_text)
            bodyView.text = nativeAd.body
            adropNativeAdView.setBodyView(bodyView)

            adropNativeAdView.setMediaView(itemView.findViewById(R.id.content))
            adropNativeAdView.setNativeAd(nativeAd)
        }
    }
}
