package com.example.runyclub.ui.screens.profile

import android.net.Uri

interface ProfileEditActions {
    fun startEditing()

    fun saveUser()

    fun updateUserName(newName: String)

    fun updateImgUri(newUri: Uri?)

    fun cancelEditing()

    fun editAge()

    fun editWeight()
}