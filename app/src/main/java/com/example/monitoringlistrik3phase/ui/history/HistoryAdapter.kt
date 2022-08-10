package com.example.monitoringlistrik3phase.ui.historyimport android.view.LayoutInflaterimport android.view.ViewGroupimport androidx.recyclerview.widget.RecyclerViewimport com.example.monitoringlistrik3phase.databinding.ItemRiyawatNewBindingclass HistoryAdapter(private val listData: ArrayList<HistoryModel>) :    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {    inner class HistoryViewHolder(private val binding: ItemRiyawatNewBinding) :        RecyclerView.ViewHolder(binding.root) {        fun bind(historyModel: HistoryModel) {            binding.apply {                historyModel.apply {                    tvDate.text = date                    tvAmpereStatus.text = ampere.plus(" A")                    tvTeganganStatus.text = tegangan.plus(" V")                    tvDayaStatus.text = daya.plus(" W")                    tvFasa.text = listrik                    tvPersentase.text = persentase.plus(" %")                    when {                        persentase.toInt() < 50 -> {                            tvStatuesPersentase.text = "Tidak Stabil"                        }                        persentase.toInt() > 50 -> {                            tvStatuesPersentase.text = "Stabil"                        }                    }                }            }        }    }    override fun onCreateViewHolder(        parent: ViewGroup,        viewType: Int,    ): HistoryAdapter.HistoryViewHolder {        return HistoryViewHolder(ItemRiyawatNewBinding.inflate(LayoutInflater.from(parent.context),            parent,            false))    }    override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {        holder.bind(listData[position])    }    override fun getItemCount(): Int = listData.size}