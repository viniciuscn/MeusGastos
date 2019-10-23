package nichele.meusgastos.exemplo_recview_group;

public class HeaderModel implements iRelatorio {
	String header;

	public HeaderModel(String string) {
		header = string;
	}

	public void setheader(String header) {
		this.header = header;
	}

	@Override
	public boolean isHeader() {
		return true;
	}

	@Override
	public String getName() {
		return header;
	}
}
