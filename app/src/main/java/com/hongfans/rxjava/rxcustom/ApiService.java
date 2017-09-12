package com.hongfans.rxjava.rxcustom;

import android.net.Uri;

import java.util.List;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/12 22:15
 */
public class ApiService{

    public Uri getUriSync(String tag){
        Api api = new ApiImpl();
        List<News> newsList = api.getNewsList(tag);
        News latestNews = getLatestNews(newsList);
        Uri uri = api.save(latestNews);
        return uri;
    }


    public News getLatestNews(List<News> list){
        if(list != null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    public void getUriAsync(String tag, final Callback<Uri> callback){
        final ApiAsync api = new ApiAsync();
        api.getNewsList(tag, new Callback<List<News>>(){
            @Override
            public void onSuccess(List<News> list){
                News latestNews = getLatestNews(list);
                api.save(latestNews, new Callback<Uri>(){
                    @Override
                    public void onSuccess(Uri list){
                        callback.onSuccess(list);
                    }

                    @Override
                    public void onFailure(Exception e){
                        callback.onFailure(e);
                    }
                });
            }

            @Override
            public void onFailure(Exception e){

            }
        });
    }

    public AsyncWork<Uri> getUriWork(final String tag){
        final ApiWork apiWork = new ApiWork();
        return new AsyncWork<Uri>(){
            @Override
            public void start(final Callback<Uri> call){
                AsyncWork<List<News>> newsList = apiWork.getNewsList(tag);
                newsList.start(new Callback<List<News>>(){
                    @Override
                    public void onSuccess(List<News> list){
                        News latestNews = getLatestNews(list);
                        AsyncWork<Uri> save = apiWork.save(latestNews);
                        save.start(call);
                    }

                    @Override
                    public void onFailure(Exception e){
                        call.onFailure(e);
                    }
                });
            }
        };
    }

    // 事件变换
    public AsyncWork<Uri> getUriTransform(String tag){
        final ApiWork apiWork = new ApiWork();
        // 1
        final AsyncWork<List<News>> newsListWork = apiWork.getNewsList(tag);
        // 2
        final AsyncWork<News> latestNewsAsyncWork = new AsyncWork<News>(){
            @Override
            public void start(final Callback<News> call){
                newsListWork.start(new Callback<List<News>>(){
                    @Override
                    public void onSuccess(List<News> list){
                        News latestNews = getLatestNews(list);
                        call.onSuccess(latestNews);
                    }

                    @Override
                    public void onFailure(Exception e){
                        call.onFailure(e);
                    }
                });
            }
        };
        // 3
        AsyncWork<Uri> uriAsyncWork = new AsyncWork<Uri>(){
            @Override
            public void start(final Callback<Uri> call){
                latestNewsAsyncWork.start(new Callback<News>(){
                    @Override
                    public void onSuccess(News list){
                        AsyncWork<Uri> save = apiWork.save(list);
                        save.start(call);
                    }

                    @Override
                    public void onFailure(Exception e){

                    }
                });
            }
        };

        return uriAsyncWork;
    }

    public AsyncWork<Uri> getUriOperation(String tag){
        final ApiWork apiWork = new ApiWork();
        // 1
        final AsyncWork<List<News>> newsListWork = apiWork.getNewsList(tag);
        // 2
        final AsyncWork<News> latestNewsAsyncWork = newsListWork.map(new Func<List<News>, News>(){
            @Override
            public News call(List<News> list){
                return getLatestNews(list);
            }
        });

        /*final AsyncWork<News> latestNewsAsyncWork = new AsyncWork<News>(){
            @Override
            public void start(final Callback<News> call){
                newsListWork.start(new Callback<List<News>>(){
                    @Override
                    public void onSuccess(List<News> list){
                        News latestNews = getLatestNews(list);
                        call.onSuccess(latestNews);
                    }

                    @Override
                    public void onFailure(Exception e){
                        call.onFailure(e);
                    }
                });
            }
        };*/
        // 3
        AsyncWork<Uri> asyncWorkAsyncWork = latestNewsAsyncWork.flatMap(new Func<News, AsyncWork<Uri>>(){
            @Override
            public AsyncWork<Uri> call(News news){
                return apiWork.save(news);
            }
        });

        /*AsyncWork<Uri> uriAsyncWork = new AsyncWork<Uri>(){
            @Override
            public void start(final Callback<Uri> call){
                latestNewsAsyncWork.start(new Callback<News>(){
                    @Override
                    public void onSuccess(News list){
                        AsyncWork<Uri> save = apiWork.save(list);
                        save.start(call);
                    }

                    @Override
                    public void onFailure(Exception e){

                    }
                });
            }
        };*/

        return asyncWorkAsyncWork;
//        return uriAsyncWork;
    }


}
