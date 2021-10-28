package br.com.iesb.qualeonumero.view

import android.view.View
import br.com.iesb.qualeonumero.R
import br.com.iesb.qualeonumero.databinding.ActivityMainBinding

object Led {

    /**
    * Popula os dígitos de led de acordo com o valor informado pelo usuário.
    * O número é dividido em segmentos e são verificados o tamanho do digito e sua posição
    * para que sejam apresentad0s de forma correta.
     *
     * Também é verificado se o digito à esquerda é 0, caso seja o digito não será apresentado.
    * */
    fun showLed(suggestion: String, binding: ActivityMainBinding) {
        when (suggestion.length) {
            3 -> {
                binding.thirdDigit.root.visibility = View.VISIBLE
                binding.secondDigit.root.visibility = View.VISIBLE
                binding.firstDigit.root.visibility = View.VISIBLE

                if (suggestion[0].toString() == "0") {
                    binding.thirdDigit.root.visibility = View.GONE
                }

                if (suggestion[1].toString() == "0" && suggestion[0].toString() == "0") {
                    binding.secondDigit.root.visibility = View.GONE
                }

                drawLed(suggestion[0].toString(), binding.thirdDigit.root)
                drawLed(suggestion[1].toString(), binding.secondDigit.root)
                drawLed(suggestion[2].toString(), binding.firstDigit.root)
            }
            2 -> {
                binding.thirdDigit.root.visibility = View.GONE
                binding.secondDigit.root.visibility = View.VISIBLE
                binding.firstDigit.root.visibility = View.VISIBLE

                if (suggestion[0].toString() == "0") {
                    binding.secondDigit.root.visibility = View.GONE
                }

                drawLed(suggestion[0].toString(), binding.secondDigit.root)
                drawLed(suggestion[1].toString(), binding.firstDigit.root)
            }
            else -> {
                binding.thirdDigit.root.visibility = View.GONE
                binding.secondDigit.root.visibility = View.GONE
                binding.firstDigit.root.visibility = View.VISIBLE

                drawLed(suggestion[0].toString(), binding.firstDigit.root)
            }
        }
    }

    /**
     * Efetua o desenho do digito na tela acendendo e apagando os leds necessários para
     * representação deste.
     *
     * */
    private fun drawLed(digit: String, digitView: View) {

        val ledOn = 1f
        val ledOff = 0.05f

        val led0 = digitView.findViewById<View>(R.id.segment_0)
        val led1 = digitView.findViewById<View>(R.id.segment_1)
        val led2 = digitView.findViewById<View>(R.id.segment_2)
        val led3 = digitView.findViewById<View>(R.id.segment_3)
        val led4 = digitView.findViewById<View>(R.id.segment_4)
        val led5 = digitView.findViewById<View>(R.id.segment_5)
        val led6 = digitView.findViewById<View>(R.id.segment_6)


        when (digit) {
            "0" -> {
                led0.alpha = ledOn
                led1.alpha = ledOn
                led2.alpha = ledOn
                led3.alpha = ledOn
                led4.alpha = ledOn
                led5.alpha = ledOn
                led6.alpha = ledOff
            }
            "1" -> {
                led0.alpha = ledOff
                led1.alpha = ledOff
                led2.alpha = ledOn
                led3.alpha = ledOn
                led4.alpha = ledOff
                led5.alpha = ledOff
                led6.alpha = ledOff
            }
            "2" -> {
                led0.alpha = ledOff
                led1.alpha = ledOn
                led2.alpha = ledOn
                led3.alpha = ledOff
                led4.alpha = ledOn
                led5.alpha = ledOn
                led6.alpha = ledOn
            }
            "3" -> {
                led0.alpha = ledOff
                led1.alpha = ledOn
                led2.alpha = ledOn
                led3.alpha = ledOn
                led4.alpha = ledOn
                led5.alpha = ledOff
                led6.alpha = ledOn
            }
            "4" -> {
                led0.alpha = ledOn
                led1.alpha = ledOff
                led2.alpha = ledOn
                led3.alpha = ledOn
                led4.alpha = ledOff
                led5.alpha = ledOff
                led6.alpha = ledOn
            }
            "5" -> {
                led0.alpha = ledOn
                led1.alpha = ledOn
                led2.alpha = ledOff
                led3.alpha = ledOn
                led4.alpha = ledOn
                led5.alpha = ledOff
                led6.alpha = ledOn
            }
            "6" -> {
                led0.alpha = ledOn
                led1.alpha = ledOn
                led2.alpha = ledOff
                led3.alpha = ledOn
                led4.alpha = ledOn
                led5.alpha = ledOn
                led6.alpha = ledOn
            }
            "7" -> {
                led0.alpha = ledOff
                led1.alpha = ledOn
                led2.alpha = ledOn
                led3.alpha = ledOn
                led4.alpha = ledOff
                led5.alpha = ledOff
                led6.alpha = ledOff
            }
            "8" -> {
                led0.alpha = ledOn
                led1.alpha = ledOn
                led2.alpha = ledOn
                led3.alpha = ledOn
                led4.alpha = ledOn
                led5.alpha = ledOn
                led6.alpha = ledOn
            }
            "9" -> {
                led0.alpha = ledOn
                led1.alpha = ledOn
                led2.alpha = ledOn
                led3.alpha = ledOn
                led4.alpha = ledOn
                led5.alpha = ledOff
                led6.alpha = ledOn
            }
        }
    }
}