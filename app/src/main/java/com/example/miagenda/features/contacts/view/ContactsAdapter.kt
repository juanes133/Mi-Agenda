package com.example.miagenda.features.contacts.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.miagenda.R
import com.example.miagenda.model.ContactsModel

class ContactsAdapter(
    private val contactsList: ArrayList<ContactsModel>,
    private val onClickListener: (ContactsModel) -> Unit,
    private val onDeleteListener: (ContactsModel) -> Unit,
) :
    RecyclerView.Adapter<ContactsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ContactsViewHolder(
            layoutInflater.inflate(R.layout.item_contacts, parent, false))
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.render(contactsList[position], onClickListener, onDeleteListener)
    }

    fun remove(contactsModel: ContactsModel) {
        val index = contactsList.indexOf(contactsModel)
        contactsList.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, contactsList.size)
    }

    override fun getItemCount(): Int = contactsList.size
}