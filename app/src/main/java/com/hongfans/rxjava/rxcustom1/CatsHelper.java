package com.hongfans.rxjava.rxcustom1;

import android.net.Uri;

import java.util.Collections;
import java.util.List;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/9/27 22:00
 */
public class CatsHelper{

    interface CutestCatCallback{

        void onCutestCatSaved(Uri uri);

        void onQueryFailed(Exception e);
    }

    Api api = new ApiImpl();


    public void saveTheCutestCat(String query, CutestCatCallback cutestCatCallback){
        api.queryCats(query, new Api.CatsQueryCallback(){
            @Override
            public void onCatListReceived(List<Cat> cats){
                Cat cutest = findCutest(cats);
                api.store(cutest, new Api.StoreCallback(){
                    @Override
                    public void onCatStored(Uri uri){
                        cutestCatCallback.onCutestCatSaved(uri);
                    }

                    @Override
                    public void onStoreFailed(Exception e){
                        cutestCatCallback.onQueryFailed(e);
                    }
                });
            }

            @Override
            public void onError(Exception e){
                cutestCatCallback.onQueryFailed(e);
            }
        });
    }

    private Cat findCutest(List<Cat> cats){
        return Collections.max(cats);
    }

    // -----------------------------------
    ApiWrapper apiWrapper;

    public void saveTheCutestCat(String query, Callback<Uri> cutestCallback){
        apiWrapper.queryCats(query, new Callback<List<Cat>>(){
            @Override
            public void onResult(List<Cat> result){
                Cat cutest = findCutest(result);
                apiWrapper.store(cutest, cutestCallback);
            }

            @Override
            public void onError(Exception e){
                cutestCallback.onError(e);
            }
        });
    }

    // --------------------------------------

    public AsyncJob<Uri> saveTheCutestCat(String query){
        return new AsyncJob<Uri>(){
            @Override
            public void start(Callback<Uri> cutestCallback){
                apiWrapper.queryCats(query)
                          .start(new Callback<List<Cat>>(){
                              @Override
                              public void onResult(List<Cat> result){
                                  Cat cutest = findCutest(result);
                                  apiWrapper.store(cutest)
                                            .start(new Callback<Uri>(){
                                                @Override
                                                public void onResult(Uri result){
                                                    cutestCallback.onResult(result);
                                                }

                                                @Override
                                                public void onError(Exception e){
                                                    cutestCallback.onError(e);
                                                }
                                            });
                              }

                              @Override
                              public void onError(Exception e){
                                  cutestCallback.onError(e);
                              }
                          });
            }
        };
    }


    public AsyncJob<Uri> saveTheCutestCat1(String query){
        AsyncJob<List<Cat>> catsListAsyncJob = apiWrapper.queryCats(query);
        AsyncJob<Cat> cutestCatAsyncJob = new AsyncJob<Cat>(){
            @Override
            public void start(Callback<Cat> callback){
                catsListAsyncJob.start(new Callback<List<Cat>>(){
                    @Override
                    public void onResult(List<Cat> result){
                        callback.onResult(findCutest(result));
                    }

                    @Override
                    public void onError(Exception e){
                        callback.onError(e);
                    }
                });
            }
        };
        AsyncJob<Uri> storeUriAsyncJob = new AsyncJob<Uri>(){
            @Override
            public void start(Callback<Uri> cutestCatCallback){
                cutestCatAsyncJob.start(new Callback<Cat>(){
                    @Override
                    public void onResult(Cat cutest){
                        apiWrapper.store(cutest)
                                  .start(new Callback<Uri>(){
                                      @Override
                                      public void onResult(Uri result){
                                          cutestCatCallback.onResult(result);
                                      }

                                      @Override
                                      public void onError(Exception e){
                                          cutestCatCallback.onError(e);
                                      }
                                  });
                    }

                    @Override
                    public void onError(Exception e){
                        cutestCatCallback.onError(e);
                    }
                });
            }
        };

        return storeUriAsyncJob;
    }

    // ---------------------------------------

    public AsyncJob<Uri> saveTheCutestCat2(String query){
        AsyncJob<List<Cat>> catsListAsyncJob = apiWrapper.queryCats(query);
        AsyncJob<Cat> cutestCatAsyncJob = catsListAsyncJob.map(new Func<List<Cat>, Cat>(){
            @Override
            public Cat call(List<Cat> cats){
                return findCutest(cats);
            }
        });
        AsyncJob<Uri> storedUriAsyncJob = new AsyncJob<Uri>(){
            @Override
            public void start(Callback<Uri> cutestCatCallback){
                cutestCatAsyncJob.start(new Callback<Cat>(){
                    @Override
                    public void onResult(Cat cutest){
                        apiWrapper.store(cutest)
                                  .start(new Callback<Uri>(){
                                      @Override
                                      public void onResult(Uri result){
                                          cutestCatCallback.onResult(result);
                                      }

                                      @Override
                                      public void onError(Exception e){
                                          cutestCatCallback.onError(e);
                                      }
                                  });
                    }

                    @Override
                    public void onError(Exception e){
                        cutestCatCallback.onError(e);
                    }
                });
            }
        };

        return storedUriAsyncJob;
    }

    public AsyncJob<Uri> saveTheCutestCat3(String query){
        AsyncJob<List<Cat>> catsListAsyncJob = apiWrapper.queryCats(query);
        AsyncJob<Cat> cutestCatAsyncJob = catsListAsyncJob.map(new Func<List<Cat>, Cat>(){
            @Override
            public Cat call(List<Cat> cats){
                return findCutest(cats);
            }
        });
        AsyncJob<Uri> storeUriAsyncJob = cutestCatAsyncJob.flatMap(new Func<Cat, AsyncJob<Uri>>(){
            @Override
            public AsyncJob<Uri> call(Cat cat){
                return apiWrapper.store(cat);
            }
        });

        return storeUriAsyncJob;
    }

    public AsyncJob<Uri> saveTheCutestCat4(String query){
        AsyncJob<List<Cat>> catsListAsyncJob = apiWrapper.queryCats(query);
//        AsyncJob<Cat> cutestCatAsyncJob = catsListAsyncJob.map(cats -> findCutest(cats));
        AsyncJob<Cat> cutestCatAsyncJob = catsListAsyncJob.map(this :: findCutest);
        AsyncJob<Uri> storeUriAsyncJob = cutestCatAsyncJob.flatMap(cutest -> apiWrapper.store(cutest));

        return storeUriAsyncJob;
    }

}
