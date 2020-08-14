package com.hav.cigar.utility;

import java.util.ArrayList;

/**
 * Created by 06peng on 2015/6/24.
 */
public class ImageUrlUtils {
    static ArrayList<String> wishlistImageUri = new ArrayList<>();
    static ArrayList<String> cartListImageUri = new ArrayList<>();

    public static String[] getImageUrls() {
        String[] urls = new String[]{
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0704.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0693.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0689.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/CAFO.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/COCM.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/SUGC.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/VANC.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0680.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0668.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/5CD9EE3B-3952-4CDF-98B4-11DB9386BBEE.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0687.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/Illusion2.1.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0685.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0675.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/ReddClay.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0653.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0730.jpg",
        };
        return urls;
    }

    public static String[] getOffersUrls() {
        String[] urls = new String[]{
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0680.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0668.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/5CD9EE3B-3952-4CDF-98B4-11DB9386BBEE.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0687.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/Illusion2.1.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0685.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0675.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/ReddClay.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0653.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0730.jpg",
        };
        return urls;
    }

    public static String[] getHomeApplianceUrls() {
        String[] urls = new String[]{
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0680.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/thumb/CAFO.1.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/thumb/IMG_0668.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/thumb/IMG_0668.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/thumb/COCM.1.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/thumb/5CD9EE3B-3952-4CDF-98B4-11DB9386BBEE.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/thumb/IMG_0687.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/thumb/Illusion2.1.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/thumb/IMG_0685.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/thumb/IMG_0675.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/thumb/ReddClay.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/thumb/IMG_0653.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/thumb/SUGC.1.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/thumb/VANC.1.jpg",
        };
        return urls;
    }

    public static String[] getElectronicsUrls() {
        String[] urls = new String[]{
                "https://piersongeoffreyscigars-store.com/images/products/detail/EVD.jpg"
        };
        return urls;
    }

    public static String[] getLifeStyleUrls() {
        String[] urls = new String[]{
                "https://piersongeoffreyscigars-store.com/images/products/detail/CAFO.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/COCM.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/SUGC.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/VANC.jpg"

        };
        return urls;
    }

    public static String[] getBooksUrls() {
        String[] urls = new String[]{
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0704.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0693.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0689.jpg"
        };
        return urls;
    }
    public static String[] getmonthUrls() {
        String[] urls = new String[]{
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0704.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0693.jpg",
                "https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0689.jpg"
        };
        return urls;
    }

    // Methods for Wishlist
    public void addWishlistImageUri(String wishlistImageUri) {
        this.wishlistImageUri.add(0, wishlistImageUri);
    }

    public void removeWishlistImageUri(int position) {
        this.wishlistImageUri.remove(position);
    }

    public ArrayList<String> getWishlistImageUri() {
        return this.wishlistImageUri;
    }

    // Methods for Cart
    public void addCartListImageUri(String wishlistImageUri) {
        this.cartListImageUri.add(0, wishlistImageUri);
    }

    public void removeCartListImageUri(int position) {
        this.cartListImageUri.remove(position);
    }

    public ArrayList<String> getCartListImageUri() {
        return this.cartListImageUri;
    }
}
