package com.example.anastasia.enggrammar;

import com.example.anastasia.enggrammar.POJO.Question;
import com.example.anastasia.enggrammar.POJO.Test;
import io.reactivex.disposables.Disposable;

/**
 * Created by anastasia on 1/8/18.
 */

public interface RxJava {


    Disposable writeToRoomRx(String uAnswer, String id);

    Disposable addTToRoomRx(Test test);

    Disposable addQToRoomRx(Question question);

    Disposable readAnswerWithRoomRx(String id, int position);

    Disposable deleteFromRoomRx (String id);

    Disposable updateCheckedRoomRx (Boolean value, String id);

}
