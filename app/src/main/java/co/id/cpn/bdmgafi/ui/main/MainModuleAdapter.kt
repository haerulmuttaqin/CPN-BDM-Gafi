package co.id.cpn.bdmgafi.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.id.cpn.bdmgafi.R
import co.id.cpn.entity.Module
import com.google.android.material.card.MaterialCardView
import kotlin.jvm.internal.Intrinsics


class MainModuleAdapter(onItemClicked2: Function1<Module?, Unit>) :
    RecyclerView.Adapter<MainModuleAdapter.ViewHolder?>() {
    private val list = arrayListOf<Module>()

    /* access modifiers changed from: private */
    val onItemClicked: Function1<Module?, Unit> = onItemClicked2
    fun getList(): ArrayList<Module> {
        return list
    }

    fun submitData(dataList: List<Module>?) {
        list.addAll(dataList!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Intrinsics.checkNotNullParameter(parent, "parent")
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.item_module, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Intrinsics.checkNotNullParameter(holder, "holder")
        val module: Module = list[position]
        Intrinsics.checkNotNullExpressionValue(module, "list[position]")
        holder.bindWith(module, onItemClicked)
    }

    override fun getItemCount(): Int {
        val arrayList: ArrayList<Module> = list
        return (if (arrayList == null) null else Integer.valueOf(arrayList.size))!!.toInt()
    }

   class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView
        val menu: MaterialCardView
        val name: TextView
        fun getIcon(): ImageView {
            return icon
        }

        fun bindWith(module: Module, onItemClicked: Function1<Module?, Unit>) {
            Intrinsics.checkNotNullParameter(module, "module")
            name.text = module.moduleName
            icon.setImageDrawable(itemView.context.getDrawable(module.moduleIcon))
            menu.setOnClickListener(object : View.OnClickListener {
                /* synthetic */ var `f$1`: Module? = null
                override fun onClick(view: View?) {
                    onItemClicked(module)
                }
            })
        }

//        companion object {
//            /* access modifiers changed from: private */ /* renamed from: bindWith$lambda-0  reason: not valid java name */
//            fun `m509bindWith$lambda0`(
//                `this$02`: MainModuleAdapter,
//                `$module`: Module?,
//                it: View?
//            ) {
//                Intrinsics.checkNotNullParameter(`this$02`, "this$0")
//                Intrinsics.checkNotNullParameter(`$module`, "\$module")
//                `this$02`.onItemClicked.invoke(`$module`)
//            }
//        }

        init {
            val findViewById: TextView = itemView.findViewById(R.id.menu_name)
            name = findViewById
            val findViewById2: View = itemView.findViewById(R.id.menu_icon)
            icon = findViewById2 as ImageView
            val findViewById3: MaterialCardView = itemView.findViewById(R.id.menuButton)
            menu = findViewById3
        }
    }

}