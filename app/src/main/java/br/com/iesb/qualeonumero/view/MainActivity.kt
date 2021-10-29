package br.com.iesb.qualeonumero.view

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import br.com.iesb.qualeonumero.R
import br.com.iesb.qualeonumero.databinding.ActivityMainBinding
import br.com.iesb.qualeonumero.model.RandomNumber
import br.com.iesb.qualeonumero.model.Repository
import br.com.iesb.qualeonumero.viewModel.NumberViewModel
import br.com.iesb.qualeonumero.viewModel.NumberViewModelFactory
import dev.sasikanth.colorsheet.ColorSheet

/***
 *  Verificação de que se o número digitado é maior ou menor do que o pego no http request
 *  fazendo as validações de entrada e apresentando em tipo led o valor inserido pelo usuário
 *
 *  Para apresentação do display, foi utilizada a lógica de comparação em que o valor digitado é segmentado
 *  e verificado digito a digito a forma com que deve ser desenhada na tela, acendendo e apagando os
 *  leds(segmentos) conforme necessário.
 *
 *  Foi utilizada a biblioteca do retrofit 2 e converter json para a obtenção e conversão da resposta http.
 *
 *  A arquitetura utilizada foi a MVVM deixando claro as responsabilidades de cada parte do código.
 ***/
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NumberViewModel
    private lateinit var binding: ActivityMainBinding
    private var number: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this, NumberViewModelFactory(Repository())).get(
                NumberViewModel::class.java
            )

        viewModel.getNumber()
        Led.showLed("0", binding)

        viewModel.numberLiveData.observe(this) { randomNumber ->

            when {
                randomNumber.value > 300 -> onError(randomNumber)
                else -> number = randomNumber.value
            }
        }

        binding.sendButton.setOnClickListener { sendButton ->
            try {
                when (Integer.parseInt(binding.inputText.text.toString())) {
                    in 1..300 -> sendSuggestion(sendButton)
                    else -> binding.inputText.error = getString(R.string.input_error_text)
                }
            } catch (e: Exception) {
                binding.inputText.error = getString(R.string.input_exception)
            }
        }

        binding.newMatch.setOnClickListener { newMatchButton ->
            newMatch(newMatchButton)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_color, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        colorSelection()

        return super.onOptionsItemSelected(item)
    }

    /**
     * Utilizando a biblioteca ColorSheet, o usuário irá selecionar a cor dentre as definidas
     * no array de cores e conforme seleção a cor do led irá mudar.
     * */
    private fun colorSelection() {
        val colors = intArrayOf(
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.DKGRAY,
            Color.GRAY,
            Color.GREEN,
            Color.LTGRAY,
            Color.MAGENTA,
            Color.RED,
            Color.YELLOW,
            ContextCompat.getColor(this, R.color.crimson),
            ContextCompat.getColor(this, R.color.dark_crimson)
        )

        ColorSheet().colorPicker(
            colors = colors,
            listener = { color ->
                Led.colorDigit(color, binding.firstDigit.root)
                Led.colorDigit(color, binding.secondDigit.root)
                Led.colorDigit(color, binding.thirdDigit.root)
            }
        ).show(supportFragmentManager)
    }

    /**
     * Apresentação de mensagem de erro e o código do request http
     */
    private fun onError(randomNumber: RandomNumber) {
        Led.showLed(randomNumber.value.toString(), binding)
        binding.message.text = getString(R.string.erro_msg)
        binding.sendButton.isEnabled = false
        binding.newMatch.visibility = View.VISIBLE
    }

    /**
     * Verificação do número digitado onde é comparado com o número aleatório
     * vindo do request e informar se este é maior ou menor.
     */
    private fun sendSuggestion(sendButton: View) {
        val suggestion = binding.inputText.text.toString()
        if (suggestion.isNotEmpty()) {
            Led.showLed(suggestion, binding)
            val suggestionInt = Integer.parseInt(suggestion)
            when {
                suggestionInt < number -> {
                    binding.message.text = getString(R.string.greater_msg)
                }
                suggestionInt > number -> {
                    binding.message.text = getString(R.string.lesser_msg)
                }
                else -> {
                    binding.message.text = getString(R.string.nailed)
                    binding.newMatch.visibility = View.VISIBLE
                    sendButton.isEnabled = false
                }
            }
        }
    }

    /**
     * Limpa a tela, os inputs e faz um novo request para o inicio de uma nova partida
     * */
    private fun newMatch(newMatchButton: View) {
        viewModel.getNumber()
        binding.message.text = ""
        binding.inputText.text.clear()
        binding.sendButton.isEnabled = true
        Led.showLed("0", binding)
        newMatchButton.visibility = View.GONE
    }
}