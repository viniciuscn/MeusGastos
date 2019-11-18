package nichele.meusgastos.SectionedRecyclerView;



import java.util.ArrayList;

import nichele.meusgastos.Classes.Transacao;

/**
 * Created by sonu on 24/07/17.
 */

public class SectionModel {
	private String headertexto;
	private String headerentradas;
	private String headersaidas;
	private String headersaldo;
	//private ArrayList<String> itemArrayList;
	private ArrayList<Transacao> lctos;

	public SectionModel(String headertexto,
	                    String headerentradas,
	                    String headersaidas,
	                    String headersaldo,
	                    ArrayList<Transacao> lctos) {
		this.headertexto = headertexto;
		this.headerentradas = headerentradas;
		this.headersaidas = headersaidas;
		this.headersaldo = headersaldo;
		this.lctos = lctos;
	}



	public String getHeaderTexto() {
		return headertexto;
	}

	public String getHeaderEntradas() {
		return headerentradas;
	}

	public String getHeaderSaidas() {
		return headersaidas;
	}

	public String getHeaderSaldo() {
		return headersaldo;
	}

	public ArrayList<Transacao> getLctos() {
		return lctos;
	}
}