package io.github.b_lam.resplash.Data.Data;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.fastadapter.utils.ViewHolderFactory;
import java.util.List;
import io.github.b_lam.resplash.R;
import io.github.b_lam.resplash.Resplash;

/**
 * User.
 * */

public class User extends AbstractItem<User, User.ViewHolder> {

    /**
     * username : jimmyexample
     * name : James Example
     * first_name : James
     * last_name : Example
     * portfolio_url : null
     * bio : The user's bio
     * location : Montreal, Qc
     * total_likes : 20
     * total_photos : 10
     * total_collections : 5
     * downloads : 225974
     * profile_image : {"small":"https://images.unsplash.com/face-springmorning.jpg?q=80&fm=jpg&crop=faces&fit=crop&h=32&w=32","medium":"https://images.unsplash.com/face-springmorning.jpg?q=80&fm=jpg&crop=faces&fit=crop&h=64&w=64","large":"https://images.unsplash.com/face-springmorning.jpg?q=80&fm=jpg&crop=faces&fit=crop&h=128&w=128"}
     * badge : {"title":"Book contributor","primary":true,"slug":"book-contributor","link":"https://book.unsplash.com"}
     * links : {"self":"https://api.unsplash.com/users/jimmyexample","html":"https://unsplash.com/jimmyexample","photos":"https://api.unsplash.com/users/jimmyexample/photos","likes":"https://api.unsplash.com/users/jimmyexample/likes"}
     */

    public String username;
    public String name;
    public String first_name;
    public String last_name;
    public String portfolio_url;
    public String bio;
    public String location;
    public int total_likes;
    public int total_photos;
    public int total_collections;
    public int downloads;
    /**
     * small : https://images.unsplash.com/face-springmorning.jpg?q=80&fm=jpg&crop=faces&fit=crop&h=32&w=32
     * medium : https://images.unsplash.com/face-springmorning.jpg?q=80&fm=jpg&crop=faces&fit=crop&h=64&w=64
     * large : https://images.unsplash.com/face-springmorning.jpg?q=80&fm=jpg&crop=faces&fit=crop&h=128&w=128
     */

    public ProfileImage profile_image;
    /**
     * title : Book contributor
     * primary : true
     * slug : book-contributor
     * link : https://book.unsplash.com
     */

    public Badge badge;
    /**
     * self : https://api.unsplash.com/users/jimmyexample
     * html : https://unsplash.com/jimmyexample
     * photos : https://api.unsplash.com/users/jimmyexample/photos
     * likes : https://api.unsplash.com/users/jimmyexample/likes
     */

    public Links links;

    public static class ProfileImage {
        public String small;
        public String medium;
        public String large;
    }

    public static class Badge {
        public String title;
        public boolean primary;
        public String slug;
        public String link;
    }

    public static class Links {
        public String self;
        public String html;
        public String photos;
        public String likes;
    }

    public static User buildUser(Photo p) {
        User user = new User();
        user.username = p.user.username;
        user.name = p.user.name;
        user.profile_image = new ProfileImage();
        user.profile_image.large = p.user.profile_image.large;
        user.profile_image.medium = p.user.profile_image.medium;
        user.profile_image.small = p.user.profile_image.small;
        return user;
    }

    public static User buildUser(Collection c) {
        User user = new User();
        user.username = c.user.username;
        user.name = c.user.name;
        user.profile_image = new ProfileImage();
        user.profile_image.large = c.user.profile_image.large;
        user.profile_image.medium = c.user.profile_image.medium;
        user.profile_image.small = c.user.profile_image.small;
        return user;
    }

    // Fast Adapter methods
    @Override
    public int getType() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Resplash.getInstance());

        switch (sharedPreferences.getString("item_layout", "List")){
            case "List":
                return R.id.item_user;
            case "Cards":
                return R.id.item_user_card;
            case "Grid":
                return R.id.item_user;
            default:
                throw new IllegalArgumentException("Invalid item layout");
        }
    }

    @Override
    public int getLayoutRes() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Resplash.getInstance());

        switch (sharedPreferences.getString("item_layout", "List")){
            case "List":
                return R.layout.item_user;
            case "Cards":
                return R.layout.item_user_card;
            case "Grid":
                return R.layout.item_user;
            default:
                throw new IllegalArgumentException("Invalid item layout");
        }    }

    @Override
    public void bindView(User.ViewHolder holder, List payloads) {
        super.bindView(holder, payloads);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Resplash.getInstance());

        if(sharedPreferences.getString("item_layout", "List").equals("Cards")){
            Glide.with(holder.itemView.getContext())
                    .load(this.profile_image.large)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(holder.profilePictureCard);
            holder.nameCard.setText(this.name);
            holder.usernameCard.setText("@" + this.username);
        }else{
            Glide.with(holder.itemView.getContext())
                    .load(this.profile_image.large)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(holder.profilePicture);
            holder.name.setText(this.name);
            holder.username.setText("@" + this.username);
        }
    }

    // Manually create the ViewHolder class
    protected static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profilePicture, profilePictureCard;
        TextView name, nameCard;
        TextView username, usernameCard;

        public ViewHolder(View itemView) {
            super(itemView);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Resplash.getInstance());
            if(sharedPreferences.getString("item_layout", "List").equals("Cards")) {
                profilePictureCard = (ImageView) itemView.findViewById(R.id.item_user_card_profile_picture);
                nameCard = (TextView) itemView.findViewById(R.id.item_user_card_name);
                usernameCard = (TextView) itemView.findViewById(R.id.item_user_card_username);
            }else{
                profilePicture = (ImageView) itemView.findViewById(R.id.item_user_profile_picture);
                name = (TextView) itemView.findViewById(R.id.item_user_name);
                username = (TextView) itemView.findViewById(R.id.item_user_username);
            }
        }
    }

    //the static ViewHolderFactory which will be used to generate the ViewHolder for this Item
    private static final ViewHolderFactory<? extends User.ViewHolder> FACTORY = new User.ItemFactory();

    /**
     * our ItemFactory implementation which creates the ViewHolder for our adapter.
     * It is highly recommended to implement a ViewHolderFactory as it is 0-1ms faster for ViewHolder creation,
     * and it is also many many times more efficient if you define custom listeners on views within your item.
     */
    protected static class ItemFactory implements ViewHolderFactory<User.ViewHolder> {
        public User.ViewHolder create(View v) {
            return new User.ViewHolder(v);
        }
    }

    /**
     * return our ViewHolderFactory implementation here
     *
     * @return
     */
    @Override
    public ViewHolderFactory<? extends User.ViewHolder> getFactory() {
        return FACTORY;
    }
}
