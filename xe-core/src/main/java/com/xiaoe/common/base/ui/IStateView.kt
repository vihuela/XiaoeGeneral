
package com.xiaoe.common.base.ui

interface IStateView {
    fun showLoading() = Unit

    fun hideLoading() = Unit

    fun showEmpty() = Unit

    fun showContent() = Unit

    fun showMessage(content: String) = Unit

    fun showMessageFromNet(error: Any, content: String) = Unit

}
