package br.com.jwar.sharedbill.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String? = null
) : Parcelable