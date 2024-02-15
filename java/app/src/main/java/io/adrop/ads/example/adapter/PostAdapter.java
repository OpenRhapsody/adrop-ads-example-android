package io.adrop.ads.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.adrop.ads.example.R;
import io.adrop.ads.nativeAd.AdropNativeAd;
import io.adrop.ads.nativeAd.AdropNativeAdView;
import org.jetbrains.annotations.NotNull;


public class PostAdapter extends RecyclerView.Adapter {
    private final AdropNativeAd nativeAd;

    public PostAdapter(AdropNativeAd nativeAd) {
        this.nativeAd = nativeAd;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_native_ad, parent, false);
        return new NativeAdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ((NativeAdViewHolder) holder).onBindView(nativeAd);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    private static class NativeAdViewHolder extends RecyclerView.ViewHolder {
        private final AdropNativeAdView nativeAdView;

        public NativeAdViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            nativeAdView = itemView.findViewById(R.id.adrop_native_ad_view);
        }

        public void onBindView(AdropNativeAd nativeAd) {
            nativeAdView.setIconView(itemView.findViewById(R.id.profile), null);
            nativeAdView.setAdvertiserView(itemView.findViewById(R.id.title), null);
            nativeAdView.setMediaView(itemView.findViewById(R.id.content));
            nativeAdView.setBodyView(itemView.findViewById(R.id.content_text));
            nativeAdView.setNativeAd(nativeAd);
        }
    }
}
