package me.hammarstrom.copycatlibrary

import android.content.Context
import android.content.Intent
import me.hammarstrom.copycatlibrary.ui.CopycatActivity

class Copycat {

    companion object {
        fun getLaunchUiIntent(context: Context) =
                Intent(context, CopycatActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

}
