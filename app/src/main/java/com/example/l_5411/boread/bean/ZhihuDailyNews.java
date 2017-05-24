package com.example.l_5411.boread.bean;

import java.util.List;

/**
 * Created by L_5411 on 2017/3/12.
 */

public class ZhihuDailyNews {

    /**
     * date : 20170311
     * stories : [{"images":["http://pic1.zhimg.com/13f2808a86d85a04bc35ed75fea9fb20.jpg"],"type":0,"id":9276341,"ga_prefix":"031122","title":"小事 · 战略家外婆"},{"images":["http://pic4.zhimg.com/bb3c6f6d2355d31a4645ea49d9ee6b57.jpg"],"type":0,"id":9278775,"ga_prefix":"031121","title":"如果失去记忆，我会很爱你"},{"images":["http://pic2.zhimg.com/c692013408a55e6eb05457c8ffc422e9.jpg"],"type":0,"id":9281361,"ga_prefix":"031120","title":"让道歉更容易获得理解和原谅的 3 条原则"},{"images":["http://pic2.zhimg.com/459cb9cc7903df56b20c11349fa28305.jpg"],"type":0,"id":9280802,"ga_prefix":"031119","title":"买了很多书，一直也没看，这是「非理性消费」吗？"},{"images":["http://pic4.zhimg.com/17a9684febbaab24dea2081dda734137.jpg"],"type":0,"id":9281129,"ga_prefix":"031118","title":"扇贝分正反面吗？"},{"images":["http://pic2.zhimg.com/a2d19f38baf7375bd6a3fd6edc6c9651.jpg"],"type":0,"id":9277204,"ga_prefix":"031117","title":"鲜咸微辣、略带酸甜的鱼香酱汁，让一切食材都好吃了起来"},{"images":["http://pic1.zhimg.com/15c5d3a921b296e5e1f9abef044ce1e4.jpg"],"type":0,"id":9280744,"ga_prefix":"031116","title":"鸟睡觉的时候为什么要单腿站立？我都替它累"},{"images":["http://pic1.zhimg.com/dc6c7b3c7bc30f2fba0c933a0d87fb04.jpg"],"type":0,"id":9280729,"ga_prefix":"031115","title":"本来自由贸易得好好的，忽然有人不甘心喝汤，也想吃肉"},{"images":["http://pic2.zhimg.com/ebaf7c9e2b058e19d12a43dc7c430775.jpg"],"type":0,"id":9280481,"ga_prefix":"031114","title":"春天来了，我们带上装备出门跑步吧"},{"images":["http://pic2.zhimg.com/4405f94f9ade6450835a0f753914c0c9.jpg"],"type":0,"id":9280687,"ga_prefix":"031113","title":"朴槿惠如何一步步成为韩国历史上第一个遭弹劾下台的总统？"},{"images":["http://pic4.zhimg.com/e7b58c0fa82b9f8aae6451a402e1eae7.jpg"],"type":0,"id":9280514,"ga_prefix":"031113","title":"水在 -5°C 的冰箱里没结冰，怎么拿出来立马就结冰了？"},{"images":["http://pic3.zhimg.com/28a12f6918e0398ce5d5d422fc668c9e.jpg"],"type":0,"id":9278995,"ga_prefix":"031112","title":"大误 · 李白与杜甫的友谊到底是怎样的？"},{"images":["http://pic1.zhimg.com/b2a095a69d77db88b37fdc0f8fed1a4c.jpg"],"type":0,"id":9279924,"ga_prefix":"031111","title":"当「开放世界」有了套路，游戏也变得不好玩了"},{"images":["http://pic1.zhimg.com/7987cc7128aaeb6e750fa73053debe34.jpg"],"type":0,"id":9278937,"ga_prefix":"031109","title":"中国保持低犯罪率的一个重要原因，就是让青少年远离毒品"},{"images":["http://pic4.zhimg.com/17baf7dcccb1a5d8937ef14d750d42f3.jpg"],"type":0,"id":9275910,"ga_prefix":"031108","title":"汽车共享在中国有戏吗？"},{"images":["http://pic3.zhimg.com/39b02a6775a27ef59b9f9e333efb7c62.jpg"],"type":0,"id":9279909,"ga_prefix":"031107","title":"我是一名产品经理，我的产品都被我亲手做死了"},{"images":["http://pic2.zhimg.com/0b17110ef12400007f7c74c7be30544d.jpg"],"type":0,"id":9279786,"ga_prefix":"031107","title":"海外私人代购小心涉嫌商标侵权"},{"images":["http://pic2.zhimg.com/4d02ec1c562448f063f7f93ddf3840fd.jpg"],"type":0,"id":9279805,"ga_prefix":"031107","title":"共享单车烧钱大战，能烧出另一个「滴滴」吗？"},{"images":["http://pic4.zhimg.com/e90090ebc42a2462b138c12628a4a3a3.jpg"],"type":0,"id":9278214,"ga_prefix":"031106","title":"瞎扯 · 如何正确地吐槽"}]
     */

    private String date;
    private List<StoriesBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public static class StoriesBean {
        /**
         * images : ["http://pic1.zhimg.com/13f2808a86d85a04bc35ed75fea9fb20.jpg"]
         * type : 0
         * id : 9276341
         * ga_prefix : 031122
         * title : 小事 · 战略家外婆
         */

        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
