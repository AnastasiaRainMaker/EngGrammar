package com.example.anastasia.enggrammar;

import io.reactivex.disposables.Disposable;

/**
 * Created by anastasia on 1/8/18.
 */

public interface RxJava {

    Disposable readWithRX (OnFinishedListener listener, String id, int position);

    interface OnFinishedListener {
        void onFinished(String uAnswer, int position);
        void onError(String message);
    }

}
