package br.com.jwar.sharedbill.utility

import android.content.Context
import br.com.jwar.sharedbill.core.utility.StringProvider

class AndroidStringProvider(private val context: Context) : StringProvider {
    override fun getString(resId: Int): String {
        return context.getString(resId)
    }
}