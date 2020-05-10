package com.example.smartpark.Adapters

import com.example.smartpark.Models.Event
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.smartpark.R

class EventListAdapter (
    var mContext: Context,
    var resource: Int,
    var events: MutableList<Event>) : ArrayAdapter<Event>(mContext, resource, events) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val view = layoutInflater.inflate(resource, null)

        val eventTitleView = view.findViewById<TextView>(R.id.eventTitle)
        val instituteNameView = view.findViewById<TextView>(R.id.instituteName)
        val dateAndTimeView = view.findViewById<TextView>(R.id.dateAndTime)

        var event = events.get(position)
        eventTitleView.text = event.getEventTitle()
        instituteNameView.text = "Local: " + event.getInstituteName()
        dateAndTimeView.text = ""
        dateAndTimeView.append(event.getDate() + " - " + event.getTime())

        return view
    }
}