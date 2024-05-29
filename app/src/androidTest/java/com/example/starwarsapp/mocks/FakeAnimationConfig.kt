package com.example.starwarsapp.mocks

import com.example.starwarsapp.domain.utils.AnimationConfigInterface

object FakeAnimationConfig : AnimationConfigInterface {
    override val isAnimationEnabled: Boolean = false
}