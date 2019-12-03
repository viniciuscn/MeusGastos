package nichele.meusgastos.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyTextWatcher implements TextWatcher {
   private final WeakReference<EditText> editTextWeakReference;
   private final Locale locale;

//   public MoneyTextWatcher(EditText editText, Locale regiao) {
//      this.editTextWeakReference = new WeakReference<EditText>(editText);
//      //this.regiao = regiao != null ? regiao : Locale.getDefault();
//   }

   public MoneyTextWatcher(EditText editText) {
      this.editTextWeakReference = new WeakReference<EditText>(editText);
      //this.regiao = Locale.getDefault();
      this.locale = rotinas.regiao;
   }

   @Override
   public void beforeTextChanged(CharSequence s, int start, int count, int after) {

   }

   @Override
   public void onTextChanged(CharSequence s, int start, int before, int count) {

   }

   @Override
   public void afterTextChanged(Editable editable) {
      EditText editText = editTextWeakReference.get();
      if (editText == null) return;
      editText.removeTextChangedListener(this);

      //BigDecimal parsed = parseToBigDecimal(editable.toString(), regiao);
      BigDecimal parsed = parseToBigDecimal(editable.toString());
      String formatted = NumberFormat.getCurrencyInstance(locale).format(parsed);

      editText.setText(formatted);
      editText.setSelection(formatted.length());
      editText.addTextChangedListener(this);
   }

   private BigDecimal parseToBigDecimal(String value) {
      //String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance(regiao).getCurrency().getSymbol());
      //String cleanString = value.replaceAll(replaceable, "");
      //return new BigDecimal(cleanString ).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
      return new BigDecimal(rotinas.limpacampovalor(value) ).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
   }
}