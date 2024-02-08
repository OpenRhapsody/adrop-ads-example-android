package io.adrop.ads.example.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import io.adrop.ads.example.R;
import io.adrop.ads.example.model.Post;
import io.adrop.ads.nativeAd.AdropNativeAd;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

enum ViewType {
    ADROP_NATIVE,
    POST_ITEM
}

public class PostAdapter extends RecyclerView.Adapter {
    private ArrayList<Post> posts = new ArrayList<>();
    private AdropNativeAd nativeAd;

    public PostAdapter(AdropNativeAd nativeAd) {
        this.nativeAd = nativeAd;
        posts.add(new Post());
        posts.add(new Post());
        posts.add(new Post());
        posts.add(new Post());
        posts.add(new Post());
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (viewType == ViewType.ADROP_NATIVE.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_native_ad, parent, false);
            return new NativeAdViewHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 2) return ViewType.ADROP_NATIVE.ordinal();
        return ViewType.POST_ITEM.ordinal();
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if (position == 2) {
            ((NativeAdViewHolder) holder).onBindView(nativeAd);
            return;
        }

        int index = position < 2 ? position : position - 1;
        Log.d("adrop", "post size " + posts.size());
        Log.d("adrop", "position " + position + ", " + "index " + index);
        ((PostViewHolder) holder).onBindView(posts.get(index), index);
    }

    @Override
    public int getItemCount() {
        return posts.size() + 1;
    }

    private static class PostViewHolder extends RecyclerView.ViewHolder {

        public PostViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        @SuppressLint("SetTextI18n")
        public void onBindView(Post post, int position) {
            ((TextView) itemView.findViewById(R.id.title)).setText(post.title);
            ((TextView) itemView.findViewById(R.id.sub_title)).setText(post.subTitle);
            ((TextView) itemView.findViewById(R.id.content_text)).setText(position + " " + post.content);
            ((TextView) itemView.findViewById(R.id.like_count)).setText(post.like + "");
            ((TextView) itemView.findViewById(R.id.comment_count)).setText(post.comment + "");

            Glide.with(itemView.getContext())
                    .load(post.icon)
                    .centerCrop()
                    .into(((ImageView) itemView.findViewById(R.id.profile)));

            Glide.with(itemView.getContext())
                    .load(post.image)
                    .centerCrop()
                    .into(((ImageView) itemView.findViewById(R.id.content)));
        }
    }

    private static class NativeAdViewHolder extends PostViewHolder {
        public NativeAdViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        public void onBindView(AdropNativeAd nativeAd) {
            nativeAd.setIconView(itemView.findViewById(R.id.profile), null);
            nativeAd.setHeadLineView(itemView.findViewById(R.id.title), null);
            nativeAd.setMediaView(itemView.findViewById(R.id.content));
            nativeAd.setBodyView(itemView.findViewById(R.id.content_text));
        }
    }
}
