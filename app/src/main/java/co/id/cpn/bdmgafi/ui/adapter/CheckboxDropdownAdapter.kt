package co.id.cpn.bdmgafi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import co.id.cpn.bdmgafi.R
import co.id.cpn.entity.Region


class CheckboxDropdownAdapter(
    private val mContext: Context,
    private val itemLayout: Int,
    private var dataList: List<Region>?
) :
    ArrayAdapter<Any?>(mContext, itemLayout, dataList!!) {
    private var isFromView = false
    override fun getCount(): Int {
        return dataList!!.size
    }

    override fun getItem(position: Int): Region? {
        return dataList!![position]
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var view = view
        if (view == null) {
            view = LayoutInflater.from(parent.context)
                .inflate(itemLayout, parent, false)
        }
        val strName = view!!.findViewById<TextView>(R.id.text) as TextView
        val checkBox = view.findViewById<CheckBox>(R.id.checkbox) as CheckBox
        val row = view.findViewById<CheckBox>(R.id.row) as RelativeLayout
        strName.text = getItem(position)?.regionName
        view.tag = position

        row.setOnClickListener {
            checkBox.isChecked = true
        }

        isFromView = true
        checkBox.isChecked = getItem(position)?.isSelected == true
        isFromView = false

        checkBox.tag = position
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            val getPosition = buttonView.tag as Int
            if (!isFromView) {
                getItem(position)?.isSelected = isChecked
            }
        }



        return view
    }
} 