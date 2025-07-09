package com.lucasbmmn.timetracker.iu.clients

import androidx.compose.runtime.mutableStateListOf
import com.lucasbmmn.timetracker.data.dao.ClientDao
import com.lucasbmmn.timetracker.model.Client

class ClientsViewModel {
    private val clientDao = ClientDao()

    var clients = mutableStateListOf<Client>()
        private set

    init {
        this.loadClients()
    }

    private fun loadClients() {
        this.clients.clear()
        this.clients.addAll(this.clientDao.all)
    }

    fun addClient(client: Client) {
        this.clientDao.insert(client)
        this.loadClients()
    }

    fun deleteClient(client: Client) {
        this.clientDao.delete(client)
        this.loadClients()
    }

    fun editClient(newClient: Client) {
        this.clientDao.update(newClient)
        this.loadClients()
    }
}