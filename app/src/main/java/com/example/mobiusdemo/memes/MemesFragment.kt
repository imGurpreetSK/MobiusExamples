package com.example.mobiusdemo.memes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mobiusdemo.base.*
import com.example.mobiusdemo.databinding.MemesFragmentBinding
import com.example.mobiusdemo.stranger.Meme
import com.jakewharton.rxbinding4.view.clicks
import com.spotify.mobius.Init
import com.spotify.mobius.Update
import com.spotify.mobius.functions.Consumer
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@AndroidEntryPoint
class MemesFragment : BaseScreen<
        MemesScreenKey,
        MemesFragmentBinding,
        MemesModel,
        MemesEvent,
        MemesEffect,
        MemesEffect.MemesViewEffect
>(), MemesUi, MemesUiActions {

    @Inject
    lateinit var memesEffectHandlerFactory: MemesEffectHandler.Factory

    override fun defaultModel(): MemesModel = MemesModel(emptyList())

    override fun bindView(layoutInflater: LayoutInflater, container: ViewGroup?): MemesFragmentBinding =
        MemesFragmentBinding.inflate(layoutInflater, container, false)

    override fun events(): Observable<MemesEvent> {
        return binding.retry.clicks().map { MemesEvent.Retry }
    }

    override fun createInit(): Init<MemesModel, MemesEffect> = Init {
        first(MemesModel(emptyList()), MemesEffect.LoadMemes, MemesEffect.Loading)
    }

    override fun createUpdate(): Update<MemesModel, MemesEvent, MemesEffect> = MemesUpdate()

    override fun createEffectHandler(
        viewEffectsConsumer: Consumer<MemesEffect.MemesViewEffect>
    ): ObservableTransformer<MemesEffect, MemesEvent> = memesEffectHandlerFactory
        .create(viewEffectsConsumer)
        .build()

    override fun uiRenderer(): ViewRenderer<MemesModel> = MemesUiRenderer(this)

    override fun viewEffectHandler(): ViewEffectsHandler<MemesEffect.MemesViewEffect> = MemesUiEffectsHandler(this)

    override fun renderMemes(memes: List<Meme>) {
        binding.retry.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.memes.visibility = View.VISIBLE

        binding.memes.text = memes.fold("") { acc, meme ->
            "$acc\n${meme.id}"
        }
    }

    override fun showLoading() {
        binding.retry.visibility = View.GONE
        binding.memes.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun showFetchSuccessful() {
        Toast.makeText(context, "Fetch successful", Toast.LENGTH_SHORT).show()
    }

    override fun showRetry() {
        binding.progressBar.visibility = View.GONE
        binding.memes.visibility = View.GONE
        binding.retry.visibility = View.VISIBLE
    }
}

@Parcelize
class MemesScreenKey : ScreenKey() {

    override val analyticsName: String
        get() = TODO("Not yet implemented")

    override fun instantiateFragment(): Fragment {
        TODO("Not yet implemented")
    }
}
