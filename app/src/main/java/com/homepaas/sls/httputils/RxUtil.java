package com.homepaas.sls.httputils;

import android.util.Log;

import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.newmvp.base.BaseView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxUtil {
    /**
     * 公共处理，线程切换代码
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                //.retry(0) 错误重试机制，值为重试次数
                return tObservable
                        .retry(0)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


//    /**
//     * 支持背压
//     * <p>
//     * 公共处理，线程切换代码
//     *
//     * @param <T>
//     * @return
//     */
//    public static <T> FlowableTransformer<T, T> applyFlowableSchedulers() {
//        return new FlowableTransformer<T, T>() {
//            @Override
//            public Publisher<T> apply(Flowable<T> upstream) {
//                //.retry(0) 错误重试机制，值为重试次数
//                return upstream.retry(0)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }


    /**
     * 返回结果处理（compose）(Observable)
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<ResponseWrapper<T>, T> handleResponseWrapperObservable() {
        return new Observable.Transformer<ResponseWrapper<T>, T>() {

            @Override
            public Observable<T> call(Observable<ResponseWrapper<T>> responseWrapperObservable) {
                return responseWrapperObservable.concatMap(new Func1<ResponseWrapper<T>, Observable<? extends T>>() {
                    @Override
                    public Observable<? extends T> call(ResponseWrapper<T> tResponseWrapper) {
                        Meta meta = tResponseWrapper.meta;
                        if (meta.isSuccess()) {
                            return createDataObservable(tResponseWrapper.data);
                        } else {
                            composeResponseWrapperErrorCode(tResponseWrapper);
                            return Observable.error(new ApiException(tResponseWrapper.meta.getErrorMsg(), tResponseWrapper.meta.getErrorCode()));
                        }
                    }
                });
            }
        };
    }

//    /**
//     * 返回结果处理（compose）(Flowable)
//     *
//     * @param <T>
//     * @return
//     */
//    public static <T> FlowableTransformer<ResponseWrapper<T>, T> handleResponseWrapperFlowable() {
//        return new FlowableTransformer<ResponseWrapper<T>, T>() {
//            @Override
//            public Publisher<T> apply(Flowable<ResponseWrapper<T>> upstream) {
//                return upstream.concatMap(new Function<ResponseWrapper<T>, Publisher<? extends T>>() {
//                    @Override
//                    public Publisher<? extends T> apply(ResponseWrapper<T> tResponseWrapper) throws Exception {
//                        if (tResponseWrapper.isSuccess()) {
//                            return createDataFlowable(tResponseWrapper.getBusinessComment());
//                        } else {
//                            return Flowable.error(new ApiException(tResponseWrapper.getMsg(), tResponseWrapper.getCode(), tResponseWrapper.getEnumCode()));
//                        }
//                    }
//                });
//            }
//        };
//    }

//    /**
//     * 返回结果处理（compose）(Observable)---只返回json中的msg数据
//     *
//     * @return
//     */
//    public static ObservableTransformer<ResponseWrapper<Object>, String> handleResponseWrapperMessage() {
//        return new ObservableTransformer<ResponseWrapper<Object>, String>() {
//            @Override
//            public ObservableSource<String> apply(Observable<ResponseWrapper<Object>> upstream) {
//                return upstream.concatMap(new Function<ResponseWrapper<Object>, ObservableSource<String>>() {
//                    @Override
//                    public ObservableSource<String> apply(ResponseWrapper<Object> tResponseWrapper) throws Exception {
//                        if (tResponseWrapper.isSuccess()) {
//                            return createDataObservable(tResponseWrapper.getMsg());
//                        } else {
//                            composeResponseWrapperErrorCode(tResponseWrapper);
//                            return Observable.error(new ApiException(tResponseWrapper.getMsg(), tResponseWrapper.getCode(), tResponseWrapper.getEnumCode()));
//                        }
//                    }
//                });
//            }
//        };
//    }

//    /**
//     * 返回结果处理（compose）(Flowable)---只返回json中的msg数据
//     *
//     * @return
//     */
//    public static FlowableTransformer<ResponseWrapper<Object>, String> handleResponseWrapperMessageFlowable() {
//        return new FlowableTransformer<ResponseWrapper<Object>, String>() {
//            @Override
//            public Publisher<String> apply(Flowable<ResponseWrapper<Object>> upstream) {
//                return upstream.concatMap(new Function<ResponseWrapper<Object>, Publisher<String>>() {
//                    @Override
//                    public Publisher<String> apply(ResponseWrapper<Object> tResponseWrapper) throws Exception {
//                        if (tResponseWrapper.isSuccess()) {
//                            return createDataFlowable(tResponseWrapper.getMsg());
//                        } else {
//                            return Flowable.error(new ApiException(tResponseWrapper.getMsg(), tResponseWrapper.getCode(), tResponseWrapper.getEnumCode()));
//                        }
//                    }
//                });
//            }
//        };
//    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createDataObservable(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (t != null) {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } else {
                    Log.e("Result", " t is null");
                    subscriber.onError(new ApiException("", "0", ""));
                }
            }
        });
    }

//    /**
//     * 生成 Flowable
//     * 默认采用BackpressureStrategy.LATEST
//     *
//     * @param <T>
//     * @return
//     */
//    public static <T> Publisher<T> createDataFlowable(final T t) {
//        return createDataFlowable(t, BackpressureStrategy.LATEST);
//    }

//    /**
//     * 生成 Flowable
//     *
//     * @param t
//     * @param mode
//     * @param <T>
//     * @return
//     */
//    public static <T> Publisher<T> createDataFlowable(final T t, BackpressureStrategy mode) {
//        return Flowable.create(new FlowableOnSubscribe<T>() {
//            @Override
//            public void subscribe(FlowableEmitter<T> s) throws Exception {
//                if (t != null) {
//                    s.onNext(t);
//                    s.onComplete();
//                } else {
//                    s.onError(new ApiException("", 0, ""));
//                }
//            }
//        }, mode);
//    }
    //----------------------------------------------------rxJava控制进度条--------------------

    /**
     * 指定开始和结束时显示进度条的Observable（compose）(Observable)
     * @param view  调用弹框依赖的载体
     * @param cancelable 弹框师傅可以按返回键进行取消
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> progressDialogObservable(final BaseView view, final boolean cancelable) {
        return new Observable.Transformer<T, T>() {

            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (view != null) {
                            view.showLoading(cancelable);
                        }
                    }
                }).doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        if (view != null)
                            view.hideLoading();
                    }
                }); // 指定主线程
            }
        };
    }
    /**
     * 指定开始和结束时显示进度条的Observable（compose）(Observable)
     * @param view  调用弹框依赖的载体
     * @param cancelable 弹框师傅可以按返回键进行取消
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> progressDialogObservable(final BaseView view, final boolean cancelable,boolean isHideCommon) {
        return new Observable.Transformer<T, T>() {

            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (view != null) {
                            view.showLoading(cancelable);
                        }
                    }
                }).doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        if (view != null)
                            view.hideLoading();
                    }
                }); // 指定主线程
            }
        };
    }

//    /**
//     * 指定开始和结束时显示进度条的Flowable（compose）(Flowable)
//     *
//     * @param <T>
//     * @return
//     */
//    public static <T> FlowableTransformer<T, T> progressDialogFlowable(final BaseView view, final String text, final boolean cancelable) {
//        return new FlowableTransformer<T, T>() {
//            @Override
//            public Publisher<T> apply(Flowable<T> upstream) {
//                return upstream.doOnSubscribe(new Consumer<Subscription>() {
//                    @Override
//                    public void accept(Subscription subscription) throws Exception {
//                        if (view != null)
//                            view.showLoading(text, cancelable);
//                    }
//                }).doOnTerminate(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        if (view != null)
//                            view.hideLoading();
//                    }
//                });
//            }
//        };
//    }


    /**
     * 指定开始和结束时显示进度条的Observable（compose）(Observable)
     *
     * @param view
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> progressDialogObservable(final BaseView view) {
        return progressDialogObservable(view, true);
    }

    /**
     * 指定开始和结束时显示进度条的Flowable（compose）(Flowable)
     *
     * @param view
     * @param text
     * @param <T>
     * @return
     */
//    public static <T> FlowableTransformer<T, T> progressDialogFlowable(final BaseView view) {
//        return progressDialogFlowable(view, true);
//    }
    //----------------------------------------------------rxJava控制进度条--------------------

    /**
     * 后台返回错误code统一进行处理
     */
    public static void composeResponseWrapperErrorCode(ResponseWrapper responseWrapper) {
        if (responseWrapper.meta.isAuthFailed()) {
//                            subscriber.onError(new AuthException(meta.getErrorMsg()));
        }

//                        else {
//                            subscriber.onError(new GetDataException(meta.getErrorMsg()));
//}
    }
}
