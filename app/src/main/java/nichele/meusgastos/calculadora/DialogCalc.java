package nichele.meusgastos.calculadora;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import nichele.meusgastos.BancoSQLite;
import nichele.meusgastos.R;
import nichele.meusgastos.calculadora.Calculadora;
import nichele.meusgastos.calculadora.Operacao;

public class DialogCalc extends AppCompatActivity {

   private Calculadora calculadora = new Calculadora();
   private TextView visor;
   private TextView visorPrincipal;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.dialog_calc);

      this.visor = findViewById(R.id.visor);
      this.visorPrincipal = findViewById(R.id.visorPrincipal);
      atualizarVisor();

   }

   private void atualizarVisor() {
      if (this.calculadora != null) {
         visor.setText(calculadora.getValorTexto());
         visorPrincipal.setText(calculadora.getValorTextoPrincipal());
      } else {
         visor.setText("");
         visorPrincipal.setText("0");
      }
   }

   private void setCaracter(char caracter) {
      try {
         calculadora.setCaracter(caracter);
         atualizarVisor();
      } catch (Exception e) {
         e.printStackTrace();
         //Toast.makeText(getBaseContext(), "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
      }
   }



   private void setOperacao(Operacao operation) {
      calculadora.setOperacao(operation);
      atualizarVisor();
   }

   public void handleButtonUm(View view) {
      setCaracter('1');
   }

   public void handleButtonDois(View view) {
      setCaracter('2');
   }

   public void handleButtonTres(View view) {
      setCaracter('3');
   }

   public void handleButtonQuatro(View view) {
      setCaracter('4');
   }

   public void handleButtonCinco(View view) {
      setCaracter('5');
   }

   public void handleButtonSeis(View view) {
      setCaracter('6');
   }

   public void handleButtonSete(View view) {
      setCaracter('7');
   }

   public void handleButtonOito(View view) {
      setCaracter('8');
   }

   public void handleButtonNove(View view) {
      setCaracter('9');
   }

   public void handleButtonZero(View view) {
      setCaracter('0');
   }

   public void handleButtonSoma(View view) {
      setOperacao(Operacao.ADICAO);
   }

   public void handleButtonSubtrai(View view) {
      setOperacao(Operacao.SUBTRACAO);
   }

   public void handleButtonMultiplica(View view) {
      setOperacao(Operacao.MULTIPLICACAO);
   }

   public void handleButtonDivide(View view) {
      setOperacao(Operacao.DIVISAO);
   }

   public void handleButtonPorcentagem(View view) {
      setOperacao(Operacao.PORCENTAGEM);
   }

   public void handleButtonVirgula(View view) {
      setCaracter(',');
   }

   public void handleButtonResultado(View view) {
      calculadora.calcular();
      atualizarVisor();
   }

   public void handleButtonLimpar(View view) {
      calculadora = new Calculadora();
      atualizarVisor();
   }

   public void handleButtonDesfazer(View view) {
      try {
         calculadora.removerUltimoCaracter();
         atualizarVisor();
      } catch (Exception e) {
         e.printStackTrace();
         //Toast.makeText(getBaseContext(), "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
      }
   }

}
