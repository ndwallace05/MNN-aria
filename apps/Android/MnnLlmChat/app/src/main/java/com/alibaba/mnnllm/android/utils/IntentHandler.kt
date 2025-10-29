// Created by jules on 2024/10/28.
// Copyright (c) 2024 Alibaba Group Holding Limited All rights reserved.

package com.alibaba.mnnllm.android.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import androidx.core.content.ContextCompat.startActivity
import java.text.SimpleDateFormat
import java.util.Date

class IntentHandler(private val context: Context) {

    fun sendEmail(recipient: String, subject: String, message: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun createCalendarEvent(title: String, location: String, beginTimeString: String, endTimeString: String) {
        val beginTime = parseDateTime(beginTimeString)
        val endTime = parseDateTime(endTimeString)
        if (beginTime != null && endTime != null) {
            val intent = Intent(Intent.ACTION_INSERT).apply {
                data = CalendarContract.Events.CONTENT_URI
                putExtra(CalendarContract.Events.TITLE, title)
                putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.time)
                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.time)
            }
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            }
        }
    }

    private fun parseDateTime(dateTimeString: String): Date? {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return try {
            format.parse(dateTimeString)
        } catch (e: Exception) {
            null
        }
    }

    fun openWebPage(url: String) {
        val webpage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun createTaskWithReminder(title: String, notes: String, minutes: Int) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.Events.DESCRIPTION, notes)
            putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=1")
            putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }
}
