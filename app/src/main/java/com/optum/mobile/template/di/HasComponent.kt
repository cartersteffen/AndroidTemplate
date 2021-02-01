package com.optum.mobile.template.di

interface HasComponent<C> {
    fun getComponent(): C
}
