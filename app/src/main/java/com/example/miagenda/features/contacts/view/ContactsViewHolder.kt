package com.example.miagenda.features.contacts.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.miagenda.databinding.ItemContactsBinding
import com.example.miagenda.model.ContactsModel

class ContactsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemContactsBinding.bind(view)

    fun render(
        contactsModel: ContactsModel,
        onClickListener: (ContactsModel) -> Unit,
        onDeleteListener: (ContactsModel) -> Unit
    ) {
        binding.name.text = contactsModel.name
        binding.lastName.text = contactsModel.lastName
        binding.phone.text = contactsModel.phone
        binding.mail.text = contactsModel.mail
        itemView.setOnClickListener {
            onClickListener(contactsModel)
        }
        binding.iconDelete.setOnClickListener {
            onDeleteListener(contactsModel)
        }
    }
}