package com.example.l_5411.boread.bean;

import java.util.List;

/**
 * Created by L_5411 on 2017/3/20.
 */

public class PexelsBean {

    /**
     * page : 1
     * per_page : 15
     * photos : []
     * next_page : https://api.pexels.com/v1/popular/?page=2&per_page=15
     */

    private int page;
    private int per_page;
    private String next_page;
    private List<PhotosBean> photos;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public String getNext_page() {
        return next_page;
    }

    public void setNext_page(String next_page) {
        this.next_page = next_page;
    }

    public List<PhotosBean> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotosBean> photos) {
        this.photos = photos;
    }

    public static class PhotosBean {
        /**
         * id : 2324
         * width : 5183
         * height : 2444
         * url : https://www.pexels.com/photo/skyline-buildings-new-york-skyscrapers-2324/
         * photographer : Unsplash
         * src : {}
         */

        private int id;
        private int width;
        private int height;
        private String url;
        private String photographer;
        private SrcBean src;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPhotographer() {
            return photographer;
        }

        public void setPhotographer(String photographer) {
            this.photographer = photographer;
        }

        public SrcBean getSrc() {
            return src;
        }

        public void setSrc(SrcBean src) {
            this.src = src;
        }

        public static class SrcBean {
            /**
             * original : https://static.pexels.com/photos/2324/skyline-buildings-new-york-skyscrapers.jpg
             * large : https://images.pexels.com/photos/2324/skyline-buildings-new-york-skyscrapers.jpg?w=940&h=650&auto=compress&cs=tinysrgb
             * medium : https://images.pexels.com/photos/2324/skyline-buildings-new-york-skyscrapers.jpg?h=350&auto=compress&cs=tinysrgb
             * small : https://images.pexels.com/photos/2324/skyline-buildings-new-york-skyscrapers.jpg?h=130&auto=compress&cs=tinysrgb
             * portrait : https://images.pexels.com/photos/2324/skyline-buildings-new-york-skyscrapers.jpg?w=800&h=1200&fit=crop&auto=compress&cs=tinysrgb
             * square : https://images.pexels.com/photos/2324/skyline-buildings-new-york-skyscrapers.jpg?w=1200&h=1200&fit=crop&auto=compress&cs=tinysrgb
             * landscape : https://images.pexels.com/photos/2324/skyline-buildings-new-york-skyscrapers.jpg?w=1200&h=627&fit=crop&auto=compress&cs=tinysrgb
             * tiny : https://images.pexels.com/photos/2324/skyline-buildings-new-york-skyscrapers.jpg?w=280&h=200&fit=crop&auto=compress&cs=tinysrgb
             */

            private String original;
            private String large;
            private String medium;
            private String small;
            private String portrait;
            private String square;
            private String landscape;
            private String tiny;

            public String getOriginal() {
                return original;
            }

            public void setOriginal(String original) {
                this.original = original;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getPortrait() {
                return portrait;
            }

            public void setPortrait(String portrait) {
                this.portrait = portrait;
            }

            public String getSquare() {
                return square;
            }

            public void setSquare(String square) {
                this.square = square;
            }

            public String getLandscape() {
                return landscape;
            }

            public void setLandscape(String landscape) {
                this.landscape = landscape;
            }

            public String getTiny() {
                return tiny;
            }

            public void setTiny(String tiny) {
                this.tiny = tiny;
            }
        }
    }
}
