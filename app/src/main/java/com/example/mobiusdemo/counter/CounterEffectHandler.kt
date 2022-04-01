package com.example.mobiusdemo.counter

import android.content.Context
import android.widget.Toast
import com.spotify.mobius.functions.Consumer
import com.spotify.mobius.rx3.RxMobius
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer

class CounterEffectHandler @AssistedInject constructor(
    @Assisted viewEffectsConsumer: Consumer<Unit>
) {

    fun build(context: Context): ObservableTransformer<CounterEffect, CounterEvent> {
        return RxMobius.subtypeEffectHandler<CounterEffect, CounterEvent>()
            .addAction(ZeroCount::class.java, {
                Toast.makeText(context, "Zero!", Toast.LENGTH_SHORT).show()
            }, AndroidSchedulers.mainThread())
            .build()
    }

    @AssistedFactory
    interface Factory {
        fun create(viewEffectsConsumer: Consumer<Unit>): CounterEffectHandler
    }
}
