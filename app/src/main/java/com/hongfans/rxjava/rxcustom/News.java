package com.hongfans.rxjava.rxcustom;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/12 22:10
 */
public class News{

    private String time;
    private String content;

    public News(String time, String content){
        this.time = time;
        this.content = content;
    }

    @Override
    public String toString(){
        return "News{" +
               "time='" + time + '\'' +
               ", content='" + content + '\'' +
               '}';
    }
}
