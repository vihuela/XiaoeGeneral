package com.xiaoe.audioui

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.IComponent
import com.xiaoe.app_base.ComponentRes

class AudioUIComponent : IComponent {
    override fun onCall(cc: CC): Boolean {
        when (cc.actionName) {
        }
        return true
    }

    override fun getName(): String = ComponentRes.AudioUIComponentName
}