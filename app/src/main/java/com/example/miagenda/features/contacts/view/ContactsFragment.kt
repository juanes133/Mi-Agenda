package com.example.miagenda.features.contacts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miagenda.ContactsApplication
import com.example.miagenda.databinding.FragmentContactsBinding
import com.example.miagenda.features.base.DiaryFragment
import com.example.miagenda.features.contacts.viewmodel.ContactsViewModel
import com.example.miagenda.features.contacts.viewmodel.ContactsViewModelFactory
import com.example.miagenda.model.ContactsModel
import java.util.*

class ContactsFragment : DiaryFragment() {

    private lateinit var binding: FragmentContactsBinding
    private var adapter: ContactsAdapter? = null
    private val contactsViewModel: ContactsViewModel by viewModels {
        ContactsViewModelFactory(
            (activity?.application as ContactsApplication).contactsRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(
                ContactsFragmentDirections.actionContactsFragmentToAddContactFragment(
                    (UUID.randomUUID().toString())))
        }
        contactsViewModel.contactsList.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { list ->
                initRecyclerView(list)
                binding.contactsContainer.isVisible = true
            }
        }
        contactsViewModel.contactsDeleted.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { contact ->
                binding.contactsContainer.isVisible = true
                adapter?.remove(contact)
            }
        }
        contactsViewModel.contactsError.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
            binding.contactsContainer.isVisible = false
        }
        return binding.root
    }

    private fun initRecyclerView(list: ArrayList<ContactsModel>) {
        binding.recyclerContacts.layoutManager = LinearLayoutManager(context)
        adapter = ContactsAdapter(list, {
                findNavController().navigate(
                    ContactsFragmentDirections.actionContactsFragmentToAddContactFragment(
                        (it.id)))
            }, {
                contactsViewModel.deleteContacts(it)
            })
        binding.recyclerContacts.adapter = adapter

    }

    private fun getContacts() {
        activity?.applicationContext.let {
            contactsViewModel.getContacts()
        }
    }

    override fun onResume() {
        super.onResume()
        getContacts()
    }
}