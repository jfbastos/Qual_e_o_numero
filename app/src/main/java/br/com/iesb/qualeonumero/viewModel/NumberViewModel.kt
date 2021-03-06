package br.com.iesb.qualeonumero.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.iesb.qualeonumero.model.RandomNumber
import br.com.iesb.qualeonumero.model.Repository
import kotlinx.coroutines.launch

class NumberViewModel(private val repository : Repository) : ViewModel() {

    private val _numberLiveData = MutableLiveData<RandomNumber>()
    val numberLiveData : LiveData<RandomNumber> get() = _numberLiveData

    fun getNumber(){
        viewModelScope.launch {
            try{
                val response = repository.fetchRandomNumber()
                if(response.isSuccessful){
                    _numberLiveData.postValue(response.body())
                    println(response.body())
                }else{
                    _numberLiveData.postValue(RandomNumber(response.code()))
                    println(response.code())
                }
            }catch (e : Exception){
                println(e.localizedMessage)
            }
        }
    }
}