package nichele.meusgastos.SectionedRecyclerView;



import java.util.ArrayList;

import nichele.meusgastos.Classes.Transacao;

/**
 * Created by sonu on 24/07/17.
 */

public class SectionModel {
	private String headertexto;
	private String receitas;
	private String despesas;
	private String saldo;

	private ArrayList<Transacao> lctos;

	public SectionModel(String headertexto,
	                    String receitas,
	                    String despesas,
	                    String saldo,
	                    ArrayList<Transacao> lctos) {
		this.headertexto = headertexto;
		this.lctos = lctos;
		this.receitas = receitas;
		this.despesas = despesas;
		this.saldo = saldo;
	}


	public String getHeaderTexto() {
		return headertexto;
	}

	public String getReceitas() {
		return receitas;
	}

	public String getDespesas() {
		return despesas;
	}

	public String getSaldo() {
		return saldo;
	}

	public ArrayList<Transacao> getLctos() {
		return lctos;
	}
}