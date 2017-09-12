package com.hongfans.rxjava.rxcustom;

import android.net.Uri;

import java.util.List;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/12 22:08
 */
public interface Api{

    List<News> getNewsList(String tag);

    Uri save(News news);

}
