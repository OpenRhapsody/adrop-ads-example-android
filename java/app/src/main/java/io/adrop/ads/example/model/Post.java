package io.adrop.ads.example.model;

import java.util.Random;
import java.util.UUID;

public class Post {
    public String id = UUID.randomUUID().toString();
    public String icon = "https://storage.adrop.io/public/ic_openrhapsody.png";
    public String title = "Adrop";
    public String subTitle = "OpenRhapsody";
    public String image = "https://storage.adrop.io/public/openrhapsody_title.png";
    public String content = "Adrop Post Text";
    public int like = new Random().nextInt(100);
    public int comment = new Random().nextInt(100);
}
