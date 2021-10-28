package br.com.iesb.qualeonumero.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.iesb.qualeonumero.model.Repository

class NumberViewModelFactory(private val repository: Repository):
    ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NumberViewModel(repository) as T
    }

}