package com.example.miagenda.features.addcontacts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.miagenda.ContactsApplication
import com.example.miagenda.databinding.FragmentAddContactBinding
import com.example.miagenda.features.addcontacts.viewmodel.AddContactViewModel
import com.example.miagenda.features.addcontacts.viewmodel.AddContactsViewModelFactory
import com.example.miagenda.features.base.DiaryFragment
import com.example.miagenda.model.ContactsModel
import java.util.*

class AddContactFragment : DiaryFragment() {

    private lateinit var binding: FragmentAddContactBinding
    private var contacts: ContactsModel? = null
    private val args: AddContactFragmentArgs by navArgs()
    private val addContactViewModel: AddContactViewModel by viewModels {
        AddContactsViewModelFactory(
            (activity?.application as ContactsApplication).contactsRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddContactBinding.inflate(inflater, container, false)
        addContactViewModel.findById(args.idcontacts)
        binding.btnAddContact.isEnabled = false
        addContactViewModel.contactsList.observe(viewLifecycleOwner) { it ->
            it.getContentIfNotHandled()?.let { contactDB ->
                contacts = contactDB.firstOrNull()
                contacts?.let {
                    binding.name.setText(it.name)
                    binding.lastName.setText(it.lastName)
                    binding.phone.setText(it.phone)
                    binding.mail.setText(it.mail)
                }
                binding.btnAddContact.isEnabled = true
            }
        }
        contacts?.let {
            binding.name.setText(it.name)
            binding.lastName.setText(it.lastName)
            binding.phone.setText(it.phone)
            binding.mail.setText(it.mail)
        }
        binding.btnAddContact.setOnClickListener {
            if (contacts == null) {
                contacts = ContactsModel(UUID.randomUUID().toString(),
                    binding.name.text.toString(),
                    binding.lastName.text.toString(),
                    binding.phone.text.toString(),
                    binding.mail.text.toString())
            } else {
                contacts?.let { contactsPeople ->
                    contacts = ContactsModel(contactsPeople.id,
                        binding.name.text.toString(),
                        binding.lastName.text.toString(),
                        binding.phone.text.toString(),
                        binding.mail.text.toString())
                }
            }
            insertContacts()
        }
        addContactViewModel.contactsInsert.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }
        addContactViewModel.contactsError.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    private fun insertContacts() {
        context?.let {
            contacts?.let { contact ->
                addContactViewModel.insertContacts(contact)
            }
        }
    }
}